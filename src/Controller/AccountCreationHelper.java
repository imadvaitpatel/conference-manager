package Controller;

import Presenter.AccountCreationPresenter;
import UseCase.SpeakerBuilder;
import UseCase.UserBuilder;
import UseCase.UserManager;
import Util.CancelThrowable;
import Util.PermissionLevel;

import java.util.Scanner;

public class AccountCreationHelper {

    public void choiceCreateAttendeeAccount(UserManager userManager, Scanner scanner, AccountCreationPresenter presenter) {
        presenter.notifyAttendeeCreation();
        try {
            String username = getUsername(userManager, scanner, presenter);
            String password = getPassword(scanner, presenter);

            PermissionLevel permissionLevel = PermissionLevel.ATTENDEE;

            UserBuilder userBuilder = new UserBuilder();
            userBuilder.buildUsername(username);
            userBuilder.buildPassword(password);
            userBuilder.buildPermissionLevel(permissionLevel);

            userManager.createNonSpeaker(userBuilder);

            presenter.successfullyCreatedAttendee(username);
        } catch (CancelThrowable ct) {
            presenter.returningToMenu();
        }
    }

    public void choiceCreateVipAccount(UserManager userManager, Scanner scanner, AccountCreationPresenter presenter) {
        presenter.notifyVipCreation();
        try {
            String username = getUsername(userManager, scanner, presenter);
            String password = getPassword(scanner, presenter);

            PermissionLevel permissionLevel = PermissionLevel.VIP;

            UserBuilder userBuilder = new UserBuilder();
            userBuilder.buildUsername(username);
            userBuilder.buildPassword(password);
            userBuilder.buildPermissionLevel(permissionLevel);

            userManager.createNonSpeaker(userBuilder);

            presenter.successfullyCreatedVipUser(username);
        } catch (CancelThrowable ct) {
            presenter.returningToMenu();
        }
    }

    public void choiceCreateSpeakerAccount(UserManager userManager, Scanner scanner, AccountCreationPresenter presenter) {
        presenter.notifySpeakerCreation();
        try {
            String username = getUsername(userManager, scanner, presenter);
            String password = getPassword(scanner, presenter);

            PermissionLevel permissionLevel = PermissionLevel.SPEAKER;

            SpeakerBuilder speakerBuilder = new SpeakerBuilder();
            speakerBuilder.buildUsername(username);
            speakerBuilder.buildPassword(password);
            speakerBuilder.buildPermissionLevel(permissionLevel);

            userManager.createSpeaker(speakerBuilder);

            presenter.successfullyCreatedSpeaker(username);
        } catch (CancelThrowable ct) {
            presenter.returningToMenu();
        }
    }

    public void choiceCreateOrganizer(UserManager userManager, Scanner scanner, AccountCreationPresenter presenter) {
        presenter.notifyOrganizerCreation();
        try {
            String username = getUsername(userManager, scanner, presenter);
            String password = getPassword(scanner, presenter);

            PermissionLevel permissionLevel = PermissionLevel.ORGANIZER;

            UserBuilder userBuilder = new UserBuilder();
            userBuilder.buildUsername(username);
            userBuilder.buildPassword(password);
            userBuilder.buildPermissionLevel(permissionLevel);

            userManager.createNonSpeaker(userBuilder);

            presenter.successfullyCreatedOrganizer(username);
        } catch(CancelThrowable ct) {
            presenter.returningToMenu();
        }
    }

    private String getUsername(UserManager userManager, Scanner scanner, AccountCreationPresenter presenter) throws CancelThrowable {
        String username;
        do {
            presenter.promptUsername();
            username = scanner.nextLine().trim();

            if (username.equals(CancelThrowable.CANCEL_STRING)) {
                throw new CancelThrowable();
            }

        } while (isNameTaken(username, userManager, presenter));
        return username;
    }

    private String getPassword(Scanner scanner, AccountCreationPresenter presenter) throws CancelThrowable {
        String password;
        presenter.promptPassword();
        password = scanner.nextLine().trim();

        if (password.equals(CancelThrowable.CANCEL_STRING)) {
            throw new CancelThrowable();
        }
        return password;
    }

    private boolean isNameTaken(String name, UserManager userManager, AccountCreationPresenter presenter) {
        if (userManager.hasUser(name)) {
            presenter.notifyNameTaken();
            return true;
        }
        return false;
    }
}
