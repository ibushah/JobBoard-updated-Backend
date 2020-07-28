package com.example.jobBoard.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String email;
    @Column
    private String name;
    @Column
    private String password;

    @Column
    private Boolean active;

    @Column
    private String userType;

    @Column
    private Boolean profileActive;


    @OneToOne(mappedBy = "user")
    @JsonIgnoreProperties("user")
    Profile profile;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties("user")
    List<Job> jobList;

    @OneToMany(mappedBy = "candidateProfile", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("candidateProfile")
    private Set<AppliedFor> AppliedForSet;

    @OneToMany(mappedBy = "companyProfile", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("companyProfile")
    private Set<AppliedForRecruiterJob> AppliedForRecruiterSet;


    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties("user")
    List<RecruiterJob> recruiterJobs;




    public User(String email, String name, String password, Boolean active, String userType, Boolean profileActive, Profile profile, List<Job> jobList, Set<AppliedFor> appliedForSet, List<RecruiterJob> recruiterJobs) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.active = active;
        this.userType = userType;
        this.profileActive = profileActive;
        this.profile = profile;
        this.jobList = jobList;
        AppliedForSet = appliedForSet;
        this.recruiterJobs = recruiterJobs;
    }

    public User() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean isActive) {
        this.active = isActive;
    }


    public Boolean getActive() {
        return active;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Boolean getProfileActive() {
        return profileActive;
    }

    public void setProfileActive(Boolean profileActive) {
        this.profileActive = profileActive;
    }


    public List<Job> getJobList() {
        return jobList;
    }

    public void setJobList(List<Job> jobList) {
        this.jobList = jobList;
    }

    public List<RecruiterJob> getRecruiterJobs() {
        return recruiterJobs;
    }

    public void setRecruiterJobs(List<RecruiterJob> recruiterJobs) {
        this.recruiterJobs = recruiterJobs;
    }

    public Set<AppliedFor> getAppliedForSet() {
        return AppliedForSet;
    }

    public void setAppliedForSet(Set<AppliedFor> appliedForSet) {
        AppliedForSet = appliedForSet;
    }

    public Set<AppliedForRecruiterJob> getAppliedForRecruiterSet() {
        return AppliedForRecruiterSet;
    }

    public void setAppliedForRecruiterSet(Set<AppliedForRecruiterJob> appliedForRecruiterSet) {
        AppliedForRecruiterSet = appliedForRecruiterSet;
    }
}


