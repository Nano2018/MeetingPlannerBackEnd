package fr.canalplus.meetingplanner.services;
import fr.canalplus.meetingplanner.exceptions.InvalidSlotException;
import fr.canalplus.meetingplanner.exceptions.NoRoomAvailableException;
import fr.canalplus.meetingplanner.exceptions.UnknownMeetingTypeException;
import fr.canalplus.meetingplanner.models.dto.ReservationDto;
import fr.canalplus.meetingplanner.models.dto.RoomDto;
import fr.canalplus.meetingplanner.models.entities.Equipment;
import fr.canalplus.meetingplanner.models.entities.Slot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@RunWith( SpringRunner.class )
@SpringBootTest
public class MeetingPlannerServiceTest {

    @Autowired
    MeetingPlannerService meetingPlannerService;

    @Before
    public void setUp(){}

    @Test
    public void roomAvailableAtSlot(){
        Set<Slot> slots = new HashSet<>();
        Slot slot1 = new Slot(); slot1.setStartSlot(LocalDateTime.parse("2021-09-06T09:00:00"));
        slots.add(slot1);
        Slot slot2 = new Slot(); slot2.setStartSlot(LocalDateTime.parse("2021-09-06T11:00:00"));
        slots.add(slot2);
        Assert.assertTrue(meetingPlannerService.isRoomAvailableAtSlot(LocalDateTime.parse("2021-09-06T13:00:00"),slots));
    }

    @Test
    public void noRoomAvailableAtSlot(){
        Set<Slot> slots = new HashSet<>();
        Slot slot1 = new Slot();
        slot1.setStartSlot(LocalDateTime.parse("2021-09-06T09:00:00"));
        slots.add(slot1);
        Slot slot2 = new Slot();
        slot2.setStartSlot(LocalDateTime.parse("2021-09-06T11:00:00"));
        slots.add(slot2);
        Assert.assertFalse(meetingPlannerService.isRoomAvailableAtSlot(LocalDateTime.parse("2021-09-06T08:00:00"),slots));
    }

    @Test
    public void roomContainAllNeedsEquipments(){
        Equipment equipment1 = new Equipment(); equipment1.setEquipmentType("Ecran");
        Equipment equipment2 = new Equipment(); equipment1.setEquipmentType("Webcam");
        Equipment equipment3 = new Equipment(); equipment1.setEquipmentType("Pieuvre");
        Set <Equipment> roomEquipments = new HashSet<>();
        roomEquipments.add(equipment1);
        roomEquipments.add(equipment2);
        roomEquipments.add(equipment3);
        Set <Equipment> meetingsNeeds = new HashSet<>();
        meetingsNeeds.add(equipment2);
        meetingsNeeds.add(equipment3);
        Assert.assertTrue(meetingPlannerService.isRoomContainAllNeedsEquipments(roomEquipments,meetingsNeeds));
    }

    @Test
    public void roomNotContainAllNeedsEquipments(){
        Equipment equipment1 = new Equipment(); equipment1.setEquipmentType("Ecran");
        Equipment equipment2 = new Equipment(); equipment1.setEquipmentType("Webcam");
        Equipment equipment3 = new Equipment(); equipment1.setEquipmentType("Pieuvre");
        Set <Equipment> roomEquipments = new HashSet<>();
        roomEquipments.add(equipment1);
        roomEquipments.add(equipment2);
        Set <Equipment> meetingsNeeds = new HashSet<>();
        meetingsNeeds.add(equipment1);
        meetingsNeeds.add(equipment2);
        meetingsNeeds.add(equipment3);
        Assert.assertFalse(meetingPlannerService.isRoomContainAllNeedsEquipments(roomEquipments,meetingsNeeds));
    }

    @Test
    public void availableReserveEquipments(){
        Equipment equipment = new Equipment(); equipment.setEquipmentType("Ecran");
        Assert.assertTrue(meetingPlannerService.isAvailableReserveEquipments(equipment));
    }


    @Test
    public void nbSameElements(){
        Equipment equipment1 = new Equipment(); equipment1.setEquipmentType("Ecran");
        Equipment equipment2 = new Equipment(); equipment1.setEquipmentType("Webcam");
        Equipment equipment3 = new Equipment(); equipment1.setEquipmentType("Pieuvre");
        Set <Equipment> equipments1 = new HashSet<>();
        equipments1.add(equipment1);
        equipments1.add(equipment2);
        equipments1.add(equipment3);
        Set <Equipment> equipments2 = new HashSet<>();
        equipments2.add(equipment2);
        equipments2.add(equipment3);
        Assert.assertEquals(2,meetingPlannerService.nbSameEquipments(equipments1,equipments2));

    }

    @Test
    public void getAvailableRoomOKTest() throws InvalidSlotException, NoRoomAvailableException, UnknownMeetingTypeException {
        ReservationDto reservationDto = new ReservationDto(9,"VC","2021-09-06T09:00:00");
        RoomDto roomDto = meetingPlannerService.getAvailableRoom(reservationDto);
        Assert.assertNotNull(roomDto);
    }

    @Test(expected = NoRoomAvailableException.class)
    public void getAvailableRoomNoRoomAvailableTest() throws InvalidSlotException, NoRoomAvailableException, UnknownMeetingTypeException {
        ReservationDto reservationDto = new ReservationDto(23,"VC","2021-09-06T08:00:00");
        meetingPlannerService.getAvailableRoom(reservationDto);

    }

    @Test(expected = UnknownMeetingTypeException.class)
    public void getAvailableRoomUnknownMeetingTypeTest() throws InvalidSlotException, NoRoomAvailableException, UnknownMeetingTypeException {
        ReservationDto reservationDto = new ReservationDto(23,"VTC","2021-09-06T08:00:00");
        meetingPlannerService.getAvailableRoom(reservationDto);

    }

    @Test(expected = InvalidSlotException.class)
    public void getAvailableRoomWeekEndReservationTest() throws InvalidSlotException, NoRoomAvailableException, UnknownMeetingTypeException {
        ReservationDto reservationDto = new ReservationDto(23,"VTC","2021-09-04T08:00:00");
        meetingPlannerService.getAvailableRoom(reservationDto);

    }

    @Test(expected = InvalidSlotException.class)
    public void getAvailableRoomExpiredDate() throws InvalidSlotException, NoRoomAvailableException, UnknownMeetingTypeException {
        ReservationDto reservationDto = new ReservationDto(23,"VTC","2021-08-04T08:00:00");
        meetingPlannerService.getAvailableRoom(reservationDto);

    }

    @Test
    public void getAvailableRoomFromReserveTest() throws InvalidSlotException, NoRoomAvailableException, UnknownMeetingTypeException {
        ReservationDto reservationDto1 = new ReservationDto(8,"VC","2021-09-06T09:00:00");
        ReservationDto reservationDto2 = new ReservationDto(6,"VC","2021-09-06T09:00:00");
        meetingPlannerService.getAvailableRoom(reservationDto1);
        RoomDto roomDto = meetingPlannerService.getAvailableRoom(reservationDto2);
        Assert.assertNotNull(roomDto);
        Assert.assertTrue(roomDto.getReserves().size() > 0);
    }

}
