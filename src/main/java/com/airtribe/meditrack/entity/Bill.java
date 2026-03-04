package main.java.com.airtribe.meditrack.entity;

import main.java.com.airtribe.meditrack.constants.Constants;
import main.java.com.airtribe.meditrack.interfaces.Payable;

public class Bill implements Payable {

    private double baseAmount;

    public Bill(double baseAmount) {
        this.baseAmount = baseAmount;
    }

    @Override
    public double calculateAmount() {
        return baseAmount + (baseAmount * Constants.TAX_RATE);
    }
}