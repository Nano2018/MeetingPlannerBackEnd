package fr.canalplus.meetingplanner.models.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Equipment {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String equipmentType;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<Room> rooms;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<MeetingType> meetingTypes;

    public Equipment() {
        rooms = new HashSet<>();
        meetingTypes = new HashSet<>();
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

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public void addRoom(Room room){
        this.rooms.add(room);
    }

    public void addMeeting(MeetingType meetingType){
        this.meetingTypes.add(meetingType);
    }
}
