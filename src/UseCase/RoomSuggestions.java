package UseCase;

import UseCase.EventManager;
import UseCase.EventValidator;
import UseCase.RoomManager;
import Util.BoardType;
import Util.SeatingType;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class RoomSuggestions {

    /**
     * From a given list of rooms, retrieve only the rooms that have all desired features.
     * @param roomCodes The room codes for the original list of rooms
     * @param board The desired board
     * @param seating The desired seating
     * @param hasProjector Whether or not the filtered rooms should have a projector
     * @param hasSpeakerphone Whether or not the filtered rooms should have a shared speakerphone
     * @param canGetFood Whether or not the filtered rooms can order food
     * @param rm A UseCase.RoomManager object
     * @return A subset of roomCodes of which all the elements have all the desired features.
     */
    public Set<String> filterRooms(Set<String> roomCodes, BoardType board, SeatingType seating,
                                          boolean hasProjector, boolean hasSpeakerphone, boolean canGetFood, RoomManager rm) {
        Set<String> matchedRooms = new HashSet<>();
        for(String roomCode : roomCodes) {
            BoardType boardType = rm.getBoardTypeOfRoom(roomCode);
            SeatingType seatingType = rm.getSeatingTypeOfRoom(roomCode);
            boolean projector = rm.DoesRoomHaveProjector(roomCode);
            boolean speakerPhone = rm.DoesRoomHaveSpeakerphone(roomCode);
            boolean food = rm.CanRoomGetFood(roomCode);

            if (boardType == board && seatingType == seating && projector == hasProjector
                    && speakerPhone == hasSpeakerphone && food == canGetFood) {
                matchedRooms.add(roomCode);
            }
        }

        return matchedRooms;
    }

    public Set<String> getAvailableRooms(Date[] time, RoomManager rm, EventManager em, EventValidator ev) {
        Set<String> availableRooms = new HashSet<>();

        for(String roomCode: rm.getRoomCodes()) {
            if(ev.isRoomAvailable(roomCode, time, em, rm)) {
                availableRooms.add(roomCode);
            }
        }
        return availableRooms;
    }
}
