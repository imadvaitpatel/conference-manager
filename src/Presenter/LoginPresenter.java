package Presenter;

import Util.CancelThrowable;

public class LoginPresenter {

    /**
     * Asks the user if they would like to either login as an existing Entity.User or sign up and create new Entity.User.
     */
    public void askLoginOrSignUp() {
        System.out.println("\nWhat would you like to do (Enter 1, 2 or 3 for the option you want):\n  1) Login\n  2) Sign Up\n  3) Quit");
    }

    /**
     * Prompts the user to enter a username.
     */
    public void promptUsername() {
        System.out.print("Please enter Username: ");
    }

    /**
     * Prompts the user to enter a password.
     */
    public void promptPassword() {
        System.out.print("Please enter Password: ");
    }

    /**
     * Tells the user that the username is not associated with any users.
     * @param username The username.
     */
    public void printIncorrectUsername(String username) {
        System.out.println("Username \"" + username + "\" not found! Please try again.");
    }

    /**
     * Tells the user that the password they entered is incorrect.
     */
    public void printIncorrectPassword() {
        System.out.println("Incorrect password! Please try again.");
    }

    /**
     * Tells the user that a username is already taken.
     * @param username The username.
     */
    public void printUsernameAlreadyTaken(String username) {
        System.out.println("Username \"" + username + "\" is already taken! Please try again.");
    }

    /**
     * Tells the user they are logging in
     */
    public void notifyLogin() {
        System.out.println("Enter your credentials to login (enter \""+CancelThrowable.CANCEL_STRING+"\" to cancel).");
    }

    /**
     * Tells the user they are signing up
     */
    public void notifySignUp() {
        System.out.println("Enter you credentials to sign up (enter \""+ CancelThrowable.CANCEL_STRING+"\" to cancel).");
    }

    /**
     * Tells the user they are returning to main menu
     */
    public void returningToMenu() {
        System.out.println("Returning to main menu...");
    }

    /**
     * Notifies the user that they have successfully signed up
     */
    public void successSignup() {
        System.out.println("Successfully signed up.");
    }

    /**
     * Informs the user that they have successfully logged in
     * @param mainUsername The username of the user
     */
    public void successfullyLoggedIn(String mainUsername){
        System.out.println("Logged in to " + mainUsername);
    }

    /**
     * Loading logging out
     */
    public void successfullyLoggedOut(String mainUserName){
        System.out.println("Logging you out of "+ mainUserName);
    }

    /**
     * Notifies the user that the application is quitting
     */
    public void notifyQuitting() {
        System.out.println("Quitting...");
    }

    /**
     * Tells the user to enter a number in the correct range (1-3)
     */
    public void notInRange(){
        System.out.println("Please enter a number between 1-3 and try again.");
    }
}
