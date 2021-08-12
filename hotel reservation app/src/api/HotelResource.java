package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {

    public static final CustomerService customerService = new CustomerService();
    public static final ReservationService reservationService = ReservationService.getInstance();

    public Customer getCustomer(String email){
        return customerService.getCustomer(email);
    }

    public void createACustomer(String firstName, String lastName, String email){
        customerService.addCustomer(firstName, lastName, email);
    }

    public IRoom getRoom(String roomNumber){
        return reservationService.getARoom(roomNumber);
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate){
        HotelResource hotelResource = new HotelResource();
        Customer customer = hotelResource.getCustomer(customerEmail);
        return reservationService.reserveARoom(customer, room, checkInDate, checkOutDate);
    }

    public Collection<Reservation>getCustomersReservations(String customerEmail){
        //this method jumps from String input to needing a customer input in ReservationService
        Customer customer = customerService.getCustomer(customerEmail);
        return reservationService.getCustomersReservation(customer);
    }

    public Collection<IRoom> findARoom(Date checkInDate, Date checkOutDate){
        return reservationService.findRooms(checkInDate, checkOutDate);
    }

    public Collection<IRoom>getiRoomSet(){
        return reservationService.getiRoomSet();
    }
}