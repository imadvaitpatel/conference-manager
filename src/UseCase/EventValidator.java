package UseCase;

import Entity.Room;
import Entity.Speaker;
import UseCase.EventManager;
import UseCase.RoomManager;
import UseCase.UserManager;

import java.util.Calendar;
import java.util.Date;

public class EventValidator {

    //This class contains methods involved in the creation/manipulation of Events

    /**
     * Checks if an event without a speaker can be created with the given parameters. This event can be created if:
     * - No other event has the same name as this event
     * - The room of this event is not occupied during the time of this event
     * - The capacity of this event does not exceed the room capacity
     * @param name The name of this event
     * @param roomCode The code of the room this event is held in
     * @param capacity The maximum number of attendees allowed for this event
     * @param time The time of this event
     * @param em An UseCase.EventManager
     * @param rm A UseCase.RoomManager
     * @return True if and only if an event without a speaker can be created with these parameters.
     */
    public boolean isValidSpeakerLessEvent(String name, String roomCode, int capacity, Date[] time, EventManager em,
                                           RoomManager rm) {
        if(isEventNameTaken(name, em) || !isRoomAvailable(roomCode, time, em, rm) ||
                !isValidEventCapacity(roomCode, capacity, rm)) {
            return false;
        }
        return true;
    }

    /**
     * Checks if an event with a speaker can be create with the given parameters. This event can be created if:
     * - No other event has the same name as this event
     * - The room of this event is not occupied during the time of this event
     * - The capacity of this event does not exceed the room capacity
     * - The speaker is available during the time of this event
     * @param name The name of this event
     * @param speakerName The name of the speaker for this event
     * @param roomCode The code of the room for this event
     * @param capacity The maximum number of attendees allowed for this event
     * @param time The time of this event
     * @param em An UseCase.EventManager
     * @param um A UseCase.UserManager
     * @param rm A UseCase.RoomManager
     * @return True if and only if an event with a speaker can be created with these parameters.
     */
    public boolean isValidSpeakerEvent(String name, String speakerName, String roomCode, int capacity, Date[] time,
                                        EventManager em, UserManager um, RoomManager rm) {

        return isValidSpeakerLessEvent(name, roomCode, capacity, time, em, rm)
                && isSpeakerAvailable(speakerName, time, um);

    }

    /**
     * Checks to see if there is an existing event already using a given name
     * @param name - The name
     * @param em - An UseCase.EventManager
     * @return True if and only if name is already being used by an existing event
     */
    public boolean isEventNameTaken(String name, EventManager em) {
        return em.getEventNames().contains(name);
    }

    /**
     * Checks to see if an event is allowed to have a given capacity while using a certain room. An event is not allowed
     * to have a capacity exceeding the room's capacity.
     * @param roomCode The code of the room for the event
     * @param capacity The capacity of some event
     * @param rm A UseCase.RoomManager
     * @return True if and only if the event is allowed to have this capacity.
     *
     *                 **Precondition**
     * A room with the given room code exists
     */
    public boolean isValidEventCapacity(String roomCode, int capacity, RoomManager rm) {
        Room room = rm.getRoomWithCode(roomCode);
        return capacity <= room.getCapacity() && capacity > 0;
    }

//    /**
//     * Checks to see if an event can change its capacity to a given new capacity.
//     * @param eventName The name of the event
//     * @param newCapacity The new capacity
//     * @param em An UseCase.EventManager
//     * @param rm A UseCase.RoomManager
//     * @return True if and only if the event can change its capacity to the new capacity
//     *
//     *                  **Precondition**
//     * An event with the given name exists
//     * A room with the given room code exists
//     */
//    public boolean canChangeCapacity(String eventName, int newCapacity, UseCase.EventManager em, UseCase.RoomManager rm) {
//        String roomCode = em.getRoomCodeOfEvent(eventName);
//        return isValidEventCapacity(roomCode, newCapacity, rm) && newCapacity >= em.getAttendeesOfEvent(eventName).size();
//        // returns true if new capacity does not exceed room capacity AND new capacity is not smaller than the
//        // current number of attendees for the event
//    }

    /**
     * Checks if a Entity.Speaker is available during a given time interval
     * @param speakerName - name of the speaker
     * @param time - an array where the first element is the beginning of the time interval and the second element is
     *             the end of the time interval (inclusive).
     * @param um - UseCase.UserManager object used to access the speaker with given speakerName
     * @return true if the Entity.Speaker is not scheduled for an event at any point in the time interval
     *
     *                                    **Precondition**
     * - time is of length 2
     * - time[0] is chronologically before time[1].
     */
    public boolean isSpeakerAvailable(String speakerName, Date[] time, UserManager um) {
        if(!um.hasSpeaker(speakerName)) {
            return false;
        }
        Speaker speaker = um.getSpeaker(speakerName);
        for (String eventName : speaker.getSchedule().keySet()) {
            Date startTime = speaker.getSchedule().get(eventName)[0];
            Date endTime = speaker.getSchedule().get(eventName)[1];

            if(time[0].before(endTime) && time[1].after(startTime)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a room is available during a given time interval
     * @param roomCode - room code of the room
     * @param time - an array where the first element is the beginning of the time interval and the second element is
     *             the end of the time interval (inclusive).
     * @param em - UseCase.EventManager object used to access the time intervals of every event held in this room
     * @param rm - UseCase.RoomManager object used to access the room with given roomCode
     * @return true if the room is not scheduled for an event at any point in the time interval
     *
     *                                  **Precondition**
     * - time is of length 2
     * - time[0] is chronologically before time[1].
     */
    public boolean isRoomAvailable(String roomCode, Date[] time, EventManager em, RoomManager rm) {
        if(!rm.hasRoom(roomCode)) {
            return false;
        }

        for (String eventName : rm.getRoomWithCode(roomCode).getEvents()) {
            Date startTime = em.getEventWithName(eventName).getDateAndTime();
            Date endTime = getOneHourLater(startTime);

            if(time[0].before(endTime) && time[1].after(startTime)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Helper method that returns a Date object exactly 1 hour after the given time.
     * @param startTime - A Date object of some time
     * @return A Date object set for exactly 1 hour after startTime
     */
    private Date getOneHourLater(Date startTime) {
        Calendar c = Calendar.getInstance();         // need Calendar to add time, not supported in Date class
        c.setTime(startTime);                       // convert Date to Calendar
        c.add(Calendar.HOUR, 1);
        Date oneHourLater = c.getTime();                // new Date object set one hour later

        return oneHourLater;
    }
}
