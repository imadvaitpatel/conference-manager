package Presenter;

import UseCase.EventManager;
import UseCase.MessageManager;
import UseCase.UserManager;
import Util.CancelThrowable;

import java.util.Collection;

public class AttendeePresenter extends UserPresenter {

    /**
     * Constructs a new Presenter.AttendeePresenter object
     * @param messageManager The UseCase.MessageManager of this session
     * @param userManager The UseCase.UserManager of this session
     * @param eventManager The UseCase.EventManager of this session
     */
    public AttendeePresenter(MessageManager messageManager, UserManager userManager, EventManager eventManager){
        super(messageManager, userManager, eventManager);
    }

    /**
     * Prints a menu of choices for the User
     */
    public void printMenu(){
        System.out.println("\nWhat would you like to do? (Enter the number for the option you want)");
        System.out.println("1. See schedule of all events");
        System.out.println("2. See my events");
        System.out.println("3. Sign up for events");
        System.out.println("4. Cancel enrolment in events");
        System.out.println("5. Messaging");
        System.out.println("6. Save Schedule");
        System.out.println("7. Sign out");
    }

    /**
     * Prints the schedule of events the user signed up for
     * @param user The username of the user.
     */
    public void printMyEvents(String user){
        System.out.println();
        Collection<String> events = userManager.getEvents(user);
        if (events.size() == 0) {
            System.out.println("You are currently not signed up for any events.");
        } else {
            System.out.println("Here is a list of all the events you are signed up for:");
            printEvents(events);
        }
    }

    /**
     * Prompts the user to sign up to an event
     */
    public void signupEvent(){
        printAllEvents();
        System.out.println("Which event(s) do you want to sign up to? ");
        promptEventName();
    }

    /**
     * Prompts the user to cancel their enrolment in an event
     */
    public void cancelEvent(String user){
        printMyEvents(user);
        System.out.println("Which event(s) do you want to unenroll from?");
        promptEventName();
    }

    //Helper method that prompts the user to enter an event name
    private void promptEventName(){
        System.out.println("Please enter the event name (if there are multiple, separate them with commas) or enter \"" + CancelThrowable.CANCEL_STRING + "\" to cancel: ");
    }

    /**
     * Tells the user that they did not sign up for the event
     * @param event The name of the event.
     */
    public void eventNotSignedUp(String event){
        System.out.println("We cannot unenroll you from the event: " + event + ", because you are not signed up for it!");
    }

    /**
     * Tells the user that the event is already at full capacity
     * @param event The name of the event.
     */
    public void eventAlreadySignedUp(String event) {
        System.out.println("We cannot sign you up for the event " + event + " because you have already signed up for it!");
    }

    /**
     * Notifies the user that the event they attempted to sign up for is full.
     * @param event The name of the event
     */
    public void fullCapacity(String event){
        System.out.println("Sorry, we cannot sign you up because the event " + event + " is already full!");
    }

    /**
     * Fetches the message manager.
     * @return The message manager.
     */
    public MessageManager getMessageManager(){
        return messageManager;
    }

    /**
     * Fetches the user manager.
     * @return The user manager.
     */
    public UserManager getUserManager() {
        return userManager;
    }

    /**
     * Fetches the event manager.
     * @return The event manager.
     */
    public EventManager getEventManager() {
        return eventManager;
    }

    /**
     * Informs the user that they have successfully signed up for events
     * @param events The names of the events
     */
    public void successfullySignedUp(String[] events) {
        if (events.length == 0){
            return;
        }

        StringBuilder eventList = new StringBuilder();
        for (String event : events) {
            eventList.append(String.format("%s, ", event));
        }
        eventList.replace(eventList.lastIndexOf(","), eventList.length(), "");

        System.out.printf("Successfully signed up for %s.\n", eventList.toString());
    }

    /**
     * Informs the user that they have cancelled their enrollment in some events
     * @param events The names of the events
     */
    public void successfullyCancelled(String[] events) {
        if (events.length == 0){
            return;
        }

        StringBuilder eventList = new StringBuilder();
        for (String event : events) {
            eventList.append(String.format("%s, ", event));
        }
        eventList.replace(eventList.lastIndexOf(","), eventList.length(), "");

        System.out.printf("Successfully unenrolled from %s.\n", eventList.toString());
    }

    /**
     * Tells the user that the event user is trying to sign up is for vip-only
     */
    public void vipOnlyEvent(String event){
        System.out.println("Sorry, we cannot sign you up because the event " + event + " is for vip only!");
    }

}
