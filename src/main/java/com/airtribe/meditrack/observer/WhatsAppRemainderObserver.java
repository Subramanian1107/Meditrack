package main.java.com.airtribe.meditrack.observer;

import main.java.com.airtribe.meditrack.entity.Appointment;

public class WhatsAppRemainderObserver implements AppointmentObserver {

    @Override
    public void onAppointmentCreated(Appointment appointment) {
        System.out.println("[WHATSAPP] Reminder: "
                + appointment.getPatient().getName()
                + ", you have an appointment with Dr. "
                + appointment.getDoctor().getName()
                + " at "
                + appointment.getDateTime());
    }

    @Override
    public void onAppointmentCancelled(Appointment appointment) {
        System.out.println("[WHATSAPP] Appointment cancelled for "
                + appointment.getPatient().getName());
    }
}