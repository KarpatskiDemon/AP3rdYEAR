package edu.domain.app;

import edu.domain.core.Payment;

public class OnlinePayment extends Payment {
    public OnlinePayment() {}

    public OnlinePayment(double amount, String status, String date, String paymentId) {
        super(amount, status, date, paymentId);
    }

    public void acceptPayment() { markAccepted(); System.out.println("OnlinePayment.acceptPayment"); }
}