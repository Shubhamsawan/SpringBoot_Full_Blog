package com.sprinboot.blog.payload;

import java.util.Date;

public class ErrorDetails {

    private Date timeStamp;
    private String messagte;
    private String details;

    public ErrorDetails(Date timeStamp, String messagte, String details) {
        this.timeStamp = timeStamp;
        this.messagte = messagte;
        this.details = details;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMessagte() {
        return messagte;
    }

    public void setMessagte(String messagte) {
        this.messagte = messagte;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
