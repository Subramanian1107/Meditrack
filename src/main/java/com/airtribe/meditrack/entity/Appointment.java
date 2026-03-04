package main.java.com.airtribe.meditrack.entity;

import java.time.LocalDateTime;

public class Appointment implements Cloneable {

    private int id;
    private Doctor doctor;
    private Patient patient;
    private LocalDateTime dateTime;
    private AppointmentStatus status;

    public Appointment(int id, Doctor doctor,
                       Patient patient,
                       LocalDateTime dateTime) {
        this.id = id;
        this.doctor = doctor;
        this.patient = patient;
        this.dateTime = dateTime;
        this.status = AppointmentStatus.CONFIRMED;
    }

    public void cancel() {
        this.status = AppointmentStatus.CANCELLED;
    }

    public Doctor getDoctor() { return doctor; }
    public Patient getPatient() { return patient; }
    public AppointmentStatus getStatus() { return status; }

    @Override
    public Appointment clone() {
        return new Appointment(id, doctor, patient.clone(), dateTime);
    }
}