package com.airtribe.meditrack.interfaces;

import com.airtribe.meditrack.entity.BillSummary;

public interface Payable {

    double calculateAmount();

    default BillSummary generateBill() {
        return new BillSummary(calculateAmount());
    }
}