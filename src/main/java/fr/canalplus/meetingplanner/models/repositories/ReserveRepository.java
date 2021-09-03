package fr.canalplus.meetingplanner.models.repositories;
import fr.canalplus.meetingplanner.models.entities.Reserve;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ReserveRepository extends CrudRepository<Reserve,Long> {
    Reserve findByEquipmentType(String equipmentType);
}
