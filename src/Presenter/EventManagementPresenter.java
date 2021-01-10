package Presenter;

import UseCase.EventManager;
import UseCase.MessageManager;
import UseCase.RoomManager;
import UseCase.UserManager;
import Util.CancelThrowable;

import java.util.Collection;

public class EventManagementPresenter extends OrganizerPresenter {

    /**
     * Constructs a new Presenter.OrganizerPresenter object.
     *
     * @param messageManager The UseCase.MessageManager of the session.
     * @param userManager    The UseCase.UserManager of the session.
     * @param eventManager   The UseCase.EventManager of the session.
     * @param roomManager    The UseCase.RoomManager of the session.
     */
    public EventManagementPresenter(MessageManager messageManager, UserManager userManager, EventManager eventManager, RoomManager roomManager) {
        super(messageManager, userManager, eventManager, roomManager);
    }

    /**
     * Prompts the user to enter the board type needed for the event
     */
    public void promptEventBoard() {
        System.out.println("What type of board does the event require? (Enter the number next to your choice or enter " +
                "\""+CancelThrowable.CANCEL_STRING+"\" to cancel)");
        System.out.println("  1) Smart Board");
        System.out.println("  2) White Board");
        System.out.println("  3) Chalk Board");
        System.out.println("  4) None");
    }

    /**
     * Prompts the user to enter the seating arrangement needed for the event
     */
    public void promptEventSeating() {
        System.out.println("What type of seating does the event require? (Enter the number next to your choice or enter " +
                "\""+CancelThrowable.CANCEL_STRING+"\" to cancel)");
        System.out.println("  1) Auditorium");
        System.out.println("  2) Banquet");
        System.out.println("  3) Hollow Square");
    }

    /**
     * Prompts the user to enter whether or not the event requires a projector
     */
    public void promptEventProjector() {
        System.out.println("Does this event require a projector? (Enter \"Yes\" or \"No\", or enter \""+CancelThrowable.CANCEL_STRING+"\" to cancel)");
    }

    /**
     * Prompts the user to enter whether or not the event requires a projector
     */
    public void promptEventSpeakerPhone() {
        System.out.println("Does this event require a speaker phone? (Enter \"Yes\" or \"No\", or enter \""+CancelThrowable.CANCEL_STRING+"\" to cancel)");
    }

    /**
     * Prompts the user to enter whether or not they will need to order food for the event
     */
    public void promptEventCanGetFood() {
        System.out.println("Does this require need to order food? (Enter \"Yes\" or \"No\", or enter \""+CancelThrowable.CANCEL_STRING+"\" to cancel)");
    }

    /**
     * Prints the max and current capacity of an event
     * @param eventName The name of the event
     */
    public void printCapacityBounds(String eventName) {
        int maxCapacity = roomManager.getCapacityOfRoom(eventManager.getEventWithName(eventName).getRoomCode());
        int minCapacity = eventManager.getEventWithName(eventName).getAttendees().size();
        System.out.println("The room of this event has a maximum capacity of " + maxCapacity + ".");
        System.out.println("This event currently has " + minCapacity + " people registered.");
    }

    /**
     * Prompts the user to enter a new capacity for an event
     */
    public void promptChangeCapacity() {
        System.out.println("Enter the new capacity for this event (or enter \""+CancelThrowable.CANCEL_STRING+"\" to cancel)");
    }

    /**
     * Notifies the user that the entered capacity they entered is invalid
     */
    public void notifyInvalidChangeCapacity() {
        System.out.println("The new capacity cannot be less than the number of attendees and cannot exceed the room capacity.");
    }

    /**
     * Notifies the user that a given room is not available at a given time
     * @param roomCode The room code of the room
     */
    public void notifyRoomNotAvailable(String roomCode) {
        System.out.println(roomCode + " is not available at the given time.");
    }

    /**
     * Notifies the user that a specified speaker does not exist.
     * @param speakerName The name of the speaker
     */
    public void notifySpeakerDoesNotExist(String speakerName) {
        System.out.println("There is no speaker named \"" + speakerName + "\".");
    }

    /**
     * Notifies the user that a given room does not exist
     * @param roomCode The room code of the room
     */
    public void notifyRoomDoesNotExist(String roomCode) {
        System.out.println("There is no room with room code \""+roomCode+"\" in the system.");
    }

