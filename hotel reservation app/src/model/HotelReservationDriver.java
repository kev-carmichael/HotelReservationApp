package model;

import service.*;

public class HotelReservationDriver {
    public static void main(String[] args) {
        CustomerService customerService = new CustomerService();
        customerService.addCustomer("kev", "carmichael", "kev@gmail.com");
        System.out.println(customerService.getAllCustomers());
    }
}