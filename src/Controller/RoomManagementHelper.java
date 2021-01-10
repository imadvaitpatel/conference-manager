package Controller;

import Presenter.RoomManagementPresenter;
import UseCase.RoomBuilder;
import UseCase.RoomManager;
import Util.BoardType;
import Util.CancelThrowable;
import Util.SeatingType;

import java.util.Scanner;

public class RoomManagementHelper {

    public void choiceCreateRoom(RoomManager roomManager, Scanner scanner, RoomManagementPresenter presenter) {
        try {
            RoomBuilder roomBuilder = new RoomBuilder();

            String roomCode = getRoomCode(roomManager, scanner, presenter);
            roomBuilder.buildRoomCode(roomCode);

            int capacity = getCapacity(scanner, presenter);
            roomBuilder.buildCapacity(capacity);

            BoardType board = getBoard(scanner, presenter);
            String typeOfBoard = String.valueOf(board);
            roomBuilder.buildBoard(board);

            SeatingType seatingArrangement = getSeatingArrangement(scanner, presenter);
            roomBuilder.buildSeats(seatingArrangement);

            boolean projector = getProjector(scanner, presenter);
            roomBuilder.buildProjector(projector);

            boolean speakerPhone = getSpeakerPhone(scanner, presenter);
            roomBuilder.buildSpeakerphone(speakerPhone);

            String foodString;
            boolean validFoodChoice = true;

            boolean canGetFood = getFood(scanner, presenter);
            roomBuilder.buildFood(canGetFood);

            roomManager.createRoom(roomBuilder);
            presenter.successfullyCreatedRoom(roomCode, capacity, typeOfBoard, speakerPhone, canGetFood, projector);

        }catch (CancelThrowable ct) {
            presenter.returningToMenu();
        }
    }

    private boolean getFood(Scanner scanner, RoomManagementPresenter presenter) throws CancelThrowable {
        boolean canGetFood = false;
        String foodString;
        boolean validFoodChoice;
        do {
            presenter.promptRoomCanGetFood();
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

    private boolean getSpeakerPhone(Scanner scanner, RoomManagementPresenter presenter) throws CancelThrowable {
        boolean speakerPhone = false;
        String speakerPhoneString;
        boolean validSpeakerPhoneChoice = true;

        do {
            presenter.promptRoomSpeakerPhone();
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

    private boolean getProjector(Scanner scanner, RoomManagementPresenter presenter) throws CancelThrowable {
        boolean projector = false;
        String projectorString;
        boolean validProjectorChoice = true;

        do {
            presenter.promptRoomProjector();
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

    private SeatingType getSeatingArrangement(Scanner scanner, RoomManagementPresenter presenter) throws CancelThrowable {
        SeatingType seatingArrangement = SeatingType.AUDITORIUM;
        String seatingString;
        boolean validSeatingChoice = true;

        do {
            presenter.promptRoomSeating();
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

    private BoardType getBoard(Scanner scanner, RoomManagementPresenter presenter) throws CancelThrowable {
        BoardType board = BoardType.NONE;
        String boardString;
        boolean validBoardChoice = true;

        do {
            presenter.promptRoomBoard();
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

    private int getCapacity(Scanner scanner, RoomManagementPresenter presenter) throws CancelThrowable {
        int capacity = 2;
        boolean validCapacity = false;

        do {
            String capacityString;
            try {
                presenter.promptRoomCapacity();
                capacityString = scanner.nextLine().trim();

                if (capacityString.equals(CancelThrowable.CANCEL_STRING)) {
                    throw new CancelThrowable();
                }

                capacity = Integer.parseInt(capacityString);

                if (capacity > 0) {
                    validCapacity = true;
                } else {
                    presenter.notifyInvalidCapacity();
                    validCapacity = false;
                }
            } catch (NumberFormatException nfe) {
                presenter.notifyInputNotANumber();
                validCapacity = false;

            }
        }while(!validCapacity);

        return capacity;
    }

    private String getRoomCode(RoomManager roomManager, Scanner scanner, RoomManagementPresenter presenter) throws CancelThrowable {
        String roomCode;
        do {
            presenter.promptRoomCode();
            roomCode = scanner.nextLine().trim();

            if (roomCode.equals(CancelThrowable.CANCEL_STRING)) {
                throw new CancelThrowable();
            }

        } while (isRoomCodeTaken(roomManager, roomCode, presenter));

        return roomCode;
    }

    private boolean isRoomCodeTaken(RoomManager roomManager, String roomCode, RoomManagementPresenter presenter) {
        if (roomManager.getRoomCodes().contains(roomCode)) {
            presenter.notifyRoomCodeIsTaken();
            return true;
        }

        return false;
    }
}
