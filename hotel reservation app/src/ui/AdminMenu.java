package ui;

import api.AdminResource;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.Scanner;
import static model.RoomType.valueOf;

public class AdminMenu {

    private static AdminResource adminResource = new AdminResource();

    public static void adminMenuDisplay(){
        System.out.println("------------------------------------------");
        System.out.println("Admin menu");
        System.out.println("Please enter 1-5 to select one of the following options:");
        System.out.println("1. See all customers");
        System.out.println("2. See all rooms");
        System.out.println("3. See all reservations");
        System.out.println("4. Add a room");
        System.out.println("5. Back to main menu");
    }

    public static void adminMain(){
        Scanner in = new Scanner(System.in);
        boolean response = false;
        String entry = null;
        do{
            adminMenuDisplay();
            entry = in.nextLine();
            if( (entry.equals("1"))||(entry.equals("2"))||(entry.equals("3"))||
                    (entry.equals("4"))||(entry.equals("5"))||(entry.equals("6"))){
                response=true;
            } else{
                System.out.println("\nIncorrect response. Please try again");
            }
        } while(!response);
        if(entry.equals("1")){
            getAllCustomers();
        }
        if(entry.equals("2")){
            getAllRooms();
        }
        if(entry.equals("3")){
            displayAllReservations();
        }
        if(entry.equals("4")){
            addRoom();
        }
        if(entry.equals("5")){
            MainMenu.mainMenuDriver();
        }
    }

    public static void getAllCustomers(){
        if(adminResource.getAllCustomers().size()==0){
            System.out.println("No customers added yet");
        } else{
            System.out.println(adminResource.getAllCustomers());
        }
        adminMain();
    }

    public static void getAllRooms(){
        if(adminResource.getAllRooms().size()==0){
            System.out.println("No rooms added yet. Please add room via Admin menu option 4");
        } else {
            System.out.println(adminResource.getAllRooms());
        }
        adminMain();
    }

    public static void displayAllReservations(){
        adminResource.displayAllReservations();
        adminMain();
    }

    public static void addRoom(){
        Scanner in = new Scanner(System.in);
        boolean incorrectRoomNumber = true;
        String roomNumber ="";
        do{
            System.out.println("Enter room number: ");
            roomNumber = in.nextLine();
            try{
                Integer roomNumberInt = Integer.parseInt(roomNumber);
                roomNumber = Integer.toString(roomNumberInt);
                incorrectRoomNumber = false;
            }
            catch(Exception e){
                incorrectRoomNumber = true;
            }
        }while(incorrectRoomNumber);

        boolean incorrectPrice = true;
        String priceString = "";
        Double price = 0.00;
        do{
            System.out.println("Enter price per night: ");
            priceString = in.nextLine();
            try{
                price = Double.parseDouble(priceString);
                incorrectPrice = false;
            }
            catch(Exception e){
                incorrectPrice = true;
            }
        }while(incorrectPrice);

        String roomTypeString = "";
        do {
            System.out.println("Enter room type: 1 for single, 2 for double");
            roomTypeString = in.nextLine();
        } while(!(roomTypeString.equals("1")|roomTypeString.equals("2")));
        RoomType roomType = null;
        if(roomTypeString.equals("1")){
            roomType = valueOf("SINGLE");
        }
        if(roomTypeString.equals("2")){
            roomType = valueOf("DOUBLE");
        }
        IRoom room = new Room(roomNumber, price, roomType);
        adminResource.addRoom(room);
        adminMain();
    }
}