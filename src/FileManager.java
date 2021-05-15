import model.Customer;
import model.Payment;
import model.WebShop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManager {
    private Scanner csvReader;
    private FileWriter fileWriter;
    private PaymentService paymentService;

    public FileManager(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public ArrayList<WebShop> getWebshops(String path) throws FileNotFoundException {
        csvReader = new Scanner(new File(path));
        ArrayList<WebShop> webShops = new ArrayList<>();

        while (csvReader.hasNextLine()) {
            String row = csvReader.nextLine();
            String[] data = row.split(";");

            WebShop webShop = new WebShop(
                    data[0]
            );
            if(!webShopIsExistById(webShops, webShop.getWebShopId())){
                webShops.add(webShop);
            }
        }
        return webShops;
    }

    private boolean webShopIsExistById(ArrayList<WebShop> webShops, String webShopId){
        for(WebShop webShop : webShops){
            if (webShop.getWebShopId().equals(webShopId)){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Customer> readCustomersCsvFile(String path) throws FileNotFoundException {
        csvReader = new Scanner(new File(path));
        ArrayList<Customer> customers = new ArrayList<>();

        while (csvReader.hasNextLine()) {
            String row = csvReader.nextLine();
            String[] data = row.split(";");

            Customer customer = new Customer(
                    data[0],
                    data[1],
                    data[2],
                    data[3]
            );
            customers.add(customer);
        }
        return customers;
    }

    public ArrayList<Payment> readPaymentsCsvFile(String path) throws IOException {
        csvReader = new Scanner(new File(path));
        ArrayList<Payment> payments = new ArrayList<>();

        while (csvReader.hasNextLine()) {
            String row = csvReader.nextLine();
            String[] data = row.split(";");

            Payment payment = new Payment(
                    data[0],
                    data[1],
                    data[2],
                    data[3],
                    data[4],
                    data[5],
                    data[6]
            );
            if (payment.getBankNumber().isEmpty())
                payment.setBankNumber(null);
            if (payment.getCardNumber().isEmpty())
                payment.setCardNumber(null);

            if(paymentService.customerIsInWebshop(payment.getWebShopId(), payment.getCustomerId())){
                payments.add(payment);
            }else {
                LogWriter.getInstance().Log("Validációs hiba: nem létezik ilyen customer a webshopban",row);
            }
        }
        return payments;
    }

    public void writeCustomerReport(ArrayList<Customer> customers) throws IOException {
        fileWriter = new FileWriter(Config.CUSTOMER_REPORT);
        fileWriter.append("NAME;");
        fileWriter.append("ADDRESS;");
        fileWriter.append("vásárlás összesen \n");

        for(Customer customer : customers){
            fileWriter.append(customer.getName() + ";");
            fileWriter.append(customer.getAddress() + ";");
            fileWriter.append(paymentService.getCustomerTotal(customer) + "\n");
        }
        fileWriter.flush();
        fileWriter.close();
    }

    public void writePaymentReport(ArrayList<WebShop> webShops) throws IOException {
        fileWriter = new FileWriter(Config.PAYMENT_REPORT);
        fileWriter.append("WEBSHOP;");
        fileWriter.append("kártyás vásárlások összege;");
        fileWriter.append("utalásos vásárlássok összege \n");

        for(WebShop webShop : webShops){
            fileWriter.append(webShop.getWebShopId() + ";");
            fileWriter.append(paymentService.getCardPaymentTotal(webShop) + ";");
            fileWriter.append(paymentService.getTransferPaymentTotal(webShop) + "\n");
        }
        fileWriter.flush();
        fileWriter.close();
    }
}
