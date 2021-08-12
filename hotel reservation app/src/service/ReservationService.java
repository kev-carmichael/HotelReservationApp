package service;
import model.IRoom;
import model.Reservation;
import model.Customer;

import java.util.*;
import java.util.regex.*;

public class ReservationService {

    Set<IRoom>iRoomSet = new HashSet<>();
    Set<Reservation>reservationSet = new HashSet<>();

    Scanner in = new Scanner(System.in);
    private final String roomNumberRegex = "\\d+";
    private final Pattern pattern = Pattern.compile(roomNumberRegex);

    public static ReservationService reservationService;

    public void addRoom(IRoom room){
        if(iRoomSet.size()==0){
            iRoomSet.add(room);
        } else{
            for(IRoom roomCheck : iRoomSet){
                if((room.hashCode()!=roomCheck.hashCode())&&(!(room.equals(roomCheck)))){
                    iRoomSet.add(room);
                }
            }
        }
    }

    public IRoom getARoom(String roomId){
        for(IRoom room : iRoomSet){
            if(room.getRoomNumber().equals(roomId)){
                return room;
            }
        }
        return null;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        Reservation newReservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservationSet.add(newReservation);
        return newReservation;
    }

    public Collection<IRoom>findRooms(Date checkInDate, Date checkOutDate) {
        List<IRoom> availableRoomList = new ArrayList<>();
        if (reservationSet.size() == 0) {
            availableRoomList = new ArrayList<>(iRoomSet); // same type (IRoom), so ok to put set into arraylist
        } else {
            for (IRoom room : iRoomSet) {  // to make sure cycles through every room
                for (Reservation reservation : reservationSet) {  // to cycle through all reservations
                    if (checkRoomAvailability(room, reservation, checkInDate, checkOutDate)) {
                        availableRoomList.add(room);
                    }
                }
            }
        }
        return availableRoomList;
    }

    // private access to meet rubric requirement
    private boolean checkRoomAvailability(IRoom room, Reservation reservation, Date checkInDate, Date checkOutDate){
        if(!(reservation.getRoom().getRoomNumber().equals(room.getRoomNumber()))){
            return true;
            } else if ((reservation.getRoom().getRoomNumber().equals(room.getRoomNumber())) &
                      ((checkInDate.before(reservation.getCheckInDate()) &&
                      (checkOutDate.before(reservation.getCheckInDate())||checkOutDate.equals(reservation.getCheckInDate())))) ||
                      ((checkInDate.after(reservation.getCheckOutDate())||checkInDate.equals(reservation.getCheckOutDate()))&&checkOutDate.after(reservation.getCheckOutDate()))){
            return true;
            }
        return false;
    }

    public Collection<Reservation>getCustomersReservation(Customer customer){
            return getReservations(customer);
    }

    // default access to meet rubric requirement
    Collection<Reservation>getReservations(Customer customer){
        List<Reservation>reservationList=new ArrayList<>();
        for(Reservation reservation : reservationSet){
            if(reservation.getCustomer().equals(customer)){
                reservationList.add(reservation);
            }
        }
        return reservationList;
    }

    public void printAllReservation(){
        if(getReservationSet().size()==0){
            System.out.println("No reservations added yet");
        } else {
            for(Reservation reservation : reservationSet){
                System.out.println(reservation);
            }
        }
    }

    public Set<IRoom> getiRoomSet() {
        return iRoomSet;
    }

    private Set<Reservation> getReservationSet() {
        return reservationSet;
    }

    public static ReservationService getInstance(){  // added to support Singleton pattern
        if (reservationService==null){
            reservationService = new ReservationService();
        }
        return reservationService;
    }
}