package com.example.jobBoard.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ReviewAndRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(columnDefinition = "LONGTEXT")
    String review;
    @Column
    Integer rating;
    @Column
    String rateBy;
    @Column
    Date date;
    @Column
    String type;
    @Column
    String videoUrl;


    @ManyToOne
    @JoinColumn(name = "reviewer_id")
//    @JsonIgnoreProperties("reviewAndRatings")
    User reviewerProfile;




    @ManyToOne
    @JoinColumn(name = "reviewee_id")
//    @JsonIgnoreProperties("reviewAndRatingsForCandidate")
    User revieweeProfile;

    public ReviewAndRating() {
    }

    public ReviewAndRating(String review, Integer rating, String rateBy, Date date, String type, String videoUrl) {
        this.review = review;
        this.rating = rating;
        this.rateBy = rateBy;
        this.date = date;
        this.type = type;
        this.videoUrl = videoUrl;
    }

    public ReviewAndRating(String review, Integer rating, String rateBy, Date date, String type, String videoUrl, User reviewerProfile, User revieweeProfile) {
        this.review = review;
        this.rating = rating;
        this.rateBy = rateBy;
        this.date = date;
        this.type = type;
        this.videoUrl = videoUrl;
        this.reviewerProfile = reviewerProfile;
        this.revieweeProfile = revieweeProfile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRateBy() {
        return rateBy;
    }

    public void setRateBy(String rateBy) {
        this.rateBy = rateBy;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public User getReviewerProfile() {
        return reviewerProfile;
    }

    public void setReviewerProfile(User reviewerProfile) {
        this.reviewerProfile = reviewerProfile;
    }

    public User getRevieweeProfile() {
        return revieweeProfile;
    }

    public void setRevieweeProfile(User revieweeProfile) {
        this.revieweeProfile = revieweeProfile;
    }
}
