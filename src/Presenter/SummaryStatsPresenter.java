package Presenter;

import UseCase.StatisticsGenerator;

import java.text.NumberFormat;
import java.util.ArrayList;

public class SummaryStatsPresenter {

    //The UseCase.StatisticsGenerator that is being used by the presenter
    private StatisticsGenerator statisticsGenerator;

    /**
     * Constructs a new Presenter.SummaryStatsPresenter object
     * @param statisticsGenerator The UseCase.StatisticsGenerator of this session
     */
    public SummaryStatsPresenter(StatisticsGenerator statisticsGenerator){
        this.statisticsGenerator = statisticsGenerator;
    }

    /**
     * Prints a menu of statistics choices for the Organizer
     */
    public void printStatsMenu(){
        System.out.println("\nWhat kind of statistics would you like to view?");
        System.out.println("1. Event Enrollment Statistics");
        System.out.println("2. Top 3 Events");
        System.out.println("3. Top 3 Rooms");
        System.out.println("4. Average number of events per day");
        System.out.println("5. Average number of attendees per day");
        System.out.println("6. Cancel");
    }

    /**
     * Prints the event enrollment statistics of the conference (Most popular type of event and capacity)
     */
    public void printEventEnrollStats(){
        printMostPopularTypeOfEvent();
        System.out.println();
        printCapacityStats();
    }

    //Helper method to print the most popular type of event being held in the conference
    private void printMostPopularTypeOfEvent(){
        ArrayList<String> events = statisticsGenerator.generateMostPopularTypeEvent();
        System.out.println("\nThe most popular type(s) of event(s) being enrolled in is/are: ");
        if (events.size() == 0){
            System.out.println("\nThere are currently no events being held.");
        } else {
            int amount = statisticsGenerator.generateNumberOfMostPopularTypeEvent();
            for (String event:events){
                System.out.println("- "+event + " : " + amount+ " being held currently");
            }
        }

    }

    //Helper method to print the fullness percentage of each event
    private void printCapacityStats(){
        ArrayList<String> capacityStats = statisticsGenerator.generateEventsFullStatistic();
        System.out.println("\nHere are the event capacity statistics:");
        if (capacityStats.size() == 0){
            System.out.println("\nThere are currently no events being held.");
        } else {
            for (String stat: capacityStats){
                System.out.println("- "+stat);
            }
        }
    }

    /**
     * Prints the top 3 most popular events based on number of attendees
     */
    public void printTopEvents(){
        ArrayList<String> top1 = statisticsGenerator.generateTop1Events();
        ArrayList<String> top2 = statisticsGenerator.generateTop2Events();
        ArrayList<String> top3 = statisticsGenerator.generateTop3Events();
        System.out.println("Here are the top events based on the highest attendance size");
        if (top1.size() == 0){
            System.out.println("The events currently have no one enrolled yet.");
        } else {
            for (String event: top1){
                System.out.println("1. "+event);
            }
            if (top2.size() > 0){
                for (String event: top2){
                    System.out.println("2. "+event);
                }
            }
            if (top3.size() > 0){
                for (String event: top3){
                    System.out.println("3. "+event);
                }
            }
        }
    }

    /**
     * Prints the top 3 most popular rooms based on number of events being held in the rooms
     */
    public void printTopRooms(){
        ArrayList<String> top1 = statisticsGenerator.generateTop1Rooms();
        ArrayList<String> top2 = statisticsGenerator.generateTop2Rooms();
        ArrayList<String> top3 = statisticsGenerator.generateTop3Rooms();
        System.out.println("Here are the top rooms based on the highest number of events being held in each room");
        if (top1.size() == 0){
            System.out.println("The rooms currently have no events being scheduled in them.");
        } else {
            for (String room: top1){
                System.out.println("1. "+room);
            }
            if (top2.size() > 0){
                for (String room: top2){
                    System.out.println("2. "+room);
                }
            }
            if (top3.size() > 0){
                for (String room: top3){
                    System.out.println("3. "+room);
                }
            }
        }
    }

    /**
     * Prints the average attendee number per day
     */
    public void printAverageEventNumber(){
        NumberFormat decimal = NumberFormat.getNumberInstance();
        double average = statisticsGenerator.generateAverageEvent();
        System.out.println("\nThe average number of events held per day is: " + decimal.format(average));
    }

    /**
     * Prints the average event number per day
     */
    public void printAverageAttendeeNumber(){
        NumberFormat decimal = NumberFormat.getNumberInstance();
        double average = statisticsGenerator.generateAverageAttendee();
        System.out.println("\nThe average number of attendees per day is: " + decimal.format(average));
    }

    /**
     * Tells user that they successfully cancelled and are returning back to the main menu
     */
    public void returnBackToMenu(){
        System.out.println("\nReturning back to main menu");
    }

    /**
     * Tells the user that their input is not a number
     */
    public void notANumber(){
        System.out.println("\nPlease enter a number and try again.");
    }

    /**
     * Tells the user that the number they have entered in not in the range
     */
    public void numNotInRange(){
        System.out.println("\nThe number you entered is not in the range of 1-6. Please try again");
    }
}
