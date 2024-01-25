package org.launchcode.bookshelfcorner.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class RegisterRequestDTO extends LoginRequestDTO {

    @NotNull(message = "Username is required.")
    @NotBlank(message = "Username is required.")
    @Size(min = 3, message = "Invalid username. Must be between 3 and 20 characters.")
    private String username;

//    private String verifyPassword;
//
//    public String getVerifyPassword() {
//        return verifyPassword;
//    }
//
//    public void setVerifyPassword(String verifyPassword) {
//        this.verifyPassword = verifyPassword;
//    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }
}
