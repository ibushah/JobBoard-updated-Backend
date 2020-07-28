package com.example.jobBoard.Dto;

import java.io.Serializable;
import java.util.Date;

public class AllCandidatesReferedOrNotList  implements Serializable {
    Long user_id;
    String name;
    byte[] dp;
    String presentationLetter;
    String dpContentType;
    Date appliedDate;
    Boolean isApplied;
    Boolean isSeen;
    Date referedDate;

    public AllCandidatesReferedOrNotList(Long user_id, String name, byte[] dp, String presentationLetter, String dpContentType, Date appliedDate, Boolean isApplied, Boolean isSeen, Date referedDate) {
        this.user_id = user_id;
        this.name = name;
        this.dp = dp;
        this.presentationLetter = presentationLetter;
        this.dpContentType = dpContentType;
        this.appliedDate = appliedDate;
        this.isApplied = isApplied;
        this.isSeen = isSeen;
        this.referedDate = referedDate;
    }

    public AllCandidatesReferedOrNotList() {
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getDp() {
        return dp;
    }

    public void setDp(byte[] dp) {
        this.dp = dp;
    }

    public String getPresentationLetter() {
        return presentationLetter;
    }

    public void setPresentationLetter(String presentationLetter) {
        this.presentationLetter = presentationLetter;
    }

    public String getDpContentType() {
        return dpContentType;
    }

    public void setDpContentType(String dpContentType) {
        this.dpContentType = dpContentType;
    }

    public Date getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(Date appliedDate) {
        this.appliedDate = appliedDate;
    }

    public Boolean getApplied() {
        return isApplied;
    }

    public void setApplied(Boolean applied) {
        isApplied = applied;
    }

    public Boolean getSeen() {
        return isSeen;
    }

    public void setSeen(Boolean seen) {
        isSeen = seen;
    }

    public Date getReferedDate() {
        return referedDate;
    }

    public void setReferedDate(Date referedDate) {
        this.referedDate = referedDate;
    }
}
