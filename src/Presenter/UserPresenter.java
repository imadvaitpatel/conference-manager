package Presenter;

import UseCase.EventGrouping;
import UseCase.EventManager;
import UseCase.MessageManager;
import UseCase.UserManager;
import Util.CancelThrowable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public abstract class UserPresenter {

    protected MessageManager messageManager;
    protected UserManager userManager;
    protected EventManager eventManager;

    public UserPresenter(MessageManager messageManager, UserManager userManager, EventManager eventManager) {
        this.messageManager = messageManager;
        this.userManager = userManager;
        this.eventManager = eventManager;
    }

    /**
     * Prints a menu of choices for the User
     */
    public abstract void printMenu();

    /**
     * Prints the schedule of all events
     */
    public void printAllEvents(){
        System.out.println();
        Set<String> events = eventManager.getEventNames();
        if (events.size() == 0) {
            System.out.println("There are currently no events being held.");
        } else {
            System.out.println("Here is a list of all events currently being held:");
            printEvents(events);
        }
    }

    /**
     * Prints the names of a collection of events
     * @param events A collection of event names
     */
    public void printEvents(Collection<String> events){
        EventGrouping eventGrouping = new EventGrouping();
        List<String> listedEvents = eventGrouping.arrangeChronologically(events, eventManager);

        for (String event: listedEvents){
            boolean isVip = eventManager.isVipOnly(event);
            SimpleDateFormat timeFormatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");

            String timeString = timeFormatter.format(eventManager.getTimeOfEvent(event));
            String vipStatusString = (eventManager.isVipOnly(event) ? "[VIP] " : "");
            String speakerString = getSpeakerString(eventManager.getSpeakersAtEvent(event));

            System.out.printf("%s (%s): %s\"%s\" %s\n", timeString, eventManager.getRoomCodeOfEvent(event), vipStatusString, event, speakerString);
        }

    }

    // Helper for printEvents
    private String getSpeakerString(Collection<String> speakers) {
        StringBuilder speakerString = new StringBuilder();
        List<String> speakerList = new ArrayList<>(speakers);

        if (!speakerList.isEmpty()) {
            speakerString.append("by ").append(speakerList.get(0));
        }

        for (int i = 1; i < speakerList.size(); i++) {
            if (i == speakerList.size()-1) {
                speakerString.append(", and ").append(speakerList.get(i));
            } else {
                speakerString.append(", ").append(speakerList.get(i));
            }
        }

        return speakerString.toString();
    }

    /**
     * Notifies the user that the program is returning to the main menu
     */
    public void returningToMenu() {
        System.out.println("Returning to the main menu...");
    }

    /**
     * Displays a preview of all existing message threads that mainUserName is
     * currently participating in
     * @param mainUserName The username of the user
     */
    public void displayMessageThreadsPreview(String mainUserName) {
        List<String> existingThreads = messageManager.getExistingThreads(mainUserName);
        System.out.println("\nHere are all of your current message threads:");
        for (int i = 0; i < existingThreads.size(); i++) {
            String otherUser = existingThreads.get(i);
            String[] lastMessage = messageManager.getLastMessageFromThread(mainUserName, otherUser);
            boolean you = lastMessage[1].equals(mainUserName);

            String readString = "(UNREAD)";
            if (Boolean.parseBoolean(lastMessage[3])) {
                readString = "(READ)";
            }

            System.out.printf("%d) %s:\n", existingThreads.size() - i, otherUser);
            System.out.printf("\t%s sent %s: %s\n", (you ? mainUserName + " (you)" : otherUser), readString, lastMessage[2]);
        }

        System.out.println("0) Start a new thread");
        System.out.println("To view or reply to an existing thread: Enter the number next to the user's name or enter the name of the user. \nTo start a new thread, enter 0.");
        System.out.println("To cancel, enter " + CancelThrowable.CANCEL_STRING + ".");
    }

    /**
     * Displays the contents of a message thread between mainUserName and otherUser.
     *
     * @param mainUserName The username of the user
     * @param otherUser The username of the other person involved in the message thread
     * @param showArchived Whether or not to display archived messages
     */
    public void displayMessageThread(String mainUserName, String otherUser, boolean showArchived) {
        System.out.println();

        List<String[]> threadContents = messageManager.getThreadContents(mainUserName, otherUser);

        if (threadContents.size() > 0) {
            System.out.println("Conversation with "+otherUser+":");
        }

        int i = 0;
        for (String[] message : threadContents) {
            boolean you = message[1].equals(mainUserName);

            String readString = "(UNREAD)";
            if (Boolean.parseBoolean(message[3])) {
                readString = "(READ)";
            }

            String archivedString = "";
            if (Boolean.parseBoolean(message[4])) {
                archivedString = " - [Archived]";
            }

            if (showArchived || !Boolean.parseBoolean(message[4])) {
                System.out.printf("%d)%s - %s sent %s: %s \n", i, archivedString, (you ? mainUserName + " (you)" : otherUser), readString, message[2]);
            }
            i++;
        }

        if (threadContents.size() > 0) {
            System.out.println();

            if (showArchived) {
                System.out.println("To hide archived messages: type /hideArchived");
            } else {
                System.out.println("To view archived messages: type /showArchived");
            }
            System.out.println("  To archive a message, type '/archive (x)' where x is the number beside the message.");
            System.out.println("  To unarchive a message, type '/unArchive (x)' where x is the number beside the message.");
            System.out.println("  To mark a message as unread, type '/markAsUnread (x)' where x is the number beside the message.");
            System.out.println("  To delete a message, type '/delete (x)' where x is the number beside the message.");
        }

        System.out.printf("Type the message you want to send. (Enter %s to cancel): \n", CancelThrowable.CANCEL_STRING);
    }

    /**
     * Prompts the user to enter the username of a user.
     * Gives the user a list of all users to pick from.
     */
    public void promptMessageUserName() {
        System.out.println("Here is a list of all users:");

        for (String username : userManager.getUserNames()) {
            System.out.println("- " + username);
        }

        System.out.printf("Please enter the username of the user you would like to message (or %s to cancel): \n", CancelThrowable.CANCEL_STRING);
    }

    /**
     * Notifies the user that the value they entered was not in the correct range.
     * @param lowBound The lower bound of the range
     * @param highBound The upper bound of the range
     */
    public void notifyInvalidNumberRange(int lowBound, int highBound) {
        System.out.printf("Please enter a number between %d and %d.\n", lowBound, highBound);
    }

    /**
     * Notifies the user that they did not input a number
     */
    public void notifyInputNotANumber(){
        System.out.println("The value you entered was not a number. Please try again.");
    }

    /**
     * Tells the user that the user they are trying to contact is not registered in the system
     * @param user The username that is not registered
     */
    public void userNotFound(String user){
        System.out.println("Sorry! " + user + " is not a User registered in our system!");
        System.out.println("Please try again!");
    }

    /**
     * Tells the user that the event is not an event in the system
     * @param eventName The name of the event which is not found.
     */
    public void eventNotFound(String eventName){
        System.out.printf("Sorry! %s is not a scheduled event\n", eventName);
    }

    /**
     * Prompts the user to enter a file location to save a schedule file.
     */
    public void promptOutputPath() {
        System.out.println("Enter the file location you would like to save the schedule to (or enter \""+CancelThrowable.CANCEL_STRING+"\" to cancel):");
    }

    /**
     * Informs the user the schedule is being downloaded
     */
    public void savingSchedule(){
        System.out.println("Generating and saving schedule for you...");
    }

    /**
     * Informs the user that the schedule has been saved
     * @param outputPath The path where the schedule was saved
     */
    public void successfullySavedSchedule(String outputPath) {
        System.out.println("Done. Schedule saved to \""+outputPath+"\".");
    }

}
