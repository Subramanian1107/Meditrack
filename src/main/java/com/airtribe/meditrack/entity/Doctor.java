package main.java.com.airtribe.meditrack.entity;

public class Doctor extends Person {

    private Specialization specialization;
    private int experience;
    private double fee;

    public Doctor(int id, String name, int age,
                  Specialization specialization,
                  int experience,
                  double fee) {
        super(id, name, age);
        this.specialization = specialization;
        this.experience = experience;
        this.fee = fee;
    }

    public Specialization getSpecialization() { return specialization; }
    public int getExperience() { return experience; }
    public double getFee() { return fee; }

    @Override
    public void display() {
        System.out.println("Doctor: " + name + " | " + specialization);
    }
}