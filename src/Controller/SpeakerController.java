package Controller;

import Gateway.ScheduleStorage;
import Presenter.*;
import UseCase.EventManager;
import UseCase.MessageManager;
import UseCase.RoomManager;
import UseCase.UserManager;
import Util.CancelThrowable;

import java.util.Scanner;

public class SpeakerController extends UserController {

    /**
     * Allows the user to perform speaker actions in the program.
     * @param mainUserName The username of the Entity.User performing actions.
     * @param userManager The UseCase.UserManager associated with this session.
     * @param eventManager The UseCase.EventManager associated with this session.
     * @param messageManager The UseCase.MessageManager associated with this session
     */
    @Override
    public void run(String mainUserName, UserManager userManager, EventManager eventManager,
                    MessageManager messageManager, RoomManager roomManager) {
        boolean notQuit = true;
        while (notQuit){
            notQuit = performAction(mainUserName, userManager, eventManager, messageManager, roomManager);
        }
    }

    private boolean performAction(String mainUserName, UserManager userManager, EventManager eventManager,
                                  MessageManager messageManager, RoomManager roomManager) {
        SpeakerPresenter presenter = new SpeakerPresenter(messageManager, userManager, eventManager);
        Scanner input = new Scanner(System.in);

        presenter.printMenu();
        String menuChoice = input.nextLine().trim();
        try {
            Integer.parseInt(menuChoice);
        } catch (NumberFormatException e){
            presenter.notANumber();
            return true;
        }
        if (menuChoice.equals("1")){
            choiceSeeSchedule(presenter);
        } else if (menuChoice.equals("2")){
            choiceSeeHostedEvents(presenter, mainUserName);
        } else if(menuChoice.equals("3")) {
            choiceSeeAttendeesInEvent(mainUserName, presenter, input, mainUserName, eventManager, userManager);
        } else if (menuChoice.equals("4")){
            choiceMessaging(presenter, input, mainUserName, messageManager, userManager);
            saveState(userManager, eventManager, roomManager, messageManager);
        } else if (menuChoice.equals("5")) {
            choiceSendToAll(presenter, input, mainUserName, messageManager, eventManager);
            saveState(userManager, eventManager, roomManager, messageManager);
        } else if (menuChoice.equals("6")) {
            choiceGenerateSchedule(mainUserName, userManager, eventManager, input, presenter);
        } else if (menuChoice.equals("7")) {
            return false;
        } else {
            presenter.notInRange();
        }
        return true;
    }

    //choose to see events that speaker is speaking at
    private void choiceSeeHostedEvents(SpeakerPresenter presenter, String mainUserName) {
        presenter.printEventsHostedBySpeaker(mainUserName);
    }

    //choice to see the attendees of an event hosted by the speaker
    private void choiceSeeAttendeesInEvent(String mainUserName, SpeakerPresenter presenter, Scanner input, String user, EventManager em, UserManager um) {
        try {
            String eventName;
            boolean validEvent;
            do {
                presenter.promptEventNameToAttendees(mainUserName);
                eventName = input.nextLine().trim();

                if (eventName.equals(CancelThrowable.CANCEL_STRING)) {
                    throw new CancelThrowable();
                }

                validEvent = checkIfEventExists(presenter, new String[]{eventName}, em)
                        && checkIfSpeakerHostsEvent(presenter, user, eventName, um);
            } while (!validEvent);

            presenter.printAttendeesInEvent(eventName);
        } catch (CancelThrowable ct) {
            presenter.returningToMenu();
        }
    }

    //message to all attendees
    private void choiceSendToAll(SpeakerPresenter presenter, Scanner input, String user, MessageManager messageManager, EventManager em){
        try {
            String eventName;

            do {
                presenter.promptEventNameToMessage(user);
                eventName = input.nextLine().trim();

                if (eventName.equals(CancelThrowable.CANCEL_STRING)) {
                    throw new CancelThrowable();
                }

            } while (!checkIfEventExists(presenter, new String[]{eventName}, em));

            presenter.promptMessageToAllAttendees(eventName);
            String message = input.nextLine().trim();

            if (message.equals(CancelThrowable.CANCEL_STRING)) {
                throw new CancelThrowable();
            }

            messageManager.messageEventAttendees(message, em.getEventWithName(eventName), user);

            presenter.successfullySentToAll(eventName);
        } catch (CancelThrowable ct) {
            presenter.returningToMenu();
        }
    }

    //helper method which checks if an event is hosted by a speaker
    private boolean checkIfSpeakerHostsEvent(SpeakerPresenter presenter, String speaker, String event, UserManager um) {
        if(!um.getHostedEvents(speaker).contains(event)) {
            presenter.speakerDoesNotHostEvent();
            return false;
        }
        return true;
    }

    private void choiceGenerateSchedule(String speakerUsername, UserManager userManager, EventManager eventManager, Scanner scanner, UserPresenter presenter) {
        try {
            String outputPath = "";
            presenter.promptOutputPath();
            outputPath = scanner.nextLine().trim();

            if (outputPath.equals(CancelThrowable.CANCEL_STRING)) {
                throw new CancelThrowable();
            }

            presenter.savingSchedule();

            PrintoutGenerator printoutGenerator = new PrintoutGenerator();
            String codeStr = printoutGenerator.generateSpeakerSchedule(speakerUsername, eventManager, userManager);

            presenter.successfullySavedSchedule(outputPath);

            ScheduleStorage scheduleStorage = new ScheduleStorage();
            scheduleStorage.saveSchedule(codeStr, outputPath);
        } catch (CancelThrowable ct) {
            presenter.returningToMenu();
        }
    }

}

