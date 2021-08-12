package model;

import java.text.DecimalFormat;
import java.util.Objects;

public class Room implements IRoom{
    private String roomNumber;
    private Double price;
    private RoomType roomType;

    public Room(){
    }
    
    public Room(String roomNumber, Double price, RoomType roomType){
        this.roomNumber = roomNumber;
        this.price = price;
        this.roomType = roomType;
    }
    
    public void setPrice(Double price){
        if(isFree()){
            this.price=0.00;
        } else {
            this.price=price;
        }
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setroomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public String getRoomNumber(){
        return roomNumber;
    }

    public Double getRoomPrice(){
        return price;
    }

    public RoomType getRoomType(){
        return roomType;
    }

    @Override
    public boolean isFree(){
        return false;
    }

    @Override
    public String toString(){
        return "Room number: "+roomNumber+"\nRoom price: "+new DecimalFormat("0.00").format(price)+"\nRoom type: "+roomType+"\n";
    }

    //override equals and hashCode methods and apply to ensure inequality before addition to Set
    @Override
    public int hashCode(){
        return Objects.hash(roomNumber, price, roomType);
    }

    @Override
    // only need room number to be different, other parameters can be the same
    public boolean equals(Object obj){
        Room room = (Room)obj;
        if (room.roomNumber.equals(roomNumber)) {
            return true;
        } else return false;
    }
}