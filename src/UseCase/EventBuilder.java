package UseCase;

import Entity.Event;

import java.util.Date;

abstract class EventBuilder {

    // the name of the event
    protected String name;
    // the time of the event
    protected Date dateAndTime;
    // The roomCode of the room where this event is being held
    protected String roomCode;
    // The maximum number of people that can attend the event
    protected int capacity;
    // Whether or not this event is exclusively for VIP users
    protected boolean isVip;

    /**
     * Constructs a new UseCase.EventBuilder instance with default values.
     *
     * Defaults:
     * - name: ""
     * - dateAndTime: now
     * - roomCode: ""
     * - capacity: 2
     * - isVip: false
     */
    public EventBuilder() {
        name = "";
        dateAndTime = new Date();
        roomCode = "";
        capacity = 2;
        isVip = false;
    }

    /**
     * Sets the name of the builder to the given name
     * @param name The name of the event being held
     */
    public void buildName(String name) {
        this.name = name;
    }

    /**
     * Sets the date and time of the builder to the given date and time
     * @param dateAndTime The start time of the event
     */
    public void buildDateAndTime(Date dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    /**
     * Sets the room code of the builder to the given room code
     * @param roomCode The room code of the room where the event is being held
     */
    public void buildRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    /**
     * Sets the capacity of the builder to the given capacity
     * @param capacity The capacity of the event being held
     */
    public void buildCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Sets whether or not this event is a VIP event
     * @param isVip Whether or not the event being held is for VIP users only
     */
    public void buildIsVip(boolean isVip){
        this.isVip = isVip;
    }

    /**
     * Builds a new Entity.Event object with the built parameters
     * @return The built event
     */
    public abstract Event getInstance();

}
