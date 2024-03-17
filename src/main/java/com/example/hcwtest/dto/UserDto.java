package com.example.hcwtest.dto;

public class UserDto {

    private String userId;

    private String username;

    private String email;

    private String password;

    private String confirmPassword;

    public UserDto() {

    }

    public UserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UserDto(String userId, String username , String email,int x) {
        this.userId = userId;
        this.username = username;
        this.email = email;

    }

    public UserDto(String userId,String username, String email, String password) {
        this.userId= userId;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
