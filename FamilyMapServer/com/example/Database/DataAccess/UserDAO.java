package com.example.Database.DataAccess;

import com.example.ServerModel.UserAccount;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.example.Server.connection;

/**
 * UserDAO is used to access and manipulate data in the Users table in the database.
 */
public class UserDAO {
    /**
     * Singleton instance that allows the entire program to access it.
     */
    public static final UserDAO SINGLETON = new UserDAO();

    /**
     * Only allows the UserDAO class to construct itself.
     */
    private UserDAO(){ };

    /**
     * Adds a new User to the database.
     * @param user an object containing all of the information to add a new User to the database.
     */
    public boolean addUser(UserAccount user){
        String username = user.getUsername();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String email = user.getEmail();
        String gender = user.getGender();
        String personid = user.getPersonid();

        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO Users values(" +
                    "'" + username + "', " +
                    "'" + password + "', " +
                    "'" + firstName + "', " +
                    "'" + lastName + "', " +
                    "'" + email + "', " +
                    "'" + gender + "', " +
                    "'" + personid + "');");
            return true;
        }catch(SQLException e){
            //System.err.println("Couldn't add the user to the table");
            return false;
        }
    }

    /**
     * Checks the database for a user and returns their information
     * @param username username of desired user
     * @return Returns the information of the desired user.
     */
    public UserAccount getUser(String username){
        UserAccount user;
        String password;
        String firstName;
        String lastName;
        String email;
        String gender;
        String personid;

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Users WHERE username = '" + username + "';");
            rs.next();
            password = rs.getString("password");
            firstName = rs.getString("firstname");
            lastName = rs.getString("lastname");
            email = rs.getString("email");
            gender = rs.getString("gender");
            personid = rs.getString("personid");

            user = new UserAccount(username, password, firstName, lastName, email, gender);
            user.setPersonid(personid);

            return user;
        }catch (SQLException e){
            //System.err.println("The User doesn't exist");
            return null;
        }
    }

    /**
     * Removes all Users from the database.
     * @return Returns if the function was successful in deleting all the Users from the database.
     */
    public boolean clearUserTable(){
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM Users;");
            return true;
        }catch(SQLException e){
            //System.err.println("Couldn't delete data from Users");
            return false;
        }
    }

    public Connection getConnection(){
        return connection;
    }
}
