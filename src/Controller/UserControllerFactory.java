package Controller;

import UseCase.UserManager;

public class UserControllerFactory {

    /**
     * Creates the appropriate implementation of Controller.UserController based on the permission level of the user.
     *
     * @param username The the name of the Entity.User whose controller is being created.
     * @param userManager The UseCase.UserManager of the current session.
     * @return The appropriate implementation of Controller.UserController for the user.
     */
    public UserController getUserController(String username, UserManager userManager) {
        switch (userManager.getPermissionLevel(username)) {
            case ORGANIZER:
                return new OrganizerController();
            case SPEAKER:
                return new SpeakerController();
            case VIP:
                return new VipController();
            default: // ATTENDEE
                return new AttendeeController();
        }
    }

}
