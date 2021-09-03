package fr.canalplus.meetingplanner.models.repositories;
import fr.canalplus.meetingplanner.models.entities.Equipment;
import fr.canalplus.meetingplanner.models.entities.Room;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RunWith( SpringRunner.class )
@DataJpaTest
public class SlotRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    EquipmentRepository equipmentRepository;

    @Before
    public void setUp(){

    }

    @Test
    public void slotFindAllVide(){
        Iterable<Equipment> res = equipmentRepository.findAll();
        Assert.assertEquals(res, Collections.EMPTY_LIST);
    }

    @Test
    public void slotSave(){
        Equipment equipment = new Equipment();
        equipment.setEquipmentType("Ecran");
        Equipment res =equipmentRepository.save(equipment);

        Assert.assertEquals(res.getEquipmentType(),equipment.getEquipmentType());



    }
    @Test
    public void slotFindAllSaveWithRooms(){
        Equipment equipment = new Equipment();
        equipment.setEquipmentType("Ecran");
        Room room = new Room();
        room.setName("E1001");
        equipment.getRooms().add(room);
        Equipment res =equipmentRepository.save(equipment);

        Assert.assertEquals(res.getEquipmentType(),equipment.getEquipmentType());
        Assert.assertEquals(res.getRooms().size(),equipment.getRooms().size());
    }
    @Test
    public void slotFindAll2Equipment(){
        Equipment equipment1 = new Equipment();
        equipment1.setEquipmentType("Ecran");
        Equipment equipment2 = new Equipment();
        equipment2.setEquipmentType("Webcam");
        Collection<Equipment> res =(Collection)equipmentRepository.saveAll(List.of(equipment1,equipment2));
        Assert.assertEquals(res.size(),2);
    }
    @Test
    public void slotFindByTypeOk(){
        final String TYPE_EQUIPMENT = "Ecran";
        Equipment equipment = new Equipment();
        equipment.setEquipmentType(TYPE_EQUIPMENT);
        equipmentRepository.save(equipment);

        Equipment res = equipmentRepository.findByEquipmentType(TYPE_EQUIPMENT);
        Assert.assertEquals(res.getEquipmentType(),TYPE_EQUIPMENT);
    }
    @Test
    public void slotFindByTypeKo(){
        final String TYPE_EQUIPMENT = "Webcam";
        Equipment equipment = new Equipment();
        equipment.setEquipmentType(TYPE_EQUIPMENT);
        equipmentRepository.save(equipment);
        Equipment res = equipmentRepository.findByEquipmentType("NO_EQUIPMENT");
        Assert.assertNull(res);
   
    }


}
