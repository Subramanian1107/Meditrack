package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.util.Validator;

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
        return "Patient{id=" + id + ", name='" + name + "', age=" + age + "}";
    }

    @Override
    public Patient clone() {
        return new Patient(this.id, this.name, this.age, this.medicalHistory);
    }
}