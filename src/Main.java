import Controller.LoginSystem;
import Gateway.EventStorage;
import Gateway.MessageStorage;
import Gateway.RoomStorage;
import Gateway.UserStorage;
import UseCase.EventManager;
import UseCase.MessageManager;
import UseCase.RoomManager;
import UseCase.UserManager;

public class Main {

    /**
     * Main method of the program.
     * @param args Runtime arguments of the program.
     */
    public static void main(String[] args) {
        LoginSystem loginSystem = new LoginSystem();
        UserStorage userStorage = new UserStorage();
        EventStorage eventStorage = new EventStorage();
        MessageStorage messageStorage = new MessageStorage();
        RoomStorage roomStorage = new RoomStorage();

        UserManager userManager;
        EventManager eventManager;
        MessageManager messageManager;
        RoomManager roomManager;

        // Creates a UseCase.UserManager
        if (userStorage.isSerialized()) {
            userManager = userStorage.deserializeToUserManager();
        }
        else {
            userManager = new UserManager();
        }

        // Creates an UseCase.EventManager
        if (eventStorage.isSerialized()) {
            eventManager = eventStorage.deserializeToEventManager();
        }
        else {
            eventManager = new EventManager();
        }

        // Creates a UseCase.MessageManager
        if(messageStorage.isSerialized()) {
            messageManager = messageStorage.deserializeToMessageManager();
        }
        else {
            messageManager = new MessageManager();
        }

        // Creates a UseCase.RoomManager
        if(roomStorage.isSerialized()) {
            roomManager = roomStorage.deserializeToRoomManager();
        }
        else {
            roomManager = new RoomManager();
        }

        // Starts the login system
        loginSystem.run(userManager, eventManager, messageManager, roomManager);

        // Stores the final versions of the Managers
        userStorage.serializeUserManager(userManager);
        eventStorage.serializeEventManager(eventManager);
        messageStorage.serializeMessageManager(messageManager);
        roomStorage.serializeRoomManager(roomManager);

    }
}
