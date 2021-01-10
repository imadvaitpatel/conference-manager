package UseCase;

import Entity.Speaker;
import Entity.User;
import Util.PermissionLevel;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class UserManager implements Serializable {
    // The set of users being managed
    private Set<User> nonSpeakers;
    private Set<Speaker> speakers;


    /**
     * Creates a new empty UseCase.UserManager instance.
     */
    public UserManager() {
        nonSpeakers = new HashSet<>();
        speakers = new HashSet<>();


        // Creates a default admin user
        UserBuilder userBuilder = new UserBuilder();
        userBuilder.buildUsername("HeadOrganizer");
        userBuilder.buildPassword("1234");
        userBuilder.buildPermissionLevel(PermissionLevel.ORGANIZER);

        createNonSpeaker(userBuilder);

    }


    /**vip
     * Fetches the Users managed by this UseCase.UserManager.
     * @return The Users of this UseCase.UserManager.
     */
    public Set<User> getUsers() {
        Set<User> allUsers = new HashSet<>();
        allUsers.addAll(nonSpeakers);
        allUsers.addAll(speakers);

        return allUsers;
    }

    /**
     * Fetches the Speakers managed by this UseCase.UserManager.
     * @return The Speakers of this UseCase.UserManager.
     */
    public Set<Speaker> getSpeakers() {
        return speakers;
    }

    /**
     * Fetches the usernames of all users managed by this UseCase.UserManager
     * @return The set of usernames
     */
    public Set<String> getUserNames() {
        Set<String> usernames = new HashSet<>();

        for (User user : getUsers()) {
            usernames.add(user.getUsername());
        }

        return usernames;
    }

    /**
     * Fetches the usernames of all speakers managed by this UseCase.UserManager
     * @return The set of usernames.
     */
    public Set<String> getSpeakerNames() {
        Set<String> usernames = new HashSet<>();

        for (Speaker speaker: getSpeakers()) {
            usernames.add(speaker.getUsername());
        }

        return usernames;
    }

    /**
     * Removes the user with the given username from the set of users.
     * @param username The username of the user to be removed.
     */
    public void removeUser(String username) {
        if (hasSpeaker(username)){
            speakers.remove(getSpeaker(username));
        }
        else if (hasNonSpeaker(username)) {
            nonSpeakers.remove(getUser(username));
        }
    }

    /**
     * Checks if a user with the given username exists.
     * @param username The username being checked.
     * @return True if and only if a user with the given username exists.
     */
    public boolean hasUser(String username){
        return hasNonSpeaker(username) || hasSpeaker(username);
    }

    /**
     * Checks if a nonSpeaker with the given username exists.
     * @param username The username being checked.
     * @return True if and only if a nonSpeaker with the given username exists.
     */
    public boolean hasNonSpeaker(String username) {
        for (User nonSpeaker : nonSpeakers) {
            if (nonSpeaker.getUsername().equals(username)){
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if a speaker with the given username exists.
     * @param username The username being checked.
     * @return True if and only if a speaker with the given username exists.
     */
    public boolean hasSpeaker(String username){
        for (Speaker speaker : speakers) {
            if (speaker.getUsername().equals(username)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Fetches the user with the given username.
     * @param username The username of the user being fetched.
     * @return The user with the given username or null if no user has that username.
     */
    public User getUser(String username) {
        if (hasSpeaker(username)){
            return getSpeaker(username);
        }

        for (User nonSpeaker : nonSpeakers) {
            if (nonSpeaker.getUsername().equals(username)) {
                return nonSpeaker;
            }
        }

        return null;
    }

    /**
     * Fetches the speaker with the given username.
     * @param username The username of the speaker being fetched.
     * @return The speaker with the given username or null if no user has that username.
     */
    public Speaker getSpeaker(String username) {
        for (Speaker speaker : speakers) {
            if (speaker.getUsername().equals(username)) {
                return speaker;
            }
        }

        return null;
    }

    /**
     * Creates a new Entity.User and adds it to the set of Users.
     * @param userBuilder The UseCase.UserBuilder instance which will create the new user.
     * @return The newly created Entity.User.
     */
    public User createNonSpeaker(UserBuilder userBuilder) {
        User newUser = userBuilder.getInstance();
        nonSpeakers.add(newUser);

        return newUser;
    }

    /**
     * Creates a new Entity.Speaker and adds it to the set of Users and to the set of Speakers.
     * @param speakerBuilder The UseCase.SpeakerBuilder instance which will create the new speaker.
     * @return The newly created Entity.Speaker.
     */
    public Speaker createSpeaker(SpeakerBuilder speakerBuilder) {
        Speaker newSpeaker = speakerBuilder.getInstance();
        speakers.add(newSpeaker);

        return newSpeaker;
    }

    /**
     * Checks whether or not the Entity.User with 'username' is signed up for the event with 'eventName'.
     * @param username The username of the Entity.User.
     * @param eventName The name of the event.
     * @return True if and only if the Entity.User with username 'username' is signed up for the event with name 'eventName'.
     */
    public boolean isSignedUp(String username, String eventName) {
        if (hasUser(username)) {
            User currUser = getUser(username);

            return currUser.isSignedUp(eventName);
        }

        return false;
    }

    /**
     * Signs a Entity.User up for an event.
     * @param username The username of the Entity.User.
     * @param eventName The name of the Entity.Event.
     */
    public void addEvent(String username, String eventName) {
        if (hasUser(username)) {
            User currUser = getUser(username);

            currUser.addEvent(eventName);
        }
    }

    /**
     * Removes an event from a Entity.User.
     * @param username The username of the Entity.User.
     * @param eventName The name of the Entity.Event.
     */
    public void removeEvent(String username, String eventName) {
        if (hasUser(username)) {
            User currUser = getUser(username);

            if (currUser.isSignedUp(eventName)) {
                currUser.removeEvent(eventName);
            }
        }
    }

    /**
     * Fetches all of the events the user is signed up for.
     * @param username The username of the Entity.User.
     * @return A collection of all the events the user is signed up for.
     */
    public Collection<String> getEvents(String username) {
        if (hasUser(username)) {
            return getUser(username).getEventList();
        }

        return null;
    }

    /**
     * Fetches the permission level of the Entity.User with the given username.
     * @param username The username of the Entity.User.
     * @return The permission level of the user.
     */
    public PermissionLevel getPermissionLevel(String username) {
        if (hasUser(username)) {
            return getUser(username).getPermissionLevel();
        }

        return null;
    }

    /**
     * Gets a collection of events which are hosted by a given speaker.
     * @param speakerName The name of the speaker.
     * @return The collection of event names which are hosted by the given speaker.
     */
    public Collection<String> getHostedEvents(String speakerName) {
        if (hasSpeaker(speakerName)){
            Speaker speaker = getSpeaker(speakerName);
            return speaker.getSchedule().keySet();
        } else {
            return null;
        }
    }

    /**
     * Add an event to the list of events the speaker is speaking at
     * @param speakerName The name of the speaker
     * @param eventName The name of the event
     */
    public void addEventToSpeaker(String speakerName, String eventName, Date[] timeOfSpeech) {
        getSpeaker(speakerName).assignEvent(eventName, timeOfSpeech);
    }

    /**
     * Remove an event from the list of events the speaker is speaking at
     * @param speakerName The name of the speaker
     * @param eventName The name of the event
     */
    public void removeEventFromSpeaker(String speakerName, String eventName) {
        getSpeaker(speakerName).unassignEvent(eventName);
    }

    /**
     * Get the avaliable speakers
     * @param time find avaliable speakers during this time
     * @return String list of all avaliable speakers
     */
    public Collection<String> getAvailableSpeakers(Date[] time) {
        EventValidator ev = new EventValidator();
        Set<String> availableSpeakers = new HashSet<>();
        for(String speaker: getSpeakerNames()) {
            if(ev.isSpeakerAvailable(speaker, time, this)) {
                availableSpeakers.add(speaker);
            }
        }
        return availableSpeakers;
    }

}
