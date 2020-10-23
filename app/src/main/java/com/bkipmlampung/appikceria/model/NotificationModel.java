package com.bkipmlampung.appikceria.model;

import com.google.gson.annotations.SerializedName;

public class NotificationModel {

    @SerializedName("title")
    private String title;
    @SerializedName("message")
    private String message;
    @SerializedName("channel_id")
    private String channel_id;
    @SerializedName("grup_id")
    private String group_id;
    @SerializedName("id")
    private String id;
    @SerializedName("user")
    private String user;
    @SerializedName("photo_path")
    private String photo_path;

    public NotificationModel(String title, String message, String channel_id, String group_id, String id, String user, String photo_path) {
        this.title = title;
        this.message = message;
        this.channel_id = channel_id;
        this.group_id = group_id;
        this.id = id;
        this.user = user;
        this.photo_path = photo_path;
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

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }
}
