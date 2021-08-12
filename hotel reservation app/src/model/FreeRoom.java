package model;

public class FreeRoom extends Room{

    public FreeRoom() {
    }

    public FreeRoom(String roomNumber, Double price, RoomType roomType) {
        super(roomNumber, price, roomType);
    }

    public boolean isFree(){
        return true;
    }

    @Override
    public String toString(){
        return "Room number: "+getRoomNumber()+"\nRoom price: "+getRoomPrice()+"\nRoom type: "+getRoomType();
    }
}
