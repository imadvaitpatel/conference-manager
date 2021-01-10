package Presenter;

import UseCase.EventManager;
import UseCase.MessageManager;
import UseCase.RoomManager;
import UseCase.UserManager;
import Util.CancelThrowable;

public class RoomManagementPresenter extends OrganizerPresenter {

    /**
     * Constructs a new Presenter.OrganizerPresenter object.
     *
     * @param messageManager The UseCase.MessageManager of the session.
     * @param userManager    The UseCase.UserManager of the session.
     * @param eventManager   The UseCase.EventManager of the session.
     * @param roomManager    The UseCase.RoomManager of the session.
     */
    public RoomManagementPresenter(MessageManager messageManager, UserManager userManager, EventManager eventManager, RoomManager roomManager) {
        super(messageManager, userManager, eventManager, roomManager);
    }

    /**
     * Prompts the user to enter the type of board in the room
     */
    public void promptRoomBoard() {
        System.out.println("Pick the type of board for this room: (Enter the number next to your choice or enter " +
                "\""+CancelThrowable.CANCEL_STRING+"\" to cancel)");
        System.out.println("  1) Smart Board");
        System.out.println("  2) White Board");
        System.out.println("  3) Chalk Board");
        System.out.println("  4) None");
    }

    /**
     * Prompts the user to enter the seating arrangement in the room
     */
    public void promptRoomSeating() {
        System.out.println("What type of seating does the room have? (Enter the number next to your choice or enter " +
                "\""+CancelThrowable.CANCEL_STRING+"\" to cancel)");
        System.out.println("  1) Auditorium");
        System.out.println("  2) Banquet");
        System.out.println("  3) Hollow Square");
    }

    /**
     * Prompts the user the enter whether or not the room has a projector
     */
    public void promptRoomProjector() {
        System.out.println("Does this room have a projector? (Enter \"Yes\" or \"No\", or enter \""+CancelThrowable.CANCEL_STRING+"\" to cancel)");
    }

    /**
     * Prompts the user to enter whether or not he room has a speaker phone
     */
    public void promptRoomSpeakerPhone() {
        System.out.println("Does this room have a speaker phone? (Enter \"Yes\" or \"No\", or enter \""+CancelThrowable.CANCEL_STRING+"\" to cancel)");
    }

    /**
     * Prompts the user to enter whether or not food can be order to the room
     */
    public void promptRoomCanGetFood() {
        System.out.println("Can food be order to this room? (Enter \"Yes\" or \"No\", or enter \""+CancelThrowable.CANCEL_STRING+"\" to cancel)");
    }

    /**
     * Prompts the user to enter the capacity of the room
     */
    public void promptRoomCapacity() {
        System.out.println("Enter the capacity of this room. Enter \""+ CancelThrowable.CANCEL_STRING+"\" to cancel.");
    }

    /**
     * Notifies the user that a room code is unavailable.
     */
    public void notifyRoomCodeIsTaken() {
        System.out.println("This room code is already taken.");
    }


}
