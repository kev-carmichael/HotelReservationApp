package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;

public class AdminResource {

    public static final CustomerService customerService = new CustomerService();
    public static final ReservationService reservationService = ReservationService.getInstance();

    public void addRoom(IRoom room){
        reservationService.addRoom(room);
    }

    public Collection<IRoom>getAllRooms(){
        return reservationService.getiRoomSet();
    }

    public Collection<Customer>getAllCustomers(){
        return customerService.getAllCustomers();
    }

    public void displayAllReservations(){
        reservationService.printAllReservation();
    }
}
