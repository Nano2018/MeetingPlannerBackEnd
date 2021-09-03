package fr.canalplus.meetingplanner.services;
import fr.canalplus.meetingplanner.exceptions.UnknownMeetingTypeException;
import fr.canalplus.meetingplanner.exceptions.InvalidSlotException;
import fr.canalplus.meetingplanner.exceptions.NoRoomAvailableException;
import fr.canalplus.meetingplanner.models.dto.ReservationDto;
import fr.canalplus.meetingplanner.models.dto.RoomDto;
import fr.canalplus.meetingplanner.models.entities.Equipment;
import fr.canalplus.meetingplanner.models.entities.Room;
import fr.canalplus.meetingplanner.models.entities.Slot;

import java.time.LocalDateTime;
import java.util.Set;


public interface MeetingPlannerService {
    RoomDto getAvailableRoom(ReservationDto reservation) throws UnknownMeetingTypeException, InvalidSlotException, NoRoomAvailableException;
    boolean isRoomAvailableAtSlot(LocalDateTime startSlot, Set<Slot> slots);
    boolean isRoomContainAllNeedsEquipments(Set<Equipment> roomEquipment, Set<Equipment> meetingNeeds);
    boolean isAvailableReserveEquipments(Equipment equipment);
    int nbSameEquipments(Set<Equipment> roomEquipments,Set<Equipment> meetingNeeds);
    void registerRoomSlot(Room optimalRoom, LocalDateTime slotSTart);
}
