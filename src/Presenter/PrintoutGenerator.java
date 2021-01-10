package Presenter;

import UseCase.EventGrouping;
import UseCase.EventManager;
import UseCase.UserManager;

import java.text.SimpleDateFormat;
import java.util.*;

public class PrintoutGenerator {

    private enum Style {
        ALL, ATTENDEE, SPEAKER
    };

    private final SimpleDateFormat fullDayFormat = new SimpleDateFormat("MMMM dd, yyyy");
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");

    private final String headString = "<!doctype html>\n" +
            "<html>\n" +
            "  <head>\n" +
            "    <title>Schedule</title>\n" +
            "    <link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css?family=Roboto\">\n" +
            "    <style>\n" +
            "      html {\n" +
            "        background-color: white\n" +
            "      }\n" +
            "      div.body {\n" +
            "        margin-left: 7%;\n" +
            "        margin-right: 7%;\n" +
            "      }\n" +
            "      div.header {\n" +
            "        margin-top: 10px;\n" +
            "        margin-bottom: 0px;\n" +
            "        border-width: 0px;\n" +
            "        border-style: solid;\n" +
            "        border-color: black;\n" +
            "        border-bottom-width: 3px;\n" +
            "        display: flex;\n" +
            "        flex-direction: row;\n" +
            "        justify-content: space-between;\n" +
            "        padding-left: 10px;\n" +
            "        padding-right: 10px;\n" +
            "      }\n" +
            "      h2 {\n" +
            "        font-family: Roboto;\n" +
            "      }\n" +
            "      h3 {\n" +
            "        align-self: center;\n" +
            "        font-family: Roboto;\n" +
            "      }\n" +
            "      li {\n" +
            "        font-family: Roboto;\n" +
            "      }\n" +
            "      div.allDateSection {\n" +
            "        border-width: thin;\n" +
            "        border-style: solid;\n" +
            "        padding: 20px;\n" +
            "        background-color: #EEFFFF;\n" +
            "        border-radius: 20px;\n" +
            "        margin-bottom: 20px;\n" +
            "      }\n" +
            "      div.attendeeDateSection {\n" +
            "        border-width: thin;\n" +
            "        border-style: solid;\n" +
            "        padding: 20px;\n" +
            "        background-color: #EEFFEE;\n" +
            "        border-radius: 20px;\n" +
            "        margin-bottom: 20px;\n" +
            "      }\n" +
            "      div.speakerDateSection {\n" +
            "        border-width: thin;\n" +
            "        border-style: solid;\n" +
            "        padding: 20px;\n" +
            "        background-color: #FFEEFF;\n" +
            "        border-radius: 20px;\n" +
            "        margin-bottom: 20px;\n" +
            "      }\n" +
            "      h3.dateSectionHeader {\n" +
            "        margin-bottom: 5px;\n" +
            "        margin-top: 0px;\n" +
            "      }\n" +
            "    </style>\n" +
            "  </head>\n" +
            "  <body>\n";

    private final String tailString = "    </div>\n" +
            "  </body>\n" +
            "</html>";

    /**
     * Generates the html code for the schedule of an attendee
     * @param username The username of the attendee
     * @param eventManager The EventManager associated with this session
     * @param userManager The UserManager associated with this session
     * @return A string containing the generated html code
     */
    public String generateAttendeeSchedule(String username, EventManager eventManager, UserManager userManager) {
        return headString
                + generateHeaderCode(eventManager)
                + generateAttendeeScheduleCode(username, eventManager, userManager)
                + generateFullScheduleCode(eventManager)
                + tailString;
    }

    /**
     * Generates the html code for the schedule of a speaker
     * @param speakerName The username of the speaker
     * @param eventManager The EventManager associated with this session
     * @param userManager The UserManager associated with this session
     * @return A string containing the generated html code
     */
    public String generateSpeakerSchedule(String speakerName, EventManager eventManager, UserManager userManager) {
        return headString
                + generateHeaderCode(eventManager)
                + generateSpeakerScheduleCode(speakerName, eventManager, userManager)
                + generateFullScheduleCode(eventManager)
                + tailString;
    }

    /**
     * Generates the html code for the schedule of a conference
     * @param eventManager The EventManager associated with this session
     * @return A string containing the generated html code
     */
    public String generateFullSchedule(EventManager eventManager) {
        return headString
                + generateHeaderCode(eventManager)
                + generateFullScheduleCode(eventManager)
                + tailString;
    }

