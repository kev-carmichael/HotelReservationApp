package ui;
import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import service.ReservationService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;


public class MainMenu {
    private static final HotelResource hotelResource = new HotelResource();
    private static final ReservationService reservationService = ReservationService.getInstance();

    public static void main(String[]args) {
        mainMenuDriver();
    }

    public static void mainMenuDriver(){
        Scanner in = new Scanner(System.in);
        boolean response = true;
        String entry = null;
        do{
            mainMenuDisplay();
            entry = in.nextLine();
            switch(entry){
                case "1": findARoom();
                    break;
                case "2": getCustomersReservations();
                    break;
                case "3": createAnAccount();
                    break;
                case "4": AdminMenu.adminMain();
                    break;
                case "5": in.close();
                    System.exit(0);
                    break;
                default:
                    response = false;
                    System.out.println("\nIncorrect response. Please try again");
                    break;
            }
        } while(!response);
    }

    public static void mainMenuDisplay(){
        System.out.println("============================================");
        System.out.println("Welcome to the hotel reservation application");
        System.out.println("Please enter 1-5 to select one of the following options:");
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create an account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
    }

    public static void findARoom(){
        Scanner in = new Scanner(System.in);
        Calendar calendarIn = Calendar.getInstance();
        Calendar calendarOut = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(("MM/dd/yyyy"));
        Date checkInDate = new Date();
        Date checkOutDate = new Date();
        String checkInString = "";
        String checkOutString="";
        boolean incorrectDate = true;
        do{
            System.out.println("Enter check-in date in format mm/dd/yyyy: ");
            checkInString = in.nextLine();
            simpleDateFormat.setLenient(false);
            try{
                checkInDate = simpleDateFormat.parse(checkInString);
                incorrectDate = false;
            }
            catch(ParseException e){
                incorrectDate = true;
            }
        }while(incorrectDate);
        do{
            System.out.println("Enter check-out date in format mm/dd/yyyy: ");
            checkOutString = in.nextLine();
            simpleDateFormat.setLenient(false);
            try{
                checkOutDate = simpleDateFormat.parse(checkOutString);
                incorrectDate = false;
            }
            catch(ParseException e){
                incorrectDate = true;
            }
        }while(incorrectDate);


        boolean incorrectIncreaseSearchDays = true;
        String increaseSearchDays = "";
        Integer increaseSearchDaysInt = 0;
        do{
            System.out.println("Enter days to increase search by (in case of unavailability): ");
            increaseSearchDays = in.nextLine();
            try{
                increaseSearchDaysInt = Integer.parseInt(increaseSearchDays);
                incorrectIncreaseSearchDays = false;
            }
            catch(Exception e){
                incorrectIncreaseSearchDays = true;
            }
        }while(incorrectIncreaseSearchDays);





        if((hotelResource.findARoom(checkInDate, checkOutDate)).size()==0) {
            System.out.println("No rooms available for check-in date "+checkInDate+" and check-out date "+checkOutDate);
            calendarIn.setTime(checkInDate);
            calendarIn.add(Calendar.DATE, increaseSearchDaysInt);
            checkInDate = calendarIn.getTime();
            calendarOut.setTime(checkOutDate);
            calendarOut.add(Calendar.DATE, increaseSearchDaysInt);
            checkOutDate = calendarOut.getTime();
            if((hotelResource.findARoom(checkInDate, checkOutDate)).size()==0){
                System.out.println("Additional search: No rooms available for check-in date "+checkInDate+" and check-out date "+checkOutDate);
                MainMenu.mainMenuDriver();
            } else{
                roomsAvailableDisplay(checkInDate, checkOutDate);
            }
        } else{
            roomsAvailableDisplay(checkInDate, checkOutDate);
        }
    }

    private static void roomsAvailableDisplay(Date checkInDate, Date checkOutDate){
        String likeToBook = "";
        Scanner in = new Scanner(System.in);
        System.out.println("Rooms available for check-in date "+checkInDate+" and check-out date "+checkOutDate);
        System.out.println(hotelResource.findARoom(checkInDate, checkOutDate));
        do {
            System.out.println("Would you like to book y/n?");
            likeToBook = in.nextLine();
        } while(!(likeToBook.equalsIgnoreCase("y")|(likeToBook.equalsIgnoreCase("n"))));
        if(likeToBook.equalsIgnoreCase("y")){
            reserveARoom(checkInDate, checkOutDate);
        } else if(likeToBook.equalsIgnoreCase("n")){
            MainMenu.mainMenuDriver();
        }
    }

    public static void reserveARoom(Date checkInDate, Date checkOutDate){
        Scanner in = new Scanner(System.in);
        boolean accountExists = true;
        boolean roomExists = false;
        boolean correctEmail = true;
        String customerEmail = null;
        String haveAnAccount = "";
        String roomNumber = "";
        do {
            do {
                System.out.println("Do you have an account y/n?");
                haveAnAccount = in.nextLine();
            } while(!(haveAnAccount.equalsIgnoreCase("y")|(haveAnAccount.equalsIgnoreCase("n"))));
            if (haveAnAccount.equalsIgnoreCase("y")) {
                System.out.println("Enter email in format name@domain.com");
                customerEmail = in.nextLine();
                accountExists=checkIfExistingCustomer(customerEmail);
                if(!accountExists){
                    System.out.println("No existing account for that email address");
                }
            } else if (haveAnAccount.equalsIgnoreCase("n")) {
                do {
                    try {
                        correctEmail = true;
                        System.out.println("Enter email in the following format: name@domain.com ");
                        customerEmail = in.nextLine();
                        System.out.println("Enter your first name: ");
                        String firstName = in.nextLine();
                        System.out.println("Enter your last name: ");
                        String lastName = in.nextLine();
                        hotelResource.createACustomer(firstName, lastName, customerEmail);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                        correctEmail = false;
                    }
                } while (!correctEmail);
                accountExists = true;
            }
        } while(!accountExists);
        do {
            System.out.println("Enter room number you would you like to reserve: ");
            roomNumber = in.nextLine();
            for(IRoom temp : hotelResource.getiRoomSet()){
                if(temp.getRoomNumber().equals(roomNumber)){
                    roomExists=true;
                }
            }
        } while(!roomExists);
        System.out.println("Reservation details: ");
        System.out.println(hotelResource.bookARoom(customerEmail, hotelResource.getRoom(roomNumber), checkInDate, checkOutDate));
        mainMenuDriver();
    }

    public static void getCustomersReservations(){
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter your email: ");
        String customerEmail= in.nextLine();
        if(hotelResource.getCustomersReservations(customerEmail).size()==0){
            System.out.println("No reservations for that email");
            mainMenuDriver();
        } else {
            System.out.println("Your reservations: ");
            System.out.println(hotelResource.getCustomersReservations(customerEmail));
            mainMenuDriver();
        }
    }

    public static void createAnAccount(){
        Scanner in = new Scanner(System.in);
        boolean correctEmail = true;
        do {
            try {
                correctEmail = true;
                System.out.println("Enter email in the following format: name@domain.com ");
                String email = in.nextLine();
                System.out.println("Enter your first name: ");
                String firstName = in.nextLine();
                System.out.println("Enter your last name: ");
                String lastName = in.nextLine();
                hotelResource.createACustomer(firstName, lastName, email);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                correctEmail = false;
            }
        } while(!correctEmail);
        mainMenuDriver();
    }

    public static boolean checkIfExistingCustomer(String email){
        AdminResource adminResource = new AdminResource();
        for(Customer customer : adminResource.getAllCustomers()){
            if(customer.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }
}