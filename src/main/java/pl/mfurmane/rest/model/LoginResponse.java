package pl.mfurmane.rest.model;

import org.springframework.security.core.Authentication;

public class LoginResponse {

    private Authentication authToken;

    public Authentication getAuthToken() {
        return authToken;
    }

    public void setAuthToken(Authentication authToken) {
        this.authToken = authToken;
    }

    public LoginResponse(Authentication sessionToken) {
        this.authToken = sessionToken;
    }

    public LoginResponse() {}
}
