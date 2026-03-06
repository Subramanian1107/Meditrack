package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.interfaces.Payable;

public class Bill implements Payable {

    private final double baseAmount;
    private final Appointment appointment;

    public Bill(double baseAmount, Appointment appointment) {
        this.baseAmount = baseAmount;
        this.appointment = appointment;
    }

    public Bill(double baseAmount) {
        this(baseAmount, null);
    }

    @Override
    public double calculateAmount() {
        return baseAmount + (baseAmount * Constants.TAX_RATE);
    }

    /** Generates an immutable BillSummary from this bill (polymorphic behavior). */
    public BillSummary generateBill() {
        double total = calculateAmount();
        return new BillSummary(total);
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public double getBaseAmount() {
        return baseAmount;
    }
}