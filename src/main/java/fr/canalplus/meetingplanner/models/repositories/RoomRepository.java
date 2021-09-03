package fr.canalplus.meetingplanner.models.repositories;
import fr.canalplus.meetingplanner.models.entities.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
@Repository
public interface RoomRepository extends CrudRepository<Room,Long> {

    @Query("SELECT r FROM Room r WHERE (r.capacity * 70)/100 >= :capacity")
    Collection<Room> findRoomByAvailableCapacity(int capacity);
    Room findByName(String name);
}
