package com.example.demo.dto;

public class LoginResponse {
    private boolean success;
    private String message;
    private AdminDTO admin;
    private String token; // Optionnel si vous voulez utiliser JWT plus tard

    // Constructeurs
    public LoginResponse() {}

    public LoginResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public LoginResponse(boolean success, String message, AdminDTO admin) {
        this.success = success;
        this.message = message;
        this.admin = admin;
    }

    // Getters et Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AdminDTO getAdmin() {
        return admin;
    }

    public void setAdmin(AdminDTO admin) {
        this.admin = admin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
