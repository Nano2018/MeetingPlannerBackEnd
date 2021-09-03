package fr.canalplus.meetingplanner.models.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class MeetingType {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String meetingType;

    @ManyToMany(mappedBy = "meetingTypes",fetch = FetchType.EAGER)
    private Set<Equipment> equipments;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Room> rooms;

    public MeetingType(){
        this.equipments = new HashSet<>();
        this.rooms = new HashSet<>();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public String getMeetingType() {
        return meetingType;
    }

    public Set<Equipment> getEquipments() {
        return equipments;
    }

    public void setMeetingType(String meetingType) {
        this.meetingType = meetingType;
    }

    public void addEquipment(Equipment equipment){
        this.equipments.add(equipment);
    }

    public void addRoom(Room room){
        this.rooms.add(room);
    }
}
