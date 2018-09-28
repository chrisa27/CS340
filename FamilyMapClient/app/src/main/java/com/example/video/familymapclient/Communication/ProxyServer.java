package com.example.video.familymapclient.Communication;

import com.example.video.familymapclient.Communication.RequestHolders.LoadRequest;
import com.example.video.familymapclient.Communication.RequestHolders.LoginRequest;
import com.example.video.familymapclient.Communication.RequestHolders.RegisterRequest;
import com.example.video.familymapclient.Communication.ResultHolders.ClearResult;
import com.example.video.familymapclient.Communication.ResultHolders.EventResponse;
import com.example.video.familymapclient.Communication.ResultHolders.FillResult;
import com.example.video.familymapclient.Communication.ResultHolders.LoadResult;
import com.example.video.familymapclient.Communication.ResultHolders.LoginResult;
import com.example.video.familymapclient.Communication.ResultHolders.PersonResponse;
import com.example.video.familymapclient.Communication.ResultHolders.RegisterResponse;

/**
 * ProxyServer is used to call the server methods from the Family Map client.
 */

public class ProxyServer {
    public static ProxyServer SINGLETON = new ProxyServer();

    private ProxyServer(){};

    /**
     * Creates a new User with 4 generations of ancestors, and logs them in.
     * @param r a data holder with a User account information.
     * @return Returns a data holder with the authorization token, username, and personid. Can also contain an error message if failed.
     */
    public RegisterResponse register(RegisterRequest r){
        return ClientCommunicator.SINGLETON.register(r);
    }

    /**
     * Logs in a user and generates an authorization token.
     * @param r a data holder that contains a username and a password.
     * @return A data holder with the authorization token and whether the login was successful or not.
     */
    public LoginResult login(LoginRequest r){
        return ClientCommunicator.SINGLETON.login(r);
    }

    /**
     * Deletes all data from all tables in the database.
     * @return A data holder with a success or error message.
     */
    public ClearResult clear(){
        return ClientCommunicator.SINGLETON.clear();
    }

    /**
     * Populates the table for an existing user. If there is already information there, it will
     * be deleted and replaced with randomly generated ancestors.
     * @return Returns a message with the number of people and events that were added to the database.
     */
    public FillResult fill(String username, int generations){
        return ClientCommunicator.SINGLETON.fill(username, generations);
    }

    /**
     * Clears all existing data, and then loads new data in the database.
     * @param r a data container that has an array of users, people, and events.
     * @return Returns whether the fill was successful or not.
     */
    public LoadResult load(LoadRequest r){
        return ClientCommunicator.SINGLETON.load(r);
    }

    /**
     * Accesses the database and returns a specific person given their unique ID.
     * @param id the unique ID of a given person.
     * @return Returns a person object, or an error message if it fails.
     */
    public PersonResponse person(String id, String token){
        return ClientCommunicator.SINGLETON.person(id, token);
    }

    /**
     * Accesses the database and returns one specific event from the given id
     * @param id The event id of the desired event.
     * @return Returns an event object, and whether or not there was an error.
     */
    public EventResponse event(String id, String token){
        return ClientCommunicator.SINGLETON.event(id, token);
    }
}
