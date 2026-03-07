package main.java.com.airtribe.meditrack.entity;

import main.java.com.airtribe.meditrack.util.Validator;
import main.java.com.airtribe.meditrack.entity.Person;
public class Patient extends Person implements Cloneable {

    private String medicalHistory;

    public Patient(int id, String name, int age, String medicalHistory) {
        super(id, name, age);
        this.medicalHistory = medicalHistory == null ? "" : medicalHistory;
    }

    public String getMedicalHistory() { return medicalHistory; }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory == null ? "" : medicalHistory;
    }

    @Override
    public void display() {
        System.out.println("Patient: " + name);
    }

    @Override
    public String toString() {
        return "Patient{id=" + super.getId() + ", name='" + name + "', age=" + age + "}";
    }

    @Override
    public Patient clone() {
        return new Patient(super.getId(), this.name, this.age, this.medicalHistory);
    }

    public String getName() {
        return super.getName();
    }
}