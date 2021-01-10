package Controller;

import Gateway.ScheduleStorage;
import Presenter.AttendeePresenter;
import Presenter.PrintoutGenerator;
import UseCase.EventManager;
import UseCase.MessageManager;
import UseCase.RoomManager;
import UseCase.UserManager;
import Util.CancelThrowable;

import java.util.Scanner;

public class AttendeeController extends UserController {

    /**
     * Gives control of the program to the user by displaying a text interface and performing actions based on user input
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

    //Helper method to display a text interface and take in user input
    private boolean performAction(String mainUserName, UserManager userManager, EventManager eventManager,
                                  MessageManager messageManager, RoomManager roomManager){
        AttendeePresenter presenter = new AttendeePresenter(messageManager, userManager, eventManager);

        Scanner input = new Scanner(System.in);

        presenter.printMenu();
        String menuChoice = input.nextLine().trim();

        try {
            Integer.parseInt(menuChoice);
        } catch (NumberFormatException e){
            presenter.notifyInputNotANumber();
            return true;
        }
        if (menuChoice.equals("1")){
            choiceSeeSchedule(presenter);
        } else if (menuChoice.equals("2")){
            choiceSeeMyEvents(presenter, mainUserName);
        } else if (menuChoice.equals("3")){
            choiceSignUpEvents(mainUserName, presenter, userManager, eventManager, roomManager, input);
            saveState(userManager, eventManager, roomManager, messageManager);
        } else if (menuChoice.equals("4")) {
            choiceCancelEvents(mainUserName, presenter, userManager, eventManager, input);
            saveState(userManager, eventManager, roomManager, messageManager);
        } else if (menuChoice.equals("5")) {
            choiceMessaging(presenter, input, mainUserName, messageManager, userManager);
            saveState(userManager, eventManager, roomManager, messageManager);
        } else if (menuChoice.equals("6")) {
            choiceGenerateSchedule(mainUserName, userManager, eventManager, input, presenter);
        } else if (menuChoice.equals("7")) {
            return false;
        } else {
            presenter.notifyInvalidNumberRange(1, 7);
        }
        return true;
    }

    /**
     * Method to execute the choice of seeing the user's own schedule of events
     * @param presenter The Presenter.AttendeePresenter involved in the action
     * @param user The String username of the user that wants to see their schedule
     */
    protected void choiceSeeMyEvents(AttendeePresenter presenter, String user){
        presenter.printMyEvents(user);
    }

    /**
     * Method to execute the choice of signing up to events
     * @param user The String username of the user that wants to sign up to an event
     * @param presenter The Presenter.AttendeePresenter involved in the action
     * @param userM The UseCase.UserManager involved in the action
     * @param eventM The UseCase.EventManager involved in the action
     * @param input The Scanner involved in the action
     */
    protected void choiceSignUpEvents(String user, AttendeePresenter presenter, UserManager userM, EventManager eventM,
                                      RoomManager roomM, Scanner input){
        try {
            boolean validEvent;
            String[] signupEvent;
            do {
                presenter.signupEvent();
                signupEvent = arrayNoSpaces(input.nextLine().trim().split(","));

                if (signupEvent.length == 0 || signupEvent[0].equals(CancelThrowable.CANCEL_STRING)) {
                    throw new CancelThrowable();
                }

                validEvent = checkIfEventExists(presenter, signupEvent, eventM)
                        && !checkIfEventFull(presenter, signupEvent, eventM)
                        && !checkIfUserAlreadySignedUp(presenter, signupEvent, user, userM)
                        && !checkIfEventVipOnly(presenter, signupEvent, eventM);
            } while (!validEvent);

            signUpForEvents(user, signupEvent, userM, eventM, roomM);

            presenter.successfullySignedUp(signupEvent);
        } catch (CancelThrowable ct) {
            presenter.returningToMenu();
        }
    }

    /**
     * Method to execute the choice of cancelling enrollment from events
     * @param user The String username of the user that wants to cancel enrollment from event
     * @param presenter The Presenter.AttendeePresenter involved in the action
     * @param userM The UseCase.UserManager involved in the action
     * @param eventM The UseCase.EventManager involved in the action
     * @param input The Scanner involved in the action
     */
    protected void choiceCancelEvents(String user, AttendeePresenter presenter, UserManager userM, EventManager eventM, Scanner input){
        try {
            boolean validEvent;
            String[] cancelEvent;
            do {
                presenter.cancelEvent(user);
                cancelEvent = arrayNoSpaces(input.nextLine().trim().split(","));

                if (cancelEvent.length == 0 || cancelEvent[0].equals(CancelThrowable.CANCEL_STRING)) {
                    throw new CancelThrowable();
                }

                validEvent = checkIfUserCanUnenrollForEvent(presenter, cancelEvent, user, userM);
            } while (!validEvent);

            cancelEnrolmentInEvents(user, cancelEvent, userM, eventM);

            presenter.successfullyCancelled(cancelEvent);
        } catch (CancelThrowable ct) {
            presenter.returningToMenu();
        }
    }

