package UseCase;

import Entity.*;

import java.io.Serializable;
import java.util.*;

public class EventManager implements Serializable {

    // Manages the parties
    private PartyManager partyManager;

    // Manages the talks
    private TalkManager talkManager;

    // Manages the discussions
    private DiscussionManager discussionManager;


    /**
     * Create a new UseCase.EventManager with no events
     */
    public EventManager() {
        partyManager = new PartyManager();
        talkManager = new TalkManager();
        discussionManager = new DiscussionManager();
    }

    /**
     * Fetches ALL of the events of this UseCase.EventManager
     * @return the list of ALL events managed by this UseCase.EventManager
     */
    public Set<Event> getEvents() {
        Set<Event> allEvents = new HashSet<>();
        allEvents.addAll(partyManager.getParties());
        allEvents.addAll(talkManager.getTalks());
        allEvents.addAll(discussionManager.getDiscussions());

        return allEvents;
    }

    /**
     * Fetches the speakerless events of this UseCase.EventManager
     * @return the list of all speakerless events managed by this UseCase.EventManager
     */
    public Set<String> getParties(){
        return partyManager.getPartyNames();
    }

    /**
     * Fetches the Talks of this UseCase.EventManager
     * @return the list of all talks managed by this UseCase.EventManager
     */
    public Set<String> getTalks(){
        return talkManager.getTalkNames();
    }

    /**
     * Fetches the discussions of this UseCase.EventManager
     * @return the list of all discussions managed by this UseCase.EventManager
     */
    public Set<String> getDiscussions(){
        return discussionManager.getDiscussionNames();
    }


    /**
     * Fetches the names of ALL the events managed by this UseCase.EventManager
     * @return a list of ALL the names of events managed by this UseCase.EventManager
     */
    public Set<String> getEventNames() {
        Set<String> eventNames = new HashSet<>();
        eventNames.addAll(partyManager.getPartyNames());
        eventNames.addAll(talkManager.getTalkNames());
        eventNames.addAll(discussionManager.getDiscussionNames());

        return eventNames;
    }

    /**
     * Retrieves an Entity.Event object from the list of ALL events, given the name of the event
     * @param eventName - name of event
     * @return the Entity.Event object
     */
    public Event getEventWithName(String eventName) {
        for(Event event: getEvents()) {
            if (event.getName().equals(eventName)) {
                return event;
            }
        }
        return null;
    }

    /**
     * Retrieves a Entity.Talk object given the name of the talk
     * @param talkName - name of talk
     * @return the Entity.Talk object
     */
    public Talk getTalkWithName(String talkName) {
        return talkManager.getTalkWithName(talkName);
    }

    /**
     * Retrieves a Entity.Discussion object given the name of the discussion
     * @param discussionName - name of discussion
     * @return the Entity.Discussion object
     */
    public Discussion getDiscussionWithName(String discussionName) {
        return discussionManager.getDiscussionWithName(discussionName);
    }

    /**
     * Fetches the set of speakers at the given event
     * @param eventName The name of the event
     * @return The set of speakers
     */
    public Set<String> getSpeakersAtEvent(String eventName) {
        Set<String> speakers = new HashSet<>();

        if (talkManager.hasTalk(eventName)) {
            String speaker = talkManager.getSpeaker(eventName);
            if (speaker != null) {
                speakers.add(speaker);
            }
        }
        else if(discussionManager.hasDiscussion(eventName)) {
            speakers.addAll(discussionManager.getSpeakers(eventName));
        }

        return speakers;
    }

    /**
     * Retrieves the date and time of an event given the event's name
     * @param eventName - the name of the event
     * @return the date and time of the event
     *
     *                  **Precondition**
     *  An event with eventName exists
     */
    public Date getTimeOfEvent(String eventName) {
        Event event = getEventWithName(eventName);
        return event.getDateAndTime();
    }

    /**
     * Retrieves the room code of an event given the event's name
     * @param eventName - the name of the event
     * @return the room code of the event
     *
     *                 **Precondition**
     *  An event with eventName exists
     */
    public String getRoomCodeOfEvent(String eventName) {
        Event event = getEventWithName(eventName);
        return event.getRoomCode();
    }

    /**
     * Gets a collection of the names of the users attending an event.
     * @param eventName The name of the event.
     * @return The collection of names.
     */
    public Collection<String> getAttendeesOfEvent(String eventName) {
        Event event = getEventWithName(eventName);
        if (event != null) {
            return event.getAttendees();
        }
        return new HashSet<>();
    }

    /**
     * Checks if an event with eventName exists
     * @param eventName - name of the event
     * @return true if and only if an event with that name exists
     */
    public boolean hasEvent(String eventName) {
        return partyManager.hasParty(eventName) || talkManager.hasTalk(eventName) || discussionManager.hasDiscussion(eventName);
    }

