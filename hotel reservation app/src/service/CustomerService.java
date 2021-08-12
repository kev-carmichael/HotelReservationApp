package service;
import model.Customer;

import java.util.*;

public class CustomerService {
    static Map<String, Customer>customersMap=new HashMap<>();

    private static CustomerService customerService = new CustomerService();

    public void addCustomer(String firstName, String lastName, String email){
        Customer customer = new Customer(firstName, lastName, email);
        customersMap.put(email, customer);
    }

    public Customer getCustomer(String customerEmail){
        return customersMap.get(customerEmail);
    }

    public Collection<Customer>getAllCustomers(){
            return customersMap.values();
    }
}