package Entity;

import java.io.Serializable;
import java.util.Date;

public class Talk extends Event implements Serializable {

    // name of the speaker for this Entity.Talk
    private String speakerUsername;

    /**
     * Creates a new Entity.Talk. A Entity.Talk is an Entity.Event with exactly one speaker.
     * @param name - the name of this Entity.Talk
     * @param dateAndTime - the date and time of this Entity.Talk
     * @param roomCode - the code of the room this Entity.Talk is held
     * @param speakerUsername - the speaker for this Entity.Talk
     * @param capacity - the maximum number of attendees for this Entity.Talk
     */
    public Talk(String name, Date dateAndTime, String roomCode, String speakerUsername, int capacity) {
        super(name, dateAndTime, roomCode, capacity);
        this.speakerUsername = speakerUsername;
    }

    /**
     *
     * @return username of Entity.Speaker for this Entity.Talk.
     */
    public String getSpeakerUsername() {
        return speakerUsername;
    }

    /**
     * Changes the Entity.Speaker of this Entity.Event
     * @param newSpeakerUsername - username of a new Entity.Speaker for this Entity.Talk
     */
    public void setSpeakerUsername(String newSpeakerUsername) {
        speakerUsername = newSpeakerUsername;
    }
}