    /**
     * Method to check if any event in a list of events is full
     * @param presenter The Presenter.AttendeePresenter involved in the action
     * @param events The String array of event names of events we want to check are full
     * @param eventManager The UseCase.EventManager involved in the action
     * @return Returns true if all events are full, false otherwise
     */
    protected boolean checkIfEventFull(AttendeePresenter presenter, String[] events, EventManager eventManager){
        for (String event: events) {
            if(eventManager.isEventFull(event)) {
                presenter.fullCapacity(event);
                return true;
            }
        }
        return false;
    }

    /**
     * Method the check if a user is can unenroll from an event
     * @param presenter The Presenter.AttendeePresenter involved in the action
     * @param events The String array of event names of events
     * @param user The String username of the user who wants to unenroll
     * @param userManager The UseCase.UserManager involved in the action
     * @return Returns true if the user can unenroll from all events, false otherwise
     */
    protected boolean checkIfUserCanUnenrollForEvent(AttendeePresenter presenter, String[] events, String user, UserManager userManager) {
        for(String event: events) {
            if(!userManager.isSignedUp(user, event)) {
                presenter.eventNotSignedUp(event);
                return false;
            }
        }
        return true;
    }

    /**
     * Method to check if a user has already signed up for an event
     * @param presenter The Presenter.AttendeePresenter involved in the action
     * @param events The String array of event names of events
     * @param user The String username of the user
     * @param userManager The UseCase.UserManager involved in the action
     * @return Return true if the user has already signed up to all the events, false otherwise
     */
    protected boolean checkIfUserAlreadySignedUp(AttendeePresenter presenter, String[] events, String user, UserManager userManager) {
        for(String event: events) {
            if(userManager.isSignedUp(user, event)) {
                presenter.eventAlreadySignedUp(event);
                return true;
            }
        }
        return false;
    }

    /**
     * Signs up user to the specified events
     * @param user The user to sign up to the events
     * @param events The list of event being signed up to
     * @param um The UseCase.UserManager
     * @param em The UseCase.EventManager
     */
    protected void signUpForEvents(String user, String[] events, UserManager um, EventManager em, RoomManager rm){
        if (events.length == 1) {
            em.addUserToEvent(user, events[0], rm);
            um.addEvent(user, events[0]);
        } else{
            for(String event:events){
                em.addUserToEvent(user, event, rm);
                um.addEvent(user,event);
            }
        }
    }

    /**
     * Cancel the user's enrolment in specified events
     * @param user he user canceling the enrolment to events
     * @param events The list of events to be un-enrolled from
     * @param um The UseCase.UserManager
     * @param em The UseCase.EventManager
     */
    protected void cancelEnrolmentInEvents(String user, String[] events, UserManager um, EventManager em){
        if (events.length == 1) {
            em.removeUserFromEvent(user, events[0]);
            um.removeEvent(user, events[0]);
        } else{
            for(String event:events){
                em.removeUserFromEvent(user, event);
                um.removeEvent(user, event);
            }
        }
    }

    /**
     * Method to check if all events in a list of events are for vip-only
     * @param presenter The Presenter.AttendeePresenter involved in the action
     * @param events The String array of event names of events
     * @param eventManager The UseCase.EventManager involved in the action
     * @return Returns true if and only if all events are for vip-only
     */
    protected boolean checkIfEventVipOnly(AttendeePresenter presenter, String[] events, EventManager eventManager){
        for (String event: events) {
            if(eventManager.isVipOnly(event)) {
                presenter.vipOnlyEvent(event);
                return true;
            }
        }
        return false;
    }

    protected void choiceGenerateSchedule(String mainUsername, UserManager userManager, EventManager eventManager, Scanner scanner, AttendeePresenter presenter) {
        try {
            String outputPath = "";
            presenter.promptOutputPath();
            outputPath = scanner.nextLine().trim();

            if (outputPath.equals(CancelThrowable.CANCEL_STRING)) {
                throw new CancelThrowable();
            }

            presenter.savingSchedule();

            PrintoutGenerator printoutGenerator = new PrintoutGenerator();
            String codeStr = printoutGenerator.generateAttendeeSchedule(mainUsername, eventManager, userManager);

            presenter.successfullySavedSchedule(outputPath);

            ScheduleStorage scheduleStorage = new ScheduleStorage();
            scheduleStorage.saveSchedule(codeStr, outputPath);
        } catch (CancelThrowable ct) {
            presenter.returningToMenu();
        }
    }


}
