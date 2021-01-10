package UseCase;

import Entity.Room;
import Util.BoardType;
import Util.SeatingType;

public class RoomBuilder {

    // The roomCode of the room
    private String roomCode;

    // The capacity of the room
    private int capacity;

    // The type of board in the room
    private BoardType board;
    // The seating arrangement in the room
    private SeatingType seatingArrangement;
    // Whether or not the room has a microphone for the speaker
    private boolean hasSpeakerPhone;
    // Whether or not this room has a projector
    private boolean hasProjector;
    // Whether or not this room can order food
    private boolean canGetFood;

    /**
     * Constructs a new UseCase.RoomBuilder instance
     *
     * Default Values:
     * - roomCode: ""
     * - capacity: 2
     * - board: NONE
     * - seatingArrangement: AUDITORIUM
     * - hasSpeakerPhone: false
     * - hasProjector: false
     * - canGetFood: false
     */
    public RoomBuilder() {
        roomCode = "";
        capacity = 2;

        board = BoardType.NONE;
        seatingArrangement = SeatingType.AUDITORIUM;
        hasSpeakerPhone = false;
        hasProjector = false;
        canGetFood = false;
    }

    /**
     * Sets the room code of the builder to the given room code
     * @param roomCode The room code of the room being built
     */
    public void buildRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    /**
     * Sets the capacity of the builder to the given capacity
     * @param capacity The capacity of the room being built
     */
    public void buildCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Sets the board of the builder to the given Util.BoardType
     * @param board The Util.BoardType of the room being built
     */
    public void buildBoard(BoardType board) {
        this.board = board;
    }

    /**
     * Sets the seating arrangement of the builder to the given Util.SeatingType
     * @param seatingArrangement The seating arrangement of the room being built
     */
    public void buildSeats(SeatingType seatingArrangement) {
        this.seatingArrangement = seatingArrangement;
    }

    /**
     * Sets whether or not the room being built has a speaker phone
     * @param hasSpeakerPhone Whether or not the room being built has a speaker phone
     */
    public void buildSpeakerphone(boolean hasSpeakerPhone) {
        this.hasSpeakerPhone = hasSpeakerPhone;
    }

    /**
     * Sets whether or not the room being built has a projector
     * @param hasProjector Whether or not the room being built has a projector
     */
    public void buildProjector(boolean hasProjector) {
        this.hasProjector = hasProjector;
    }

    /**
     * Sets whether or not the room being built can order food
     * @param canGetFood Whether or not the room being built can order food
     */
    public void buildFood(boolean canGetFood) {
        this.canGetFood = canGetFood;
    }

    /**
     * Creates a room instance based on the attributes which have been built by the builder.
     * @return The new room instance
     */
    public Room getRoom() {
        Room room = new Room();

        room.setRoomCode(roomCode);
        room.setCapacity(capacity);
        room.setBoard(board);
        room.setSeatingArrangement(seatingArrangement);
        room.setProjector(hasProjector);
        room.setSpeakerphone(hasSpeakerPhone);
        room.setCanGetFood(canGetFood);

        return room;
    }
}
