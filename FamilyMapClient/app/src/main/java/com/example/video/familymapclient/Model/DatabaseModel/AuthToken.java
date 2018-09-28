package com.example.video.familymapclient.Model.DatabaseModel;

/**
 * AuthToken represents all of the authorization tokens associated with given users.
 */
public class AuthToken {
    /** A unique set of characters given when a user logs in */
    private String token;

    /** Username of the user that just logged on*/
    private String username;

    /**
     * Randomly generates the authorization token for a given user.
     * @param username username of the user logging in
     */
    public AuthToken(String username, String token) {
        this.token = token;
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() { return username; }
}
