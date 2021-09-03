package api;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.*;
import model.Email;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class EmailResources {
    private static final String USER_ID = "abdurahman.sherif@gmail.com";
    private static final String DATE_PATTERN_1 = "EEE, dd MMM yyyy HH:mm:ss Z";
    private static final String DATE_PATTERN_2 = "dd MMM yyyy HH:mm:ss Z";

    private static Gmail service;

    public EmailResources() throws GeneralSecurityException, IOException {
        service = GoogleApiModule.gmailService();
    }

    public void listLabelsResponse() throws IOException {
        // Print the labels in the user's account.
        ListLabelsResponse listLabelsResponse = service.users().labels().list(USER_ID).execute();
        List<Label> labels = listLabelsResponse.getLabels();
        if (labels.isEmpty()) {
            System.out.println("No labels found.");
        } else {
            System.out.println("Labels:");
            for (Label label : labels) {
                System.out.printf("- %s\n", label.getName());
            }
        }
    }

    public List<Email> listMessages() throws IOException, ParseException {
        ListMessagesResponse listMessagesResponse = service.users().messages().list(USER_ID).execute();
        List<Message> messages = listMessagesResponse.getMessages();

        System.out.println("Messages: ");
        // Only get emails from the last 7 days
        return getEmails(messages, 3L);
    }

    private List<Email> getEmails(List<Message> messages, long duration) throws IOException, ParseException {
        List<Email> emails = new ArrayList<>();
        Date date = new Date();
        for (Message rawMsg : messages) {
            Message message = getMessage(rawMsg.getId());
            Email email = new Email();

            long timeDiffInDays = TimeUnit.DAYS.convert(System.currentTimeMillis() - date.getTime(),
                    TimeUnit.MILLISECONDS);
            if (timeDiffInDays >= duration) {
                break;
            }

            for (MessagePartHeader header : message.getPayload().getHeaders()) {
                switch (header.getName()) {
                    case "Date":
                        date = parseDate(header.getValue());
                        email.setDate(date);
                        break;
                    case "From":
                        email.setFrom(header.getValue());
                        break;
                    case "Subject":
                        email.setSubject(header.getValue());
                        break;
                }
            }
            emails.add(email);
        }
        return emails;
    }

    public Message getMessage(String id) throws IOException {
        return service.users()
                .messages()
                .get(USER_ID, id)
                .execute();
    }

    public void listenTopic(String topic) {
        // subscribe to a topic and get notified when it occurs
    }

    public void queryEmail(String topic) {
        // TODO: query email based on topic
    }

    private Date parseDate(String rawDate) throws ParseException {
        Date date;
        SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN_1);

        int i = 0;
        int maxRetries = 2;
        while (true) {
            try {
                date = format.parse(rawDate);
                break;
            } catch (ParseException pe) {
                // Retry with the second pattern, if it doesn't work throw an exception
                format = new SimpleDateFormat(DATE_PATTERN_2);
                if (++i == maxRetries) throw pe;
            }
        }
        return date;
    }
}
