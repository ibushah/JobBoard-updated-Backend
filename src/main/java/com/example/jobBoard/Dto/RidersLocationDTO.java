package com.example.jobBoard.Dto;

public class RidersLocationDTO {
     Long id;
     String name;
     String email;
     String password;
     Boolean active ;
     String userType;
     Boolean profileActive;
    Double longitude;
    Double latitude;
    Boolean userOnlineStatus;

    public RidersLocationDTO(Long id, String name, String email, String password, Boolean active, String userType, Boolean profileActive, Double longitude, Double latitude, Boolean userOnlineStatus) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.active = active;
        this.userType = userType;
        this.profileActive = profileActive;
        this.longitude = longitude;
        this.latitude = latitude;
        this.userOnlineStatus = userOnlineStatus;
    }

    public Boolean getUserOnlineStatus() {
        return userOnlineStatus;
    }

    public void setUserOnlineStatus(Boolean userOnlineStatus) {
        this.userOnlineStatus = userOnlineStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }



    public Boolean getProfileActive() {
        return profileActive;
    }

    public void setProfileActive(Boolean profileActive) {
        this.profileActive = profileActive;
    }
}
