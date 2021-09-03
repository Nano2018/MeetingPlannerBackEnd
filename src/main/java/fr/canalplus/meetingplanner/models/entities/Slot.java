package fr.canalplus.meetingplanner.models.entities;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Slot {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private LocalDateTime startSlot;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<Room> rooms;

    public Slot(){
        this.rooms = new HashSet<>();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collection<Room> getRooms() {
        return rooms;
    }

    public LocalDateTime getStartSlot() {
        return startSlot;
    }

    public void setStartSlot(LocalDateTime startSlot) {
        this.startSlot = startSlot;
    }

    public void addRoom(Room room){
        this.rooms.add(room);
    }

}
