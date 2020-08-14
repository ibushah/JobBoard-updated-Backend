package com.example.jobBoard.Dto;

import java.io.Serializable;
import java.util.Date;

public class NotificationsDTO implements Serializable {
    Long jobId;
    String jobTitle;
    String username;
    byte[] userDp;
    Date date;
    Boolean seenOrNot;
    String typeOfJob;



    public NotificationsDTO() {
    }

    public NotificationsDTO(Long jobId, String jobTitle, String username, byte[] userDp, Date date, Boolean seenOrNot, String typeOfJob) {
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.username = username;
        this.userDp = userDp;
        this.date = date;
        this.seenOrNot = seenOrNot;
        this.typeOfJob = typeOfJob;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getUserDp() {
        return userDp;
    }

    public void setUserDp(byte[] userDp) {
        this.userDp = userDp;
    }

    public Boolean getSeenOrNot() {
        return seenOrNot;
    }

    public void setSeenOrNot(Boolean seenOrNot) {
        this.seenOrNot = seenOrNot;
    }

    public String getTypeOfJob() {
        return typeOfJob;
    }

    public void setTypeOfJob(String typeOfJob) {
        this.typeOfJob = typeOfJob;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }
}
