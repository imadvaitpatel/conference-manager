package UseCase;

import Entity.Talk;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TalkManager implements Serializable {
    // list of talks
    private Set<Talk> talks;

    /**
     * Creates a new UseCase.TalkManager with no talks
     */
    public TalkManager() {
        talks = new HashSet<>();
    }

    /**
     * Fetches all of the talks of this UseCase.TalkManager
     * @return The list of talks
     */
    public Set<Talk> getTalks() {
        return talks;
    }

    /**
     * Fetches the names of the talks managed by the UseCase.TalkManager
     * @return A list of the names of the talks
     */
    public Set<String> getTalkNames() {
        Set<String> names = new HashSet<>();

        for (Talk talk : getTalks()) {
            names.add(talk.getName());
        }

        return names;
    }

    /**
     * Retrieves a Entity.Talk object with the given name
     * @param talkName The name of the talk
     * @return The Entity.Talk object with the given name
     */
    public Talk getTalkWithName(String talkName) {
        for (Talk talk : getTalks()) {
            if (talk.getName().equals(talkName)) {
                return talk;
            }
        }

        return null;
    }

    /**
     * Checks whether or not a talk with the given name exists
     * @param talkName The name of the talk
     * @return Returns true if and only if there is a talk with the given name
     */
    public boolean hasTalk(String talkName) {
        return getTalkNames().contains(talkName);
    }

    /**
     * Removes the talk with the given name
     * @param talkName The name of the talk
     */
    public void removeTalk(String talkName) {
        Talk talk = getTalkWithName(talkName);

        if (talk != null) {
            talks.remove(talk);
        }
    }

    /**
     * Creates a Entity.Talk from the given UseCase.TalkBuilder
     * @param talkBuilder The UseCase.TalkBuilder to be used
     */
    public void createTalk(TalkBuilder talkBuilder) {
        talks.add(talkBuilder.getInstance());
    }

    /**
     * Fetches the name of the speaker of the talk
     * @param talkName The name of the talk
     * @return The name of the speaker
     */
    public String getSpeaker(String talkName) {
        Talk talk = getTalkWithName(talkName);
        if (talk != null) {
            return talk.getSpeakerUsername();
        }
        return null;
    }

    /**
     * Assigns a new speaker to an existing talk.
     * @param talkName - The name of the talk.
     * @param speakerName - The name of the speaker.
     * @param userManager - A UseCase.UserManager.
     * @return True if and only if a new speaker was successfully assigned.
     */
    public boolean assignSpeaker(String talkName, String speakerName, UserManager userManager) {
        if (userManager.hasSpeaker(speakerName)) {
            Talk talk = getTalkWithName(talkName);
            Date[] time = new Date[2];
            time[0] = talk.getDateAndTime();
            time[1] = getOneHourLater(time[0]);

            EventValidator ev = new EventValidator();

            if (ev.isSpeakerAvailable(speakerName, time, userManager)) {
                String originalSpeaker = talk.getSpeakerUsername();
                userManager.getSpeaker(originalSpeaker).unassignEvent(talkName);

                talk.setSpeakerUsername(speakerName);

                userManager.getSpeaker(speakerName).assignEvent(talkName, time);

                return true;
            }
        }

        return false;
    }

    /**
     * Helper method which creates a Date object with a time one hour later than startTime
     * @param startTime - the returned Date object will be one hour after this time
     * @return - a Date object one hour after starTime
     */
    private Date getOneHourLater(Date startTime) {
        Calendar c = Calendar.getInstance();         // need Calendar to add time, not supported in Date class
        c.setTime(startTime);                       // convert Date to Calendar
        c.add(Calendar.HOUR, 1);

        return c.getTime();                         //return new Date object set to 1 hour later
    }
}
