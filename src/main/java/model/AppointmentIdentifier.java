package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * This class is for adding instructions to identify schedulable events from email.
 */
@Getter
public class AppointmentIdentifier {
    /**
     * Unique substring of sender's email address.
     */
    @JsonProperty("sender")
    private String sender;

    /**
     * Substring of email subject that will be used to extract appointment date. TODO: Extend this to email body
     */
    @JsonProperty("subject")
    private String subject;

    /**
     * Title of the appointment to be scheduled.
     */
    @JsonProperty("title")
    private String title;

    public AppointmentIdentifier() {}
}
