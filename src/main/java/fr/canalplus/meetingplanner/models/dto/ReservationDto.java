
package fr.canalplus.meetingplanner.models.dto;

public class ReservationDto {
    private int nbPersons;
    private String meetingType;
    private  String startSlot;

    public ReservationDto(int nbPersons, String meetingType, String startSlot){
        this.nbPersons = nbPersons;
        this.meetingType = meetingType;
        this.startSlot = startSlot;
    }
    public ReservationDto(){}
    public int getNbPersons() {
        return nbPersons;
    }
    public void setNbPersons(int nbPersons) {
        this.nbPersons = nbPersons;
    }
    public String getMeetingType() {
        return meetingType;
    }
    public void setMeetingType(String meetingType) {
        this.meetingType = meetingType;
    }
    public String getStartSlot() {
        return startSlot;
    }
    public void setStartSlot(String startSlot) {
        this.startSlot = startSlot;
    }
}

