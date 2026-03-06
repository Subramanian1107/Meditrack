package com.airtribe.meditrack.test;

import com.airtribe.meditrack.entity.*;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.service.AppointmentService;
import com.airtribe.meditrack.service.DoctorService;
import com.airtribe.meditrack.service.PatientService;

import java.util.Comparator;
import java.util.List;

/** Manual tests for core functionality. */
public class TestRunner {

    public static void main(String[] args) {
        System.out.println("=== MediTrack Manual Tests ===\n");

        testPersonValidation();
        testInheritanceAndPolymorphism();
        testCloning();
        testBillAndBillSummary();
        testSearchOverloads();
        testComparator();
        testStreams();

        System.out.println("\n=== All manual tests completed ===");
    }

    static void testPersonValidation() {
        System.out.println("1. Person validation (Validator)");
        try {
            new Patient(1, "", 25, "none");
            System.out.println("  FAIL: empty name should throw");
        } catch (Exception e) {
            System.out.println("  OK: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }
        try {
            new Patient(1, "Alice", 200, "none");
            System.out.println("  FAIL: invalid age should throw");
        } catch (Exception e) {
            System.out.println("  OK: " + e.getClass().getSimpleName());
        }
    }

    static void testInheritanceAndPolymorphism() {
        System.out.println("\n2. Inheritance & polymorphism");
        Person p = new Patient(1, "Alice", 30, "none");
        p.display();
        p = new Doctor(2, "Bob", 45, Specialization.CARDIOLOGY, 10, 150.0);
        p.display();
        System.out.println("  OK: dynamic dispatch via display()");
    }

    static void testCloning() {
        System.out.println("\n3. Deep vs shallow copy");
        Patient orig = new Patient(1, "Alice", 30, "asthma");
        Patient copy = orig.clone();
        System.out.println("  Patient clone: " + (copy != orig && copy.getName().equals(orig.getName())));
        Doctor d = new Doctor(2, "Bob", 45, Specialization.GENERAL, 5, 100);
        Appointment a = new Appointment(1, d, orig, java.time.LocalDateTime.now());
        Appointment aCopy = a.clone();
        System.out.println("  Appointment deep copy (independent doctor): " + (aCopy.getDoctor() != a.getDoctor()));
    }

    static void testBillAndBillSummary() {
        System.out.println("\n4. Bill, BillSummary, Payable");
        Bill b = new Bill(100.0);
        double amt = b.calculateAmount();
        System.out.println("  Bill 100 + tax: " + amt);
        var summary = b.generateBill();
        System.out.println("  BillSummary immutable: " + summary.getTotalAmount());
    }

    static void testSearchOverloads() {
        System.out.println("\n5. Search overloads (searchPatient by id/name/age)");
        PatientService ps = new PatientService();
        ps.addPatient("Alice", 25, "none");
        ps.addPatient("Bob", 25, "none");
        ps.addPatient("Alice Wong", 30, "none");
        Patient byId = ps.searchPatient(1);
        List<Patient> byName = ps.searchPatient("Alice");
        List<Patient> byAge = ps.searchPatientByAge(25);
        System.out.println("  searchPatient(1): " + (byId != null));
        System.out.println("  searchPatient(\"Alice\"): " + byName.size());
        System.out.println("  searchPatientByAge(25): " + byAge.size());
    }

    static void testComparator() {
        System.out.println("\n6. Comparator");
        DoctorService ds = new DoctorService();
        ds.addDoctor("Charlie", 50, Specialization.CARDIOLOGY, 20, 200);
        ds.addDoctor("Alice", 40, Specialization.GENERAL, 10, 100);
        var sorted = ds.getAllDoctors().stream().sorted(DoctorService.BY_NAME).toList();
        System.out.println("  Sorted by name: " + sorted.get(0).getName());
    }

    static void testStreams() {
        System.out.println("\n7. Streams & lambdas");
        DoctorService ds = new DoctorService();
        ds.addDoctor("A", 40, Specialization.CARDIOLOGY, 5, 100);
        ds.addDoctor("B", 50, Specialization.CARDIOLOGY, 15, 200);
        double avg = ds.getAverageFee();
        System.out.println("  Average fee: " + avg);
    }
}
