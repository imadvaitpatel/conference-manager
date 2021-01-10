package Presenter;

import UseCase.EventManager;
import UseCase.MessageManager;
import UseCase.UserManager;
import Util.CancelThrowable;

import java.util.Collection;

public class SpeakerPresenter extends UserPresenter{
    /**
     * Constructs a new Presenter.SpeakerPresenter.
     * @param messageManager The UseCase.MessageManager associated with this session.
     * @param userManager The UseCase.UserManager associated with this session.
     * @param eventManager The UseCase.EventManager associated with this session.
     */
    public SpeakerPresenter(MessageManager messageManager, UserManager userManager, EventManager eventManager) {
        super(messageManager, userManager, eventManager);
    }

    /**
     * Prints a menu of choices for the Entity.User
     */
    public void printMenu(){
        System.out.println("\nWhat would you like to do? (Enter the number for the option you want)");
        System.out.println("1. See schedule of all events");
        System.out.println("2. See events I am hosting");
        System.out.println("3. See attendees of my event");
        System.out.println("4. Messaging");
        System.out.println("5. Message all attendees of an event");
        System.out.println("6. Save Schedule");
        System.out.println("7. Sign Out");
    }

    /**
     * Prompts user to enter an event name
     * @param speakerName The name of the speaker
     */
    public void promptEventNameToMessage(String speakerName){
        printEventsHostedBySpeaker(speakerName);
        System.out.println("Which event's attendees do you wish to message: (Enter name of event or enter \""+CancelThrowable.CANCEL_STRING+"\" to cancel)");
    }

    /**
     * Displays the names of the events which are hosted by a specific speaker
     * @param speakerName The name of a speaker.
     */
    public void printEventsHostedBySpeaker(String speakerName) {
        System.out.println();
        if (userManager.hasSpeaker(speakerName)) {
            Collection<String> events = userManager.getHostedEvents(speakerName);

            if (events.size() == 0) {
                System.out.println("You are not hosting any events.");
            }
            else {
                System.out.println("Here is a list of events you are hosting:");
                printEvents(events);
            }
        } else {
            System.out.println("This speaker does not exist.");
        }
    }

    /**
     * Prompts the user to enter the name of an event whose attendee list will be displayed.
     * @param speakerName The name of the speaker
     */
    public void promptEventNameToAttendees(String speakerName){
        printEventsHostedBySpeaker(speakerName);
        System.out.println("Which event do you wish to see the attendance list of: (Enter name of event or enter \""+CancelThrowable.CANCEL_STRING+"\" to cancel)");
    }

    /**
     * Notifies the user that the speaker does not host an event.
     */
    public void speakerDoesNotHostEvent() {
        System.out.println("This speaker does not host that event.");
    }

    /**
     * Prints a list of all attendees attending an event.
     * @param eventName The name of the event.
     */
    public void printAttendeesInEvent(String eventName) {
        Collection<String> attendees = eventManager.getAttendeesOfEvent(eventName);
        if (attendees.isEmpty()) {
            System.out.println("No one is currently signed up for this event");
        }
        else {
            System.out.println("Here are all the attendees in the event " + eventName + ":");
            for(String attendee: attendees) {
                System.out.println("- " + attendee);
            }
        }
    }

    /**
     * Prompts the user to enter the message they would like to send to all attendees of an event
     * @param eventName The name of the event
     */
    public void promptMessageToAllAttendees(String eventName) {
        System.out.println("Enter the message you would like to send to all attendees of " + eventName + " (or enter \""+ CancelThrowable.CANCEL_STRING+"\" to cancel): ");
    }

    /**
     * Informs the user that the message has been successfully sent to all attendees of an event
     * @param eventName The name of the event
     */
    public void successfullySentToAll(String eventName) {
        System.out.println("Message successfully sent to all attendees of " + eventName + ".");
    }

    /**
     * Tells the user the input they have entered is not a number
     */
    public void notANumber(){
        System.out.println("Please enter a number and try again.");
    }

    /**
     * Tells the user that the number they entered is not in the specified range
     */
    public void notInRange(){
        System.out.println("The number you have entered is not in the range 1-6. Please try again.");
    }
}
