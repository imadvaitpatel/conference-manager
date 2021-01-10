package Controller;

import Gateway.ScheduleStorage;
import Presenter.*;
import UseCase.*;
import Util.CancelThrowable;

import java.util.*;

public class OrganizerController extends UserController {

    /**
     * Gives control of the program to the user by displaying a text interface and performing specified actions based
     * on their input.
     * @param mainUserName The username of the user.
     * @param userManager The UseCase.UserManager of the session.
     * @param eventManager The UseCase.EventManager of the session.
     * @param messageManager The UseCase.MessageManager of the session.
     */
    @Override
    public void run(String mainUserName, UserManager userManager, EventManager eventManager,
                    MessageManager messageManager, RoomManager roomManager) {
        EventValidator eventValidator = new EventValidator();
        boolean active = true;

        while (active) {
            active = performAction(mainUserName, userManager, eventManager, messageManager, roomManager, eventValidator);
        }
    }

    private boolean performAction(String mainUserName, UserManager userManager, EventManager eventManager,
                                  MessageManager messageManager, RoomManager roomManager, EventValidator eventValidator){
        OrganizerPresenter presenter = new OrganizerPresenter(messageManager, userManager, eventManager, roomManager);
        Scanner scanner = new Scanner(System.in);

        AccountCreationHelper ach = new AccountCreationHelper();
        EventManagementHelper emh = new EventManagementHelper();
        OrganizerMessagingHelper omh = new OrganizerMessagingHelper();
        RoomManagementHelper rmh = new RoomManagementHelper();

        AccountCreationPresenter acp = new AccountCreationPresenter(messageManager, userManager, eventManager, roomManager);
        EventManagementPresenter emp = new EventManagementPresenter(messageManager, userManager, eventManager, roomManager);
        OrganizerMessagingPresenter omp = new OrganizerMessagingPresenter(messageManager, userManager, eventManager, roomManager);
        RoomManagementPresenter rmp = new RoomManagementPresenter(messageManager, userManager, eventManager, roomManager);

        presenter.printMenu();
        String menuChoice = scanner.nextLine().trim();

        try {
            Integer.parseInt(menuChoice);
        } catch (NumberFormatException e){
            presenter.notANumber();
            return true;
        }
        if (menuChoice.equals("1")){
            choiceSeeSpeakerList(presenter);
        }
        else if (menuChoice.equals("2")) {
            ach.choiceCreateAttendeeAccount(userManager, scanner, acp);
            saveState(userManager, eventManager, roomManager, messageManager);
        }
        else if (menuChoice.equals("3")) {
            ach.choiceCreateVipAccount(userManager, scanner, acp);
            saveState(userManager, eventManager, roomManager, messageManager);
        }
        else if (menuChoice.equals("4")){
            ach.choiceCreateSpeakerAccount(userManager, scanner, acp);
            saveState(userManager, eventManager, roomManager, messageManager);
        }
        else if (menuChoice.equals("5")) {
            ach.choiceCreateOrganizer(userManager, scanner, acp);
            saveState(userManager, eventManager, roomManager, messageManager);
        }
        else if (menuChoice.equals("6")){
            choiceSeeRoomList(presenter);
        }
        else if (menuChoice.equals("7")){
            rmh.choiceCreateRoom(roomManager, scanner, rmp);
            saveState(userManager, eventManager, roomManager, messageManager);
        }
        else if (menuChoice.equals("8")){
            choiceSeeSchedule(presenter);
        }
        else if (menuChoice.equals("9")){
            emh.choiceCreateEvent(userManager, eventManager, roomManager, eventValidator, scanner, emp);
            saveState(userManager, eventManager, roomManager, messageManager);
        }
        else if (menuChoice.equals("10")){
            emh.choiceCancelEvent(userManager, eventManager, roomManager, scanner, emp);
            saveState(userManager, eventManager, roomManager, messageManager);
        }
        else if(menuChoice.equals("11")){
            emh.choiceChangeEventCapacity(eventManager, roomManager, scanner, emp);
            saveState(userManager, eventManager, roomManager, messageManager);
        }
        else if (menuChoice.equals("12")) {
            emh.choiceManageSpeakers(userManager, eventManager, scanner, emp);
            saveState(userManager, eventManager, roomManager, messageManager);
        }
        else if (menuChoice.equals("13")) {
            choiceSeeUserList(presenter);
        }
        else if (menuChoice.equals("14")) {
            choiceMessaging(presenter, scanner, mainUserName, messageManager, userManager);
            saveState(userManager, eventManager, roomManager, messageManager);
        }
        else if (menuChoice.equals("15")) {
            omh.choiceMessageAllSpeakers(mainUserName, userManager, messageManager, scanner, omp);
            saveState(userManager, eventManager, roomManager, messageManager);
        }
        else if (menuChoice.equals("16")) {
            omh.choiceMessageAllUsers(mainUserName, userManager, messageManager, scanner, omp);
            saveState(userManager, eventManager, roomManager, messageManager);
        }
        else if (menuChoice.equals("17")){
            choiceStatistics(eventManager, roomManager, scanner);
        }
        else if (menuChoice.equals("18")) {
            choiceGenerateSchedule(eventManager, scanner, presenter);
        }
        else if (menuChoice.equals("19")) {
            return false;
        } else {
            presenter.notifyInvalidNumberRange(1, 19);
        }

        return true;
    }

