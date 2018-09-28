package com.example.Database.DataAccess;

import com.example.ServerModel.Person;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import static com.example.Server.connection;

/**
 * PersonDAO is used to manipulate the data in the Person table in the database.
 */

public class PersonDAO {
    /**
     * Singleton instance that allows the entire program to access it.
     */
    public static final PersonDAO SINGLETON = new PersonDAO();

    /**
     * Only allows the PersonDAO class to construct itself.
     */
    private PersonDAO(){};

    /**
     * Adds a new person with all of their respective parameters to the Person table.
     * @param person a person object containing all of the information to add.
     */
    public boolean addPerson(Person person){
        String personid = person.getPersonID();
        String descendentid = person.getDescendentID();
        String firstname = person.getFirstName();
        String lastname = person.getLastName();
        String gender = person.getGender();
        String father = null;
        String mother = null;
        String spouse = null;

        if (person.getFather() != null){
            father = person.getFather();
        }
        if (person.getMother() != null){
            mother = person.getMother();
        }
        if (person.getSpouse() != null){
            spouse = person.getSpouse();
        }

        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO Persons values(" +
                    "'" + personid + "', " +
                    "'" + descendentid + "', " +
                    "'" + firstname + "', " +
                    "'" + lastname + "', " +
                    "'" + gender + "', " +
                    "'" + mother + "', " +
                    "'" + father + "', " +
                    "'" + spouse + "');");
            return true;
        }catch(SQLException e){
            //System.err.println("Couldn't add new person to the table");
            return false;
        }
    }

    /**
     * Queries the  database and returns a person based on a given ID.
     * @param personid the id of the desired person.
     * @return returns a person object associated with the given id
     */
    public Person getPerson(String personid) {
        Person temp;
        String descendentID;
        String firstName;
        String lastName;
        String gender;
        String mother;
        String father;
        String spouse;

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Persons WHERE personid = '" + personid + "';");
            rs.next();
            descendentID = rs.getString("descendant");
            firstName = rs.getString("first");
            lastName = rs.getString("last");
            gender = rs.getString("gender");
            mother = rs.getString("mother");
            father = rs.getString("father");
            spouse = rs.getString("spouse");

            temp = new Person(descendentID, firstName, lastName, gender);
            temp.setPersonID(personid);
            if (!mother.equals("null")) {
                temp.setMother(mother);
            }
            if (!father.equals("null")) {
                temp.setFather(father);
            }
            if (!spouse.equals("null")) {
                temp.setSpouse(spouse);
            }

            return temp;
        }catch(SQLException e){
            //System.err.println("Couldn't find the person in the table");
            return null;
        }
    }

    public Person getPersonID(String personid, String descendentid){
        Person temp;
        String firstName;
        String lastName;
        String gender;
        String mother;
        String father;
        String spouse;

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Persons WHERE personid = '" + personid + "' AND descendant = '" + descendentid + "';");
            rs.next();
            firstName = rs.getString("first");
            lastName = rs.getString("last");
            gender = rs.getString("gender");
            mother = rs.getString("mother");
            father = rs.getString("father");
            spouse = rs.getString("spouse");

            temp = new Person(descendentid, firstName, lastName, gender);
            temp.setPersonID(personid);
            if (!mother.equals("null")) {
                temp.setMother(mother);
            }
            if (!father.equals("null")) {
                temp.setFather(father);
            }
            if (!spouse.equals("null")) {
                temp.setSpouse(spouse);
            }

            return temp;
        }catch(SQLException e){
            //System.err.println("Couldn't find the person in the table");
            return null;
        }
    }

    /**
     * Gathers all of the Person entries in the database for a given user.
     * @param descendentid the ID of the user whose ancestors we want to access.
     * @return set of all the people in the Person table.
     */
    public Set<Person> getAllPeople(String descendentid){
        HashSet<Person> allPeople = new HashSet<>();
        Person temp;
        String personid;
        String firstName;
        String lastName;
        String gender;
        String mother;
        String father;
        String spouse;

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Persons WHERE descendant = '" + descendentid + "';");
            boolean exists = false;

            while(rs.next()) {
                exists = true;
                personid = rs.getString("personid");
                firstName = rs.getString("first");
                lastName = rs.getString("last");
                gender = rs.getString("gender");
                mother = rs.getString("mother");
                father = rs.getString("father");
                spouse = rs.getString("spouse");

                temp = new Person(descendentid, firstName, lastName, gender);
                temp.setPersonID(personid);
                if (!mother.equals("null")) {
                    temp.setMother(mother);
                }
                if (!father.equals("null")) {
                    temp.setFather(father);
                }
                if (!spouse.equals("null")) {
                    temp.setSpouse(spouse);
                }
                    allPeople.add(temp);
            }
            if (exists) {
                return allPeople;
            }
            else {
                return null;
            }
        }catch(SQLException e){
            //System.err.println("Error getting all people");
            return null;
        }
    }

    /**
     * Deletes all of the People that are associated with a given user.
     * @param descendentid the id for the user whose information will be deleted.
     * @return Returns whether the delete was successful or not.
     */
    public boolean deleteUserInfo(String descendentid){

        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM Persons WHERE descendant = '" + descendentid + "';");

            return true;
        }catch (SQLException e){
            //System.err.println("Error deleting user Person data");
            return false;
        }
    }

    /**
     * Deletes all of the data in the Person table.
     * @return Returns whether the function was successful in deleting all of the data.
     */
    public boolean clearPersonTable(){
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM Persons;");
            return true;
        }catch(SQLException e){
            //System.err.println("Couldn't delete data from Persons");
            return false;
        }
    }

    public Connection getConnection(){
        return connection;
    }
}
