package Controller;

import Presenter.AttendeePresenter;
import Presenter.VipPresenter;
import UseCase.EventManager;
import UseCase.MessageManager;
import UseCase.RoomManager;
import UseCase.UserManager;
import Util.CancelThrowable;

import java.util.Scanner;

public class VipController extends AttendeeController {

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
        VipPresenter presenter = new VipPresenter(messageManager, userManager, eventManager);
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
        }
        else if(menuChoice.equals("2")){
            choiceSeeScheduleOfVipOnlyEvents(presenter);
        }
        else if (menuChoice.equals("3")){
            choiceSeeMyEvents(presenter, mainUserName);
        } else if (menuChoice.equals("4")){
            choiceSignUpEvents(mainUserName, presenter, userManager, eventManager, roomManager, input);
            saveState(userManager, eventManager, roomManager, messageManager);
        }  else if (menuChoice.equals("5")) {
            choiceCancelEvents(mainUserName, presenter, userManager, eventManager, input);
            saveState(userManager, eventManager, roomManager, messageManager);
        } else if (menuChoice.equals("6")) {
            choiceMessaging(presenter, input, mainUserName, messageManager, userManager);
            saveState(userManager, eventManager, roomManager, messageManager);
        } else if (menuChoice.equals("7")) {
            choiceGenerateSchedule(mainUserName, userManager, eventManager, input, presenter);
        } else if (menuChoice.equals("8")) {
            return false;
        } else {
            presenter.notifyInvalidNumberRange(0, 8);
        }
        return true;
    }
    /**
     * Method to execute the choice of seeing all vip-only events
     * @param presenter The Presenter.AttendeePresenter involved in the action
     */
    protected void choiceSeeScheduleOfVipOnlyEvents(VipPresenter presenter){
        presenter.printVip0nlyEvents();
    }


    @Override
    protected void choiceSignUpEvents(String user, AttendeePresenter presenter, UserManager userM,
                                      EventManager eventM, RoomManager roomM, Scanner input){
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
                        && !checkIfUserAlreadySignedUp(presenter, signupEvent, user, userM);
            } while (!validEvent);

            signUpForEvents(user, signupEvent, userM, eventM, roomM);

            presenter.successfullySignedUp(signupEvent);
        } catch (CancelThrowable ct) {
            presenter.returningToMenu();
        }
    }
}
