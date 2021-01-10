package Presenter;

import UseCase.EventManager;
import UseCase.MessageManager;
import UseCase.RoomManager;
import UseCase.UserManager;
import Util.CancelThrowable;

public class OrganizerMessagingPresenter extends OrganizerPresenter {
    /**
     * Constructs a new Presenter.OrganizerPresenter object.
     *
     * @param messageManager The UseCase.MessageManager of the session.
     * @param userManager    The UseCase.UserManager of the session.
     * @param eventManager   The UseCase.EventManager of the session.
     * @param roomManager    The UseCase.RoomManager of the session.
     */
    public OrganizerMessagingPresenter(MessageManager messageManager, UserManager userManager, EventManager eventManager, RoomManager roomManager) {
        super(messageManager, userManager, eventManager, roomManager);
    }

    /**
     * Prompts the user to enter the contents of a message.
     * @param onlySpeakers Specifices whether or not this message is only being sent to speakers
     */
    public void promptMessageContents(boolean onlySpeakers) {
        System.out.println("Messaging " + (onlySpeakers ? "speakers" : "everyone") + ": ");
        System.out.println("Enter the message you want to send (or enter \""+ CancelThrowable.CANCEL_STRING+"\"):");
    }

    /**
     * Notifies the user that a message has been successfully sent.
     */
    public void notifySuccessfullySentMessage() {
        System.out.println("Message successfully sent!");
    }


}
