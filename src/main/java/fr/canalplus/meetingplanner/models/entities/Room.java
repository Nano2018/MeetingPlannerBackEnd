package fr.canalplus.meetingplanner.models.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private int capacity;

    @ManyToMany(mappedBy = "rooms",fetch = FetchType.EAGER)
    private Set<Equipment> equipments;

    @ManyToMany(mappedBy = "rooms",fetch = FetchType.EAGER)
    private Set<MeetingType> meetingTypes;

    @ManyToMany(mappedBy = "rooms",fetch = FetchType.EAGER)
    private Set<Slot> slots;

    public Room(){
        equipments = new HashSet<>();
        meetingTypes = new HashSet<>();
        slots = new HashSet<>();
    }

    public Set<Slot> getSlots() {
        return slots;
    }

    public void setSlots(Set<Slot> slots) {
        this.slots = slots;
    }

    public Set<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(Set<Equipment> equipments) {
        this.equipments = equipments;
    }

    public Set<MeetingType> getMeetings() {
        return meetingTypes;
    }

    public void setMeetings(Set<MeetingType> meetingTypes) {
        this.meetingTypes = meetingTypes;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public Long getId() {
        return id;
    }



    public void setId(Long id) {
        this.id = id;
    }
    public void addEquipment(Equipment equipment){
        this.equipments.add(equipment);
    }
    public void addMeeting(MeetingType meetingType){
        this.meetingTypes.add(meetingType);
    }
    public void addSlot(Slot slot){
        this.slots.add(slot);
    }
}
