package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Payment{
    private String webShopId;
    private String customerId;
    private PaymentType paymentType;
    private int total;
    private String bankNumber;
    private String cardNumber;
    private LocalDate dateOfPayment;

    public Payment(String webShopId,
                   String customerId,
                   String paymentType,
                   String amount,
                   String bankNumber,
                   String cardNumber,
                   String dateOfPayment) {

        this.webShopId = webShopId;
        this.customerId = customerId;
        this.paymentType = PaymentType.valueOf(paymentType);
        this.total = Integer.parseInt(amount);
        this.bankNumber = bankNumber;
        this.cardNumber = cardNumber;
        this.dateOfPayment = LocalDate.parse(dateOfPayment, DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }

//getters & setters

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getWebShopId() {
        return webShopId;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public int getTotal() {
        return total;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }
}
