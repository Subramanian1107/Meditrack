package main.java.com.airtribe.meditrack.service;

import main.java.com.airtribe.meditrack.entity.Doctor;
import main.java.com.airtribe.meditrack.entity.Specialization;
import main.java.com.airtribe.meditrack.util.DataStore;
import main.java.com.airtribe.meditrack.util.IdGenerator;

import java.util.List;

public class DoctorService {

    private DataStore<Doctor> doctorStore = new DataStore<>();
    private IdGenerator idGen = IdGenerator.getInstance();

    public Doctor addDoctor(String name, int age,
                            Specialization spec,
                            int exp,
                            double fee) {

        int id = idGen.generateId();
        Doctor d = new Doctor(id, name, age, spec, exp, fee);
        doctorStore.add(id, d);
        return d;
    }

    public List<Doctor> getAllDoctors() {
        return doctorStore.getAll();
    }
}