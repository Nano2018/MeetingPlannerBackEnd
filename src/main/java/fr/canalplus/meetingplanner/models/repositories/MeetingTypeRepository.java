package fr.canalplus.meetingplanner.models.repositories;
import fr.canalplus.meetingplanner.models.entities.MeetingType;
import org.springframework.data.repository.CrudRepository;

public interface MeetingTypeRepository extends CrudRepository<MeetingType,Long> {
    MeetingType findByMeetingType(String meetingType);
}
