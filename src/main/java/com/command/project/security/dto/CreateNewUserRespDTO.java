package com.command.project.security.dto;

public class CreateNewUserRespDTO {

    private String email;
    private String username;

    public CreateNewUserRespDTO(){

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
