package Presenter;

import UseCase.EventManager;
import UseCase.MessageManager;
import UseCase.RoomManager;
import UseCase.UserManager;
import Util.CancelThrowable;

public class OrganizerPresenter extends UserPresenter {

    protected final RoomManager roomManager;

    /**
     * Constructs a new Presenter.OrganizerPresenter object.
     * @param messageManager The UseCase.MessageManager of the session.
     * @param userManager The UseCase.UserManager of the session.
     * @param eventManager The UseCase.EventManager of the session.
     * @param roomManager The UseCase.RoomManager of the session.
     */
    public OrganizerPresenter(MessageManager messageManager, UserManager userManager, EventManager eventManager, RoomManager roomManager){
        super(messageManager, userManager, eventManager);
        this.roomManager = roomManager;
    }

    /**
     * Displays the menu of actions which the Organizer can perform.
     */
    public void printMenu() {
        System.out.println("\nWhat would you like to do? (Enter the number for the option you want)");
        System.out.println("1. See list of speakers");
        System.out.println("2. Create a attendee account");
        System.out.println("3. Create a VIP account");
        System.out.println("4. Create a speaker account");
        System.out.println("5. Create an organizer account");
        System.out.println("6. See list of rooms");
        System.out.println("7. Add a room");
        System.out.println("8. See schedule of all events");
        System.out.println("9. Create an event");
        System.out.println("10. Cancel an event");
        System.out.println("11. Change capacity of an event");
        System.out.println("12. Assign/Remove speakers from an event");
        System.out.println("13. See list of all users");
        System.out.println("14. Read/Write messages");
        System.out.println("15. Message all speakers");
        System.out.println("16. Message all users");
        System.out.println("17. See summary statistics");
        System.out.println("18. Save schedule");
        System.out.println("19. Sign out");
    }

    /**
     * Displays a list of all speaker names.
     */
    public void printSpeakerList(){
        System.out.println();
        if(userManager.getSpeakerNames().size() == 0) {
            System.out.println("There are currently no registered speakers.");
        } else {
            System.out.println("Speakers:");
        }

        for (String speakerName : userManager.getSpeakerNames()) {
            System.out.println("- " + speakerName);
        }
    }

    /**
     * Displays a list of all room codes.
     */
    public void printRoomList() {
        System.out.println();
        if (roomManager.getRoomCodes().size() == 0){
            System.out.println("There are currently no registered rooms.");
        } else {
            System.out.println("Rooms:");
        }

        for (String roomCode : roomManager.getRoomCodes()) {
            System.out.println("- " + roomCode);
        }
    }

    /**
     * Displays a list of all users.
     */
    public void printUserList() {
        if (userManager.getUserNames().size() == 0) {
            System.out.println("There are currently no registered users.");
        } else {
            System.out.println("Users:");
        }

        for (String username : userManager.getUserNames()) {
            System.out.println("- " + username);
        }
    }

    /**
     * Prompts the user to enter a room code.
     */
    public void promptRoomCode() {
        System.out.println("Enter a room code (or enter\""+CancelThrowable.CANCEL_STRING+"\" to cancel): ");
    }

    /**
     * Notifies the user that an event could not be created and asks them to try again.
     */
    public void notifyErrorCreatingEvent() {
        System.out.println("An unexpected error occurred while creating this event.");
    }

    /**
     * Notifies the user that they must enter a positive capacity
     */
    public void notifyInvalidCapacity() {
        System.out.println("Capacity must be greater than 0.");
    }

    /**
     * Notifies the user that they have successfully created room with given room information
     *
     * @param roomCode - room code of this room
     * @param capacity - capacity of this room
     * @param typeOfBoard - type of Board of this room
     * @param speakerPhone - whether this room has speakerphone or not
     * @param canGetFood - whether this room can have food or not
     * @param projector - whether this room has projector or not
     */
    public void successfullyCreatedRoom(String roomCode, int capacity, String typeOfBoard, boolean speakerPhone, boolean canGetFood, boolean projector) {
        System.out.println("Room \""+roomCode+"\" (Capacity: "+ capacity +
                ", Board type: " + typeOfBoard +
                ", Has speakerphone: " + speakerPhone +
                ", Can have food: " + canGetFood +
                ", Has projector: " + projector +
                ") is successfully created.");
    }

    /**
     * Ask the user to enter Yes or No
     */
    public void notifyNotYesOrNo() {
        System.out.println("Please enter \"Yes\" or \"No\".");
    }

    /**
     * Tells the user the input they have entered is not a number
     */
    public void notANumber(){
        System.out.println("Please enter a number and try again.");
    }

}


