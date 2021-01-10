package UseCase;

import Entity.Room;
import Util.BoardType;
import Util.SeatingType;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class RoomManager implements Serializable {

    // list of rooms
    private Set<Room> rooms;

    /**
     * Creates a new UseCase.RoomManager with no rooms.
     */
    public RoomManager() {
        rooms = new HashSet<>();
    }

    /**
     * Fetches the list of rooms managed by this UseCase.RoomManager.
     * @return The rooms managed by this UseCase.RoomManager
     */
    public Set<Room> getRooms() {
        return rooms;
    }

    /**
     * Fetches the room codes of the rooms managed by this UseCase.EventManager.
     * @return a list of all the room codes of all the rooms stored by this UseCase.EventManager
     */
    public Set<String> getRoomCodes() {
        Set<String> roomCodes = new HashSet<>();

        for(Room room: rooms) {
            roomCodes.add(room.getRoomCode());
        }

        return roomCodes;
    }

    /**
     * Retrieve a Entity.Room object given the room's code
     * @param roomCode - the code of the room
     * @return a Entity.Room object
     */
    public Room getRoomWithCode(String roomCode) {
        for(Room room: rooms) {
            if (room.getRoomCode().equals(roomCode)) {
                return room;
            }
        }
        return null;
    }

    /**
     * Retrieve the capacity of a room given the room's name
     * @param roomCode - the code of the room
     * @return the room's capacity
     *
     *                 **Precondition**
     *  A room with roomCode exists
     */
    public int getCapacityOfRoom(String roomCode) {
        Room room = getRoomWithCode(roomCode);
        return room.getCapacity();
    }

    /**
     * Retrieve the events which are held in a room.
     * @param roomCode - the code of the room
     * @return the events held in this room
     *
     *                 **Precondition**
     * A room with roomCode exists
     */
    public Collection<String> getEventsOfRoom(String roomCode) {
        Room room = getRoomWithCode(roomCode);
        return room.getEvents();
    }

    /**
     * Retrieve the type of board in a room
     * @param roomCode - the code of the room
     * @return the type of the board in this room
     *
     *                 **Precondition**
     * A room with roomCode exists
     */
    public BoardType getBoardTypeOfRoom(String roomCode) {
        Room room = getRoomWithCode(roomCode);
        return room.getBoard();
    }

    /**
     * Retrieve the type of the seating in a room
     * @param roomCode the code of the room
     * @return the type of the seating in this room
     *
     *                 **Precondition**
     * room with roomCode exists
     */
    public SeatingType getSeatingTypeOfRoom(String roomCode) {
        Room room = getRoomWithCode(roomCode);
        return room.getSeatingArrangement();
    }

    /**
     * Check if a room has a projector
     * @param roomCode the code of the room
     * @return True if and only if the room has a projector
     *
     *                 **Precondition**
     * room with roomCode exists
     */
    public boolean DoesRoomHaveProjector(String roomCode) {
        Room room = getRoomWithCode(roomCode);
        return room.hasProjector();
    }

    /**
     * Check if a room has a shared speakerphone
     * @param roomCode the code of the room
     * @return True if and only if the room has a shared speakerphone
     *
     *                 **Precondition**
     * room with roomCode exists
     */
    public boolean DoesRoomHaveSpeakerphone(String roomCode) {
        Room room = getRoomWithCode(roomCode);
        return room.hasSharedSpeakerphone();
    }

    /**
     * Check if a room can order food
     * @param roomCode the code of the room
     * @return True if and only i f the room can order food
     *
     *                 **Precondition**
     * room with roomCode exists
     */
    public boolean CanRoomGetFood(String roomCode) {
        Room room = getRoomWithCode(roomCode);
        return room.canGetFood();
    }

    /**
     * Checks if a room with roomCode exists
     * @param roomCode - the code of the room
     * @return true if and only if a room with that code exists
     */
    public boolean hasRoom(String roomCode) {
        return getRoomCodes().contains(roomCode);
    }

    /**
     * Create a room from a given UseCase.RoomBuilder. If the room code is already taken, then this method does nothing.
     */
    public void createRoom(RoomBuilder roomBuilder) {
        Room room = roomBuilder.getRoom();
        if (!getRoomCodes().contains(room.getRoomCode())) {
            rooms.add(room);
        }
    }

    /**
     * Add an event to the list of events being held in a room
     * @param roomCode The code of the room
     * @param eventName The name of the event
     */
    public void addEvent(String roomCode, String eventName) {
        getRoomWithCode(roomCode).addEvent(eventName);
    }

    /**
     * Remove an event from the list of events being held in a room
     * @param roomCode The code of the room
     * @param eventName The name of the event
     */
    public void removeEvent(String roomCode, String eventName) {
        getRoomWithCode(roomCode).removeEvent(eventName);
    }
}
