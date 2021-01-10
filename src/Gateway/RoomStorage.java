package Gateway;

import UseCase.RoomManager;

import java.io.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RoomStorage {

    private final String FILE_PATH = "StoredRoomManager.ser";

    // final because every Gateway.RoomStorage class will have the same logger
    private final static Logger logger = Logger.getLogger(RoomStorage.class.getName());
    private static final Handler handler = new ConsoleHandler();

    /**
     * Create a new Gateway.RoomStorage instance
     */
    public RoomStorage() {
        logger.addHandler(handler);
    }

    /**
     * Checks whether or not there is a serialized UseCase.RoomManager.
     * @return True if and only if there is a .ser file for the UseCase.RoomManager.
     */
    public boolean isSerialized() {
        File file = new File(FILE_PATH);
        return file.exists() && !file.isDirectory();
    }

    /**
     * Serializes a UseCase.RoomManager
     * @param rm - UseCase.RoomManager to be serialized
     */
    public void serializeRoomManager(RoomManager rm) {
        try {
            OutputStream fileOut = new FileOutputStream(FILE_PATH);
            OutputStream buffer = new BufferedOutputStream(fileOut);
            ObjectOutput out = new ObjectOutputStream(buffer);

            out.writeObject(rm);
            out.close();
        }
        catch(IOException e) {
            logger.log(Level.SEVERE, "out.writeObject(rm) failed.", e);
        }
    }

    /**
     * Deserializes a .ser file to a UseCase.RoomManager class instance.
     * @return a UseCase.RoomManager class instance
     */
    public RoomManager deserializeToRoomManager() {
        RoomManager rm = null;
        try {
            InputStream fileIn = new FileInputStream(FILE_PATH);
            InputStream buffer = new BufferedInputStream(fileIn);
            ObjectInput in = new ObjectInputStream(buffer);

            rm = (RoomManager) in.readObject();
            in.close();
        }
        catch(IOException e) {
            logger.log(Level.SEVERE, "Cannot read from input.", e);
        }
        catch(ClassNotFoundException c) {
            System.out.println("UseCase.RoomManager class not found.");
        }
        return rm;
    }
}