    /**
     * Notifies the user that the event name they entered already exists.
     */
    public void notifyEventNameTaken() {
        System.out.println("An event with this name already exists.");
    }

    /**
     * Prompts the user to enter the names of the speakers at a discussion
     */
    public void promptDiscussionSpeakers() {
        System.out.println("Enter the names of the speakers at this discussion as a comma-separated list (or enter \""+CancelThrowable.CANCEL_STRING+"\" to cancel):");
    }

    /**
     * Prompts the user to enter the name of the speaker giving a talk
     */
    public void promptTalkSpeaker() {
        System.out.println("Enter the name of the speaker giving this talk (or enter \""+CancelThrowable.CANCEL_STRING+"\" to cancel):");
    }

    /**
     * Prints a list of available speakers if possible.
     * @param availableSpeakers The list of available speakers
     */
    public void printAvailableSpeakers(Collection<String> availableSpeakers) {
        if (userManager.getSpeakerNames().isEmpty()) {
            System.out.println("No speakers have been created. Please create a speaker.");
        }
        else if(availableSpeakers.isEmpty()) {
            System.out.println("There are no available speakers at this time. Please change the time of the event or make a new speaker.");
        }
        else {
            System.out.println("Here is a list of all available speakers.");
            for(String speaker: availableSpeakers) {
                System.out.println("-" + speaker);
            }
        }
    }

    /**
     * Prompts the user to select a type of event to create
     */
    public void promptEventType() {
        System.out.println("What type of event are you creating? (Enter the number of your choice, or enter \""+CancelThrowable.CANCEL_STRING+"\" to cancel):");
        System.out.println("  1) Party (no speakers)");
        System.out.println("  2) Talk (1 speaker)");
        System.out.println("  3) Discussion (2+ speakers)");
    }

    /**
     * Prompts user to enter the capacity of an event.
     */
    public void promptEventCapacity() {
        System.out.println("Enter the capacity of this event (or enter \""+CancelThrowable.CANCEL_STRING+"\" to cancel): ");
    }

    /**
     * Prompts user to indicate whether if this event is for vip-only.
     */
    public void promptVipOnlyEvent() {
        System.out.println("Will this event be for Vip-only (Enter Yes or No). " +
                "Enter \""+CancelThrowable.CANCEL_STRING+"\" to cancel.");
    }

    /**
     * Notifies the user that an entered date and time does not match the specified format.
     */
    public void notifyIncorrectDateFormat() {
        System.out.println("The date and time you entered is not in the correct format.");
    }

    /**
     * Prompts the user the enter a date and time in a specified format.
     */
    public void promptDate() {
        System.out.println("Enter a date and time of your event (DD-MMM-YYYY HH:MM:SS) or enter \""+CancelThrowable.CANCEL_STRING+"\" to cancel:");
    }


    /**
     * Prints a list of suggested rooms if possible. Prints available rooms or informs user if there are no
     * possible suggestions.
     * @param suggestedRoomCodes The suggested rooms
     */
    public void printRoomSuggestions(Collection<String> suggestedRoomCodes, Collection<String> availableRoomCodes) {
        if (roomManager.getRoomCodes().isEmpty()) {
            System.out.println("We cannot suggest any rooms because no rooms have been created. Please cancel and create a room.");
        }
        else if(availableRoomCodes.isEmpty()) {
            System.out.println("Unfortunately, there are no available rooms. Please cancel and create a new room for this event.");
        }
        else if(suggestedRoomCodes.isEmpty()) {
            System.out.println("Unfortunately, we cannot suggest any rooms based on the needs of your event");
            System.out.println("Here is a list of all available rooms instead.");
            for(String roomCode: availableRoomCodes) {
                System.out.println("-" + roomCode);
            }
        }
        else {
            System.out.println("Here are some rooms that you might be looking for.");
            for(String roomCode : suggestedRoomCodes) {
                System.out.println("-" + roomCode);
            }
        }
    }

    /**
     * Notifies the user that a given speaker is not speaking at an event
     * @param speakerName The name of the speaker
     */
    public void notifySpeakerNotAtThisDiscussion(String speakerName) {
        System.out.println(speakerName + " is not speaking at this event.");
    }

