package Presenter;

import UseCase.EventManager;
import UseCase.MessageManager;
import UseCase.RoomManager;
import UseCase.UserManager;
import Util.CancelThrowable;

public class AccountCreationPresenter extends OrganizerPresenter {
    /**
     * Constructs a new Presenter.OrganizerPresenter object.
     *
     * @param messageManager The UseCase.MessageManager of the session.
     * @param userManager    The UseCase.UserManager of the session.
     * @param eventManager   The UseCase.EventManager of the session.
     * @param roomManager    The UseCase.RoomManager of the session.
     */
    public AccountCreationPresenter(MessageManager messageManager, UserManager userManager, EventManager eventManager, RoomManager roomManager) {
        super(messageManager, userManager, eventManager, roomManager);
    }

    /**
     * Informs the user that a new attendee was successfully created
     * @param attendeeName The name of the attendee
     */
    public void successfullyCreatedAttendee(String attendeeName) {
        System.out.println("Attendee \""+attendeeName+"\" successfully created.");
    }

    /**
     * Informs the user that a new VIP user was successfully created.
     * @param username The username of the VIP user
     */
    public void successfullyCreatedVipUser(String username){
        System.out.println("Vip User \""+username+"\" successfully created.");
    }

    /**
     * Informs the user that a new speaker was successfully created
     * @param speakerName Then name of the speaker
     */
    public void successfullyCreatedSpeaker(String speakerName) {
        System.out.println("Speaker \""+speakerName+"\" successfully created.");
    }

    /**
     * Informs the user that a new organizer was successfully created
     * @param name The name of the organizer
     */
    public void successfullyCreatedOrganizer(String name) {
        System.out.println("Organizer \""+name+"\" successfully created.");
    }

    /**
     * Notifies the user that they are creating a new attendee
     */
    public void notifyAttendeeCreation() {
        System.out.println("\nAttendee Creation:");
    }

    /**
     * Notifies the user that they are creating a new vip user
     */
    public void notifyVipCreation() {
        System.out.println("\nVIP Creation:");
    }

    /**
     * Notifies the user that they are creating a new speaker
     */
    public void notifySpeakerCreation() {
        System.out.println("\nSpeaker Creation:");
    }

    /**
     * Notifies the user that they are creating a new organizer
     */
    public void notifyOrganizerCreation() {
        System.out.println("\nOrganizer Creation:");
    }

    /**
     * Prompts the user to enter a username.
     */
    public void promptUsername() {
        System.out.println("Enter a username (or enter \""+ CancelThrowable.CANCEL_STRING+"\" to cancel): ");
    }

    /**
     * Prompts the user to enter a password.
     */
    public void promptPassword() {
        System.out.println("Enter a password (or enter \""+CancelThrowable.CANCEL_STRING+"\" to cancel): ");
    }

    /**
     * Notifies the user that a given name is already taken
     */
    public void notifyNameTaken(){
        System.out.println("A user with this name already exists.");
    }

}
