package fr.canalplus.meetingplanner.exceptions;

public class NoRoomAvailableException extends Exception{
    public NoRoomAvailableException(String message) {
        super(message);
    }
}
