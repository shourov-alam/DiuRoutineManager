package com.al.diuroutinemanager.Model;

public class NotificationModel {

    String title,message,date;
    Long timestamp;


    public NotificationModel(){

    }

    public NotificationModel(String title, String message, String date,Long timestamp) {
        this.title = title;
        this.message = message;
        this.date = date;
        this.timestamp=timestamp;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}

