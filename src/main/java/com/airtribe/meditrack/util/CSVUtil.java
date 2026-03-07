package main.java.com.airtribe.meditrack.util;

import main.java.com.airtribe.meditrack.entity.*;
import com.airtribe.meditrack.util.DateUtil;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** CSV I/O using String.split(",") and try-with-resources. */
public final class CSVUtil {

    private static final String DELIM = ",";

    private CSVUtil() {}

    // ----- Doctors -----
    public static void saveDoctors(List<Doctor> doctors, String path) throws IOException {
        try (BufferedWriter w = Files.newBufferedWriter(Path.of(path))) {
            for (Doctor d : doctors) {
                String line = d.getId() + DELIM + escape(d.getName()) + DELIM + d.getAge() + DELIM
                        + d.getSpecialization().name() + DELIM + d.getExperience() + DELIM + d.getFee();
                w.write(line);
                w.newLine();
            }
        }
    }

    public static List<Doctor> loadDoctors(String path) throws IOException {
        List<Doctor> list = new ArrayList<>();
        if (!Files.exists(Path.of(path))) return list;
        try (BufferedReader r = Files.newBufferedReader(Path.of(path))) {
            String line;
            while ((line = r.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length < 6) continue;
                int id = parseInt(parts[0]);
                String name = unescape(parts[1]);
                int age = parseInt(parts[2]);
                Specialization spec = Specialization.valueOf(parts[3].trim());
                int exp = parseInt(parts[4]);
                double fee = parseDouble(parts[5]);
                list.add(new Doctor(id, name, age, spec, exp, fee));
            }
        }
        return list;
    }

    // ----- Patients -----
    public static void savePatients(List<Patient> patients, String path) throws IOException {
        try (BufferedWriter w = Files.newBufferedWriter(Path.of(path))) {
            for (Patient p : patients) {
                String line = p.getId() + DELIM + escape(p.getName()) + DELIM + p.getAge() + DELIM + escape(p.getMedicalHistory());
                w.write(line);
                w.newLine();
            }
        }
    }

    public static List<Patient> loadPatients(String path) throws IOException {
        List<Patient> list = new ArrayList<>();
        if (!Files.exists(Path.of(path))) return list;
        try (BufferedReader r = Files.newBufferedReader(Path.of(path))) {
            String line;
            while ((line = r.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",", 4);
                if (parts.length < 4) continue;
                int id = parseInt(parts[0]);
                String name = unescape(parts[1]);
                int age = parseInt(parts[2]);
                String history = unescape(parts[3]);
                list.add(new Patient(id, name, age, history));
            }
        }
        return list;
    }

    // ----- Appointments (requires doctors and patients loaded first) -----
    public static void saveAppointments(List<Appointment> appointments, String path) throws IOException {
        try (BufferedWriter w = Files.newBufferedWriter(Path.of(path))) {
            for (Appointment a : appointments) {
                String line = a.getId() + DELIM + a.getDoctor().getId() + DELIM + a.getPatient().getId()
                        + DELIM + DateUtil.format(a.getDateTime()) + DELIM + a.getStatus();
                w.write(line);
                w.newLine();
            }
        }
    }

    public static List<Appointment> loadAppointments(String path,
                                                      java.util.Map<Integer, Doctor> doctorMap,
                                                      java.util.Map<Integer, Patient> patientMap) throws IOException {
        List<Appointment> list = new ArrayList<>();
        if (!Files.exists(Path.of(path))) return list;
        try (BufferedReader r = Files.newBufferedReader(Path.of(path))) {
            String line;
            while ((line = r.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length < 5) continue;
                int id = parseInt(parts[0]);
                int doctorId = parseInt(parts[1]);
                int patientId = parseInt(parts[2]);
                LocalDateTime dt = DateUtil.parse(parts[3]);
                AppointmentStatus status = AppointmentStatus.valueOf(parts[4].trim());
                Doctor doc = doctorMap.get(doctorId);
                Patient pat = patientMap.get(patientId);
                if (doc != null && pat != null)
                    list.add(new Appointment(id, doc, pat, dt, status));
            }
        }
        return list;
    }

    private static final String COMMA_PLACEHOLDER = "##COMMA##";

    private static String escape(String s) {
        if (s == null) return "";
        return s.replace(",", COMMA_PLACEHOLDER).replace("\n", " ");
    }

    private static String unescape(String s) {
        if (s == null) return "";
        return s.replace(COMMA_PLACEHOLDER, ",");
    }

    private static int parseInt(String s) {
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static double parseDouble(String s) {
        try {
            return Double.parseDouble(s.trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}
