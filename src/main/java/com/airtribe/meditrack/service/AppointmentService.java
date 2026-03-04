package main.java.com.airtribe.meditrack.service;


import main.java.com.airtribe.meditrack.entity.*;
import main.java.com.airtribe.meditrack.exception.*;
import main.java.com.airtribe.meditrack.util.*;

import java.time.LocalDateTime;
import java.util.List;

public class AppointmentService {

    private DataStore<Appointment> appointmentStore = new DataStore<>();
    private IdGenerator idGen = IdGenerator.getInstance();

    public Appointment bookAppointment(Doctor d, Patient p) {
        int id = idGen.generateId();
        Appointment a = new Appointment(id, d, p, LocalDateTime.now());
        appointmentStore.add(id, a);
        return a;
    }

    public void cancelAppointment(int id)
            throws AppointmentNotFoundException {

        Appointment a = appointmentStore.get(id);
        if (a == null)
            throw new AppointmentNotFoundException("Appointment not found");

        a.cancel();
    }

    public List<Appointment> getAllAppointments() {
        return appointmentStore.getAll();
    }
}