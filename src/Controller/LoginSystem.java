package Controller;

import Presenter.LoginPresenter;
import UseCase.*;
import Util.CancelThrowable;
import Util.PermissionLevel;

import java.util.Scanner;

public class LoginSystem {

    /**
     * Allows the user to login as a specific Entity.User using a username and password pair.
     * The user can also sign up as a new Attendee by creating a username and password pair.
     *
     * @param userManager The UseCase.UserManager associated with this session.
     * @param eventManager The UseCase.EventManager associated with this session.
     * @param messageManager The UseCase.MessageManager associated with this session.
     * @param roomManager The UseCase.RoomManager associated with this session
     */
    public void run(UserManager userManager, EventManager eventManager, MessageManager messageManager, RoomManager roomManager) {
        LoginPresenter presenter = new LoginPresenter();
        Scanner scanner = new Scanner(System.in);

        String username, password;
        String mainUsername = "";

        String response;

        // Asks if the user would like to login or sign up
        while (true) {
            boolean secondTime = false;
            do {
                if (secondTime){
                    presenter.notInRange();
                }
                secondTime = true;
                presenter.askLoginOrSignUp();
                response = scanner.nextLine().trim();
            } while (!(response.equals("1") || response.equalsIgnoreCase("Login") || response.equals("2") || response.equalsIgnoreCase("Sign Up") || response.equals("3")));

            if (response.equals("3")) {
                presenter.notifyQuitting();
                return;
            }
            try {
                if (response.equals("1") || response.equalsIgnoreCase("Login")) {
                    do {
                        presenter.notifyLogin();
                        // Ask for username
                        presenter.promptUsername();
                        username = scanner.nextLine().trim();

                        if (username.equals(CancelThrowable.CANCEL_STRING)) {
                            throw new CancelThrowable();
                        }

                        // Ask for password
                        presenter.promptPassword();
                        password = scanner.nextLine().trim();

                        if (password.equals(CancelThrowable.CANCEL_STRING)) {
                            throw new CancelThrowable();
                        }

                    } while (!attemptLogin(username, password, userManager, presenter));

                    mainUsername = username;
                    presenter.successfullyLoggedIn(mainUsername);

                } else if (response.equals("2") || response.equalsIgnoreCase("Sign Up")) {
                    presenter.notifySignUp();
                    do {
                        // Asks for a username
                        presenter.promptUsername();
                        username = scanner.nextLine().trim();

                        if (username.equals(CancelThrowable.CANCEL_STRING)) {
                            throw new CancelThrowable();
                        }

                    } while (!isAvailable(username, userManager, presenter));

                    // Asks for a password
                    presenter.promptPassword();
                    password = scanner.nextLine().trim();

                    if (password.equals(CancelThrowable.CANCEL_STRING)) {
                        throw new CancelThrowable();
                    }

                    UserBuilder userBuilder = new UserBuilder();
                    userBuilder.buildUsername(username);
                    userBuilder.buildPassword(password);
                    userBuilder.buildPermissionLevel(PermissionLevel.ATTENDEE);

                    userManager.createNonSpeaker(userBuilder);
                    mainUsername = username;
                    presenter.successSignup();
                }

                // Creates and calls the appropriate Controller.UserController
                UserControllerFactory controllerFactory = new UserControllerFactory();
                UserController userController = controllerFactory.getUserController(mainUsername, userManager);

                userController.run(mainUsername, userManager, eventManager, messageManager, roomManager);

                presenter.successfullyLoggedOut(mainUsername);
            } catch (CancelThrowable ct) {
                presenter.returningToMenu();
            }
        }
    }

    /**
     * Checks if this username and password pair is associated with a Entity.User in the UseCase.UserManager.
     *
     * @param username The username being attempted.
     * @param password The password being attempted.
     * @param userManager The UseCase.UserManager associated with this session.
     * @param loginPresenter The Presenter.LoginPresenter creating the display.
     * @return True if and only if there is a Entity.User in the UseCase.UserManager with the given username and password pair.
     */
    private boolean attemptLogin(String username, String password, UserManager userManager, LoginPresenter loginPresenter) {
        if (userManager.hasUser(username)) {
            if (userManager.getUser(username).getPassword().equals(password)) {
                return true;
            }
            else {
                // Incorrect password
                loginPresenter.printIncorrectPassword();
                return false;
            }
        } else{
            // Username not found
            loginPresenter.printIncorrectUsername(username);
            return false;
        }

    }

    /**
     * Checks whether or not another Entity.User in the UseCase.UserManager has the given username.
     *
     * @param username The username being checked.
     * @param userManager The UseCase.UserManager associated with the current session.
     * @param loginPresenter The LoginPresented creating the display.
     * @return True if and only if the username is not taken.
     */
    private boolean isAvailable(String username, UserManager userManager, LoginPresenter loginPresenter) {
        if (userManager.hasUser(username)) {
            // This username is already taken
            loginPresenter.printUsernameAlreadyTaken(username);
            return false;
        }

        return true;
    }

}
