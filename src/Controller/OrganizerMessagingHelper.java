package Controller;

import Presenter.OrganizerMessagingPresenter;
import UseCase.MessageManager;
import UseCase.UserManager;
import Util.CancelThrowable;

import java.util.Scanner;
import java.util.Set;

public class OrganizerMessagingHelper extends OrganizerController {

    public void choiceMessageAllSpeakers(String mainUserName, UserManager userManager, MessageManager messageManager, Scanner scanner, OrganizerMessagingPresenter presenter) {
        try {
            presenter.promptMessageContents(true);
            String messageContents = scanner.nextLine().trim();

            if (messageContents.equals(CancelThrowable.CANCEL_STRING)) {
                throw new CancelThrowable();
            }

            Set<String> speakerNames = userManager.getSpeakerNames();
            for (String name : speakerNames) {
                if (!name.equals(mainUserName)) {
                    messageManager.messageUser(messageContents, mainUserName, name);
                }
            }

            presenter.notifySuccessfullySentMessage();
        } catch (CancelThrowable ct) {
            presenter.returningToMenu();
        }
    }

    public void choiceMessageAllUsers(String mainUserName, UserManager userManager, MessageManager messageManager, Scanner scanner, OrganizerMessagingPresenter presenter) {
        try {
            presenter.promptMessageContents(false);
            String messageContents = scanner.nextLine().trim();

            if (messageContents.equals(CancelThrowable.CANCEL_STRING)) {
                throw new CancelThrowable();
            }

            Set<String> userNames = userManager.getUserNames();
            for (String name : userNames) {
                if (!name.equals(mainUserName)) {
                    messageManager.messageUser(messageContents, mainUserName, name);
                }
            }

            presenter.notifySuccessfullySentMessage();
        } catch (CancelThrowable ct) {
            presenter.returningToMenu();
        }
    }
}
