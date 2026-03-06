package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.*;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.interfaces.Payable;
import com.airtribe.meditrack.observer.AppointmentObserver;
import com.airtribe.meditrack.util.BillFactory;
import com.airtribe.meditrack.util.CSVUtil;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.IdGenerator;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AppointmentService {

    private final DataStore<Appointment> appointmentStore = new DataStore<>();
    private final List<AppointmentObserver> observers = new ArrayList<>();
    private final IdGenerator idGen = IdGenerator.getInstance();

    public Appointment bookAppointment(Doctor d, Patient p) {
        return bookAppointment(d, p, LocalDateTime.now());
    }

    public Appointment bookAppointment(Doctor d, Patient p, LocalDateTime dateTime) {
        int id = idGen.generateId();
        Appointment a = new Appointment(id, d, p, dateTime);
        appointmentStore.add(id, a);
        observers.forEach(o -> o.onAppointmentCreated(a));
        return a;
    }

    public void addObserver(AppointmentObserver observer) {
        observers.add(observer);
    }

    public Appointment getAppointment(int id) {
        return appointmentStore.get(id);
    }

    public void cancelAppointment(int id) throws AppointmentNotFoundException {
        Appointment a = appointmentStore.get(id);
        if (a == null)
            throw new AppointmentNotFoundException("Appointment #" + id + " not found");
        a.cancel();
        observers.forEach(o -> o.onAppointmentCancelled(a));
    }

    public void confirmAppointment(int id) throws AppointmentNotFoundException {
        Appointment a = appointmentStore.get(id);
        if (a == null)
            throw new AppointmentNotFoundException("Appointment #" + id + " not found");
        a.confirm();
    }

    public List<Appointment> getAllAppointments() {
        return appointmentStore.getAll();
    }

    public BillSummary generateBill(int appointmentId) throws AppointmentNotFoundException {
        Appointment a = appointmentStore.get(appointmentId);
        if (a == null)
            throw new AppointmentNotFoundException("Appointment #" + appointmentId + " not found");
        double baseAmount = a.getDoctor().getFee();
        Payable bill = BillFactory.createBill(BillFactory.BillType.CONSULTATION, baseAmount, a);
        return bill.generateBill();
    }

    public BillSummary generateBill(int appointmentId, BillFactory.BillType type) throws AppointmentNotFoundException {
        Appointment a = appointmentStore.get(appointmentId);
        if (a == null)
            throw new AppointmentNotFoundException("Appointment #" + appointmentId + " not found");
        double baseAmount = a.getDoctor().getFee();
        Payable bill = BillFactory.createBill(type, baseAmount, a);
        return bill.generateBill();
    }

    /** Analytics: appointments count per doctor (Streams). */
    public Map<String, Long> getAppointmentsPerDoctor() {
        return appointmentStore.getAll().stream()
                .filter(a -> a.getStatus() != AppointmentStatus.CANCELLED)
                .collect(Collectors.groupingBy(
                        a -> a.getDoctor().getName(),
                        Collectors.counting()
                ));
    }

    public void loadFromCsv(String path, DoctorService doctorService, PatientService patientService) throws IOException {
        Map<Integer, Doctor> doctorMap = new HashMap<>();
        for (Doctor d : doctorService.getAllDoctors()) doctorMap.put(d.getId(), d);
        Map<Integer, Patient> patientMap = new HashMap<>();
        for (Patient p : patientService.getAllPatients()) patientMap.put(p.getId(), p);
        int maxId = 0;
        for (Appointment a : CSVUtil.loadAppointments(path, doctorMap, patientMap)) {
            appointmentStore.add(a.getId(), a);
            maxId = Math.max(maxId, a.getId());
        }
        idGen.ensureMinimumId(maxId);
    }

    public void saveToCsv(String path) throws IOException {
        CSVUtil.saveAppointments(appointmentStore.getAll(), path);
    }
}
