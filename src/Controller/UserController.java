package Controller;

import Gateway.EventStorage;
import Gateway.MessageStorage;
import Gateway.RoomStorage;
import Gateway.UserStorage;
import Presenter.UserPresenter;
import UseCase.EventManager;
import UseCase.MessageManager;
import UseCase.RoomManager;
import UseCase.UserManager;
import Util.CancelThrowable;

import java.util.List;
import java.util.Scanner;

abstract class UserController {
    /**
     * The main run method of the controller.
     * This method lets the user perform actions in the program as a specific mainUser.
     *
     * @param mainUserName The username of the Entity.User performing actions.
     * @param userManager The UseCase.UserManager associated with this session.
     * @param eventManager The UseCase.EventManager associated with this session.
     * @param messageManager The UseCase.MessageManager associated with this session
     * @param roomManager The UseCase.RoomManager associated with this session
     */
    public abstract void run(String mainUserName, UserManager userManager, EventManager eventManager,
                    MessageManager messageManager, RoomManager roomManager);

    protected void saveState(UserManager userManager, EventManager eventManager, RoomManager roomManager, MessageManager messageManager) {
        saveState(userManager);
        saveState(eventManager);
        saveState(roomManager);
        saveState(messageManager);
    }

    protected void saveState(UserManager userManager) {
        UserStorage userStorage = new UserStorage();

        userStorage.serializeUserManager(userManager);
    }

    protected void saveState(EventManager eventManager) {
        EventStorage eventStorage = new EventStorage();

        eventStorage.serializeEventManager(eventManager);
    }

    protected void saveState(RoomManager roomManager) {
        RoomStorage roomStorage = new RoomStorage();

        roomStorage.serializeRoomManager(roomManager);
    }

    protected void saveState(MessageManager messageManager) {
        MessageStorage messageStorage = new MessageStorage();

        messageStorage.serializeMessageManager(messageManager);
    }

    protected void choiceMessaging(UserPresenter presenter, Scanner input, String mainUserName, MessageManager messageManager, UserManager userManager) {
        try {
            List<String> existingThreads = messageManager.getExistingThreads(mainUserName);
            String choiceMessageThreads;

            do {
                presenter.displayMessageThreadsPreview(mainUserName);
                choiceMessageThreads = input.nextLine().trim();

                if (choiceMessageThreads.equals(CancelThrowable.CANCEL_STRING)) {
                    throw new CancelThrowable();
                }

            } while(!isInRange(0, existingThreads.size()+1, choiceMessageThreads, presenter));

            int messageThreadInt = Integer.parseInt(choiceMessageThreads);
            String otherUser;

            boolean reply = messageThreadInt != 0;

            if (!reply) {
                do {
                    presenter.promptMessageUserName();
                    otherUser = input.nextLine().trim();

                    if (otherUser.equals(CancelThrowable.CANCEL_STRING)) {
                        throw new CancelThrowable();
                    }

                } while(!checkIfUserExists(presenter, otherUser, userManager));
            } else {
                otherUser = existingThreads.get(existingThreads.size() - messageThreadInt);
            }

            messageManager.markThreadAsRead(mainUserName, otherUser);

            boolean showArchived = false;
            while (true) {
                presenter.displayMessageThread(mainUserName, otherUser, showArchived);
                String message = input.nextLine().trim();

                if (message.equals(CancelThrowable.CANCEL_STRING)) {
                    throw new CancelThrowable();
                }
                else if (message.equalsIgnoreCase("/showArchived")) {
                    showArchived = true;
                } else if (message.equalsIgnoreCase("/hideArchived")) {
                    showArchived = false;
                } else if (message.toLowerCase().startsWith("/archive")) {
                    String clean = message.replaceAll("\\D+","");

                    List<String[]> threadContents = messageManager.getThreadContents(mainUserName, otherUser);

                    try {
                        int choice = Integer.parseInt(clean);

                        if (0 <= choice && choice < threadContents.size()) {
                            messageManager.archiveMessage(Integer.parseInt(threadContents.get(choice)[0]));
                        } else {
                            presenter.notifyInvalidNumberRange(0, threadContents.size()-1);
                        }
                    } catch (NumberFormatException nfe) {
                        presenter.notifyInputNotANumber();
                    }
                } else if (message.toLowerCase().startsWith("/unarchive")) {
                    String clean = message.replaceAll("\\D+","");

                    List<String[]> threadContents = messageManager.getThreadContents(mainUserName, otherUser);

                    try {
                        int choice = Integer.parseInt(clean);

                        if (0 <= choice && choice < threadContents.size()) {
                            messageManager.unArchiveMessage(Integer.parseInt(threadContents.get(choice)[0]));
                        } else {
                            presenter.notifyInvalidNumberRange(0, threadContents.size()-1);
                        }
                    } catch (NumberFormatException nfe) {
                        presenter.notifyInputNotANumber();
                    }
                } else if (message.toLowerCase().startsWith("/markasunread")) {
                    String clean = message.replaceAll("\\D+","");

                    List<String[]> threadContents = messageManager.getThreadContents(mainUserName, otherUser);

                    try {
                        int choice = Integer.parseInt(clean);

                        if (0 <= choice && choice < threadContents.size()) {
                            messageManager.markAsUnread(Integer.parseInt(threadContents.get(choice)[0]));
                        } else {
                            presenter.notifyInvalidNumberRange(0, threadContents.size()-1);
                        }
                    } catch (NumberFormatException nfe) {
                        presenter.notifyInputNotANumber();
                    }
                } else if (message.toLowerCase().startsWith("/delete")) {
                    String clean = message.replaceAll("\\D+","");

                    List<String[]> threadContents = messageManager.getThreadContents(mainUserName, otherUser);

                    try {
                        int choice = Integer.parseInt(clean);

                        if (0 <= choice && choice < threadContents.size()) {
                            messageManager.deleteMessage(Integer.parseInt(threadContents.get(choice)[0]));
                        } else {
                            presenter.notifyInvalidNumberRange(0, threadContents.size()-1);
                        }
                    } catch (NumberFormatException nfe) {
                        presenter.notifyInputNotANumber();
                    }
                } else if (!message.equals("")) {
                    if (reply) {
                        messageManager.respondToThread(mainUserName, otherUser, message);
                    }
                    else {
                        messageManager.messageUser(message, mainUserName, otherUser);
                    }
                }
            }
        } catch (CancelThrowable ct) {
            presenter.returningToMenu();
        }
    }

