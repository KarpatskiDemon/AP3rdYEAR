package edu.domain.app;

import edu.domain.core.Payment;

public class OnlinePayment extends Payment {
    public OnlinePayment() {}

    public OnlinePayment(double amount, String status, String date, String paymentId) {
        super(amount, status, date, paymentId);
    }

    @Override
    public void acceptPayment() { markAccepted(); System.out.println("OnlinePayment.acceptPayment"); }

    @Override
    public void checkStatus() { System.out.println("OnlinePayment.checkStatus:" + status); }

    @Override
    public double calculateFee() { return amount * 0.02; }
}
