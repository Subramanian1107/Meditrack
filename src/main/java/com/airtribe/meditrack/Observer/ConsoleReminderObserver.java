package com.airtribe.meditrack.observer;

import com.airtribe.meditrack.entity.Appointment;

/** Console-based reminder for appointments (Observer pattern). */
public class ConsoleReminderObserver implements AppointmentObserver {

    @Override
    public void onAppointmentCreated(Appointment appointment) {
        System.out.println("[Reminder] Appointment #" + appointment.getId() + " created for "
                + appointment.getPatient().getName() + " with Dr. " + appointment.getDoctor().getName()
                + " at " + appointment.getDateTime());
    }

    @Override
    public void onAppointmentCancelled(Appointment appointment) {
        System.out.println("[Reminder] Appointment #" + appointment.getId() + " has been cancelled.");
    }
}
