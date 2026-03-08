package main.java.com.airtribe.meditrack;

import main.java.com.airtribe.meditrack.constants.Constants;
import main.java.com.airtribe.meditrack.entity.*;
import main.java.com.airtribe.meditrack.exception.AppointmentNotFoundException;
import main.java.com.airtribe.meditrack.exception.InvalidDataException;
import main.java.com.airtribe.meditrack.observer.ConsoleRemainderObserver;
import main.java.com.airtribe.meditrack.observer.EmailRemainderObserver;
import main.java.com.airtribe.meditrack.observer.WhatsAppRemainderObserver;
import main.java.com.airtribe.meditrack.service.AppointmentService;
import main.java.com.airtribe.meditrack.service.DoctorService;
import main.java.com.airtribe.meditrack.service.PatientService;
import main.java.com.airtribe.meditrack.util.AIHelper;
import main.java.com.airtribe.meditrack.util.BillFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final DoctorService doctorService = new DoctorService();
    private static final PatientService patientService = new PatientService();
    private static final AppointmentService appointmentService = new AppointmentService();

    public static void main(String[] args) {
        if (args.length > 0 && "--loadData".equals(args[0])) {
            loadPersistedData();
        }
        appointmentService.addObserver(new ConsoleRemainderObserver());
        appointmentService.addObserver(new EmailRemainderObserver());
        appointmentService.addObserver(new WhatsAppRemainderObserver());
        runMenu();
    }

    private static void loadPersistedData() {
        try {
            doctorService.loadFromCsv(Constants.DOCTOR_FILE);
            patientService.loadFromCsv(Constants.PATIENT_FILE);
            appointmentService.loadFromCsv(Constants.APPOINTMENT_FILE, doctorService, patientService);
            System.out.println("Data loaded from CSV files.");
        } catch (IOException e) {
            System.err.println("Failed to load data: " + e.getMessage());
        }
    }

    private static void saveData() {
        try {
            doctorService.saveToCsv(Constants.DOCTOR_FILE);
            patientService.saveToCsv(Constants.PATIENT_FILE);
            appointmentService.saveToCsv(Constants.APPOINTMENT_FILE);
            System.out.println("Data saved.");
        } catch (IOException e) {
            System.err.println("Failed to save: " + e.getMessage());
        }
    }

    private static void runMenu() {
        while (true) {
            printMainMenu();
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": doctorMenu(); break;
                case "2": patientMenu(); break;
                case "3": appointmentMenu(); break;
                case "4": billingMenu(); break;
                case "5": searchMenu(); break;
                case "6": analyticsMenu(); break;
                case "7": saveData(); break;
                case "0":
                    System.out.println("Exiting.");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("\n===== MediTrack =====");
        System.out.println("1. Doctors");
        System.out.println("2. Patients");
        System.out.println("3. Appointments");
        System.out.println("4. Billing");
        System.out.println("5. Search");
        System.out.println("6. Analytics");
        System.out.println("7. Save data");
        System.out.println("0. Exit");
        System.out.print("Choice: ");
    }

    private static void doctorMenu() {
        while (true) {
            System.out.println("\n--- Doctors ---");
            System.out.println("1. Add  2. List  3. Update  4. Delete  0. Back");
            String c = sc.nextLine().trim();
            switch (c) {
                case "1":
                    addDoctor();
                    break;
                case "2":
                    doctorService.printAll(doctorService.getAllDoctors());
                    break;
                case "3":
                    updateDoctor();
                    break;
                case "4":
                    deleteDoctor();
                    break;
                case "0":
                    return;
            }
        }
    }

    private static void addDoctor() {
        System.out.print("Name: ");
        String name = sc.nextLine().trim();
        System.out.print("Age: ");
        int age = readInt();
        System.out.print("Specialization (CARDIOLOGY,DERMATOLOGY,ORTHOPEDIC,GENERAL): ");
        Specialization spec = readSpec();
        System.out.print("Experience (years): ");
        int exp = readInt();
        System.out.print("Fee: ");
        double fee = readDouble();
        try {
            Doctor d = doctorService.addDoctor(name, age, spec, exp, fee);
            System.out.println("Added: " + d);
        } catch (InvalidDataException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void updateDoctor() {
        System.out.print("Doctor ID: ");
        int id = readInt();
        Doctor d = doctorService.getDoctor(id);
        if (d == null) { System.out.println("Not found."); return; }
        System.out.print("Name [" + d.getName() + "]: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) name = d.getName();
        System.out.print("Age [" + d.getAge() + "]: ");
        int age = readInt();
        if (age <= 0) age = d.getAge();
        System.out.print("Specialization [" + d.getSpecialization() + "]: ");
        Specialization spec = readSpec();
        if (spec == null) spec = d.getSpecialization();
        System.out.print("Experience [" + d.getExperience() + "]: ");
        int exp = readInt();
        if (exp < 0) exp = d.getExperience();
        System.out.print("Fee [" + d.getFee() + "]: ");
        double fee = readDouble();
        if (fee < 0) fee = d.getFee();
        doctorService.updateDoctor(id, name, age, spec, exp, fee);
        System.out.println("Updated.");
    }

    private static void deleteDoctor() {
        System.out.print("Doctor ID: ");
        int id = readInt();
        Doctor d = doctorService.removeDoctor(id);
        System.out.println(d != null ? "Removed: " + d.getName() : "Not found.");
    }

    private static void patientMenu() {
        while (true) {
            System.out.println("\n--- Patients ---");
            System.out.println("1. Add  2. List  3. Update  4. Delete  0. Back");
            String c = sc.nextLine().trim();
            switch (c) {
                case "1": addPatient(); break;
                case "2": patientService.printAll(patientService.getAllPatients()); break;
                case "3": updatePatient(); break;
                case "4": deletePatient(); break;
                case "0": return;
            }
        }
    }

    private static void addPatient() {
        System.out.print("Name: ");
        String name = sc.nextLine().trim();
        System.out.print("Age: ");
        int age = readInt();
        System.out.print("Medical history: ");
        String hist = sc.nextLine().trim();
        try {
            Patient p = patientService.addPatient(name, age, hist);
            System.out.println("Added: " + p);
        } catch (InvalidDataException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void updatePatient() {
        System.out.print("Patient ID: ");
        int id = readInt();
        Patient p = patientService.getPatient(id);
        if (p == null) { System.out.println("Not found."); return; }
        System.out.print("Name [" + p.getName() + "]: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) name = p.getName();
        System.out.print("Age [" + p.getAge() + "]: ");
        int age = readInt();
        if (age <= 0) age = p.getAge();
        System.out.print("Medical history: ");
        String hist = sc.nextLine().trim();
        patientService.updatePatient(id, name, age, hist.isEmpty() ? p.getMedicalHistory() : hist);
        System.out.println("Updated.");
    }

    private static void deletePatient() {
        System.out.print("Patient ID: ");
        int id = readInt();
        Patient p = patientService.removePatient(id);
        System.out.println(p != null ? "Removed: " + p.getName() : "Not found.");
    }

    private static void appointmentMenu() {
        while (true) {
            System.out.println("\n--- Appointments ---");
            System.out.println("1. Create  2. View all  3. Cancel  4. Confirm  0. Back");
            String c = sc.nextLine().trim();
            switch (c) {
                case "1": createAppointment(); break;
                case "2":
                    appointmentService.getAllAppointments().forEach(System.out::println);
                    break;
                case "3": cancelAppointment(); break;
                case "4": confirmAppointment(); break;
                case "0": return;
            }
        }
    }

    private static void createAppointment() {
        System.out.print("Enter symptom: ");
        String symptom = sc.nextLine();

        // AI recommends specialization
        Specialization spec = AIHelper.recommendSpecialization(symptom);
        System.out.println("AI recommends specialization: " + spec);

        // AI recommends doctors
        var doctors = AIHelper.recommendDoctors(symptom, doctorService.getAllDoctors());

        if (doctors.isEmpty()) {
            System.out.println("No doctors found for this symptom.");
            return;
        }

        System.out.println("Recommended Doctors:");
        doctors.forEach(System.out::println);

        System.out.print("Doctor ID: ");
        Doctor d = doctorService.getDoctor(readInt());
        System.out.print("Patient ID: ");
        Patient p = patientService.getPatient(readInt());
        if (d == null || p == null) {
            System.out.println("Doctor or patient not found.");
            return;
        }
        // AI slot suggestion
        var slots = AIHelper.suggestSlots(d, appointmentService.getAllAppointments());

        if (!slots.isEmpty()) {
            System.out.println("AI Suggested Slots:");
            slots.forEach(s -> System.out.println("  " + s));
        }
        Appointment a = appointmentService.bookAppointment(d, p, LocalDateTime.now());
        System.out.println("Created: " + a);
    }

    private static void cancelAppointment() {
        System.out.print("Appointment ID: ");
        int id = readInt();
        try {
            appointmentService.cancelAppointment(id);
            System.out.println("Cancelled.");
        } catch (AppointmentNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void confirmAppointment() {
        System.out.print("Appointment ID: ");
        int id = readInt();
        try {
            appointmentService.confirmAppointment(id);
            System.out.println("Confirmed.");
        } catch (AppointmentNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void billingMenu() {
        System.out.print("Appointment ID: ");
        int id = readInt();
        System.out.print("Bill type (1=Consultation 2=Procedure): ");
        String t = sc.nextLine().trim();
        BillFactory.BillType type = "2".equals(t) ? BillFactory.BillType.PROCEDURE : BillFactory.BillType.CONSULTATION;
        try {
            BillSummary bs = appointmentService.generateBill(id, type);
            System.out.println("Bill generated: " + bs);
        } catch (AppointmentNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void searchMenu() {
        System.out.println("\n--- Search ---");
        System.out.println("1. Doctor by ID  2. Doctor by name  3. Doctor by specialization");
        System.out.println("4. Patient by ID  5. Patient by name  6. Patient by age");
        String c = sc.nextLine().trim();
        switch (c) {
            case "1":
                System.out.print("Doctor ID: ");
                Doctor d = doctorService.searchById(readInt());
                System.out.println(d != null ? d : "Not found.");
                break;
            case "2":
                System.out.print("Name: ");
                doctorService.searchByName(sc.nextLine()).forEach(System.out::println);
                break;
            case "3":
                System.out.print("Specialization: ");
                doctorService.searchBySpecialization(readSpec()).forEach(System.out::println);
                break;
            case "4":
                System.out.print("Patient ID: ");
                Patient p = patientService.searchById(readInt());
                System.out.println(p != null ? p : "Not found.");
                break;
            case "5":
                System.out.print("Name: ");
                patientService.searchPatient(sc.nextLine()).forEach(System.out::println);
                break;
            case "6":
                System.out.print("Age: ");
                patientService.searchPatientByAge(readInt()).forEach(System.out::println);
                break;
        }
    }

    private static void analyticsMenu() {
        System.out.println("Average doctor fee: " + doctorService.getAverageFee());
        Map<String, Long> byDoctor = appointmentService.getAppointmentsPerDoctor();
        System.out.println("Appointments per doctor:");
        byDoctor.forEach((name, count) -> System.out.println("  " + name + ": " + count));
        // Get data for AI methods
        var doctors = doctorService.getAllDoctors();
        var appointments = appointmentService.getAllAppointments();

        // ---------------- AI FEATURE 1 ----------------
        Doctor leastBusy = AIHelper.recommendLeastBusyDoctor(doctors, appointments);

        if (leastBusy != null) {
            System.out.println("\nAI Recommendation (Least Busy Doctor):");
            System.out.println(leastBusy);
        }

        // ---------------- AI FEATURE 2 ----------------
        System.out.print("\nEnter your consultation budget: ");
        double budget = readDouble();

        var affordableDoctors =
                AIHelper.recommendAffordableDoctors(doctors, budget);

        System.out.println("Doctors within your budget:");

        if (affordableDoctors.isEmpty()) {
            System.out.println("No doctors available within this budget.");
        } else {
            affordableDoctors.forEach(System.out::println);
        }

        // ---------------- AI FEATURE 3 ----------------
        int peakHour = AIHelper.detectPeakHour(appointments);

        if (peakHour != -1) {
            System.out.println("\nAI Insight: Clinic peak appointment hour = "
                    + peakHour + ":00");
        } else {
            System.out.println("\nNo appointment data available.");
        }
    }

    private static Specialization readSpec() {
        String s = sc.nextLine().trim().toUpperCase();
        if (s.isEmpty()) return null;
        try {
            return Specialization.valueOf(s);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private static int readInt() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static double readDouble() {
        try {
            return Double.parseDouble(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
