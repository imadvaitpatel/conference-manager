package Presenter;

import UseCase.EventManager;
import UseCase.MessageManager;
import UseCase.UserManager;

import java.util.HashSet;
import java.util.Set;

public class VipPresenter extends AttendeePresenter{

    /**
     * Constructs a new Presenter.VipPresenter object.
     * @param messageManager The UseCase.MessageManager of the session.
     * @param userManager The UseCase.UserManager of the session.
     * @param eventManager The UseCase.EventManager of the session.
     */
    public VipPresenter(MessageManager messageManager, UserManager userManager, EventManager eventManager){
        super(messageManager, userManager, eventManager);
    }

    /**
     * Prints a menu of actions which the vip user can perform.
     */
    public void printMenu(){
        System.out.println("\nYou are a VIP");
        System.out.println("What would you like to do? (Enter the number for the option you want)");
        System.out.println("1. See schedule of all events");
        System.out.println("2. See schedule of all VIP-only events");
        System.out.println("3. See my events");
        System.out.println("4. Sign up for events");
        System.out.println("5. Cancel enrolment in events");
        System.out.println("6. Messaging");
        System.out.println("7. Save Schedule");
        System.out.println("8. Sign out");
    }

    /**
     * Prints the schedule of all vip-only events
     */
    public void printVip0nlyEvents() {
        Set<String> events = eventManager.getEventNames();

        Set<String> vipEvents = new HashSet<>();
        for (String event : events) {
            if (eventManager.isVipOnly(event)) {
                vipEvents.add(event);
            }
        }

        System.out.println();
        if (vipEvents.size() == 0) {
            System.out.println("There are currently no VIP-only events being held.");
        } else {
            System.out.println("VIP Events:");
            printEvents(vipEvents);
        }
    }
}
