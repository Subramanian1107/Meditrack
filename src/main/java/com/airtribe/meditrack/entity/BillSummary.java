package com.airtribe.meditrack.entity;

public final class BillSummary {

    private final double totalAmount;

    public BillSummary(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public String toString() {
        return "BillSummary{totalAmount=" + totalAmount + "}";
    }
}