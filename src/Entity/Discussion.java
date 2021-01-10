package Entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Discussion extends Event implements Serializable {

    // names of the speakers for this Entity.Discussion
    private Set<String> speakerUsernames;

    /**
     * Creates a new Entity.Discussion. A Entity.Discussion is an Entity.Event with multiple speakers.
     *
     * @param name - name of this Entity.Discussion
     * @param dateAndTime - date and time of this Entity.Discussion
     * @param roomCode - code of the room this Entity.Discussion is held
     * @param speakerUsernames - a collection of speakers for the event
     * @param capacity - the maximum number of attendees for this Entity.Event
     */
    public Discussion(String name, Date dateAndTime, String roomCode, Collection<String> speakerUsernames, int capacity) {
        super(name, dateAndTime, roomCode, capacity);
        this.speakerUsernames = new HashSet<>();
        this.speakerUsernames.addAll(speakerUsernames);
    }

    /**
     *return the usernames of all speakers in this Discussion
     */
    public Set<String> getSpeakerUsernames() {
        return speakerUsernames;
    }

    /**
     * add new speaker to this Discussion
     *
     * @param speakerUsername - username of this speaker
     */
    public void addSpeaker(String speakerUsername) {
        speakerUsernames.add(speakerUsername);
    }

    /**
     * remove this speaker from this Discussion
     *
     * @param speakerUsername - username of this speaker
     */
    public void removeSpeaker(String speakerUsername) {
        speakerUsernames.remove(speakerUsername);
    }
}
