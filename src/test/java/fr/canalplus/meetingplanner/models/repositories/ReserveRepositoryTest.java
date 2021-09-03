package fr.canalplus.meetingplanner.models.repositories;

import fr.canalplus.meetingplanner.models.entities.Reserve;
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
public class ReserveRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    ReserveRepository reserveRepository;

    @Before
    public void setUp(){

    }

    @Test
    public void noReserve(){
        Iterable<Reserve> res = reserveRepository.findAll();
        Assert.assertEquals(res, Collections.EMPTY_LIST);
    }

    @Test
    public void saveReserve(){
        Reserve reserve = new Reserve();
        reserve.setNumber(5);
        reserve.setEquipmentType("Ecran");
        Reserve res =reserveRepository.save(reserve);
        Assert.assertEquals(reserve.getEquipmentType(),res.getEquipmentType());
        Assert.assertEquals(reserve.getNumber(),res.getNumber());

    }

}
