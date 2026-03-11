package edu.domain.core;

public abstract class Payment {
    protected double amount;
    protected String status;
    protected String date;
    protected String paymentId;

    private static int count = 0;

    protected Payment() { count++; }

    public Payment(double amount, String status, String date, String paymentId) {
        this.amount = amount; this.status = status; this.date = date; this.paymentId = paymentId; count++;
    }

    public static int total() { return count; }

    public abstract void acceptPayment();

    public void checkStatus() { System.out.println(getClass().getSimpleName() + ".checkStatus:" + status); }

    public String generateReceipt() {
        return "RECEIPT\n" +
                "PaymentId: " + paymentId + "\n" +
                "Date: " + date + "\n" +
                "Amount: " + amount + " eur\n" +
                "Status: " + status + "\n";
    }

    void coreAudit(String m) { System.out.println("core.audit:" + m); }

    protected void markAccepted() { this.status = "ACCEPTED"; }
    protected void markDeclined() { this.status = "DECLINED"; }
    protected void markRefunded() { this.status = "REFUNDED"; }

    public double getAmount() { return amount; }
    public String getStatus() { return status; }
    public String getDate() { return date; }
    public String getPaymentId() { return paymentId; }

    public String toString() {
        return "Payment : \n" +
                "Amount : " + amount + " eur\n" +
                "Status : " + status + "\n" +
                "Date : " + date + "\n" +
                "PaymentId : " + paymentId + "\n";
    }
}