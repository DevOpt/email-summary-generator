package model;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Email {
    private String from;
    private String subject;
    private Date date;

    public Email() {
        this.subject = null;
        this.from = null;
        this.date = null;
    }

    public Email(String from, String subject, Date date) {
        this.subject = subject;
        this.from = from;
        this.date = date;
    }
}
