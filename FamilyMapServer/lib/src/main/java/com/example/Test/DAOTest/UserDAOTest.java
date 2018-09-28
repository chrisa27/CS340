package com.example.Test.DAOTest;

import com.example.Database.DataAccess.AuthDAO;
import com.example.Database.DataAccess.UserDAO;
import com.example.Server;
import com.example.ServerModel.UserAccount;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by video on 3/1/2017.
 */

public class UserDAOTest {
    private static Connection connection;
    private static UserAccount user1;
    private static UserAccount user2;
    private static Server server;



    @BeforeClass
    public static void initialize() throws Exception{
        server = new Server();
        server.initializeDatabase();
        user1 = new UserAccount("chrisa27", "pass", "Chris", "Arnold", "Chrisarnold27@gmail.com", "m");
        user1.setPersonid("555");
        user2 = new UserAccount("jenny15", "password", "Jenny", "Doe", "JD309@yahoo.com", "f");
        user2.setPersonid("666");
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:UserDummy.db");

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE if exists Users;");
            statement.executeUpdate("CREATE TABLE Users(" +
                    "username TEXT PRIMARY KEY," +
                    "password TEXT NOT NULL," +
                    "firstname TEXT NOT NULL," +
                    "lastname TEXT NOT NULL," +
                    "email TEXT NOT NULL," +
                    "gender TEXT NOT NULL," +
                    "personid TEXT NOT NULL);");
        }catch (SQLException e){
            System.err.println("Couldn't create Users table");
        }
    }

    @Before
    public void reset(){
        try{
            Statement statement1 = connection.createStatement();
            statement1.executeUpdate("DELETE FROM Users");
            statement1.executeUpdate("INSERT INTO Users Values(" +
                    "'chrisa27'," +
                    "'pass'," +
                    "'Chris'," +
                    "'Arnold'," +
                    "'Chrisarnold27@gmail.com'," +
                    "'m'," +
                    "'555');");
            statement1.executeUpdate("INSERT INTO Users Values(" +
                    "'jenny15'," +
                    "'password'," +
                    "'Jenny'," +
                    "'Doe'," +
                    "'JD309@yahoo.com'," +
                    "'f'," +
                    "'666');");

            Statement statement2 = UserDAO.SINGLETON.getConnection().createStatement();
            statement2.executeUpdate("DELETE FROM Users;");
        }catch(SQLException e){
            System.err.println("Couldn't clear the table");
        }
        UserDAO.SINGLETON.addUser(user1);
        UserDAO.SINGLETON.addUser(user2);
    }

    @Test
    public void addTest(){
        ResultSet rs1;
        ResultSet rs2;

        try {
            Statement statement1 = connection.createStatement();
            Statement statement2 = UserDAO.SINGLETON.getConnection().createStatement();
            rs1 = statement1.executeQuery("SELECT * FROM Users");
            rs2 = statement2.executeQuery("SELECT * FROM Users");

            rs1.next();
            rs2.next();

            String username = rs1.getString("username");
            String Username = rs2.getString("username");
            String password = rs1.getString("password");
            String Password = rs2.getString("password");
            String firstName = rs1.getString("firstname");
            String FirstName = rs2.getString("firstname");
            String lastName = rs1.getString("lastname");
            String LastName = rs2.getString("lastname");
            String email = rs1.getString("email");
            String Email = rs2.getString("email");
            String gender = rs1.getString("gender");
            String Gender = rs2.getString("gender");
            String personID = rs1.getString("personid");
            String PersonID = rs2.getString("personid");

            assertEquals(username, Username);
            assertEquals(password, Password);
            assertEquals(firstName, FirstName);
            assertEquals(lastName, LastName);
            assertEquals(email, Email);
            assertEquals(gender, Gender);
            assertEquals(personID, PersonID);

            rs1.next();
            rs2.next();

            username = rs1.getString("username");
            Username = rs2.getString("username");
            password = rs1.getString("password");
            Password = rs2.getString("password");
            firstName = rs1.getString("firstname");
            FirstName = rs2.getString("firstname");
            lastName = rs1.getString("lastname");
            LastName = rs2.getString("lastname");
            email = rs1.getString("email");
            Email = rs2.getString("email");
            gender = rs1.getString("gender");
            Gender = rs2.getString("gender");
            personID = rs1.getString("personid");
            PersonID = rs2.getString("personid");

            assertEquals(username, Username);
            assertEquals(password, Password);
            assertEquals(firstName, FirstName);
            assertEquals(lastName, LastName);
            assertEquals(email, Email);
            assertEquals(gender, Gender);
            assertEquals(personID, PersonID);
        }catch(SQLException e){
            System.err.println("error");
            assertTrue(false);
        }
    }

    @Test
    public void getTest(){
        UserAccount test = UserDAO.SINGLETON.getUser(user1.getUsername());
        assertEquals(test.getUsername(), user1.getUsername());
        assertEquals(test.getPassword(), user1.getPassword());
        assertEquals(test.getFirstName(), user1.getFirstName());
        assertEquals(test.getLastName(), user1.getLastName());
        assertEquals(test.getEmail(), user1.getEmail());
        assertEquals(test.getGender(), user1.getGender());
        assertEquals(test.getPersonid(), user1.getPersonid());

        test = UserDAO.SINGLETON.getUser(user2.getUsername());
        assertEquals(test.getUsername(), user2.getUsername());
        assertEquals(test.getPassword(), user2.getPassword());
        assertEquals(test.getFirstName(), user2.getFirstName());
        assertEquals(test.getLastName(), user2.getLastName());
        assertEquals(test.getEmail(), user2.getEmail());
        assertEquals(test.getGender(), user2.getGender());
        assertEquals(test.getPersonid(), user2.getPersonid());

        assertEquals(UserDAO.SINGLETON.getUser("lol"), null);
    }

    @Test
    public void clearTest(){
        ResultSet rs;

        assertTrue(UserDAO.SINGLETON.clearUserTable());

        try{
            Statement statement = UserDAO.SINGLETON.getConnection().createStatement();

            rs = statement.executeQuery("SELECT * FROM Users");

            rs.next();
            rs.getString("username");

            assertTrue(false);
        }catch(SQLException e){
            assertTrue(true);
        }
    }

    @AfterClass
    public static void end(){
        try {
            connection.close();
            UserDAO.SINGLETON.getConnection().close();
        }catch(SQLException e){
            System.err.print("Couldn't close connection");
        }
    }
}
