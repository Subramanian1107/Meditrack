package main.java.com.airtribe.meditrack.service;

import main.java.com.airtribe.meditrack.entity.Patient;
import main.java.com.airtribe.meditrack.util.DataStore;
import main.java.com.airtribe.meditrack.util.IdGenerator;

import java.util.List;

public class PatientService {

    private DataStore<Patient> patientStore = new DataStore<>();
    private IdGenerator idGen = IdGenerator.getInstance();

    public Patient addPatient(String name, int age, String history) {

        int id = idGen.generateId();
        Patient p = new Patient(id, name, age, history);
        patientStore.add(id, p);
        return p;
    }

    public List<Patient> getAllPatients() {
        return patientStore.getAll();
    }
}