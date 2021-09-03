package fr.canalplus.meetingplanner.models.repositories;
import fr.canalplus.meetingplanner.models.entities.MeetingType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

@RunWith( SpringRunner.class )
@DataJpaTest
public class MeetingTypeRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    MeetingTypeRepository meetingTypeRepository;

    @Before public void setUp(){}

    @Test
    public void setMeetingTypeFindAllVide(){
        Iterable<MeetingType> res = meetingTypeRepository.findAll();
        Assert.assertEquals(res, Collections.EMPTY_LIST);
    }

    @Test
    public void meetingTypeSave(){
        MeetingType meetingType = new MeetingType();
        meetingType.setMeetingType("VC");
        MeetingType res =meetingTypeRepository.save(meetingType);
        Assert.assertEquals(res.getMeetingType(),meetingType.getMeetingType());
    }

    @Test
    public void meetingTypeFindByTypeOk(){
        final String TYPE_MEETING = "VC";
        MeetingType meetingType = new MeetingType();
        meetingType.setMeetingType(TYPE_MEETING);
        meetingTypeRepository.save(meetingType);
        MeetingType res = meetingTypeRepository.findByMeetingType(TYPE_MEETING);
        Assert.assertEquals(res.getMeetingType(),TYPE_MEETING);

    }
    @Test
    public void meetingTypeFindByTypeKo(){
        final String TYPE_MEETING = "VC";
        MeetingType meetingType = new MeetingType();
        meetingType.setMeetingType(TYPE_MEETING);
        meetingTypeRepository.save(meetingType);
        MeetingType res = meetingTypeRepository.findByMeetingType("CV");
        Assert.assertNull(res);
    }

}
