package main.java.com.airtribe.meditrack.entity;

import java.time.LocalDateTime;

import main.java.com.airtribe.meditrack.entity.Doctor;
import main.java.com.airtribe.meditrack.entity.Patient;
import main.java.com.airtribe.meditrack.entity.AppointmentStatus;

public class Appointment implements Cloneable {

    private int id;
    private Doctor doctor;
    private Patient patient;
    private LocalDateTime dateTime;
    private AppointmentStatus status;

    public Appointment(int id, Doctor doctor,
                       Patient patient,
                       LocalDateTime dateTime) {
        this(id, doctor, patient, dateTime, AppointmentStatus.PENDING);
    }

    public Appointment(int id, Doctor doctor,
                       Patient patient,
                       LocalDateTime dateTime,
                       AppointmentStatus status) {
        this.id = id;
        this.doctor = doctor;
        this.patient = patient;
        this.dateTime = dateTime;
        this.status = status;
    }

    public void cancel() {
        this.status = AppointmentStatus.CANCELLED;
    }

    public void confirm() {
        this.status = AppointmentStatus.CONFIRMED;
    }

    public int getId() { return id; }

    public Doctor getDoctor() { return doctor; }

    public Patient getPatient() { return patient; }

    public LocalDateTime getDateTime() { return dateTime; }

    public AppointmentStatus getStatus() { return status; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Appointment)) return false;
        Appointment that = (Appointment) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return "Appointment{id=" + id +
                ", doctor=" + doctor.getName() +
                ", patient=" + patient.getName() +
                ", dateTime=" + dateTime +
                ", status=" + status + "}";
    }

    @Override
    public Appointment clone() {
        Doctor doctorCopy = new Doctor(doctor);   // requires copy constructor
        Patient patientCopy = patient.clone();    // requires clone() in Patient
        return new Appointment(id, doctorCopy, patientCopy, dateTime, status);
    }
}