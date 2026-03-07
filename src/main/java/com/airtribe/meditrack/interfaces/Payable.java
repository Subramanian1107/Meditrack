package main.java.com.airtribe.meditrack.interfaces;

import main.java.com.airtribe.meditrack.entity.BillSummary;

public interface Payable {

    double calculateAmount();

    default BillSummary generateBill() {
        return new BillSummary(calculateAmount());
    }
}