package main.java.com.airtribe.meditrack.observer;

import main.java.com.airtribe.meditrack.entity.Appointment;

public class EmailRemainderObserver implements AppointmentObserver {

    @Override
    public void onAppointmentCreated(Appointment appointment) {
        System.out.println("[EMAIL] Appointment confirmed for "
                + appointment.getPatient().getName()
                + " with Dr. "
                + appointment.getDoctor().getName()
                + " at "
                + appointment.getDateTime());
    }

    @Override
    public void onAppointmentCancelled(Appointment appointment) {
        System.out.println("[EMAIL] Appointment cancelled for "
                + appointment.getPatient().getName()
                + " with Dr. "
                + appointment.getDoctor().getName());
    }
}