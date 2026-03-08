package edu.domain.app;

import edu.domain.core.Payment;

public class CashPayment extends Payment {
    public CashPayment() {}

    public CashPayment(double amount, String status, String date, String paymentId) {
        super(amount, status, date, paymentId);
    }

    public void acceptPayment() { markAccepted(); System.out.println("CashPayment.acceptPayment"); }
}