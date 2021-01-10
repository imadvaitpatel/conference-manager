package Gateway;

import UseCase.MessageManager;

import java.io.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageStorage {

    private final String FILE_PATH = "StoredMessageManager.ser";

    // final because every Gateway.MessageStorage class will have the same logger
    private final static Logger logger = Logger.getLogger(MessageStorage.class.getName());
    private static final Handler handler = new ConsoleHandler();

    /**
     * Create a new Gateway.MessageStorage instance
     */
    public MessageStorage() {
        logger.addHandler(handler);
    }

    /**
     * Checks whether or not there is a serialized UseCase.MessageManager.
     * @return True if and only if there is a .ser file for the UseCase.MessageManager.
     */
    public boolean isSerialized() {
        File file = new File(FILE_PATH);
        return file.exists() && !file.isDirectory();
    }

    /**
     * Serializes a UseCase.MessageManager
     * @param mm - UseCase.MessageManager to be serialized
     */
    public void serializeMessageManager(MessageManager mm) {
        try {
            OutputStream fileOut = new FileOutputStream(FILE_PATH);
            OutputStream buffer = new BufferedOutputStream(fileOut);
            ObjectOutput out = new ObjectOutputStream(buffer);

            out.writeObject(mm);
            out.close();
        }
        catch(IOException e) {
            logger.log(Level.SEVERE, "out.writeObject(mm) failed.", e);
        }
    }

    /**
     * Deserializes a .ser file to a UseCase.MessageManager class instance.
     * @return a UseCase.MessageManager class instance
     */
    public MessageManager deserializeToMessageManager() {
        MessageManager mm = null;
        try {
            InputStream fileIn = new FileInputStream(FILE_PATH);
            InputStream buffer = new BufferedInputStream(fileIn);
            ObjectInput in = new ObjectInputStream(buffer);

            mm = (MessageManager) in.readObject();
            in.close();
        }
        catch(IOException e) {
            logger.log(Level.SEVERE, "Cannot read from input.", e);
        }
        catch(ClassNotFoundException c) {
            System.out.println("UseCase.MessageManager class not found.");
        }
        return mm;
    }
}
