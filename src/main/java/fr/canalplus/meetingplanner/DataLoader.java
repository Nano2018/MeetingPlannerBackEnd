package fr.canalplus.meetingplanner;

import fr.canalplus.meetingplanner.models.entities.Equipment;
import fr.canalplus.meetingplanner.models.repositories.MeetingTypeRepository;
import fr.canalplus.meetingplanner.models.entities.MeetingType;
import fr.canalplus.meetingplanner.models.entities.Reserve;
import fr.canalplus.meetingplanner.models.entities.Room;
import fr.canalplus.meetingplanner.models.repositories.EquipmentRepository;
import fr.canalplus.meetingplanner.models.repositories.ReserveRepository;
import fr.canalplus.meetingplanner.models.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements ApplicationRunner {

    private RoomRepository roomRepository;
    private ReserveRepository reserveRepository;
    private MeetingTypeRepository meetingTypeRepository;
    private EquipmentRepository equipmentRepository;
    @Autowired
    public DataLoader(RoomRepository roomRepository, ReserveRepository reserveRepository, MeetingTypeRepository meetingTypeRepository, EquipmentRepository equipmentRepository){
        this.roomRepository = roomRepository;
        this.reserveRepository = reserveRepository;
        this.meetingTypeRepository = meetingTypeRepository;
        this.equipmentRepository = equipmentRepository;
    }

    public void run(ApplicationArguments args){
        List<String> equipments = Arrays.asList("Ecran","Pieuvre","Webcam","Tableau");
        List<String> roomsName = Arrays.asList("E1001","E1002","E1003","E1004","E2001","E2002","E2003","E2004","E3001","E3002","E3003","E3004");
        List<Integer> roomsCapacity = Arrays.asList(23,10,8,4,4,15,7,9,13,8,9,4);
        List<List<String>> roomsEquipments = Arrays.asList(null,List.of("Ecran"),List.of("Pieuvre"),List.of("Tableau"),null,List.of("Ecran","Webcam"),null,List.of("Tableau"),List.of("Ecran","Webcam","Pieuvre"),null,List.of("Ecran","Pieuvre"),null);
        List<String> reserveEquipments = Arrays.asList("Ecran","Pieuvre","Webcam","Tableau");
        List<Integer> reserveEquipmentsNumber = Arrays.asList(5,4,4,2);
        List<String> meetingTypes = Arrays.asList("VC","SPEC","RS","RC");
        List<List<String>> meetingNeeds = Arrays.asList(List.of("Ecran","Pieuvre","Webcam"),List.of("Tableau"),null,List.of("Tableau","Ecran","Pieuvre"));

        equipments.forEach((_equipment)->{
            Equipment equipment = new Equipment();
            equipment.setEquipmentType(_equipment);
            this.equipmentRepository.save(equipment);
        });

        List<Reserve> reserves = new ArrayList<>();
        for(int j=0; j < reserveEquipments.size(); j++){
            Reserve reserve = new Reserve();
            reserve.setEquipmentType(reserveEquipments.get(j));
            reserve.setNumber(reserveEquipmentsNumber.get(j));
            reserves.add(reserve);
        }
        this.reserveRepository.saveAll(reserves);

        for(int j =0; j < meetingTypes.size(); j++){
            MeetingType meetingType = new MeetingType();
            meetingType.setMeetingType(meetingTypes.get(j));
            this.meetingTypeRepository.save(meetingType);
            try{
                meetingNeeds.get(j).forEach((_equipment) ->{
                    Equipment equipment = this.equipmentRepository.findByEquipmentType(_equipment);
                    if(equipment != null){
                        equipment.addMeeting(meetingType);
                        meetingType.addEquipment(equipment);
                        this.equipmentRepository.save(equipment);
                    }
                });

            }catch (NullPointerException e){
            }

        }
        for(int i =0; i < roomsName.size(); i++){
            Room room = new Room();
            room.setName(roomsName.get(i));
            room.setCapacity(roomsCapacity.get(i));
            this.roomRepository.save(room);
            try{
                roomsEquipments.get(i).forEach((_equipment) ->{
                    Equipment equipment = this.equipmentRepository.findByEquipmentType(_equipment);
                    if(equipment != null){
                        equipment.addRoom(room);
                        room.addEquipment(equipment);
                        this.equipmentRepository.save(equipment);
                    }
                });

            }catch (NullPointerException e){
            }

        }
    }
}
