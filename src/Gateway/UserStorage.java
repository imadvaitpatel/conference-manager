package Gateway;

import UseCase.UserManager;

import java.io.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserStorage {

    private final String FILE_PATH = "StoredUserManager.ser";

    // final because every Gateway.UserStorage class will have the same logger
    private final static Logger logger = Logger.getLogger(UserStorage.class.getName());
    private static final Handler handler = new ConsoleHandler();

    /**
     * Create a new Gateway.UserStorage instance
     */
    public UserStorage() {
        logger.addHandler(handler);
    }

    /**
     * Checks whether or not there is a serialized UseCase.UserManager.
     * @return True if and only if there is a .ser file for the UseCase.UserManager.
     */
    public boolean isSerialized() {
        File file = new File(FILE_PATH);
        return file.exists() && !file.isDirectory();
    }

    /**
     * Serializes a UseCase.UserManager
     * @param um - UseCase.UserManager to be serialized
     */
    public void serializeUserManager(UserManager um) {
        try {
            OutputStream fileOut = new FileOutputStream(FILE_PATH);
            OutputStream buffer = new BufferedOutputStream(fileOut);
            ObjectOutput out = new ObjectOutputStream(buffer);

            out.writeObject(um);
            out.close();
        }
        catch(IOException e) {
            logger.log(Level.SEVERE, "out.writeObject(um) failed.", e);
        }
    }

    /**
     * Deserializes a .ser file to a UseCase.UserManager class instance.
     * @return a UseCase.UserManager class instance
     */
    public UserManager deserializeToUserManager() {
        UserManager um = null;
        try {
            InputStream fileIn = new FileInputStream(FILE_PATH);
            InputStream buffer = new BufferedInputStream(fileIn);
            ObjectInput in = new ObjectInputStream(buffer);

            um = (UserManager) in.readObject();
            in.close();
        }
        catch(IOException e) {
            logger.log(Level.SEVERE, "Cannot read from input.", e);
        }
        catch(ClassNotFoundException c) {
            System.out.println("UseCase.UserManager class not found.");
        }
        return um;
    }
}
