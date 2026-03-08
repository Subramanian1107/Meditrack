package main.java.com.airtribe.meditrack.util;

import main.java.com.airtribe.meditrack.entity.Appointment;
import main.java.com.airtribe.meditrack.entity.AppointmentStatus;
import main.java.com.airtribe.meditrack.entity.Doctor;
import main.java.com.airtribe.meditrack.entity.Specialization;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class AIHelper {

    private static final int SLOT_MINUTES = 30;

    // Rule-based symptom → specialization mapping
    public static Specialization recommendSpecialization(String symptom) {

        if (symptom == null)
            return Specialization.GENERAL;

        symptom = symptom.toLowerCase();

        if (symptom.contains("chest") || symptom.contains("heart"))
            return Specialization.CARDIOLOGY;

        if (symptom.contains("skin") || symptom.contains("rash") || symptom.contains("acne"))
            return Specialization.DERMATOLOGY;

        if (symptom.contains("bone") || symptom.contains("joint"))
            return Specialization.ORTHOPEDIC;

        return Specialization.GENERAL;
    }

    // Recommend doctors based on symptom
    public static List<Doctor> recommendDoctors(
            String symptom,
            List<Doctor> doctors) {

        Specialization spec = recommendSpecialization(symptom);

        return doctors.stream()
                .filter(d -> d.getSpecialization() == spec)
                .sorted(Comparator.comparingDouble(Doctor::getFee))
                .limit(3)
                .collect(Collectors.toList());
    }

    // Auto-suggest appointment slots
    public static List<LocalDateTime> suggestSlots(
            Doctor doctor,
            List<Appointment> appointments) {

        List<LocalDateTime> slots = new ArrayList<>();

        LocalDateTime start = LocalDateTime.now().plusMinutes(30);

        for (int i = 0; i < 10; i++) {

            LocalDateTime slot = start.plusMinutes(i * SLOT_MINUTES);

            boolean conflict = appointments.stream()
                    .anyMatch(a -> a.getDoctor().equals(doctor)
                            && a.getDateTime().equals(slot)
                            && a.getStatus() != AppointmentStatus.CANCELLED);

            if (!conflict)
                slots.add(slot);

            if (slots.size() == 3)
                break;
        }

        return slots;
    }

    // ---------------- NEW AI FEATURES ----------------

    // AI Feature 1: Recommend least busy doctor
    public static Doctor recommendLeastBusyDoctor(
            List<Doctor> doctors,
            List<Appointment> appointments) {

        Map<Doctor, Long> doctorLoad = appointments.stream()
                .filter(a -> a.getStatus() != AppointmentStatus.CANCELLED)
                .collect(Collectors.groupingBy(
                        Appointment::getDoctor,
                        Collectors.counting()));

        Doctor bestDoctor = null;
        long minAppointments = Long.MAX_VALUE;

        for (Doctor doctor : doctors) {

            long load = doctorLoad.getOrDefault(doctor, 0L);

            if (load < minAppointments) {
                minAppointments = load;
                bestDoctor = doctor;
            }
        }

        return bestDoctor;
    }

    // AI Feature 2: Recommend doctors within patient budget
    public static List<Doctor> recommendAffordableDoctors(
            List<Doctor> doctors,
            double maxFee) {

        return doctors.stream()
                .filter(d -> d.getFee() <= maxFee)
                .sorted(Comparator.comparingDouble(Doctor::getFee))
                .collect(Collectors.toList());
    }

    // AI Feature 3: Detect clinic peak hour
    public static int detectPeakHour(List<Appointment> appointments) {

        Map<Integer, Long> hourCount = appointments.stream()
                .collect(Collectors.groupingBy(
                        a -> a.getDateTime().getHour(),
                        Collectors.counting()));

        return hourCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(-1);
    }
}