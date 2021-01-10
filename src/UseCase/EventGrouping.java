package UseCase;

import java.util.*;

// This class groups and arranges collections of events into specific orders and arrangements
// Used primarily for helping presenter classes arrange events
public class EventGrouping {

    /**
     * Arranges the events into a nested list where each sublist represents a single day and contains the events which
     * occurs on that day in chronological order
     * @param events The events being arranged
     * @param eventManager The EventManager for the given events
     * @return The given events grouped by day
     */
    public List<List<String>> groupByDay(Collection<String> events, EventManager eventManager) {
        List<String> sortedEvents = arrangeChronologically(events, eventManager);
        List<List<String>> schedule = new ArrayList<>();

        for (String event : sortedEvents) {
            boolean placed = false;

            for (List<String> dayList : schedule) {
                Date eventDate = eventManager.getTimeOfEvent(event);
                Date listDate = eventManager.getTimeOfEvent(dayList.get(0));

                if (sameDay(eventDate, listDate)) {
                    dayList.add(event);
                    placed = true;
                    break;
                }
            }

            if (!placed) {
                List<String> nextDayList = new ArrayList<>();
                nextDayList.add(event);
                schedule.add(nextDayList);
            }
        }

        return schedule;
    }

    /**
     * Arranges the given events into chronological order (based on start time)
     * @param events A collection of events
     * @param eventManager The EventManager for the events
     * @return The events arranged in chronological order
     */
    public List<String> arrangeChronologically(Collection<String> events, EventManager eventManager) {
        List<String> schedule = new ArrayList<>();

        for (String event : events) {
            boolean inserted = false;

            for (int i = 0; i < schedule.size(); i++) {
                Date eventDate = eventManager.getTimeOfEvent(event);
                Date otherEventDate = eventManager.getTimeOfEvent(schedule.get(i));

                if (eventDate.before(otherEventDate)) {
                    schedule.add(i, event);
                    inserted = true;
                    break;
                }
            }

            if (!inserted) {
                schedule.add(event);
            }
        }

        return schedule;
    }

    private boolean sameDay(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();

        calendar1.setTime(date1);
        calendar2.setTime(date2);

        return (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)) &&
                (calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR));
    }

}
