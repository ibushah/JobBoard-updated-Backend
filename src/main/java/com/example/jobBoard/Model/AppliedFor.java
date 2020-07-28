package com.example.jobBoard.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "applied_for")
public class AppliedFor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    @JsonIgnore
    @ManyToOne
    @JoinColumn
    private Job job;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="candidate_id")
    @JsonIgnoreProperties("AppliedForSet")
    User candidateProfile;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="company_id")
    @JsonIgnoreProperties("AppliedForSet")
    User companyProfile;


    @Column
    private Boolean isNotified;

    @Column
    Date appliedDate;

    public AppliedFor(Job job, User candidateProfile, User companyProfile, Boolean isNotified, Date appliedDate) {
        this.job = job;
        this.candidateProfile = candidateProfile;
        this.companyProfile = companyProfile;
        this.isNotified = isNotified;
        this.appliedDate = appliedDate;
    }

    public AppliedFor() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public User getCandidateProfile() {
        return candidateProfile;
    }

    public void setCandidateProfile(User candidateProfile) {
        this.candidateProfile = candidateProfile;
    }

    public User getCompanyProfile() {
        return companyProfile;
    }

    public void setCompanyProfile(User companyProfile) {
        this.companyProfile = companyProfile;
    }

    public Boolean getNotified() {
        return isNotified;
    }

    public void setNotified(Boolean notified) {
        isNotified = notified;
    }

    public Date getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(Date appliedDate) {
        this.appliedDate = appliedDate;
    }
}
