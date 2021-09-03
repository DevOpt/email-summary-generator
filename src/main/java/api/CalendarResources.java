package api;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import org.apache.commons.lang3.time.DateUtils;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;

public class CalendarResources {

    private static Calendar service;

    public CalendarResources() throws GeneralSecurityException, IOException {
        service = GoogleApiModule.calendarService();
    }

    public void listEvents() throws IOException {
        // List the next 10 events from the primary calendar.
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list("primary")
                .setMaxResults(10)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();
        if (items.isEmpty()) {
            System.out.println("No upcoming events found.");
        } else {
            System.out.println("Upcoming events");
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
                System.out.printf("%s (%s)\n", event.getSummary(), start);
            }
        }
    }

    public List<Event> listEvents(DateTime startTime) throws IOException {
        Events events = service.events().list("primary")
                .setMaxResults(10)
                .setTimeMin(startTime)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        return events.getItems();
    }

    public void addEvent(String title, Date date) throws IOException {
        System.out.println("Events: ");
        DateTime startDateTime = new DateTime(date);
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime);

        DateTime endDateTime = new DateTime(DateUtils.addHours(date, 1));
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime);

        // Check if the event already exists
        if (eventExists(title, startDateTime)) {
            return;
        }

        Event event = new Event()
                .setSummary(title)
                .setDescription("Testing adding events")
                .setStart(start)
                .setEnd(end);

        event = service.events().insert("primary", event).execute();
        System.out.printf("Event created: %s\n", event.getHtmlLink());
    }

    private boolean eventExists(String title, DateTime startTime) throws IOException {
        List<Event> events = listEvents(startTime);
        for (Event event : events) {
            if (event.getSummary().equals(title) && event.getStart().getDateTime().getValue() == startTime.getValue()) {
                System.out.printf("%s already scheduled at %s", title, startTime);
                return true;
            }
        }
        return false;
    }
}
