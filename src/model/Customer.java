package model;

import java.util.ArrayList;
import java.util.List;

public class Customer{
    private String webShopId;
    private String customerId;
    private String name;
    private String address;
    private List<Payment> payments;

    public Customer(String webShopId, String customerId, String name, String address) {
        this.webShopId = webShopId;
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.payments = new ArrayList<>();
    }


    public void addPayment(Payment payment){
        this.payments.add(payment);
    }

    //getters & setters
    public String getName() {
        return name;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public String getAddress() {
        return address;
    }

    public String getWebShopId() {
        return webShopId;
    }

    public String getCustomerId() {
        return customerId;
    }
}
