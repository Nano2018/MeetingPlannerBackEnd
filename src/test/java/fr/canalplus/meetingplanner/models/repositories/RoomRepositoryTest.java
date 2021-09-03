package fr.canalplus.meetingplanner.models.repositories;
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
public class RoomRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    RoomRepository roomRepository;

    @Before
    public void setUp(){

    }

    @Test
    public void roomFindAllVide(){
        Iterable<Room> res = roomRepository.findAll();
        Assert.assertEquals(res, Collections.EMPTY_LIST);
    }

    @Test
    public void roomSave(){
        Room room = new Room();
        room.setName("E1001");
        room.setCapacity(32);
        Room res =roomRepository.save(room);

        Assert.assertEquals(res.getName(),room.getName());
        Assert.assertEquals(res.getCapacity(),room.getCapacity());
    }

    @Test
    public void roomFindAll2Rooms(){
        Room room1 = new Room();
        room1.setName("E1001");
        room1.setCapacity(32);
        Room room2 = new Room();
        room2.setName("E1002");
        room2.setCapacity(32);
        roomRepository.saveAll(List.of(room1,room2));


        Collection<Room> res = (Collection)roomRepository.findAll();
        Assert.assertEquals(res.size(),2);
    }



}
