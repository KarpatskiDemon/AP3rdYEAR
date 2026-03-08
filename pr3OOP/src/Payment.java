public class Payment {
    private double amount;
    private String status;
    private String date;
    private String paymentId;

    public Payment() {}

    public Payment(double amount, String status, String date, String paymentId) {
        this.amount = amount;
        this.status = status;
        this.date = date;
        this.paymentId = paymentId;
    }

    public void acceptPayment() { markAccepted(); }

    public void checkStatus() {}

    public void generateReceipt() {}

    public double getAmount() { return amount; }

    public String getStatus() { return status; }

    public String getDate() { return date; }

    public String getPaymentId() { return paymentId; }

    void markAccepted() { this.status = "ACCEPTED"; }

    void markDeclined() { this.status = "DECLINED"; }

    void markRefunded() { this.status = "REFUNDED"; }

    protected void validateAmount() {
        if (amount <= 0) throw new IllegalArgumentException();
    }

    public String toString() {
        return "Payment : \n" +
                "Amount : " + amount + " eur\n" +
                "Status : " + status + "\n" +
                "Date : " + date + "\n" +
                "PaymentId : " + paymentId + "\n";
    }
}