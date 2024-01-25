package org.launchcode.bookshelfcorner.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ChangePasswordRequestDTO {

    @NotNull(message = "Password is required.")
    @NotBlank(message = "Password is required.")
    @Size(min = 8, message = "Invalid password. Must be 8 or more characters.")
    private String password;

    private int userId;

    public int getUserId() { return userId; }

    public void setUserId(int userId) { this.userId = userId; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
