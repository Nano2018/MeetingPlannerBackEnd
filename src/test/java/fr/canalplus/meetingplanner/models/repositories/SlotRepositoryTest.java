package fr.canalplus.meetingplanner.models.repositories;
import fr.canalplus.meetingplanner.models.entities.Equipment;
import fr.canalplus.meetingplanner.models.entities.Room;
import fr.canalplus.meetingplanner.models.entities.Slot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Collections;

@RunWith( SpringRunner.class )
@DataJpaTest
public class SlotRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    SlotRepository slotRepository;

    @Before
    public void setUp(){

    }

    @Test
    public void noSlot(){
        Iterable<Slot> res = slotRepository.findAll();
        Assert.assertEquals(res, Collections.EMPTY_LIST);
    }

    @Test
    public void saveSlot(){
        Slot slot = new Slot();
        slot.setStartSlot(LocalDateTime.parse("2021-09-06T09:00:00"));
        Slot res =slotRepository.save(slot);
        Assert.assertEquals(slot.getStartSlot(),res.getStartSlot());

    }
    @Test
    public void saveSlotRoom(){
        Slot slot = new Slot();
        slot.setStartSlot(LocalDateTime.parse("2021-09-06T09:00:00"));
        Room room = new Room();
        room.setName("E1001");
        slot.getRooms().add(room);
        Slot res =slotRepository.save(slot);
        Assert.assertEquals(slot.getStartSlot(),res.getStartSlot());
        Assert.assertEquals(slot.getRooms().size(),res.getRooms().size());
    }

}