    private boolean isInRange(int lowerBound, int upperBound, String string, UserPresenter presenter) {
        try {
            int num = Integer.parseInt(string);

            if (lowerBound <= num && num < upperBound) {
                return true;
            } else {
                presenter.notifyInvalidNumberRange(lowerBound, upperBound-1);
                return false;
            }
        } catch (NumberFormatException e) {
            presenter.notifyInputNotANumber();
            return false;
        }
    }

    /**
     * Method to check if a user exists
     * @param presenter The Presenter.AttendeePresenter involved in the action
     * @param user The String username of the user we want to check exists
     * @param userManager The UseCase.UserManager involved in the action
     * @return Returns true if the user exists, false otherwise
     */
    protected boolean checkIfUserExists(UserPresenter presenter, String user, UserManager userManager){
        if (!userManager.hasUser(user)){
            presenter.userNotFound(user);
            return false;
        } else{
            return true;
        }
    }

    /**
     * Method to execute the choice of seeing the schedule of events
     * @param presenter The Presenter.AttendeePresenter involved in the action
     */
    protected void choiceSeeSchedule(UserPresenter presenter){
        presenter.printAllEvents();
    }

    /**
     * Helper method to remove whitespace from arrays of strings
     * @param list An array of strings
     * @return The array of strings but with each element trimmed
     */
    protected String[] arrayNoSpaces(String [] list){
        String [] newList = new String[list.length];
        for(int i = 0; i< list.length; i++){
            newList[i] = list[i].trim();
        }
        return newList;
    }

    /**
     * Method to check if all events in a list of events exist
     * @param presenter The Presenter.AttendeePresenter involved in the action
     * @param events The String array of event names of events we want to check exists
     * @param eventManager The UseCase.EventManager involved in the action
     * @return Returns true if all events exist, false otherwise
     */
    protected boolean checkIfEventExists(UserPresenter presenter, String[] events, EventManager eventManager){
        for (String event: events) {
            if(!eventManager.hasEvent(event)) {
                presenter.eventNotFound(event);
                return false;
            }
        }
        return true;
    }

}
