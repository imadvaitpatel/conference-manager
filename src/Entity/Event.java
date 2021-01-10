package Entity;

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;
import java.util.Date;

public abstract class Event implements Serializable {

    // name of this Entity.Event
    private String name;

    // date and time of this Entity.Event
    private Date dateAndTime;

    // room this Entity.Event is held
    private String roomCode;

    // list of users attending this Entity.Event
    private Set<String> attendanceUsernames;

    // capacity of this Entity.Event
    private int capacity;

    // whether this Entity.Event is vip-only
    private boolean isVipEvent;

    /**
     * Creates a new Entity.Event
     * @param name - name of this Entity.Event
     * @param dateAndTime - date and time of this Entity.Event
     * @param roomCode - the code of the Entity.Room which this Entity.Event is held
     * @param eventCapacity - the maximum number of attendees for this Entity.Event
     *
     *                 **Precondition**
     * eventCapacity is less than or equal to the capacity this room is held in
     */

    public Event(String name, Date dateAndTime, String roomCode, int eventCapacity) {
        this.name = name;
        this.dateAndTime = dateAndTime;
        this.roomCode = roomCode;
        this.capacity = eventCapacity;
        attendanceUsernames = new HashSet<>();
    }

    /**
     * Changes the name of this Entity.Event
     * @param newName - new name for this Entity.Event
     */
    public void setName(String newName) {
        name = newName;
    }


    /**
     * Changes the date of this Entity.Event
     * @param newDate - new date of this Entity.Event
     */
    public void setDate(Date newDate)  { dateAndTime = newDate; }

    /**
     * Changes the room of this Entity.Event
     * @param newRoomCode - code for new Entity.Room for this Entity.Event
     */
    public void setRoom(String newRoomCode) {
        roomCode = newRoomCode;
    }

    /**
     * Changes the capacity of this Entity.Event
     * @param newCapacity - new capacity of this Entity.Event
     *
     *                    **Precondition**
     * newCapacity is less than or equal to the capacity of the room this Entity.Event is held in
     * newCapacity is greater than or equal to the number of attendees for this event
     */
    public void setCapacity(int newCapacity) {
        capacity = newCapacity;
    }

    /**
     * Changes whether this event is vip-only or not
     * @param isVipEvent - new state of this event
     */
    public void setVipEvent(boolean isVipEvent)  {
        this.isVipEvent = isVipEvent; }

    /**
     * Adds newAttendee to the list of attendance
     * @param newUsername - username of new attendee to be added
     */
    public void addAttendee(String newUsername) {
        attendanceUsernames.add(newUsername);
    }

    /**
     * Adds every attendee in the list newAttendees
     * @param newUsernames - list of usernames of new attendees to be added to this Entity.Event
     */
    public void addAllAttendees(Collection<String> newUsernames) { attendanceUsernames.addAll(newUsernames); }

    /**
     * Removes an attendee from the list of attendance for this Entity.Event
     * @param username - username of an attendee to be removed from the list of attendance
     */
    public void removeAttendee(String username) { attendanceUsernames.remove(username); }

    /**
     *
     * @param usernames list of usernames for the attendees to be removed from this Entity.Event
     */
    public void removeAllAttendees(Collection<String> usernames) { attendanceUsernames.removeAll(usernames); }

    /**
     *
     * @return name of this Entity.Event.
     */
    public String getName() { return name; }


    /**
     *
     * @return date of this Entity.Event.
     */
    public Date getDateAndTime() {
        return dateAndTime;
    }

    /**
     *
     * @return code for the room this Entity.Event is held in.
     */
    public String getRoomCode() {  return roomCode; }

    /**
     *
     * @return list of usernames for attendees of this Entity.Event.
     */
    public Set<String> getAttendees() {
        return attendanceUsernames;
    }

    /**
     *
     * @return the maximum number of attendees for this event
     */
    public int getCapacity(){ return capacity; }

    /**
     *
     * @return whether this event is vip-only or not.
     */
    public boolean getIsVipOnly(){ return isVipEvent; }
}
