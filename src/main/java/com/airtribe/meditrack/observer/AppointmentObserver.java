package main.java.com.airtribe.meditrack.observer;

import main.java.com.airtribe.meditrack.entity.Appointment;

/** Observer for appointment notifications (e.g. reminders). */
public interface AppointmentObserver {

    void onAppointmentCreated(Appointment appointment);
    void onAppointmentCancelled(Appointment appointment);
}
