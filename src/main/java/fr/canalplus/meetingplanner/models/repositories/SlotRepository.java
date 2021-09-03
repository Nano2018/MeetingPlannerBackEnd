package fr.canalplus.meetingplanner.models.repositories;
import fr.canalplus.meetingplanner.models.entities.Slot;
import org.springframework.data.repository.CrudRepository;


public interface SlotRepository extends CrudRepository<Slot,Long> {
}
