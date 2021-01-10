package UseCase;

import Entity.User;
import Util.PermissionLevel;

public class UserBuilder {

    // The username of the user
    protected String username;
    // The password which needs to be used to login as the user
    protected String password;
    // The permission level of the user
    protected PermissionLevel permissionLevel;

    /**
     * Constructs a new UseCase.UserBuilder with default values.
     *
     * Defaults:
     * - username: ""
     * - password: ""
     * - permissionLevel: ATTENDEE
     */
    public UserBuilder() {
        username = "";
        password = "";
        permissionLevel = PermissionLevel.ATTENDEE;
    }

    /**
     * Sets the username of the user being built
     * @param username The username of the user
     */
    public void buildUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the password of the user being built
     * @param password The password of the user
     */
    public void buildPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the permission level of the user being built
     * @param permissionLevel The permission level of the user being built
     */
    public void buildPermissionLevel(PermissionLevel permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    /**
     * Creates a new Entity.User object based on the values of this UseCase.UserBuilder
     * @return The new Entity.User object
     */
    public User getInstance() {
        return new User(username, password, permissionLevel);
    }

}
