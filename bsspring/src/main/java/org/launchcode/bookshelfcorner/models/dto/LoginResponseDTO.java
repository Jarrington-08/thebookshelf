package org.launchcode.bookshelfcorner.models.dto;

public class LoginResponseDTO {

    private Integer userId;
    private String message;

    public LoginResponseDTO(Integer userId, String message) {
        super();
        this.message = message;
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
