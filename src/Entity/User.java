package Entity;

import Util.PermissionLevel;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Collection;
import java.util.Set;

public class User implements Serializable {
    // The type of the user
    private PermissionLevel permissionLevel;

    // The list of the names of the events that this user is signed up for
    private Set<String> eventList;

    // The username of the user
    private String username;
    // The password of the user
    private String password;

    /**
     * Constructs a Entity.User of the given Util.PermissionLevel with a given username and password.
     *
     * @param username The username used to log in as this user.
     * @param password The password used to log in as this user.
     * @param permissionLevel The Util.PermissionLevel of the user.
     */
    public User(String username, String password, PermissionLevel permissionLevel) {
        this.permissionLevel = permissionLevel;

        eventList = new HashSet<>();

        this.username = username;
        this.password = password;
    }

    /**
     * Fetches the permission level.
     *
     * @return The permission level of this Entity.User.
     */
    public PermissionLevel getPermissionLevel() {
        return permissionLevel;
    }

    /**
     * Sets the permission level of this user.
     *
     * @param permissionLevel The new permission level of this Entity.User.
     */
    public void setPermissionLevel(PermissionLevel permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    /**
     * Fetches the event list of the Entity.User.
     * @return The event list.
     */
    public Collection<String> getEventList() {
        return eventList;
    }

    /**
     * Adds an event to the event list.
     * @param eventName The name of the event to be added.
     */
    public void addEvent(String eventName) {
        eventList.add(eventName);
    }

    /**
     * Adds all event names in a given collection to the event list.
     * @param eventNames The collection of event names to be added.
     */
    public void addEvents(Collection<String> eventNames) {
        eventList.addAll(eventNames);
    }

    /**
     * Removes an event name from the event list.
     * @param eventName The name of the event to be removed.
     */
    public void removeEvent(String eventName) {
        eventList.remove(eventName);
    }

    /**
     * Removes all events whose names are in a given collection from the event list.
     * @param eventNames The collection of event names to be removed.
     */
    public void removeEvents(Collection<String> eventNames) {
        eventList.removeAll(eventNames);
    }

    /**
     * Checks whether or not the Entity.User is signed up for a certain event.
     * @param eventName The name of an event which may or may not be in the event list.
     * @return True if and only if the given event name is in the event list.
     */
    public boolean isSignedUp(String eventName) {
        return eventList.contains(eventName);
    }

    /**
     * Fetches the username of the Entity.User.
     * @return The username of the Entity.User.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the Entity.User to a new username.
     * @param username The new username of the Entity.User.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Fetches the password of the Entity.User.
     * @return The password of the Entity.User.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the Entity.User to a new password.
     * @param password The new password of the Entity.User.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
