package com.example.jobBoard.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Notifications {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "notificationForUser")
    User notificationForUser;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "notificationByUser")
    User notificationByUser;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "job_id")
    Job job;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "recruiter_job_id")
    RecruiterJob recruiterJob;

    @Column
    String typeOfJob;

    @Column
    Boolean seenOrNot;

    @Column
    Date notificationDate;

    @Column
    String notificateFor;

    public Notifications() {
    }


    public Notifications(User notificationForUser, User notificationByUser, Job job, String typeOfJob, Boolean seenOrNot, Date notificationDate, String notificateFor) {
        this.notificationForUser = notificationForUser;
        this.notificationByUser = notificationByUser;
        this.job = job;
        this.typeOfJob = typeOfJob;
        this.seenOrNot = seenOrNot;
        this.notificationDate = notificationDate;
        this.notificateFor = notificateFor;
    }

    public Notifications(User notificationForUser, User notificationByUser, Job job, RecruiterJob recruiterJob, String typeOfJob, Boolean seenOrNot, Date notificationDate, String notificateFor) {
        this.notificationForUser = notificationForUser;
        this.notificationByUser = notificationByUser;
        this.job = job;
        this.recruiterJob = recruiterJob;
        this.typeOfJob = typeOfJob;
        this.seenOrNot = seenOrNot;
        this.notificationDate = notificationDate;
        this.notificateFor = notificateFor;
    }

    public RecruiterJob getRecruiterJob() {
        return recruiterJob;
    }

    public void setRecruiterJob(RecruiterJob recruiterJob) {
        this.recruiterJob = recruiterJob;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getNotificationForUser() {
        return notificationForUser;
    }

    public void setNotificationForUser(User notificationForUser) {
        this.notificationForUser = notificationForUser;
    }

    public User getNotificationByUser() {
        return notificationByUser;
    }

    public void setNotificationByUser(User notificationByUser) {
        this.notificationByUser = notificationByUser;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public String getTypeOfJob() {
        return typeOfJob;
    }

    public void setTypeOfJob(String typeOfJob) {
        this.typeOfJob = typeOfJob;
    }

    public Boolean getSeenOrNot() {
        return seenOrNot;
    }

    public void setSeenOrNot(Boolean seenOrNot) {
        this.seenOrNot = seenOrNot;
    }

    public Date getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(Date notificationDate) {
        this.notificationDate = notificationDate;
    }

    public String getNotificateFor() {
        return notificateFor;
    }

    public void setNotificateFor(String notificateFor) {
        this.notificateFor = notificateFor;
    }
}


