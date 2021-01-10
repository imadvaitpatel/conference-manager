package UseCase;

import Entity.Event;
import Entity.Room;
import UseCase.EventManager;
import UseCase.RoomManager;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class StatisticsGenerator {
    // This class generates summary statistics of the conference (use case?)

    // The UseCase.EventManager being used to generate the statistics
    private EventManager eventManager;

    //The UseCase.RoomManager being used to generate the statistics
    private RoomManager roomManager;

    /**
     * Constructs a new UseCase.StatisticsGenerator object
     * @param eventManager The UseCase.EventManager of this session
     * @param roomManager The UseCase.RoomManager of this session
     */
    public StatisticsGenerator(EventManager eventManager, RoomManager roomManager){
        this.eventManager = eventManager;
        this.roomManager = roomManager;
    }

    /**
     * Find the most popular type of event (Entity.Talk, Entity.Discussion, Speakerless) based on number of each event scheduled
     * @return The list of most popular type(s) of event(s)
     */
    public ArrayList<String> generateMostPopularTypeEvent(){
        // returns if a talk, party or discussion is the most popular
        int numTalk = eventManager.getTalks().size();
        int numDiscussion = eventManager.getDiscussions().size();
        int numSpeakerlessEvent = eventManager.getParties().size();
        int maxEventNumber = Math.max(numTalk, Math.max(numDiscussion, numSpeakerlessEvent));
        ArrayList<String> popularEvents = new ArrayList<>();
        if (maxEventNumber == 0){
            return popularEvents;
        }
        if (numTalk == maxEventNumber){
            popularEvents.add("Talks");
        }
        if (numDiscussion == maxEventNumber){
            popularEvents.add("Discussions");
        }
        if (numSpeakerlessEvent == maxEventNumber){
            popularEvents.add("Speakerless Events");
        }

        return popularEvents;
    }

    /**
     * Finds number of events scheduled of the most popular event type
     * @return The number of events of the most popular event type
     */
    public int generateNumberOfMostPopularTypeEvent(){
        //returns the number of events of the most popular event type
        int numTalk = eventManager.getTalks().size();
        int numDiscussion = eventManager.getDiscussions().size();
        int numSpeakerlessEvent = eventManager.getParties().size();
        return Math.max(numTalk, Math.max(numDiscussion, numSpeakerlessEvent));
    }

    /**
     * Find the percentage of event fullness for each event scheduled
     * @return The list of events along with their percent fullness
     */
    public ArrayList<String> generateEventsFullStatistic(){
        //returns the percentage of attendees compared to capacity of event (for each event)
        Set<Event> events = eventManager.getEvents();
        ArrayList<String> fullStats = new ArrayList<>();
        NumberFormat percent = NumberFormat.getPercentInstance();
        for (Event event: events){
            double fullPercent = event.getAttendees().size() / (double)event.getCapacity();
            fullStats.add(event.getName()+ " : " + percent.format(fullPercent) + " full");
        }
        return fullStats;
    }

    /**
     * Finds the most popular event(s) (based on attendance size)
     * @return A list of event names
     */
    public ArrayList<String> generateTop1Events(){
        //returns the top events in the entire conference (most full events)
        ArrayList<String> top1Events = new ArrayList<>();
        Set<Event> events = eventManager.getEvents();
        int maxSize = getMaxAttendee(events);
        if (maxSize == 0) {
            return top1Events;
        }
        for (Event event: events){
            if (event.getAttendees().size() == maxSize){
                top1Events.add(event.getName());
            }
        }
        return top1Events;

    }

    /**
     * Finds the second most popular event(s) (based on attendance size)
     * @return The list of event names
     */
    public ArrayList<String> generateTop2Events(){
        //returns the second most full events
        Set<Event> allEvents = eventManager.getEvents();
        int max = getMaxAttendee(allEvents);
        Set<Event> notTop1Event = getEventsWithSizeLessThan(allEvents, max);
        int secondMax = getMaxAttendee(notTop1Event);
        ArrayList<String> top2Events = new ArrayList<>();
        if (secondMax == 0){
            return top2Events;
        }
        for (Event event: notTop1Event){
            if (event.getAttendees().size() == secondMax){
                top2Events.add(event.getName());
            }
        }
        return top2Events;
    }

    /**
     * Finds the third most popular event(s) (based on attendance size)
     * @return The list of event names
     */
    public ArrayList<String> generateTop3Events(){
        //returns the third most full events
        Set<Event> allEvents = eventManager.getEvents();
        int max = getMaxAttendee(allEvents);
        Set<Event> notTop1Event = getEventsWithSizeLessThan(allEvents, max);
        int secondMax = getMaxAttendee(notTop1Event);
        Set<Event> notTop2Event = getEventsWithSizeLessThan(notTop1Event, secondMax);
        int thirdMax = getMaxAttendee(notTop2Event);
        ArrayList<String> top3Events = new ArrayList<>();
        if (thirdMax == 0){
            return top3Events;
        }
        for (Event event: notTop2Event){
            if (event.getAttendees().size() == thirdMax){
                top3Events.add(event.getName());
            }
        }
        return top3Events;

    }

    //helper method that returns the maximum amount of attendees in the set of events
    private int getMaxAttendee(Set<Event> events){
        int max = 0;
        for (Event event: events){
            if (event.getAttendees().size() > max){
                max = event.getAttendees().size();
            }
        }
        return max;
    }

    //helper method that returns a set of events with attendees less than a given size
    private Set<Event> getEventsWithSizeLessThan(Set<Event> events, int size){
        Set<Event> newList = new HashSet<>();
        for(Event event: events){
            if (event.getAttendees().size() < size){
                newList.add(event);
            }
        }
        return newList;
    }

    /**
     * Finds the most popular room(s) (based on number of events held in that room)
     * @return The list of Entity.Room codes
     */
    public ArrayList<String> generateTop1Rooms(){
        //returns the top 1 rooms in the entire conference (most used rooms)
        Set<Room> rooms = roomManager.getRooms();
        int max = getMaxEvents(rooms);
        ArrayList<String> top1Rooms = new ArrayList<>();
        if (max == 0){
            return top1Rooms;
        }
        for (Room room: rooms){
            if (room.getEvents().size() == max){
                top1Rooms.add(room.getRoomCode());
            }
        }
        return top1Rooms;
    }

    /**
     * Finds the second most popular room(s) (based on number of events held in that room)
     * @return The list of Entity.Room codes
     */
    public ArrayList<String> generateTop2Rooms(){
        //returns the top 2  rooms in the entire conference (second most used rooms)
        Set<Room> allRooms = roomManager.getRooms();
        Set<Room> notTop1Rooms = otherRooms(allRooms, generateTop1Rooms());
        int secondMax = getMaxEvents(notTop1Rooms);
        ArrayList<String> top2Rooms = new ArrayList<>();
        if (secondMax == 0){
            return top2Rooms;
        }
        for (Room room: notTop1Rooms){
            if (room.getEvents().size() == secondMax){
                top2Rooms.add(room.getRoomCode());
            }
        }
        return top2Rooms;
    }

    /**
     * Find the third most popular room(s) (based on number of events being held in that room)
     * @return The list of Entity.Room codes
     */
    public ArrayList<String> generateTop3Rooms(){
        Set<Room> allRooms = roomManager.getRooms();
        ArrayList<String> top1Top2Rooms = new ArrayList<>(generateTop1Rooms());
        top1Top2Rooms.addAll(generateTop2Rooms());
        Set<Room> notTop2Rooms = otherRooms(allRooms, top1Top2Rooms);
        int thirdMax = getMaxEvents(notTop2Rooms);
        ArrayList<String> top3Rooms = new ArrayList<>();
        if (thirdMax == 0){
            return top3Rooms;
        }
        for (Room room: notTop2Rooms){
            if (room.getEvents().size() == thirdMax){
                top3Rooms.add(room.getRoomCode());
            }
        }
        return top3Rooms;
    }

    //helper method that returns the maximum amount of events held in the set of rooms
    private int getMaxEvents(Set<Room> rooms){
        int max = 0;
        for (Room room: rooms){
            if (room.getEvents().size() > max){
                max = room.getEvents().size();
            }
        }
        return max;
    }

    //returns the set of rooms except the ones in the array list
    private Set<Room> otherRooms(Set<Room> rooms, ArrayList<String> roomCodes){
        Set<Room> newRooms = new HashSet<>(rooms);
        for (String roomCode: roomCodes) {
            newRooms.removeIf(room -> room.getRoomCode().equals(roomCode));
        }
        return newRooms;
    }

    /**
     * Finds the average number of attendees per day at the conference
     * @return a double of the average
     */
    public double generateAverageAttendee(){
        //returns the average number of attendees for any given day
        Set<Event> events = eventManager.getEvents();
        int attendees = 0;
        for (Event event:events){
            attendees += event.getAttendees().size();
        }
        double days = getNumberOfDays(events);
        if (attendees == 0 || days == 0){
            return 0;
        } else {
            return attendees/days;
        }

    }

    /**
     * Finds the average number of events held per day at the conference
     * @return a double of the average
     */
    public double generateAverageEvent(){
        //returns the average number of events held a day
        Set<Event> events = eventManager.getEvents();
        int numEvents = events.size();
        double days = getNumberOfDays(events);
        if (numEvents == 0 || days == 0) {
            return 0;
        }
        else {
            return numEvents/days;
        }
    }

    //helper method to find the number of days that the conference is held
    private double getNumberOfDays(Set<Event> events){
        ArrayList<String> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        for (Event event: events){
            calendar.setTime(event.getDateAndTime());
            String date = calendar.get(Calendar.DAY_OF_YEAR) + " " + calendar.get(Calendar.DAY_OF_MONTH)
                    + " " + calendar.get(Calendar.DATE);
            if (!dates.contains(date)){
                dates.add(date);
            }
        }
        return dates.size();
    }
}