    private String generateHeaderCode(EventManager eventManager) {
        EventGrouping eventGrouping = new EventGrouping();

        List<String> events = eventGrouping.arrangeChronologically(eventManager.getEventNames(), eventManager);
        String startDay = fullDayFormat.format(eventManager.getTimeOfEvent(events.get(0)));
        String endDay = fullDayFormat.format(eventManager.getTimeOfEvent(events.get(events.size()-1)));


        return String.format(
                "    <div class=\"header\">\n" +
                "        <h3>CSC207 Tech Conference</h3>\n" +
                "        <div class=\"headerTabs\">\n" +
                "          <h3>%s - %s</h3>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "    <div class=\"body\">\n", startDay, endDay);
    }

    private String generateFullScheduleCode(EventManager eventManager) {
        EventGrouping eventGrouping = new EventGrouping();

        Collection<String> allEvents = eventManager.getEventNames();
        List<List<String>> groupedEvents = eventGrouping.groupByDay(allEvents, eventManager);

        StringBuilder codeStr = new StringBuilder();

        codeStr.append("      <h2>All Events:</h2>\n");

        for (List<String> dayList : groupedEvents) {
            codeStr.append(generateDateSectionCode(dayList, eventManager, Style.ALL));
        }

        return codeStr.toString();
    }

    private String generateAttendeeScheduleCode(String username, EventManager eventManager, UserManager userManager) {
        EventGrouping eventGrouping = new EventGrouping();

        Collection<String> myEvents = userManager.getEvents(username);
        List<List<String>> groupedEvents = eventGrouping.groupByDay(myEvents, eventManager);

        StringBuilder codeStr = new StringBuilder();

        codeStr.append("      <h2>My Events:</h2>\n");

        for (List<String> dayList : groupedEvents) {
            codeStr.append(generateDateSectionCode(dayList, eventManager, Style.ATTENDEE));
        }

        return codeStr.toString();
    }

    private String generateSpeakerScheduleCode(String speakerName, EventManager eventManager, UserManager userManager) {
        EventGrouping eventGrouping = new EventGrouping();

        if (!userManager.hasSpeaker(speakerName)) {
            return "";
        }

        Collection<String> myEvents = userManager.getHostedEvents(speakerName);
        List<List<String>> groupedEvents = eventGrouping.groupByDay(myEvents, eventManager);

        StringBuilder codeStr = new StringBuilder();

        codeStr.append("      <h2>Speaking at:</h2>\n");

        for (List<String> dayList : groupedEvents) {
            codeStr.append(generateDateSectionCode(dayList, eventManager, Style.ATTENDEE));
        }

        return codeStr.toString();
    }

    private String generateDateSectionCode(List<String> events, EventManager eventManager, Style style) {
        StringBuilder codeStr = new StringBuilder();

        String fullDayString = fullDayFormat.format(eventManager.getTimeOfEvent(events.get(0)));

        switch (style) {
            case ALL:
                codeStr.append("      <div class=\"allDateSection\">\n");
                break;
            case ATTENDEE:
                codeStr.append("      <div class=\"attendeeDateSection\">\n");
                break;
            case SPEAKER:
                codeStr.append("      <div class=\"speakerDateSection\">\n");
                break;
        }
        codeStr.append(String.format("        <h3 class=\"dateSectionHeader\">%s</h3>\n", fullDayString));

        for (String event : events) {
            String timeString = timeFormat.format(eventManager.getTimeOfEvent(event));

            Set<String> speakers = eventManager.getSpeakersAtEvent(event);
            String speakerString = getSpeakerString(speakers);
            String vipString = (eventManager.isVipOnly(event) ? "[VIP] " : "");
            String roomCode = eventManager.getRoomCodeOfEvent(event);

            codeStr.append(String.format("        <li>%s (%s): %s<strong>%s</strong> %s</li>\n", timeString, roomCode, vipString, event, speakerString));
        }

        codeStr.append("      </div>\n");

        return codeStr.toString();
    }

    private String getSpeakerString(Collection<String> speakers) {
        StringBuilder speakerString = new StringBuilder();
        List<String> speakerList = new ArrayList<>(speakers);

        if (!speakerList.isEmpty()) {
            speakerString.append("by ").append(speakerList.get(0));
        }

        for (int i = 1; i < speakerList.size(); i++) {
            if (i == speakerList.size()-1) {
                speakerString.append(", and ").append(speakerList.get(i));
            } else {
                speakerString.append(", ").append(speakerList.get(i));
            }
        }

        return speakerString.toString();
    }
}
