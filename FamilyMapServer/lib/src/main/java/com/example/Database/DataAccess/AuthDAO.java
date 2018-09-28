package com.example.Database.DataAccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.ServerModel.AuthToken;

import static com.example.Server.connection;

/**
 * AuthDAO is used to access and manipulate the data in the Tokens table in the database.
 */
public class AuthDAO {
    /**
     * Singleton instance that allows the entire program to access it.
     */
    public static final AuthDAO SINGLETON = new AuthDAO();

    /**
     * Only allows the AuthDAO class to construct itself.
     */
    private AuthDAO(){};

    /**
     * Adds a new Authorization token to the database.
     * @param authToken Authorization token to be added.
     */
    public boolean addAuthToken(AuthToken authToken){
        String token = authToken.getToken();
        String username = authToken.getUsername();

        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO Tokens values(" +
                    "'" + token + "', " +
                    "'" + username + "');");
            return true;
        }catch(SQLException e){
            //System.err.print("Couldn't add the token to the table");
            return false;
        }
    }

    /**
     * Queries the database for a given authorization token
     * @param token the desired authorization token.
     * @return Returns true if the token is in the database.
     */
    public boolean exists(String token){
        try{
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Tokens WHERE token = '" + token + "';");
            rs.next();
            String user = rs.getString("token");
            return true;
        }catch(SQLException e){
            //System.err.println("The token doesn't exist");
            return false;
        }
    }

    public String getUsername(String token){
        try{
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT username FROM Tokens WHERE token = '" + token + "';");
            rs.next();
            return rs.getString("username");
        }catch(SQLException e){
            //System.err.println("The token doesn't exist");
            return null;
        }
    }

    /**
     * Removes all of the tokens from the database.
     * @return Returns if the function was removing all tokens from the database.
     */
    public boolean clearTokenTable(){
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM Tokens;");
            return true;
        }catch(SQLException e){
            //System.err.println("Couldn't delete data from Tokens");
            return false;
        }
    }

    public Connection getConnection(){
        return connection;
    }
}
