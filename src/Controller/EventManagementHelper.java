package Controller;

import Presenter.EventManagementPresenter;
import UseCase.*;
import Util.BoardType;
import Util.CancelThrowable;
import Util.EventType;
import Util.SeatingType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class EventManagementHelper {

    public void choiceCreateEvent(UserManager userManager, EventManager eventManager, RoomManager roomManager,
                                  EventValidator eventValidator, Scanner scanner, EventManagementPresenter presenter){

        try {

            String eventName = getEventName(eventManager, scanner, presenter);
            EventType eventType = getEventType(scanner, presenter);
            int capacity = getCapacity(scanner, presenter);
            boolean isVip = getVip(scanner, presenter);
            Date[] time = getStartAndEnd(scanner, presenter);

            BoardType board = getBoard(scanner, presenter);
            SeatingType seatingArrangement = getSeatingArrangement(scanner, presenter);
            boolean projector = getProjector(scanner, presenter);
            boolean speakerPhone = getSpeakerPhone(scanner, presenter);
            boolean canGetFood = getFood(scanner, presenter);

            String roomCode = getRoomCode(eventManager, roomManager, eventValidator, scanner, presenter, time, board, seatingArrangement, projector, speakerPhone, canGetFood);

            roomManager.addEvent(roomCode, eventName);

            switch (eventType) {
                case PARTY:
                    PartyBuilder partyBuilder = new PartyBuilder();

                    partyBuilder.buildName(eventName);
                    partyBuilder.buildCapacity(capacity);
                    partyBuilder.buildDateAndTime(time[0]);
                    partyBuilder.buildIsVip(isVip);
                    partyBuilder.buildRoomCode(roomCode);

                    eventManager.createParty(partyBuilder);
                    break;
                case TALK:
                    TalkBuilder talkBuilder = new TalkBuilder();

                    talkBuilder.buildName(eventName);
                    talkBuilder.buildCapacity(capacity);
                    talkBuilder.buildDateAndTime(time[0]);
                    talkBuilder.buildIsVip(isVip);
                    talkBuilder.buildRoomCode(roomCode);

                    String speaker = getTalkSpeaker(time, scanner, userManager, presenter);
                    talkBuilder.buildSpeaker(speaker);

                    userManager.addEventToSpeaker(speaker, eventName, time);

                    eventManager.createTalk(talkBuilder);
                    break;
                case DISCUSSION:
                    DiscussionBuilder discussionBuilder = new DiscussionBuilder();

                    discussionBuilder.buildName(eventName);
                    discussionBuilder.buildCapacity(capacity);
                    discussionBuilder.buildDateAndTime(time[0]);
                    discussionBuilder.buildIsVip(isVip);
                    discussionBuilder.buildRoomCode(roomCode);

                    Collection<String> speakers = getDiscussionSpeakers(time, scanner, userManager, presenter);
                    discussionBuilder.addSpeakers(speakers);

                    for(String speakerName : speakers) {
                        userManager.addEventToSpeaker(speakerName, eventName, time);
                    }

                    eventManager.createDiscussion(discussionBuilder);
            }

        } catch (CancelThrowable ct) {
            presenter.returningToMenu();
        }
    }

    public void choiceCancelEvent(UserManager userManager, EventManager eventManager, RoomManager roomManager,
                                  Scanner scanner, EventManagementPresenter presenter) {
        try {
            String eventName;

            do {
                presenter.printAllEvents();
                presenter.promptEventName();
                eventName = scanner.nextLine().trim();

                if (eventName.equals(CancelThrowable.CANCEL_STRING)) {
                    throw new CancelThrowable();
                }

            } while (!doesEventExist(eventName, eventManager, presenter));

            destroyEvent(eventName, userManager, eventManager, roomManager);

            presenter.successfullyCancelledEvent(eventName);
        } catch (CancelThrowable ct) {
            presenter.returningToMenu();
        }
    }

    public void choiceChangeEventCapacity(EventManager eventManager, RoomManager roomManager, Scanner scanner, EventManagementPresenter presenter) {
        try {
            String eventName = getExistingEventName(eventManager, scanner, presenter);
            int newCapacity = getCapacityChange(eventManager, roomManager, scanner, presenter, eventName);

            eventManager.changeCapacityOfEvent(eventName, newCapacity);
            presenter.successfullyChangedCapacity(eventName, newCapacity);

        } catch(CancelThrowable ct) {
            presenter.returningToMenu();
        }
    }

    public void choiceManageSpeakers(UserManager userManager, EventManager eventManager, Scanner scanner, EventManagementPresenter presenter) {
        try {
            String eventName;
            String speakerName;

            eventName = checkPartyEvent(eventManager, scanner, presenter); //checks if an event is a party

            if (eventManager.getTalks().contains(eventName)) {
                String speaker = eventManager.getTalkWithName(eventName).getSpeakerUsername();
                presenter.notifyEventCanHaveOneSpeaker(speaker);

                do {
                    presenter.printSpeakerList();
                    presenter.promptSpeakerName();
                    speakerName = scanner.nextLine().trim();

                    if (speakerName.equals(CancelThrowable.CANCEL_STRING)) {
                        throw new CancelThrowable();
                    }

                } while (!canAddSpeakerToEvent(speakerName, eventName, userManager, eventManager, presenter));

                if (!eventManager.assignSpeakerToTalk(eventName, speakerName, userManager)) {
                    presenter.notifyErrorAssigningSpeaker();
                } else {
                    presenter.successfullyAssignedSpeaker(speakerName, eventName);
                }
            }
            else {
                presenter.promptAddOrRemoveSpeakers();
                String choiceString;
                int choice = 0;
                do {
                    try {
                        choiceString = scanner.nextLine().trim();

                        if (choiceString.equals(CancelThrowable.CANCEL_STRING)) {
                            throw new CancelThrowable();
                        }

                        choice = Integer.parseInt(choiceString);

                        if (choice < 1 || choice > 2) {
                            presenter.notifyInvalidNumberRange(1, 2);
                        }

                    } catch (NumberFormatException e) {
                        presenter.notifyInputNotANumber();

                    }
                } while(choice != 1 && choice != 2);

                if(choice == 1) {
                    choiceOne(userManager, eventManager, scanner, presenter, eventName);
                }
                else {
                    choiceTwo(userManager, eventManager, scanner, presenter, eventName);
                }

            }
        } catch (CancelThrowable ct) {
            presenter.returningToMenu();
        }
    }

    //for choiceManageSpeaker
    private String checkPartyEvent(EventManager eventManager, Scanner scanner, EventManagementPresenter presenter) throws CancelThrowable{
        String eventName;

        boolean validEvent = false;
        do {
            presenter.printAllEvents();
            presenter.promptEventName();
            eventName = scanner.nextLine().trim();

            if (eventName.equals(CancelThrowable.CANCEL_STRING)) {
                throw new CancelThrowable();
            }

            if(doesEventExist(eventName, eventManager, presenter)) {
                if (eventManager.getParties().contains(eventName)) {
                    presenter.notifyEventCannotHaveSpeakers(eventName);
                    validEvent = false;
                }
                else {
                    validEvent = true;
                }
            }
        } while (!validEvent);

        return eventName;
    }

    //for choiceManageSpeaker
    private void choiceOne(UserManager userManager, EventManager eventManager, Scanner scanner, EventManagementPresenter presenter, String eventName) throws CancelThrowable{
        String speakerName;
        do {
            presenter.printSpeakerList();
            presenter.promptSpeakerName();
            speakerName = scanner.nextLine().trim();

            if (speakerName.equals(CancelThrowable.CANCEL_STRING)) {
                throw new CancelThrowable();
            }

        } while (!canAddSpeakerToEvent(speakerName, eventName, userManager, eventManager, presenter));

        if (!eventManager.addSpeakerToDiscussion(eventName, speakerName, userManager)) {
            presenter.notifyErrorAssigningSpeaker();
        } else {
            presenter.successfullyAssignedSpeaker(speakerName, eventName);
        }
    }

    //for choiceManageSpeaker
    private void choiceTwo(UserManager userManager, EventManager eventManager, Scanner scanner, EventManagementPresenter presenter, String eventName) throws CancelThrowable{
        String speakerName;
        do {
            presenter.printDiscussionSpeakers(eventName);
            presenter.promptRemoveSpeakers();
            speakerName = scanner.nextLine().trim();

            if (speakerName.equals(CancelThrowable.CANCEL_STRING)) {
                throw new CancelThrowable();
            }
        } while(!canRemoveSpeakerFromDiscussion(speakerName, eventName, userManager, eventManager, presenter));

        eventManager.removeSpeakerFromDiscussion(eventName, speakerName, userManager);
        presenter.successfullyRemovedSpeaker(speakerName, eventName);
    }


    private boolean isSpeakerAtThisEvent(String eventName, String speakerName, EventManager eventManager) {
        if (eventManager.getSpeakersAtEvent(eventName).contains(speakerName)) {
            return true;
        }
        return false;
    }

    private boolean isSpeakerAvailable(String speakerName, String eventName, UserManager userManager, EventManager eventManager, EventManagementPresenter presenter) {
        Date[] time = new Date[2];
        time[0] = eventManager.getTimeOfEvent(eventName);

        Calendar cal = Calendar.getInstance();
        cal.setTime(time[0]);
        cal.add(Calendar.HOUR, 1);

        time[1] = cal.getTime();

        EventValidator eventValidator = new EventValidator();
        if(eventValidator.isSpeakerAvailable(speakerName, time, userManager)) {
            return true;
        }
        presenter.notifySpeakerNotAvailable(speakerName);
        return false;
    }

    private boolean canAddSpeakerToEvent(String speakerName, String eventName, UserManager userManager, EventManager eventManager, EventManagementPresenter presenter) {
        if(!doesSpeakerExist(speakerName, userManager, presenter)) {
            return false;
        }
        if(isSpeakerAtThisEvent(eventName, speakerName, eventManager)) {
            presenter.notifySpeakerAlreadySpeaking(speakerName);
            return false;
        }
        return isSpeakerAvailable(speakerName, eventName, userManager, eventManager, presenter);
    }

    private boolean canRemoveSpeakerFromDiscussion(String speakerName, String eventName, UserManager userManager, EventManager eventManager, EventManagementPresenter presenter) {
        if(!doesSpeakerExist(speakerName, userManager, presenter)) {
            return false;
        }
        if(!isSpeakerAtThisEvent(eventName, speakerName, eventManager)) {
            presenter.notifySpeakerNotAtThisDiscussion(speakerName);
            return false;
        }
        return true;
    }

    private String getRoomCode(EventManager eventManager, RoomManager roomManager, EventValidator eventValidator, Scanner scanner, EventManagementPresenter presenter, Date[] time, BoardType board, SeatingType seatingArrangement, boolean projector, boolean speakerPhone, boolean canGetFood) throws CancelThrowable {
        Set<String> suggestedRooms;
        Set<String> availableRooms;
        String roomCode;
        RoomSuggestions suggest = new RoomSuggestions();
        availableRooms = suggest.getAvailableRooms(time, roomManager, eventManager, eventValidator);
        suggestedRooms = suggest.filterRooms(availableRooms, board, seatingArrangement, projector, speakerPhone, canGetFood, roomManager);

        boolean validRoomCode = true;

        do {
            presenter.printRoomSuggestions(suggestedRooms, availableRooms);
            presenter.promptRoomCode();
            roomCode = scanner.nextLine().trim();

            if (roomCode.equals(CancelThrowable.CANCEL_STRING)) {
                throw new CancelThrowable();
            }

            validRoomCode = isRoomAvailable(roomCode, time, eventValidator, eventManager, roomManager, presenter) && doesRoomExist(roomCode, roomManager, presenter);

        } while(!validRoomCode);

        return roomCode;
    }

    private boolean getFood(Scanner scanner, EventManagementPresenter presenter) throws CancelThrowable {
        boolean canGetFood = false;
        String foodString;
        boolean validFoodChoice = true;

        do {
            presenter.promptEventCanGetFood();
            foodString = scanner.nextLine().trim();

            if (foodString.equals(CancelThrowable.CANCEL_STRING)) {
                throw new CancelThrowable();
            }

            validFoodChoice = true;

            if (foodString.equalsIgnoreCase("Yes") || foodString.equalsIgnoreCase("y")) {
                canGetFood = true;
            } else if (foodString.equalsIgnoreCase("No") || foodString.equalsIgnoreCase("n")) {
                canGetFood = false;
            } else{
                presenter.notifyNotYesOrNo();
                validFoodChoice = false;
            }
        } while (!validFoodChoice);

        return canGetFood;
    }

    private boolean getSpeakerPhone(Scanner scanner, EventManagementPresenter presenter) throws CancelThrowable {
        boolean speakerPhone = false;
        String speakerPhoneString;
        boolean validSpeakerPhoneChoice = true;

        do {
            presenter.promptEventSpeakerPhone();
            speakerPhoneString = scanner.nextLine().trim();

            if (speakerPhoneString.equals(CancelThrowable.CANCEL_STRING)) {
                throw new CancelThrowable();
            }

            validSpeakerPhoneChoice = true;

            if (speakerPhoneString.equalsIgnoreCase("Yes") || speakerPhoneString.equalsIgnoreCase("y")) {
                speakerPhone = true;
            } else if (speakerPhoneString.equalsIgnoreCase("No") || speakerPhoneString.equalsIgnoreCase("n")) {
                speakerPhone = false;
            } else{
                presenter.notifyNotYesOrNo();
                validSpeakerPhoneChoice = false;
            }
        } while (!validSpeakerPhoneChoice);

        return speakerPhone;
    }

    private boolean getProjector(Scanner scanner, EventManagementPresenter presenter) throws CancelThrowable {
        boolean projector = false;
        String projectorString;
        boolean validProjectorChoice = true;

        do {
            presenter.promptEventProjector();
            projectorString = scanner.nextLine().trim();

            if (projectorString.equals(CancelThrowable.CANCEL_STRING)) {
                throw new CancelThrowable();
            }

            validProjectorChoice = true;

            if (projectorString.equalsIgnoreCase("Yes") || projectorString.equalsIgnoreCase("y")) {
                projector = true;
            } else if (projectorString.equalsIgnoreCase("No") || projectorString.equalsIgnoreCase("n")) {
                projector = false;
            } else{
                presenter.notifyNotYesOrNo();
                validProjectorChoice = false;
            }
        } while (!validProjectorChoice);

        return projector;
    }

    private SeatingType getSeatingArrangement(Scanner scanner, EventManagementPresenter presenter) throws CancelThrowable {
        SeatingType seatingArrangement = SeatingType.AUDITORIUM;
        String seatingString;
        boolean validSeatingChoice = true;

        do {
            presenter.promptEventSeating();
            seatingString = scanner.nextLine().trim();

            if (seatingString.equals(CancelThrowable.CANCEL_STRING)) {
                throw new CancelThrowable();
            }

            validSeatingChoice = true;

            switch (seatingString) {
                case "1":
                    seatingArrangement = SeatingType.AUDITORIUM;
                    break;
                case "2":
                    seatingArrangement = SeatingType.BANQUET;
                    break;
                case "3":
                    seatingArrangement = SeatingType.HOLLOW_SQUARE;
                    break;
                default:
                    presenter.notifyInvalidNumberRange(0, 3);
                    validSeatingChoice = false;
            }
        } while (!validSeatingChoice);

        return seatingArrangement;
    }

    private BoardType getBoard(Scanner scanner, EventManagementPresenter presenter) throws CancelThrowable {
        BoardType board = BoardType.NONE;
        String boardString;
        boolean validBoardChoice = true;

        do {
            presenter.promptEventBoard();
            boardString = scanner.nextLine().trim();

            if (boardString.equals(CancelThrowable.CANCEL_STRING)) {
                throw new CancelThrowable();
            }

            validBoardChoice = true;

            switch (boardString) {
                case "1":
                    board = BoardType.SMART_BOARD;
                    break;
                case "2":
                    board = BoardType.WHITE_BOARD;
                    break;
                case "3":
                    board = BoardType.CHALK_BOARD;
                    break;
                case "4":
                    board = BoardType.NONE;
                    break;
                default:
                    presenter.notifyInvalidNumberRange(0, 4);
                    validBoardChoice = false;
            }
        } while (!validBoardChoice);

        return board;
    }

    private Date[] getStartAndEnd(Scanner scanner, EventManagementPresenter presenter) throws CancelThrowable {
        Date[] time = new Date[2];
        String dateString;
        boolean properDate = true;

        do {
            presenter.promptDate();
            try {
                dateString = scanner.nextLine().trim();

                if (dateString.equals(CancelThrowable.CANCEL_STRING)) {
                    throw new CancelThrowable();
                }

                SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
                time[0] = formatter.parse(dateString);

                Calendar c = Calendar.getInstance();         // need Calendar to add time, not supported in Date class
                c.setTime(time[0]);                       // convert Date to Calendar
                c.add(Calendar.HOUR, 1);
                time[1] = c.getTime();                 // event lasts 1 hour so endTime is 1 hour after startTime

                properDate = true;
            } catch (ParseException parseException) {
                presenter.notifyIncorrectDateFormat();
                properDate = false;
            }
        } while (!properDate);

        return time;
    }

    private boolean getVip(Scanner scanner, EventManagementPresenter presenter) throws CancelThrowable {
        boolean isVip = false;
        String responseVIP;

        do {
            presenter.promptVipOnlyEvent();
            responseVIP = scanner.nextLine().trim();

            if (responseVIP.equals(CancelThrowable.CANCEL_STRING)) {
                throw new CancelThrowable();
            }
            else if (responseVIP.equalsIgnoreCase("Yes")) {
                isVip = true;
            }

        } while(!isValidVipResponse(responseVIP, presenter));

        return isVip;
    }

    private int getCapacity(Scanner scanner, EventManagementPresenter presenter) throws CancelThrowable {
        int capacity = 2;
        boolean validCapacity = true;

        do {
            String capacityString;

            try {
                presenter.promptEventCapacity();
                capacityString = scanner.nextLine().trim();

                if (capacityString.equals(CancelThrowable.CANCEL_STRING)) {
                    throw new CancelThrowable();
                }

                capacity = Integer.parseInt(capacityString);

                if (capacity <= 0) {
                    presenter.notifyInvalidCapacity();
                    validCapacity = false;
                } else {
                    validCapacity = true;
                }
            } catch (NumberFormatException nfe) {
                presenter.notifyInputNotANumber();
                validCapacity = false;
            }

        } while (!validCapacity);

        return capacity;
    }

    private EventType getEventType(Scanner scanner, EventManagementPresenter presenter) throws CancelThrowable {
        EventType eventType = EventType.PARTY;

        String eventTypeStr;
        int eventTypeInt;
        boolean validEventTypeResponse = true;

        do {
            try {
                presenter.promptEventType();
                eventTypeStr = scanner.nextLine().trim();

                if (eventTypeStr.equals(CancelThrowable.CANCEL_STRING)) {
                    throw new CancelThrowable();
                }

                eventTypeInt = Integer.parseInt(eventTypeStr);

                switch (eventTypeInt) {
                    case 1:
                        eventType = EventType.PARTY;
                        validEventTypeResponse = true;
                        break;
                    case 2:
                        eventType = EventType.TALK;
                        validEventTypeResponse = true;
                        break;
                    case 3:
                        eventType = EventType.DISCUSSION;
                        validEventTypeResponse = true;
                        break;
                    default:
                        presenter.notifyInvalidNumberRange(1, 3);
                        validEventTypeResponse = false;
                }
            } catch (NumberFormatException nfe) {
                presenter.notifyInputNotANumber();
                validEventTypeResponse = false;
            }
        } while(!validEventTypeResponse);

        return eventType;
    }

    private String getEventName(EventManager eventManager, Scanner scanner, EventManagementPresenter presenter) throws CancelThrowable {
        String eventName;
        do {
            presenter.promptEventName();
            eventName = scanner.nextLine().trim();

            if (eventName.equals(CancelThrowable.CANCEL_STRING)) {
                throw new CancelThrowable();
            }

        } while (isEventNameTaken(eventName, eventManager, presenter));
        return eventName;
    }

    private String getTalkSpeaker(Date[] time, Scanner scanner, UserManager userManager, EventManagementPresenter presenter) throws CancelThrowable {
        boolean valid = true;
        String speaker;

        do {
            presenter.printAvailableSpeakers(userManager.getAvailableSpeakers(time));
            presenter.promptTalkSpeaker();
            speaker = scanner.nextLine().trim();

            if (speaker.equals(CancelThrowable.CANCEL_STRING)) {
                throw new CancelThrowable();
            }

            EventValidator eventValidator = new EventValidator();
            valid = isSpeakerAvailable(speaker, time, eventValidator, userManager, presenter);

        } while(!valid);

        return speaker;
    }

    private Collection<String> getDiscussionSpeakers(Date[] time, Scanner scanner, UserManager userManager, EventManagementPresenter presenter) throws CancelThrowable {
        boolean valid;
        String speakersString;
        String[] speakers;

        do {
            presenter.printAvailableSpeakers(userManager.getAvailableSpeakers(time));
            presenter.promptDiscussionSpeakers();
            speakersString = scanner.nextLine().trim();

            if (speakersString.equals(CancelThrowable.CANCEL_STRING)) {
                throw new CancelThrowable();
            }

            valid = true;
            speakers = arrayNoSpaces(speakersString.split(","));

            EventValidator eventValidator = new EventValidator();
            for (String speaker : speakers) {
                if (!isSpeakerAvailable(speaker, time, eventValidator, userManager, presenter)) {
                    valid = false;
                }
            }

        } while(!valid);

        return Arrays.asList(speakers);
    }

    private boolean isEventNameTaken(String eventName, EventManager eventManager, EventManagementPresenter presenter) {
        if (eventManager.getEventNames().contains(eventName)) {
            presenter.notifyEventNameTaken();
            return true;
        }
        return false;
    }

    private boolean doesRoomExist(String roomCode, RoomManager roomManager, EventManagementPresenter presenter) {
        if (!roomManager.getRoomCodes().contains(roomCode)) {
            presenter.notifyRoomDoesNotExist(roomCode);
            return false;
        }
        return true;
    }

    private boolean isRoomAvailable(String roomCode, Date[] time, EventValidator eventValidator, EventManager eventManager, RoomManager roomManager, EventManagementPresenter presenter) {
        if(!doesRoomExist(roomCode, roomManager, presenter)) {
            return false;
        }
        else {
            if(eventValidator.isRoomAvailable(roomCode, time, eventManager, roomManager)) {
                return true;
            }
            else {
                presenter.notifyRoomNotAvailable(roomCode);
                return false;
            }
        }
    }

    private boolean doesSpeakerExist(String speakerName, UserManager userManager, EventManagementPresenter presenter) {
        if (userManager.hasSpeaker(speakerName)) {
            return true;
        }
        presenter.notifySpeakerDoesNotExist(speakerName);
        return false;
    }

    private boolean isSpeakerAvailable(String speakerName, Date[] time, EventValidator eventValidator, UserManager userManager, EventManagementPresenter presenter) {
        if(!doesSpeakerExist(speakerName, userManager, presenter)) {
            return false;
        }
        else {
            if(eventValidator.isSpeakerAvailable(speakerName, time, userManager)) {
                return true;
            }
            else {
                presenter.notifySpeakerNotAvailable(speakerName);
                return false;
            }
        }
    }

    private boolean isValidVipResponse(String response, EventManagementPresenter presenter) {
        if (!(response.equalsIgnoreCase("Yes") || response.equalsIgnoreCase("No"))) {
            presenter.notifyNotYesOrNo();
            return false;
        }
        return true;
    }

    private void destroyEvent(String eventName, UserManager userManager, EventManager eventManager, RoomManager roomManager) {

        //attendees no longer attend this event
        for(String user : eventManager.getEventWithName(eventName).getAttendees()) {
            userManager.getUser(user).removeEvent(eventName);
        }

        //room no longer holds this event
        roomManager.removeEvent(eventManager.getRoomCodeOfEvent(eventName), eventName);

        //speakers no longer speak at this event (if event is a talk or discussion)
        if (eventManager.getTalks().contains(eventName)) {
            userManager.removeEventFromSpeaker(eventManager.getTalkWithName(eventName).getSpeakerUsername(), eventName);
        }
        else if(eventManager.getDiscussions().contains(eventName)) {
            for(String speaker: eventManager.getDiscussionWithName(eventName).getSpeakerUsernames()) {
                userManager.removeEventFromSpeaker(speaker, eventName);
            }
        }

        eventManager.removeEvent(eventName);
    }

    private boolean doesEventExist(String eventName, EventManager eventManager, EventManagementPresenter presenter) {
        if (eventManager.getEventNames().contains(eventName)) {
            return true;
        }
        presenter.eventNotFound(eventName);
        return false;
    }

    private int getCapacityChange(EventManager eventManager, RoomManager roomManager, Scanner scanner, EventManagementPresenter presenter, String eventName) throws CancelThrowable {
        int newCapacity = 2;
        String capacityString;
        boolean validCapacity;

        do {
            try {
                presenter.printCapacityBounds(eventName);
                presenter.promptChangeCapacity();

                capacityString = scanner.nextLine().trim();

                if (capacityString.equals(CancelThrowable.CANCEL_STRING)) {
                    throw new CancelThrowable();
                }

                newCapacity = Integer.parseInt(capacityString);

                if (newCapacity >= eventManager.getEventWithName(eventName).getAttendees().size() &&
                        newCapacity <= roomManager.getCapacityOfRoom(eventManager.getEventWithName(eventName).getRoomCode())) {
                    validCapacity = true;
                } else {
                    presenter.notifyInvalidChangeCapacity();
                    validCapacity = false;
                }
            } catch(NumberFormatException nfe){
                presenter.notifyInputNotANumber();
                validCapacity = false;
            }
        } while (!validCapacity);

        return newCapacity;
    }

    private String getExistingEventName(EventManager eventManager, Scanner scanner, EventManagementPresenter presenter) throws CancelThrowable {
        String eventName;
        do {
            presenter.printAllEvents();
            presenter.promptEventName();
            eventName = scanner.nextLine().trim();

            if (eventName.equals(CancelThrowable.CANCEL_STRING)) {
                throw new CancelThrowable();
            }

        } while (!doesEventExist(eventName, eventManager, presenter));

        return eventName;
    }


    /**
     * Helper method to remove whitespace from arrays of strings
     * @param list An array of strings
     * @return The array of strings but with each element trimmed
     */
    private String[] arrayNoSpaces(String [] list){
        String [] newList = new String[list.length];
        for(int i = 0; i< list.length; i++){
            newList[i] = list[i].trim();
        }
        return newList;
    }

}
