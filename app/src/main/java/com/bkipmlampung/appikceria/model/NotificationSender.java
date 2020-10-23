package com.bkipmlampung.appikceria.model;

public class NotificationSender {

    public NotificationModel data;
    public String to;

    public NotificationSender(NotificationModel data, String to) {
        this.data = data;
        this.to = to;
    }

    public NotificationSender() {
    }

}
