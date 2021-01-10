package UseCase;

import Entity.Discussion;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class DiscussionManager implements Serializable {
    // list of discussions
    private Set<Discussion> discussions;

    /**
     * Creates a new UseCase.DiscussionManager with no talks
     */
    public DiscussionManager() {
        discussions = new HashSet<>();
    }

    /**
     * Fetches all of the discussions of this UseCase.DiscussionManager
     * @return The list of discussions
     */
    public Set<Discussion> getDiscussions() {
        return discussions;
    }

    /**
     * Fetches the names of the discussions managed by the UseCase.DiscussionManager
     * @return A list of the names of the discussions
     */
    public Set<String> getDiscussionNames() {
        Set<String> names = new HashSet<>();

        for (Discussion discussion : getDiscussions()) {
            names.add(discussion.getName());
        }

        return names;
    }

    /**
     * Retrieves a Entity.Discussion object with the given name
     * @param discussionName The name of the discussion
     * @return The Entity.Discussion object with the given name
     */
    public Discussion getDiscussionWithName(String discussionName) {
        for (Discussion discussion : getDiscussions()) {
            if (discussion.getName().equals(discussionName)) {
                return discussion;
            }
        }

        return null;
    }

    /**
     * Checks whether or not a discussion with the given name exists
     * @param discussionName The name of the discussion
     * @return Returns true if and only if there is a discussion with the given name
     */
    public boolean hasDiscussion(String discussionName) {
        return getDiscussionNames().contains(discussionName);
    }

    /**
     * Removes the discussion with the given name
     * @param discussionName The name of the discussion
     */
    public void removeDiscussion(String discussionName) {
        Discussion discussion = getDiscussionWithName(discussionName);

        if (discussion != null) {
            discussions.remove(discussion);
        }
    }

    /**
     * Creates a Entity.Discussion from the given UseCase.DiscussionBuilder
     * @param discussionBuilder The UseCase.DiscussionBuilder to be used
     */
    public void createDiscussion(DiscussionBuilder discussionBuilder) {
        discussions.add(discussionBuilder.getInstance());
    }

    /**
     * Fetches the set of speakers at the given discussion
     * @param discussionName The name of the discussion
     * @return The set of speakers
     */
    public Set<String> getSpeakers(String discussionName) {
        Discussion discussion = getDiscussionWithName(discussionName);
        if (discussion != null) {
            return discussion.getSpeakerUsernames();
        }

        return null;
    }

    /**
     * Adds a new speaker to an existing discussion.
     * @param discussionName - The name of the discussion
     * @param speakerName - The name of the speaker
     * @param userManager - A UseCase.UserManager
     * @return True if and only if a speaker was successfully added to the discussion
     */
    public boolean addSpeaker(String discussionName, String speakerName, UserManager userManager) {
        if(userManager.hasSpeaker(speakerName)) {
            Discussion discussion = getDiscussionWithName(discussionName);
            Date[] time = new Date[2];
            time[0] = discussion.getDateAndTime();
            time[1] = getOneHourLater(time[0]);

            EventValidator ev = new EventValidator();

            if(ev.isSpeakerAvailable(speakerName, time, userManager)) {
                discussion.addSpeaker(speakerName);
                userManager.getSpeaker(speakerName).assignEvent(discussionName, time);

                return true;
            }
        }
        return false;
    }

    /**
     * Removes a speaker from an existing discussion
     * @param discussionName - The name of the discussion
     * @param speakerName - The name of the speaker
     * @param userManager - A UseCase.UserManager
     */
    public void removeSpeaker(String discussionName, String speakerName, UserManager userManager) {
        if(userManager.hasSpeaker(speakerName)) {
            Discussion discussion = getDiscussionWithName(discussionName);

            if(discussion.getSpeakerUsernames().contains(speakerName)) {
                discussion.removeSpeaker(speakerName);
                userManager.getSpeaker(speakerName).unassignEvent(discussionName);
            }
        }
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
