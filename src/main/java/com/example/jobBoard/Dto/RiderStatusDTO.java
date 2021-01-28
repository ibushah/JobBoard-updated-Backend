package com.example.jobBoard.Dto;

public class RiderStatusDTO {

    Long userId;
    Boolean userOnlineStatus;

    public RiderStatusDTO(Boolean userOnlineStatus) {
        this.userOnlineStatus = userOnlineStatus;
    }

    public Boolean getUserOnlineStatus() {
        return userOnlineStatus;
    }

    public void setUserOnlineStatus(Boolean userOnlineStatus) {
        this.userOnlineStatus = userOnlineStatus;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
