package com.example.jobBoard.Dto;

public class ResetPasswordDTO {
    String token;
    String password;

    public ResetPasswordDTO(String token, String password) {
        this.token = token;
        this.password = password;
    }

    public ResetPasswordDTO() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