    /**
     * Removes an Entity.Event from the list of events managed by this UseCase.EventManager.
     * @param eventName - name of the event to be removed
     */
    public void removeEvent(String eventName) {
        if(partyManager.hasParty(eventName)) {
            partyManager.removeParty(eventName);
        }
        else if(talkManager.hasTalk(eventName)) {
            talkManager.removeTalk(eventName);
        }
        else if(discussionManager.hasDiscussion(eventName)) {
            discussionManager.removeDiscussion(eventName);
        }

    }

    /**
     * Creates and adds a new Entity.Party object to the UseCase.EventManager from the given UseCase.PartyBuilder.
     * @param partyBuilder The UseCase.PartyBuilder of the party being created
     */
    public void createParty(PartyBuilder partyBuilder) {
        partyManager.createParty(partyBuilder);
    }

    /**
     * Creates and adds a new Entity.Talk object to the UseCase.EventManager from the given UseCase.TalkBuilder.
     * @param talkBuilder The TalkBuilders of the talk being created
     */
    public void createTalk(TalkBuilder talkBuilder) {
        talkManager.createTalk(talkBuilder);
    }

    /**
     * Creates and adds a new Entity.Discussion object to the UseCase.EventManager from the given UseCase.DiscussionBuilder.
     * @param discussionBuilder The UseCase.DiscussionBuilder of the discussion being created
     */
    public void createDiscussion(DiscussionBuilder discussionBuilder) {
        discussionManager.createDiscussion(discussionBuilder);
    }

    /**
     * Checks if an event is at full capacity
     * @param eventName - name of the event
     * @return true if and only if the event has reached its capacity for attendees
     */
    public boolean isEventFull(String eventName) {
        Event event = getEventWithName(eventName);
        if (event != null) {
            return event.getAttendees().size() >= event.getCapacity();
        }
        else {
            return true;
        }
    }

    /**
     * Adds a username to an Entity.Event's list of attendance if Entity.Event is not full.
     * @param username - username to be added
     * @param eventName - name of the event to which the username is added to
     */
    public void addUserToEvent(String username, String eventName, RoomManager rm) {
        if(hasEvent(eventName)) {
            Event event = getEventWithName(eventName);
            if (event != null) {
                Room room = rm.getRoomWithCode(event.getRoomCode());
                if (event.getAttendees().size() + 1 <= room.getCapacity()) {
                    event.addAttendee(username);
                }
            }
        }
    }

    /**
     * Removes a username from an Entity.Event's list of attendance
     * @param username - username to be removed
     * @param eventName - name of event from which the username is removed
     */
    public void removeUserFromEvent(String username, String eventName) {
        if (hasEvent(eventName)) {
            Event event = getEventWithName(eventName);
            if (event != null) {
                event.removeAttendee(username);
            }
        }
    }

    /**
     * Assigns a new speaker to an existing talk.
     * @param talkName - The name of the talk.
     * @param speakerName - The name of the speaker.
     * @param userManager - A UseCase.UserManager.
     * @return True if and only if a new speaker was successfully assigned.
     */
    public boolean assignSpeakerToTalk(String talkName, String speakerName, UserManager userManager) {
        return talkManager.assignSpeaker(talkName, speakerName, userManager);
    }

    /**
     * Adds a new speaker to an existing discussion.
     * @param discussionName - The name of the discussion
     * @param speakerName - The name of the speaker
     * @param userManager - A UseCase.UserManager
     * @return True if and only if a speaker was successfully added to the discussion
     */
    public boolean addSpeakerToDiscussion(String discussionName, String speakerName, UserManager userManager) {
        return discussionManager.addSpeaker(discussionName, speakerName, userManager);
    }

    /**
     * Removes a speaker from an existing discussion
     * @param discussionName - The name of the discussion
     * @param speakerName - The name of the speaker
     * @param userManager - A UseCase.UserManager
     */
    public void removeSpeakerFromDiscussion(String discussionName, String speakerName, UserManager userManager) {
        discussionManager.removeSpeaker(discussionName, speakerName, userManager);
    }

    /**
     * Changes the capacity of an event.
     * @param eventName The name of the event
     * @param newCapacity The new capacity
     */
    public void changeCapacityOfEvent(String eventName, int newCapacity) {
        Event event = getEventWithName(eventName);
        if (event != null) {
            event.setCapacity(newCapacity);
        }
    }

    /**
     * Checks if this Entity.Event is for vip-only
     * @param eventName - name of the event
     * @return true if and only if this event is for vip-only
     */
    public boolean isVipOnly(String eventName) {
        Event event = getEventWithName(eventName);
        if (event != null) {
            return event.getIsVipOnly();
        }
        return false;
    }
}