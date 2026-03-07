package main.java.com.airtribe.meditrack.service;

import main.java.com.airtribe.meditrack.entity.Doctor;
import main.java.com.airtribe.meditrack.entity.Specialization;
import main.java.com.airtribe.meditrack.interfaces.Searchable;
import main.java.com.airtribe.meditrack.util.CSVUtil;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.IdGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DoctorService implements Searchable<Doctor> {

    private final DataStore<Doctor> doctorStore = new DataStore<>();
    private final IdGenerator idGen = IdGenerator.getInstance();

    public Doctor addDoctor(String name, int age, Specialization spec, int exp, double fee) {
        int id = idGen.generateId();
        Doctor d = new Doctor(id, name, age, spec, exp, fee);
        doctorStore.add(id, d);
        return d;
    }

    public Doctor getDoctor(int id) {
        return doctorStore.get(id);
    }

    public void updateDoctor(int id, String name, int age, Specialization spec, int exp, double fee) {
        Doctor d = doctorStore.get(id);
        if (d == null) return;
        d.setName(name);
        d.setAge(age);
        d.setSpecialization(spec);
        d.setExperience(exp);
        d.setFee(fee);
    }

    public Doctor removeDoctor(int id) {
        Doctor d = doctorStore.get(id);
        doctorStore.remove(id);
        return d;
    }

    public List<Doctor> getAllDoctors() {
        return doctorStore.getAll();
    }

    @Override
    public Doctor searchById(int id) {
        return doctorStore.get(id);
    }

    public List<Doctor> searchByName(String name) {
        if (name == null || name.trim().isEmpty()) return new ArrayList<>();
        String lower = name.trim().toLowerCase();
        return doctorStore.getAll().stream()
                .filter(d -> d.getName().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }

    public List<Doctor> searchBySpecialization(Specialization spec) {
        if (spec == null) return new ArrayList<>();
        return doctorStore.getAll().stream()
                .filter(d -> d.getSpecialization() == spec)
                .collect(Collectors.toList());
    }

    public double getAverageFee() {
        return doctorStore.getAll().stream()
                .mapToDouble(Doctor::getFee)
                .average()
                .orElse(0.0);
    }

    public static final Comparator<Doctor> BY_NAME = Comparator.comparing(Doctor::getName);
    public static final Comparator<Doctor> BY_FEE = Comparator.comparingDouble(Doctor::getFee);

    public void loadFromCsv(String path) throws IOException {
        for (Doctor d : CSVUtil.loadDoctors(path)) {
            doctorStore.add(d.getId(), d);
            idGen.ensureMinimumId(d.getId());
        }
    }

    public void saveToCsv(String path) throws IOException {
        CSVUtil.saveDoctors(doctorStore.getAll(), path);
    }
}
