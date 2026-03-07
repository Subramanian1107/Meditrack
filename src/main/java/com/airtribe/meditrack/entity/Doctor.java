package main.java.com.airtribe.meditrack.entity;

import main.java.com.airtribe.meditrack.util.Validator;
import main.java.com.airtribe.meditrack.entity.Person;
import main.java.com.airtribe.meditrack.entity.Specialization;
public class Doctor extends Person {

    private Specialization specialization;
    private int experience;
    private double fee;

    public Doctor(int id, String name, int age,
                  Specialization specialization,
                  int experience,
                  double fee) {
        super(id, name, age);
        Validator.requireNonNull(specialization, "Specialization cannot be null");
        Validator.validateExperience(experience);
        Validator.validateFee(fee);
        this.specialization = specialization;
        this.experience = experience;
        this.fee = fee;
    }

    public Specialization getSpecialization() { return specialization; }
    public int getExperience() { return experience; }
    public double getFee() { return fee; }

    public void setSpecialization(Specialization specialization) {
        Validator.requireNonNull(specialization, "Specialization cannot be null");
        this.specialization = specialization;
    }
    public void setExperience(int experience) {
        Validator.validateExperience(experience);
        this.experience = experience;
    }
    public void setFee(double fee) {
        Validator.validateFee(fee);
        this.fee = fee;
    }

    /** Copy constructor for deep cloning. */
    public Doctor(Doctor other) {
        super(other.getId(), other.getName(), other.age);
        this.specialization = other.specialization;
        this.experience = other.experience;
        this.fee = other.fee;
    }

    @Override
    public void display() {
        System.out.println("Doctor: " + name + " | " + specialization);
    }

    @Override
    public String toString() {
        return "Doctor{id=" + super.getId() + ", name='" + name + "', age=" + age +
                ", specialization=" + specialization + ", experience=" + experience +
                ", fee=" + fee + "}";
    }

    public String getName() {
        return super.getName();
    }
}