package com.example.Test.DAOTest;

import com.example.Database.DataAccess.AuthDAO;
import com.example.Server;
import com.example.ServerModel.AuthToken;

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
 * Created by video on 2/21/2017.
 */

public class AuthDAOTest {
    private static Connection connection;
    private static AuthToken token1;
    private static AuthToken token2;
    private static Server server;

    @BeforeClass
    public static void initialize()throws Exception{
        server = new Server();
        server.initializeDatabase();
        token1 = new AuthToken("chrisa");
        token2 = new AuthToken("johnny");
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:AuthDummy.db");

        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE if exists Tokens");
            statement.executeUpdate("CREATE TABLE Tokens(" +
                    "token TEXT PRIMARY KEY," +
                    "username TEXT REFERENCES Users(userid));");
        }catch(SQLException e){
            System.err.println("Couldn't create Tokens table");
        }
    }

    @Before
    public void reset(){
        try{
            Statement statement1 = connection.createStatement();
            statement1.executeUpdate("DELETE FROM Tokens");
            statement1.executeUpdate("INSERT INTO Tokens Values('" + token1.getToken() + "', 'chrisa');");
            statement1.executeUpdate("INSERT INTO Tokens Values('" + token2.getToken() + "', 'johnny');");

            Statement statement2 = AuthDAO.SINGLETON.getConnection().createStatement();
            statement2.executeUpdate("DELETE FROM Tokens");
        }catch(SQLException e){
            System.err.println("Couldn't delete content");
        }
        AuthDAO.SINGLETON.addAuthToken(token1);
        AuthDAO.SINGLETON.addAuthToken(token2);
    }

    @Test
    public void addTest(){
        ResultSet rs1;
        ResultSet rs2;

        try {
            Statement statement1 = connection.createStatement();
            Statement statement2 = AuthDAO.SINGLETON.getConnection().createStatement();
            rs1 = statement1.executeQuery("SELECT * FROM Tokens");
            rs2 = statement2.executeQuery("SELECT * FROM Tokens");

            rs1.next();
            rs2.next();

            String t1 = rs1.getString("token");
            String T1 = rs2.getString("token");
            String u1 = rs1.getString("username");
            String U1 = rs2.getString("username");

            rs1.next();
            rs2.next();
            String t2 = rs1.getString("token");
            String T2 = rs2.getString("token");
            String u2 = rs1.getString("username");
            String U2 = rs2.getString("username");

            assertEquals(t1, T1);
            assertEquals(u1, U1);
            assertEquals(t2, T2);
            assertEquals(u2, U2);
        }catch(SQLException e){
            System.err.println("error");
            assertTrue(false);
        }
    }

    @Test
    public void existsTest(){
        assertTrue(AuthDAO.SINGLETON.exists(token1.getToken()));
        assertTrue(AuthDAO.SINGLETON.exists(token2.getToken()));
        assertTrue(!AuthDAO.SINGLETON.exists("999"));
    }

    @Test
    public void getUsernameTest(){
        assertEquals(AuthDAO.SINGLETON.getUsername(token1.getToken()), token1.getUsername());
        assertEquals(AuthDAO.SINGLETON.getUsername(token2.getToken()), token2.getUsername());
        assertEquals(AuthDAO.SINGLETON.getUsername("0"), null);
    }

    @Test
    public void clearTest(){
        ResultSet rs;

        assertTrue(AuthDAO.SINGLETON.clearTokenTable());

        try{
            Statement statement = AuthDAO.SINGLETON.getConnection().createStatement();

            rs = statement.executeQuery("SELECT * FROM Tokens");

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
            AuthDAO.SINGLETON.getConnection().close();
        }catch(SQLException e){
            System.err.print("Couldn't close connection");
        }
    }
}
