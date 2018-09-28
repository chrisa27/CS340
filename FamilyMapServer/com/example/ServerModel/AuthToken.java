package com.example.ServerModel;

import com.example.Database.DataAccess.AuthDAO;

import java.util.UUID;

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
    public AuthToken(String username){
        token = generateToken();
        this.username = username;
    }

    /**
     * Generates a new unique authorization token.
     * @return Returns the token
     */
    private String generateToken(){
        String token;
        do{
            token = UUID.randomUUID().toString();
        }while(AuthDAO.SINGLETON.exists(token));

        return token;
    }

    @Override
    public int hashCode(){
        return token.hashCode();
    }

    @Override
    public boolean equals(Object o){
        if (o == null){
            return false;
        }
        if (o instanceof AuthToken){
            AuthToken obj = (AuthToken) o;
            if(obj.getToken().equals(this.token)){
                return true;
            }
        }
        return false;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() { return username; }

    public void setToken(String token){
        this.token = token;
    }
}
