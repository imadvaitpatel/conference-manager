package Entity;

import Util.BoardType;
import Util.SeatingType;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

public class Room implements Serializable {

    // The Entity.Room identifier
    private String roomCode;

    // The capacity of this Entity.Room
    private int capacity;

    // List of events being held in this Entity.Room (names)
    private Collection<String> events;

    // The type of board in this room (smart board, white board, or none)
    private BoardType board;

    // The name of the seating arrangement for this room
    private SeatingType seatingArrangement;

    // True if this room has a speakerphone for easy communication
    private boolean hasSpeakerphone;

    // True if this room has a projector
    private boolean hasProjector;

    // True if food can be delivered to this room
    private  boolean canGetFood;


    /**
     * Constructs a new room instance with default parameters
     */
    public Room(){
        events = new HashSet<>();


        roomCode = "";
        capacity = 2;

        board = BoardType.NONE;
        seatingArrangement = SeatingType.AUDITORIUM;
        hasSpeakerphone = false;
        hasProjector = false;
        canGetFood = false;
    }


    /**
     * Set the room code of this room
     * @param roomCode - The room code
     */
    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    /**
     * Set the capacity of this room
     * @param capacity The capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Set the board of this room
     * @param board The type of the board
     */
    public void setBoard(BoardType board) {
        this.board = board;
    }

    /**
     * Set the seating arrangement of this room
     * @param seatingArrangement The seating arrangement
     */
    public void setSeatingArrangement(SeatingType seatingArrangement) {
        this.seatingArrangement = seatingArrangement;
    }

    /**
     * Set whether or not this room has a speakerphone
     * @param hasSharedSpeakerphone Whether or not this room has a speakerphone
     */
    public void setSpeakerphone(boolean hasSharedSpeakerphone) {
        this.hasSpeakerphone = hasSharedSpeakerphone;
    }

    /**
     * Set whether or not this room has a projector
     * @param hasProjector Whether or not this room has a projector
     */
    public void setProjector(boolean hasProjector) {
        this.hasProjector = hasProjector;
    }

    /**
     * Set whether or not this room can get food
     * @param canGetFood Whether or not this room can get food
     */
    public void setCanGetFood(boolean canGetFood) {
        this.canGetFood = canGetFood;
    }

    /**
     * Fetches the room identifier
     * @return The room identifier as a String
     */
    public String getRoomCode(){
        return roomCode;
    }

    /**
     * Get the events being held in this room
     * @return The list of events being held in this room
     */
    public Collection<String> getEvents(){
        return events;
    }

    /**
     * Get the capacity of the room
     * @return The integer number of the capacity of the room
     */
    public int getCapacity(){
        return capacity;
    }


    /**
     * Get the type of the board for this room
     * @return The type of the board
     */
    public BoardType getBoard() { return board; }

    /**
     * Get the seating arrangement for this room
     * @return The seating arrangement
     */
    public SeatingType getSeatingArrangement() { return seatingArrangement; }

    /**
     * Get whether or not this room has a speakerphone
     * @return True if and only if this room has a speakerphone
     */
    public boolean hasSharedSpeakerphone() { return hasSpeakerphone; }

    /**
     * Get whether or not this room has a projector
     * @return True if and only if this room has a projector
     */
    public boolean hasProjector() { return hasProjector; }

    /**
     * Get whether or not this room can get food
     * @return True if and only if this room can get food
     */
    public boolean canGetFood() { return canGetFood; }
    /**
     * Add an event to the list of events being held in this room
     * @param event The event to be added to this room
     */

    public void addEvent(String event){
        events.add(event);
    }

    /**
     * Add multiple events to the list of events being held in this room
     * @param events The collection of events to be added to this room
     */
    public void addEvents(Collection<String> events){
        this.events.addAll(events);
    }

    /**
     * Removes the specified event from the list of events being held in the room
     * @param event The event to be removed from the list of events
     */
    public void removeEvent(String event){
        events.remove(event);
    }



}
