package com.example.jobBoard.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
public class Friend {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userObj;

    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties("friendList")
    private User friend;

    String status;

    public Friend(User userObj, User friend, String status) {
        this.userObj = userObj;
        this.friend = friend;
        this.status = status;
    }



    public User getUserObj() {
        return userObj;
    }

    public void setUserObj(User userObj) {
        this.userObj = userObj;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return userObj;
    }

    public void setUser(User userObj) {
        this.userObj = userObj;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }

    public Friend() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
