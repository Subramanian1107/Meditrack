package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.interfaces.Searchable;
import com.airtribe.meditrack.util.CSVUtil;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.IdGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PatientService implements Searchable<Patient> {

    private final DataStore<Patient> patientStore = new DataStore<>();
    private final IdGenerator idGen = IdGenerator.getInstance();

    public Patient addPatient(String name, int age, String history) {
        int id = idGen.generateId();
        Patient p = new Patient(id, name, age, history);
        patientStore.add(id, p);
        return p;
    }

    public Patient getPatient(int id) {
        return patientStore.get(id);
    }

    public void updatePatient(int id, String name, int age, String history) {
        Patient p = patientStore.get(id);
        if (p == null) return;
        p.setName(name);
        p.setAge(age);
        p.setMedicalHistory(history);
    }

    public Patient removePatient(int id) {
        Patient p = patientStore.get(id);
        patientStore.remove(id);
        return p;
    }

    public List<Patient> getAllPatients() {
        return patientStore.getAll();
    }

    @Override
    public Patient searchById(int id) {
        return patientStore.get(id);
    }

    /** Overloaded search: by id. */
    public Patient searchPatient(int id) {
        return searchById(id);
    }

    /** Overloaded search: by name. */
    public List<Patient> searchPatient(String name) {
        if (name == null || name.trim().isEmpty()) return new ArrayList<>();
        String lower = name.trim().toLowerCase();
        return patientStore.getAll().stream()
                .filter(p -> p.getName().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }

    /** Overloaded search: by age (separate method name due to int/int overload conflict). */
    public List<Patient> searchPatientByAge(int age) {
        return patientStore.getAll().stream()
                .filter(p -> p.getAge() == age)
                .collect(Collectors.toList());
    }

    public static final Comparator<Patient> BY_NAME = Comparator.comparing(Patient::getName);
    public static final Comparator<Patient> BY_AGE = Comparator.comparingInt(Patient::getAge);

    public void loadFromCsv(String path) throws IOException {
        for (Patient p : CSVUtil.loadPatients(path)) {
            patientStore.add(p.getId(), p);
            idGen.ensureMinimumId(p.getId());
        }
    }

    public void saveToCsv(String path) throws IOException {
        CSVUtil.savePatients(patientStore.getAll(), path);
    }
}
