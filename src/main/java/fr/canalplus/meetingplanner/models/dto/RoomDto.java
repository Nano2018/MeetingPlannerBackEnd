package fr.canalplus.meetingplanner.models.dto;

import java.util.ArrayList;
import java.util.List;

public class RoomDto {
    private String roomName;
    private List<String> reserves;

    public RoomDto(String roomName){
        this.roomName = roomName;
        this.reserves = new ArrayList<>();
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public List<String> getReserves(){
        return  this.reserves;
    }
    public void addReserve(String equipment){
        this.reserves.add(equipment);
    }
}
