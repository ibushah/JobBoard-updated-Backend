package com.example.jobBoard.Dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public class ReviewAndRatingDTO {

    String review;
    Integer rating;
    Long candidateId;
    Long jobId;
    Long companyId;
    Date date;
    String ratedBy;
    String type;
    MultipartFile video;

    public ReviewAndRatingDTO() {
    }

    public ReviewAndRatingDTO(String review, Integer rating, Long candidateId, Long jobId, Long companyId, Date date, String ratedBy, String type, MultipartFile video) {
        this.review = review;
        this.rating = rating;
        this.candidateId = candidateId;
        this.jobId = jobId;
        this.companyId = companyId;
        this.date = date;
        this.ratedBy = ratedBy;
        this.type = type;
        this.video = video;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRatedBy() {
        return ratedBy;
    }

    public void setRatedBy(String ratedBy) {
        this.ratedBy = ratedBy;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MultipartFile getVideo() {
        return video;
    }

    public void setVideo(MultipartFile video) {
        this.video = video;
    }
}
