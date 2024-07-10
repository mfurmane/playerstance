package pl.mfurmane.rest.model;

import org.springframework.security.core.Authentication;

public class LoginResponse {

    private String authToken;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public LoginResponse(String sessionToken) {
        this.authToken = sessionToken;
    }

    public LoginResponse() {}
}
