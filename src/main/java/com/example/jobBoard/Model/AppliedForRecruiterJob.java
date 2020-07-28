package com.example.jobBoard.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "applied_or_refered_recruiterJobs")
public class AppliedForRecruiterJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn
    private RecruiterJob recruiterJob;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="candidate_id")
    @JsonIgnoreProperties("AppliedForRecruiterSet")
    User candidateProfile;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="company_id")
    @JsonIgnoreProperties("AppliedForRecruiterSet")
    User companyProfile;

    @Column
    private Boolean seenOrNot;

    @Column
    private  Boolean isApplied;

    @Column
    Date appliedDate;

    @Column
    Date referedDate;

    public AppliedForRecruiterJob(RecruiterJob recruiterJob, User candidateProfile, User companyProfile, Boolean seenOrNot, Boolean isApplied, Date appliedDate, Date referedDate) {
        this.recruiterJob = recruiterJob;
        this.candidateProfile = candidateProfile;
        this.companyProfile = companyProfile;
        this.seenOrNot = seenOrNot;
        this.isApplied = isApplied;
        this.appliedDate = appliedDate;
        this.referedDate = referedDate;
    }

    public AppliedForRecruiterJob() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RecruiterJob getRecruiterJob() {
        return recruiterJob;
    }

    public void setRecruiterJob(RecruiterJob recruiterJob) {
        this.recruiterJob = recruiterJob;
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

    public Boolean getSeenOrNot() {
        return seenOrNot;
    }

    public void setSeenOrNot(Boolean seenOrNot) {
        this.seenOrNot = seenOrNot;
    }

    public Boolean getApplied() {
        return isApplied;
    }

    public void setApplied(Boolean applied) {
        isApplied = applied;
    }

    public Date getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(Date appliedDate) {
        this.appliedDate = appliedDate;
    }

    public Date getReferedDate() {
        return referedDate;
    }

    public void setReferedDate(Date referedDate) {
        this.referedDate = referedDate;
    }
}
