package Entity;

import Util.PermissionLevel;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Speaker extends User implements Serializable {

    // Mapping of the name of events this Entity.Speaker speaks at to the time this Entity.Speaker is speaking
    private Map<String, Date[]> schedule;

    /**
     * Constructs a Entity.Speaker with a given username and password.
     *
     * @param username The username used to log in as this user.
     * @param password The password used to log in as this user.
     * @param permissionLevel The Util.PermissionLevel of the user.
     */
    public Speaker(String username, String password, PermissionLevel permissionLevel) {
        super(username, password, permissionLevel);

        schedule = new HashMap<>();
    }

    /**
     *
     * @return the mapping of event names this Entity.Speaker is booked for to the timing of the event
     */
    public Map<String, Date[]> getSchedule() { return schedule; }

    /**
     * Add the name of event and the time of when this Entity.Speaker speaks at that event
     * @param eventName The name of the Entity.Event
     * @param timeOfSpeech An array containing the beginning and ending time of the speech
     *
     *                     **Precondition**
     * - timeOfSpeech has a length of 2, and the first Date element is chronologically before the second Date element
     * - timeOfSpeech does not conflict with any other Date value in eventNametoTime
     */
    public void assignEvent(String eventName, Date[] timeOfSpeech){
        schedule.put(eventName, timeOfSpeech);
    }

    /**
     * Removes the name of the event and time of when this Entity.Speaker was going to speak at that event
     * @param eventName The name of the event to be removed
     */
    public void unassignEvent(String eventName){
        schedule.remove(eventName);
    }

    /**
     * Change to Entity.Speaker Util.PermissionLevel
     *
     * @return The permission level of this Entity.User.
     */
    @Override
    public PermissionLevel getPermissionLevel() {
        return PermissionLevel.SPEAKER;
    }


}