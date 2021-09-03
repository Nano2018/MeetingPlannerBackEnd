package fr.canalplus.meetingplanner.services;
import fr.canalplus.meetingplanner.exceptions.UnknownMeetingTypeException;
import fr.canalplus.meetingplanner.exceptions.InvalidSlotException;
import fr.canalplus.meetingplanner.exceptions.NoRoomAvailableException;
import fr.canalplus.meetingplanner.models.dto.ReservationDto;
import fr.canalplus.meetingplanner.models.dto.RoomDto;
import fr.canalplus.meetingplanner.models.entities.*;
import fr.canalplus.meetingplanner.models.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MeetingPlannerServiceImpl implements MeetingPlannerService {
    RoomRepository roomRepository;
    MeetingTypeRepository meetingTypeRepository;
    EquipmentRepository equipmentRepository;
    SlotRepository slotRepository;
    ReserveRepository reserveRepository;
    @Autowired
    public MeetingPlannerServiceImpl(RoomRepository roomRepository,MeetingTypeRepository meetingTypeRepository,EquipmentRepository equipmentRepository,SlotRepository slotRepository,ReserveRepository reserveRepository){
        this.roomRepository = roomRepository;
        this.meetingTypeRepository = meetingTypeRepository;
        this.equipmentRepository = equipmentRepository;
        this.slotRepository = slotRepository;
        this.reserveRepository = reserveRepository;
    }

    /**
     * @param reservation
     * @return "room if there is at least one available room for the meeting"
     * @throws UnknownMeetingTypeException
     * @throws InvalidSlotException
     * @throws NoRoomAvailableException
     */
    public RoomDto getAvailableRoom(ReservationDto reservation) throws UnknownMeetingTypeException,InvalidSlotException,NoRoomAvailableException{

        LocalDateTime slotStart = LocalDateTime.parse(reservation.getStartSlot());
        DayOfWeek day = slotStart.getDayOfWeek();
        //check if the date is not earlier than the current date or if it is not a weekend
        if(day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY || slotStart.isBefore(LocalDateTime.now())){
            throw new InvalidSlotException("invalid slot");
        }

        MeetingType meetingType = this.meetingTypeRepository.findByMeetingType(reservation.getMeetingType());
        if(meetingType == null){
            throw new UnknownMeetingTypeException("Unknown Meeting Type");
        }

        Set<Equipment> meetingEquipmentsNeeds = meetingType.getEquipments();

        //return the list of rooms which 70% of their capacity is greater than or equal  to the number of persons passed in parameters
        Collection<Room> rooms = this.roomRepository.findRoomByAvailableCapacity(reservation.getNbPersons());

        //return all available rooms at the time slot passed in parameters
        Set<Room> availableRooms = rooms.stream().filter(room -> isRoomAvailableAtSlot(slotStart,room.getSlots())).collect(Collectors.toSet());
        if(availableRooms.size() ==0){
            throw new NoRoomAvailableException("there is no room available");
        }

        //check if there is room available without using the reserve, and  recover the one with the minimum capacity
        Room optimalRoom = availableRooms.stream().filter(room -> isRoomContainAllNeedsEquipments(room.getEquipments(),meetingEquipmentsNeeds))
                .reduce(null,(room1,room2) -> room1 == null ? room2 : room1.getCapacity() > room2.getCapacity() ? room2 : room1);

        if(optimalRoom == null){
            //if there is no directly available room then retrieve the room which have a maximum of equipments available
            Room maxRoom =  availableRooms.stream()
                    .reduce(null,(room1,room2) -> room1 == null ? room2 : nbSameEquipments(room1.getEquipments(),meetingEquipmentsNeeds) > nbSameEquipments(room2.getEquipments(),meetingEquipmentsNeeds)   ? room1 : room2);
            Set<Equipment> missedEquipments = meetingEquipmentsNeeds.stream().filter(equipment -> !maxRoom.getEquipments().contains(equipment)).collect(Collectors.toSet());
            if(missedEquipments.stream().allMatch(equipment -> isAvailableReserveEquipments(equipment))){
                RoomDto romDto = new RoomDto(maxRoom.getName());
                for(Equipment equipment : missedEquipments){
                    Reserve reserve = this.reserveRepository.findByEquipmentType(equipment.getEquipmentType());
                    reserve.setNumber(reserve.getNumber()-1);
                    this.reserveRepository.save(reserve);
                    romDto.addReserve(equipment.getEquipmentType());
                }
                this.registerRoomSlot(maxRoom,slotStart);
                return romDto;
            }else {
                throw new NoRoomAvailableException("there is no room available");
            }
        }else{
            this.registerRoomSlot(optimalRoom,slotStart);
            return new RoomDto(optimalRoom.getName());
        }
    }


    @Override
    public boolean isRoomAvailableAtSlot(LocalDateTime startSlot, Set<Slot> slots){
        for(Slot slot : slots){
            if(slot.getStartSlot().equals(startSlot) || slot.getStartSlot().equals(startSlot.minusHours(1)) || slot.getStartSlot().minusHours(1).equals(startSlot)){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isRoomContainAllNeedsEquipments(Set<Equipment> roomEquipment, Set<Equipment> meetingNeeds){
        return roomEquipment.containsAll(meetingNeeds);
    }

    @Override
    public boolean isAvailableReserveEquipments(Equipment equipment){
        return this.reserveRepository.findByEquipmentType(equipment.getEquipmentType()).getNumber() > 0;
    }

    @Override
    public int nbSameEquipments(Set<Equipment> roomEquipments,Set<Equipment> meetingNeeds){
        int cpt = 0;
        for(Equipment equipment : meetingNeeds){
            if(roomEquipments.contains(equipment)){
                cpt++;
            }
        }
        return cpt;
    }

    @Override
    public void registerRoomSlot(Room optimalRoom, LocalDateTime slotStart){
        Slot slot = new Slot();
        slot.setStartSlot(slotStart);
        this.slotRepository.save(slot);
        Room room = this.roomRepository.findByName(optimalRoom.getName());
        slot.addRoom(room);
        room.addSlot(slot);
        this.roomRepository.save(room);
    }
}