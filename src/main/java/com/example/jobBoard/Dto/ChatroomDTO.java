package com.example.jobBoard.Dto;

import com.example.jobBoard.Model.User;

import java.io.Serializable;
import java.util.Date;

public class ChatroomDTO implements Serializable {

    User user;
    String message;
    String chatroomId;
    Boolean seen;
    Date date;
    Long sender;

    public ChatroomDTO(User user, String message, String chatroomId, Boolean seen, Date date, Long sender) {
        this.user = user;
        this.message = message;
        this.chatroomId = chatroomId;
        this.seen = seen;
        this.date = date;
        this.sender = sender;
    }

    public ChatroomDTO() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(String chatroomId) {
        this.chatroomId = chatroomId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }
}
