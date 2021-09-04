package fr.canalplus.meetingplanner.controllers;
import fr.canalplus.meetingplanner.services.MeetingPlannerServiceImpl;
import fr.canalplus.meetingplanner.models.dto.ReservationDto;
import fr.canalplus.meetingplanner.models.dto.RoomDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/meetingPlanner")
@CrossOrigin("http://localhost:4200")
public class MeetingPlannerController {
    MeetingPlannerServiceImpl meetingPlannerService;
    @Autowired
    public MeetingPlannerController(MeetingPlannerServiceImpl meetingPlannerService){
        this.meetingPlannerService = meetingPlannerService;
    }

    @PostMapping("/rooms")
    @ResponseBody
    public ResponseEntity<?> getAvailableRoom(@RequestBody ReservationDto reservation){
        try{
            RoomDto room =  this.meetingPlannerService.getAvailableRoom(reservation);
            return ResponseEntity.status(HttpStatus.OK).body(room);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }
}
