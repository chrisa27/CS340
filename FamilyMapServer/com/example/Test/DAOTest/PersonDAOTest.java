package com.example.Test.DAOTest;

import com.example.Database.DataAccess.PersonDAO;
import com.example.Server;
import com.example.ServerModel.Person;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by video on 3/1/2017.
 */

public class PersonDAOTest {
    private static Connection connection;
    private static Person person1;
    private static Person person2;
    private static Person person3;
    private static Server server;



    @BeforeClass
    public static void initialize() throws Exception{
        server = new Server();
        server.initializeDatabase();
        person1 = new Person("Chris", "Arnold", "m");
        person2 = new Person(person1.getDescendentID(), "Courtney", "Arnold", "f");
        person3 = new Person("666", "Danny", "Smith", "m");
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:PersonDummy.db");

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE if exists Persons;");
            statement.executeUpdate("CREATE TABLE Persons(" +
                    "personid TEXT PRIMARY KEY," +
                    "descendant TEXT REFERENCES Users(username)," +
                    "first TEXT," +
                    "last TEXT," +
                    "gender TEXT," +
                    "mother TEXT REFERENCES Person(personid)," +
                    "father TEXT REFERENCES Person(personid)," +
                    "spouse TEXT REFERENCES Person(personid));");
        }catch(Exception e){
            System.err.println("Couldn't create Persons table");
        }
    }

    @Before
    public void reset(){
        try{
            Statement statement1 = connection.createStatement();
            statement1.executeUpdate("DELETE FROM Persons");
            statement1.executeUpdate("INSERT INTO Persons Values(" +
                    "'" + person1.getPersonID() + "'," +
                    "'" + person1.getDescendentID() + "'," +
                    "'Chris'," +
                    "'Arnold'," +
                    "'m'," +
                    null + "," +
                    null + "," +
                    null + ");");
            statement1.executeUpdate("INSERT INTO Persons Values(" +
                    "'" + person2.getPersonID() + "'," +
                    "'" + person1.getDescendentID() + "'," +
                    "'Courtney'," +
                    "'Arnold'," +
                    "'f'," +
                    null + "," +
                    null + "," +
                    null + ");");

            Statement statement2 = PersonDAO.SINGLETON.getConnection().createStatement();
            statement2.executeUpdate("DELETE FROM Persons");
        }catch(SQLException e){
            System.err.println("Couldn't delete content");
        }
        PersonDAO.SINGLETON.addPerson(person1);
        PersonDAO.SINGLETON.addPerson(person2);
    }

    @Test
    public void addTest(){
        ResultSet rs1;
        ResultSet rs2;

        try {
            Statement statement1 = connection.createStatement();
            Statement statement2 = PersonDAO.SINGLETON.getConnection().createStatement();
            rs1 = statement1.executeQuery("SELECT * FROM Persons");
            rs2 = statement2.executeQuery("SELECT * FROM Persons");

            rs1.next();
            rs2.next();

            String personid = rs1.getString("personid");
            String Personid = rs2.getString("personid");
            String descendant = rs1.getString("descendant");
            String Descendant = rs2.getString("descendant");
            String firstName = rs1.getString("first");
            String FirstName = rs2.getString("first");
            String lastName = rs1.getString("last");
            String LastName = rs2.getString("last");
            String gender = rs1.getString("gender");
            String Gender = rs2.getString("gender");
            String mother = rs1.getString("mother");
            String Mother = rs2.getString("mother");
            String father = rs1.getString("father");
            String Father = rs2.getString("father");
            String spouse = rs1.getString("spouse");
            String Spouse = rs2.getString("spouse");

            if (Mother.equals("null")){
                Mother = null;
            }
            if (Father.equals("null")){
                Father = null;
            }
            if (Spouse.equals("null")){
                Spouse = null;
            }

            assertEquals(personid, Personid);
            assertEquals(descendant, Descendant);
            assertEquals(firstName, FirstName);
            assertEquals(lastName, LastName);
            assertEquals(gender, Gender);
            assertEquals(mother, Mother);
            assertEquals(father, Father);
            assertEquals(spouse, Spouse);

            rs1.next();
            rs2.next();

            personid = rs1.getString("personid");
            Personid = rs2.getString("personid");
            descendant = rs1.getString("descendant");
            Descendant = rs2.getString("descendant");
            firstName = rs1.getString("first");
            FirstName = rs2.getString("first");
            lastName = rs1.getString("last");
            LastName = rs2.getString("last");
            gender = rs1.getString("gender");
            Gender = rs2.getString("gender");
            mother = rs1.getString("mother");
            Mother = rs2.getString("mother");
            father = rs1.getString("father");
            Father = rs2.getString("father");
            spouse = rs1.getString("spouse");
            Spouse = rs2.getString("spouse");

            if (Mother.equals("null")){
                Mother = null;
            }
            if (Father.equals("null")){
                Father = null;
            }
            if (Spouse.equals("null")){
                Spouse = null;
            }

            assertEquals(personid, Personid);
            assertEquals(descendant, Descendant);
            assertEquals(firstName, FirstName);
            assertEquals(lastName, LastName);
            assertEquals(gender, Gender);
            assertEquals(mother, Mother);
            assertEquals(father, Father);
            assertEquals(spouse, Spouse);
        }catch(SQLException e){
            System.err.println("Error");
            assertTrue(false);
        }
    }

    @Test
    public void getTest(){
        Person test = PersonDAO.SINGLETON.getPerson(person1.getPersonID());
        assertEquals(test.getPersonID(), person1.getPersonID());
        assertEquals(test.getDescendentID(), person1.getDescendentID());
        assertEquals(test.getFirstName(), person1.getFirstName());
        assertEquals(test.getLastName(), person1.getLastName());
        assertEquals(test.getGender(), person1.getGender());
        assertEquals(test.getMother(), person1.getMother());
        assertEquals(test.getFather(), person1.getFather());
        assertEquals(test.getSpouse(), person1.getSpouse());


        test = PersonDAO.SINGLETON.getPerson(person2.getPersonID());
        assertEquals(test.getPersonID(), person2.getPersonID());
        assertEquals(test.getDescendentID(), person2.getDescendentID());
        assertEquals(test.getFirstName(), person2.getFirstName());
        assertEquals(test.getLastName(), person2.getLastName());
        assertEquals(test.getGender(), person2.getGender());
        assertEquals(test.getMother(), person2.getMother());
        assertEquals(test.getFather(), person2.getFather());
        assertEquals(test.getSpouse(), person2.getSpouse());

        test = PersonDAO.SINGLETON.getPerson("2");
        assertEquals(test, null);
    }

    @Test
    public void getIDTest(){
        Person test = PersonDAO.SINGLETON.getPersonID(person1.getPersonID(), person1.getDescendentID());
        assertEquals(test.getPersonID(), person1.getPersonID());
        assertEquals(test.getDescendentID(), person1.getDescendentID());
        assertEquals(test.getFirstName(), person1.getFirstName());
        assertEquals(test.getLastName(), person1.getLastName());
        assertEquals(test.getGender(), person1.getGender());
        assertEquals(test.getMother(), person1.getMother());
        assertEquals(test.getFather(), person1.getFather());
        assertEquals(test.getSpouse(), person1.getSpouse());


        test = PersonDAO.SINGLETON.getPersonID(person2.getPersonID(), person2.getDescendentID());
        assertEquals(test.getPersonID(), person2.getPersonID());
        assertEquals(test.getDescendentID(), person2.getDescendentID());
        assertEquals(test.getFirstName(), person2.getFirstName());
        assertEquals(test.getLastName(), person2.getLastName());
        assertEquals(test.getGender(), person2.getGender());
        assertEquals(test.getMother(), person2.getMother());
        assertEquals(test.getFather(), person2.getFather());
        assertEquals(test.getSpouse(), person2.getSpouse());

        assertEquals(PersonDAO.SINGLETON.getPersonID(person1.getPersonID(), "0"), null);
        assertEquals(PersonDAO.SINGLETON.getPersonID("2", person1.getDescendentID()), null);
        assertEquals(PersonDAO.SINGLETON.getPersonID("2", "15"), null);
    }

    @Test
    public void getAllTest(){
        Set<Person> allPeople = PersonDAO.SINGLETON.getAllPeople(person1.getDescendentID());
        Iterator<Person> it = allPeople.iterator();

        Person test = it.next();
        if (test.getPersonID().equals(person1.getPersonID())) {
            assertEquals(test.getPersonID(), person1.getPersonID());
            assertEquals(test.getDescendentID(), person1.getDescendentID());
            assertEquals(test.getFirstName(), person1.getFirstName());
            assertEquals(test.getLastName(), person1.getLastName());
            assertEquals(test.getGender(), person1.getGender());
            assertEquals(test.getMother(), person1.getMother());
            assertEquals(test.getFather(), person1.getFather());
            assertEquals(test.getSpouse(), person1.getSpouse());

            test = it.next();
            assertEquals(test.getPersonID(), person2.getPersonID());
            assertEquals(test.getDescendentID(), person2.getDescendentID());
            assertEquals(test.getFirstName(), person2.getFirstName());
            assertEquals(test.getLastName(), person2.getLastName());
            assertEquals(test.getGender(), person2.getGender());
            assertEquals(test.getMother(), person2.getMother());
            assertEquals(test.getFather(), person2.getFather());
            assertEquals(test.getSpouse(), person2.getSpouse());
        }
        else{
            assertEquals(test.getPersonID(), person2.getPersonID());
            assertEquals(test.getDescendentID(), person2.getDescendentID());
            assertEquals(test.getFirstName(), person2.getFirstName());
            assertEquals(test.getLastName(), person2.getLastName());
            assertEquals(test.getGender(), person2.getGender());
            assertEquals(test.getMother(), person2.getMother());
            assertEquals(test.getFather(), person2.getFather());
            assertEquals(test.getSpouse(), person2.getSpouse());

            test = it.next();
            assertEquals(test.getPersonID(), person1.getPersonID());
            assertEquals(test.getDescendentID(), person1.getDescendentID());
            assertEquals(test.getFirstName(), person1.getFirstName());
            assertEquals(test.getLastName(), person1.getLastName());
            assertEquals(test.getGender(), person1.getGender());
            assertEquals(test.getMother(), person1.getMother());
            assertEquals(test.getFather(), person1.getFather());
            assertEquals(test.getSpouse(), person1.getSpouse());
        }



        assertEquals(PersonDAO.SINGLETON.getAllPeople("3"), null);
    }

    @Test
    public void deleteUserTest(){
        ResultSet rs;
        PersonDAO.SINGLETON.addPerson(person3);

        assertTrue(PersonDAO.SINGLETON.deleteUserInfo(person1.getDescendentID()));

        try{
            Statement statement = PersonDAO.SINGLETON.getConnection().createStatement();

            rs = statement.executeQuery("SELECT * FROM Persons");

            rs.next();

            String personid = rs.getString("personid");
            String descendant = rs.getString("descendant");
            String firstName = rs.getString("first");
            String lastName = rs.getString("last");
            String gender = rs.getString("gender");
            String mother = rs.getString("mother");
            String father = rs.getString("father");
            String spouse = rs.getString("spouse");

            if (mother.equals("null")){
                mother = null;
            }
            if (father.equals("null")){
                father = null;
            }
            if (spouse.equals("null")){
                spouse = null;
            }

            assertEquals(personid, person3.getPersonID());
            assertEquals(descendant, person3.getDescendentID());
            assertEquals(firstName, person3.getFirstName());
            assertEquals(lastName, person3.getLastName());
            assertEquals(gender, person3.getGender());
            assertEquals(mother, person3.getMother());
            assertEquals(father, person3.getFather());
            assertEquals(spouse, person3.getSpouse());

            rs.next();
            personid = rs.getString("personid");
            descendant = rs.getString("descendant");
            firstName = rs.getString("first");
            lastName = rs.getString("last");
            gender = rs.getString("gender");
            mother = rs.getString("mother");
            father = rs.getString("father");
            spouse = rs.getString("spouse");

            if (mother.equals("null")){
                mother = null;
            }
            if (father.equals("null")){
                father = null;
            }
            if (spouse.equals("null")){
                spouse = null;
            }

            assertEquals(personid, person1.getPersonID());
            assertEquals(descendant, person1.getDescendentID());
            assertEquals(firstName, person1.getFirstName());
            assertEquals(lastName, person1.getLastName());
            assertEquals(gender, person1.getGender());
            assertEquals(mother, person1.getMother());
            assertEquals(father, person1.getFather());
            assertEquals(spouse, person1.getSpouse());

            rs.next();
            personid = rs.getString("personid");

            assertTrue(false);
        }catch(SQLException e){
            assertTrue(true);
        }

    }

    @Test
    public void clearTest(){
        ResultSet rs;

        assertTrue(PersonDAO.SINGLETON.clearPersonTable());

        try{
            Statement statement = PersonDAO.SINGLETON.getConnection().createStatement();

            rs = statement.executeQuery("SELECT * FROM Persons");

            rs.next();
            rs.getString("personid");

            assertTrue(false);
        }catch(SQLException e){
            assertTrue(true);
        }
    }

    @AfterClass
    public static void end(){
        try {
            connection.close();
            PersonDAO.SINGLETON.getConnection().close();
        }catch(SQLException e){
            System.err.print("Couldn't close connection");
        }
    }
}
