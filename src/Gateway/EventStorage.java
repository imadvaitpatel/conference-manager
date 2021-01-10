package Gateway;

import UseCase.EventManager;

import java.io.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventStorage {

    private final String FILE_PATH = "StoredEventManager.ser";

    // final because every Gateway.EventStorage class will have the same logger
    private final static Logger logger = Logger.getLogger(EventStorage.class.getName());
    private static final Handler handler = new ConsoleHandler();

    /**
     * Create a new Gateway.EventStorage instance
     */
    public EventStorage() {
        logger.addHandler(handler);
    }

    /**
     * Checks whether or not there is a serialized UseCase.EventManager.
     * @return True if and only if there is a .ser file for the UseCase.EventManager.
     */
    public boolean isSerialized() {
        File file = new File(FILE_PATH);
        return file.exists() && !file.isDirectory();
    }

    /**
     * Serializes an UseCase.EventManager
     * @param em - UseCase.EventManager to be serialized
     */
    public void serializeEventManager(EventManager em) {
        try {
            OutputStream fileOut = new FileOutputStream(FILE_PATH);
            OutputStream buffer = new BufferedOutputStream(fileOut);
            ObjectOutput out = new ObjectOutputStream(buffer);

            out.writeObject(em);
            out.close();
        }
        catch(IOException e) {
            logger.log(Level.SEVERE, "out.writeObject(em) failed.", e);
        }
    }

    /**
     * Deserializes a .ser file to an UseCase.EventManager class instance.
     * @return an UseCase.EventManager class instance
     */
    public EventManager deserializeToEventManager() {
        EventManager em = null;
        try {
            InputStream fileIn = new FileInputStream(FILE_PATH);
            InputStream buffer = new BufferedInputStream(fileIn);
            ObjectInput in = new ObjectInputStream(buffer);

            em = (EventManager) in.readObject();
            in.close();
        }
        catch(IOException e) {
            logger.log(Level.SEVERE, "Cannot read from input.", e);
        }
        catch(ClassNotFoundException c) {
            System.out.println("UseCase.EventManager class not found.");
        }
        return em;
    }
}