    private void choiceSeeSpeakerList(OrganizerPresenter presenter) {
        presenter.printSpeakerList();
    }


    private void choiceSeeRoomList(OrganizerPresenter presenter) {
        presenter.printRoomList();
    }

    private void choiceSeeUserList(OrganizerPresenter presenter) {
        presenter.printUserList();
    }

    private void choiceStatistics(EventManager eventManager, RoomManager roomManager, Scanner scanner){
        boolean active = true;

        while (active) {
            active = statsAction(eventManager, roomManager, scanner);
        }

    }
    private boolean statsAction(EventManager eventManager, RoomManager roomManager, Scanner scanner){
        StatisticsGenerator statsGenerator = new StatisticsGenerator(eventManager, roomManager);
        SummaryStatsPresenter statsPresenter = new SummaryStatsPresenter(statsGenerator);
        statsPresenter.printStatsMenu();
        String menuChoice = scanner.nextLine().trim();

        try {
            Integer.parseInt(menuChoice);
        } catch (NumberFormatException e){
            statsPresenter.notANumber();
            return true;
        }
        if (menuChoice.equals("1")){
            statsPresenter.printEventEnrollStats();
        } else if (menuChoice.equals("2")){
            statsPresenter.printTopEvents();
        } else if (menuChoice.equals("3")){
            statsPresenter.printTopRooms();
        } else if (menuChoice.equals("4")){
            statsPresenter.printAverageEventNumber();
        } else if (menuChoice.equals("5")){
            statsPresenter.printAverageAttendeeNumber();
        } else if (menuChoice.equals("6")){
            statsPresenter.returnBackToMenu();
            return false;
        } else {
            statsPresenter.numNotInRange();
        }
        return true;
    }

    private void choiceGenerateSchedule(EventManager eventManager, Scanner scanner, OrganizerPresenter presenter) {
        try {
            String outputPath = "";
            presenter.promptOutputPath();
            outputPath = scanner.nextLine().trim();

            if (outputPath.equals(CancelThrowable.CANCEL_STRING)) {
                throw new CancelThrowable();
            }

            presenter.savingSchedule();

            PrintoutGenerator printoutGenerator = new PrintoutGenerator();
            String codeStr = printoutGenerator.generateFullSchedule(eventManager);

            presenter.successfullySavedSchedule(outputPath);

            ScheduleStorage scheduleStorage = new ScheduleStorage();
            scheduleStorage.saveSchedule(codeStr, outputPath);
        } catch (CancelThrowable ct) {
            presenter.returningToMenu();
        }
    }

}

