package main.java.com.airtribe.meditrack.util;

import main.java.com.airtribe.meditrack.entity.Appointment;
import main.java.com.airtribe.meditrack.entity.Bill;
import main.java.com.airtribe.meditrack.entity.BillSummary;
import main.java.com.airtribe.meditrack.interfaces.Payable;

/** Factory for creating different bill types (Factory pattern). */
public final class BillFactory {

    public enum BillType {
        CONSULTATION,
        PROCEDURE
    }

    private BillFactory() {}

    public static Payable createBill(BillType type, double baseAmount, Appointment appointment) {
        switch (type) {
            case PROCEDURE:
                return new ProcedureBill(baseAmount, appointment);
            case CONSULTATION:
            default:
                return new Bill(baseAmount, appointment);
        }
    }

    /** Procedure bills apply a higher effective rate (e.g. facility fee). */
    private static class ProcedureBill extends Bill {

        private static final double PROCEDURE_MARKUP = 1.15;

        ProcedureBill(double baseAmount, Appointment appointment) {
            super(baseAmount * PROCEDURE_MARKUP, appointment);
        }

        @Override
        public BillSummary generateBill() {
            return new BillSummary(calculateAmount());
        }
    }
}
