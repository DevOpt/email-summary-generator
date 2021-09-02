import api.EmailResources;
import model.Email;
import util.EmailSummaryUtil;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmailSummaryGenerator {

    EmailResources emailResources;

    public EmailSummaryGenerator() throws GeneralSecurityException, IOException {
        emailResources = new EmailResources();
    }

    public void generate() throws IOException, ParseException {
        List<Email> emails =  emailResources.listMessages();
        HashMap<String, Integer> emailAggregator = new HashMap<>();

        for (Email email : emails) {
            String sender = email.getFrom();

            if (sender != null && email.getDate() != null) {
                String senderDomain = sender.substring(sender.indexOf('@') + 1, sender.length() - 1);
                emailAggregator.put(senderDomain, emailAggregator.getOrDefault(senderDomain, 0) + 1);
            }
        }
        populateEmailSummaryTable(EmailSummaryUtil.sortByValue(emailAggregator));
    }

    private void populateEmailSummaryTable(Map<String, Integer> emailAggregator) {
        System.out.println("+------------------------------------------+");
        System.out.println("|              NAME              |  VALUE  |");
        System.out.println("+--------------------------------+---------+");
        for (Map.Entry<String, Integer> emails : emailAggregator.entrySet()) {
            StringBuilder nameSpace = new StringBuilder();
            for (int i =0; i < 30 - emails.getKey().length(); i++) { nameSpace.append(" "); }

            StringBuilder valueSpace = new StringBuilder();
            for (int i =0; i < 7 - String.valueOf(emails.getValue()).length(); i++) { valueSpace.append(" "); }

            System.out.println("| " + emails.getKey() + nameSpace + " | " + emails.getValue() + valueSpace + " |");
        }
        System.out.println("+------------------------------------------+");
    }

    private void scheduleAppointments() {
        // TODO: Get gmail api for query and use calendar api to add event
    }
}