    /**
     * Notifies the user that a given speaker is not available to speak at an event
     * @param speaker The name of the speaker
     */
    public void notifySpeakerNotAvailable(String speaker) {
        System.out.println(speaker + " is not available to speak at this event.");
    }

    /**
     * Notifies the user that a given speaker is already speaking at this event
     * @param speakerName The name of the speaker
     */
    public void notifySpeakerAlreadySpeaking(String speakerName) {
        System.out.println(speakerName + " is already speaking at this event.");
    }

    /**
     * Informs the user that a speaker has successfully been removed from an event
     * @param speakerName The name of the speaker
     * @param eventName The name of the event
     */
    public void successfullyRemovedSpeaker(String speakerName, String eventName) {
        System.out.println(speakerName + " was successfully removed from " + eventName + ".");
    }

    /**
     * Prompts the user to choose a speaker to remove
     */
    public void promptRemoveSpeakers() {
        System.out.println("Enter the name of the speaker you would like to remove, or enter \""+CancelThrowable.CANCEL_STRING+"\" to cancel):");
    }

    /**
     * Prints a list of speakers who will be at a discussion event
     * @param eventName The name of the event
     */
    public void printDiscussionSpeakers(String eventName) {
        System.out.println("Here are all the speakers that will be speaking at this event.");
        Collection<String> speakers = eventManager.getDiscussionWithName(eventName).getSpeakerUsernames();
        if (speakers.isEmpty()) {
            System.out.println("This event has no speakers.");
        }
        else {
            for (String speaker: eventManager.getDiscussionWithName(eventName).getSpeakerUsernames()) {
                System.out.println("-" +speaker);
            }
        }

    }

    /**
     * Notifies the user that the given event cannot have any speakers
     * @param eventName The name of the event
     */
    public void notifyEventCannotHaveSpeakers(String eventName) {
        System.out.println(eventName + " is an event that cannot have any speakers. Please select a different event.");
    }

    /**
     * Asks the user whether or not they want to add or remove speakers from an event
     */
    public void promptAddOrRemoveSpeakers() {
        System.out.println("Would you like to add or remove speakers from this event? (Enter the number of your choice, or enter \""+ CancelThrowable.CANCEL_STRING+"\" to cancel):");
        System.out.println("1) Add speakers to this event");
        System.out.println("2) Remove speakers from this event");
    }

    /**
     * Informs the user that a speaker has been successfully assigned to an event
     * @param speakerName The name of the speaker
     * @param eventName The name of the event
     */
    public void successfullyAssignedSpeaker(String speakerName, String eventName) {
        System.out.println(speakerName + " has been successfully assigned to " + eventName + ".");
    }


    /**
     * Notifies the user that the speaker could not be assigned to the event.
     */
    public void notifyErrorAssigningSpeaker() {
        System.out.println("This speaker is not available to participate at this event.");
    }

    /**
     * Prompts the user to enter the name of a speaker.
     */
    public void promptSpeakerName() {
        System.out.println("Enter the name of a speaker (or enter \""+CancelThrowable.CANCEL_STRING+"\" to cancel):");
    }

    /**
     * Notifies the user that a given event can only have one speaker
     * @param speaker The name of the current speaker at the event
     */
    public void notifyEventCanHaveOneSpeaker(String speaker) {
        System.out.println("This event can only have one speaker. Any speaker you choose will replace " + speaker + ", the current speaker.");
    }

    /**
     * Informs the user that they have successfully changed the capacity of an event
     * @param eventName The name of the event
     * @param capacity The new capacity
     */
    public void successfullyChangedCapacity(String eventName, int capacity) {
        System.out.println("Capacity of event " + eventName + " has been successfully changed to " + capacity + ".");
    }

    /**
     * Informs the user that they have successfully cancelled an event
     * @param eventName The name of the cancelled event
     */
    public void successfullyCancelledEvent(String eventName) {
        System.out.println("Event \""+eventName+"\" has successfully been cancelled.");
    }

    /**
     * Prompts the user to enter the name of an event.
     */
    public void promptEventName() {
        System.out.println("Enter an event name (or enter \""+ CancelThrowable.CANCEL_STRING+"\" to cancel):");
    }

}
