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

    public void acceptPayment() {}
    public void checkStatus() {}
    public void generateReceipt() {}

    public String toString() {
        return "Payment : \n" +
                "Amount : " + amount + " eur\n" +
                "Status : " + status + "\n" +
                "Date : " + date + "\n" +
                "PaymentId : " + paymentId + "\n";
    }
}
