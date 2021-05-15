import model.Customer;

import model.Payment;
import model.PaymentType;
import model.WebShop;

import java.io.IOException;
import java.util.ArrayList;


public class PaymentService {
    private ArrayList<Customer> customers;
    private ArrayList<Payment> payments;
    private ArrayList<WebShop> webShops;
    private FileManager fileManager;

    public PaymentService() {
        fileManager = new FileManager(this);
        try {
            this.customers = fileManager.readCustomersCsvFile(Config.CUSTOMERS_FILE);
            this.webShops = fileManager.getWebshops(Config.CUSTOMERS_FILE);
            this.separateCustomers();
            this.payments = fileManager.readPaymentsCsvFile(Config.PAYMENTS_FILE);
            this.separatePayments();
            this.addPaymentsToCustomers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void separatePayments() {
        for (WebShop webShop : webShops) {
            for (Payment payment : payments) {
                if (webShop.getWebShopId().equals(payment.getWebShopId())) {
                    webShop.addPayment(payment);
                }
            }
        }
    }

    public boolean customerIsInWebshop(String webShopID, String customerId) {
        WebShop webShop = getWebShopById(webShopID);
        for (Customer customer : webShop.getCustomers()) {
            if (customer.getCustomerId().equals(customerId)) {
                return true;
            }
        }
        return false;
    }

    private WebShop getWebShopById(String webShopId) {
        for (WebShop webShop : webShops) {
            if (webShop.getWebShopId().equals(webShopId)) {
                return webShop;
            }
        }
        return null;
    }

    private void separateCustomers() {
        for (WebShop webShop : webShops) {
            for (Customer customer : customers) {
                if (webShop.getWebShopId().equals(customer.getWebShopId())) {
                    webShop.addCustomer(customer);
                }
            }
        }
    }

    private void addPaymentsToCustomers() {
        for (Customer customer : customers) {
            for (Payment payment : payments) {
                if (customer.getWebShopId().equals(payment.getWebShopId()) && customer.getCustomerId().equals(payment.getCustomerId())) {
                    customer.addPayment(payment);
                }
            }
        }
    }

    public int getCustomerTotal(Customer customer) {
        int total = 0;
        for (Payment payment : customer.getPayments()) {
            total += payment.getTotal();
        }
        return total;
    }

    public int getCardPaymentTotal(WebShop webShop) {
        int total = 0;
        for (Payment payment : webShop.getPayments()) {
            if (payment.getPaymentType() == PaymentType.card) {
                total += payment.getTotal();
            }
        }
        return total;
    }

    public int getTransferPaymentTotal(WebShop webShop) {
        int total = 0;
        for (Payment payment : webShop.getPayments()) {
            if (payment.getPaymentType() == PaymentType.transfer) {
                total += payment.getTotal();
            }
        }
        return total;
    }

    public void createCustomerReport() {
        try {
            fileManager.writeCustomerReport(customers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createPaymentReport() {
        try {
            fileManager.writePaymentReport(webShops);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
