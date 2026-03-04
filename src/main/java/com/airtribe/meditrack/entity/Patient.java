package main.java.com.airtribe.meditrack.entity;

public class Patient extends Person implements Cloneable {

    private String medicalHistory;

    public Patient(int id, String name, int age, String medicalHistory) {
        super(id, name, age);
        this.medicalHistory = medicalHistory;
    }

    public String getMedicalHistory() { return medicalHistory; }

    @Override
    public void display() {
        System.out.println("Patient: " + name);
    }

    @Override
    public Patient clone() {
        return new Patient(this.id, this.name, this.age, this.medicalHistory);
    }
}