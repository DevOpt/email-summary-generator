package model;

import java.util.Date;
import lombok.Getter;

@Getter
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

    public void setFrom(String from) {
        this.from = from;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
