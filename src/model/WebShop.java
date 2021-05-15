package model;

import java.util.ArrayList;
import java.util.List;

public class WebShop {
    private String webShopId;
    private List<Customer> customers;
    private List<Payment> payments;


    public WebShop(String webShopId) {
        this.webShopId = webShopId;
        this.customers = new ArrayList<>();
        this.payments = new ArrayList<>();
    }

    public void addCustomer(Customer customer){
        this.customers.add(customer);
    }

    public void addPayment(Payment payment){
        this.payments.add(payment);
    }

    public String getWebShopId() {
        return webShopId;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public List<Payment> getPayments() {
        return payments;
    }
}
