package Entity;

import java.util.Date;

public class Party extends Event {
    /**
     * Creates a new Entity.Event
     *
     * @param name          - name of this Entity.Event
     * @param dateAndTime   - date and time of this Entity.Event
     * @param roomCode      - the code of the Entity.Room which this Entity.Event is held
     * @param eventCapacity - the maximum number of attendees for this Entity.Event
     *
     *                      **Precondition**
     * eventCapacity is less than or equal to the capacity this room is held in
     */
    public Party(String name, Date dateAndTime, String roomCode, int eventCapacity) {
        super(name, dateAndTime, roomCode, eventCapacity);
    }
}
