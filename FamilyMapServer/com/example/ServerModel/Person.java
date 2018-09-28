package com.example.ServerModel;

import com.example.Database.DataAccess.EventDAO;
import com.example.Database.DataAccess.PersonDAO;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

/**
 * Represents all of the ancestors and family members stored in the family map program
 */
public class Person {
    private static Gson gson = new Gson();
    private static Set<String> maleNames = populateNames("/users/guest/c/chrisa27/AndroidStudioProjects/familymapserver/lib/src/main/java/libs/json/mnames.json");
    private static Set<String> femaleNames = populateNames("/users/guest/c/chrisa27/AndroidStudioProjects/familymapserver/lib/src/main/java/libs/json/fnames.json");
    private static Set<String> surnames = populateNames("/users/guest/c/chrisa27/AndroidStudioProjects/familymapserver/lib/src/main/java/libs/json/snames.json");
    private static Set<Location> locations = populateLocations("/users/guest/c/chrisa27/AndroidStudioProjects/familymapserver/lib/src/main/java/libs/json/locations.json");

    /**
     * A unique ID for this instance of a Person
     */
    private String personID;

    /**
     * The person ID associated with the user
     */
    private String descendentID;

    /**
     * First name of the person
     */
    private String firstName;

    /**
     * Last name of the person
     */
    private String lastName;

    /**
     * Gender of the person
     */
    private String gender;

    /**
     * pointer to another person object representing the person's father (can be null)
     */
    private String father;

    /**
     * pointer to another person object representing the person's mother (can be null)
     */
    private String mother;

    /**
     * pointer to another person object representing the person's spouse (can be null)
     */
    private String spouse;

    /**
     * A list of all the events associated with this person
     */
    private Set<Event> lifeEvents;

    /**
     * Creates a new instance of person and initializes the fields for the object used for the user.
     *
     * @param firstName person's first name
     * @param lastName  person's last name
     * @param gender    person's gender
     */
    public Person(String firstName, String lastName, String gender) {
        personID = generatePersonId();
        descendentID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        father = null;
        mother = null;
        spouse = null;
        lifeEvents = generateEvents(personID, descendentID, 0);
    }

    public Person(String descendentID, String firstName, String lastName, String gender) {
        personID = generatePersonId();
        this.descendentID = descendentID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        father = null;
        mother = null;
        spouse = null;
        lifeEvents = new HashSet<>();
    }

    /**
     * Generates a new person id that isn't in use already
     *
     * @return Returns the personid
     */
    private String generatePersonId() {
        String id;
        do {
            id = UUID.randomUUID().toString();
        } while (PersonDAO.SINGLETON.getPerson(id) != null);

        return id;
    }

    public boolean generateAncestors(int generations, Person person) {
        int personBirth = 0;
        int momBirth;
        int momDeath;
        int dadBirth;
        int dadDeath;
        Event marriage;
        Event temp;

        //forget about events for now.
        if (generations == 0) {
            PersonDAO.SINGLETON.addPerson(person);
            generateEvents(person.getPersonID(), person.getDescendentID(), 0);
            return true;
        }

        temp = EventDAO.SINGLETON.getTypeEvent(person.getPersonID(), "birth");
        if (temp != null){
            personBirth = temp.getYear();
        }

        Person mom = generatePerson("f", personBirth);
        Person dad = generatePerson("m", personBirth);
        person.setMother(mom.getPersonID());
        person.setFather(dad.getPersonID());
        mom.setSpouse(dad.getPersonID());
        dad.setSpouse(mom.getPersonID());

        temp = EventDAO.SINGLETON.getTypeEvent(mom.getPersonID(), "birth");
        momBirth = temp.getYear();
        temp = EventDAO.SINGLETON.getTypeEvent(mom.getPersonID(), "death");
        momDeath = temp.getYear();
        temp = EventDAO.SINGLETON.getTypeEvent(dad.getPersonID(), "birth");
        dadBirth = temp.getYear();
        temp = EventDAO.SINGLETON.getTypeEvent(dad.getPersonID(), "death");
        dadDeath = temp.getYear();

        PersonDAO.SINGLETON.addPerson(person);

        marriage = generateMarriage(momBirth, dadBirth, momDeath, dadDeath, mom.getPersonID(), mom.getDescendentID());
        mom.addEvent(marriage);
        EventDAO.SINGLETON.addEvent(marriage);
        Location location = new Location(marriage.getLatitude(), marriage.getLongitude(), marriage.getCity(), marriage.getCountry());
        marriage = new Event(dad.getDescendentID(), dad.getPersonID(), location, "marriage", marriage.getYear());
        EventDAO.SINGLETON.addEvent(marriage);

        generateAncestors(--generations, mom);
        generateAncestors(generations, dad);

        return true;
    }

    private Person generatePerson(String gender, int personBirth) {
        String first;
        String last;

        int randName;

        if(gender.equals("m")) {
            Iterator<String> it = maleNames.iterator();
            randName = new Random().nextInt(maleNames.size());
            for (int i = 0; i < randName; i++){
                it.next();
            }
            first = it.next();
        }
        else{
            Iterator<String> it = femaleNames.iterator();
            randName = new Random().nextInt(femaleNames.size());
            for (int i = 0; i < randName; i++){
                it.next();
            }
            first = it.next();
        }

        Iterator<String> iterator = surnames.iterator();
        randName = new Random().nextInt(surnames.size());
        for (int i = 0; i < randName; i++){
            iterator.next();
        }
        last = iterator.next();

        Person person = new Person(descendentID, first, last, gender);
        person.setLifeEvents(generateEvents(person.getPersonID(), person.getDescendentID(), personBirth));

        return person;
    }

    public Set<Event> generateEvents(String pid, String did, int childBirth) {
        Set<Event> events = new HashSet<>();
        Location location;
        Event event;
        int birthYear;
        int deathYear = 2017;
        int randLocation;

        //checks if it is the starting user.
        if (pid.equals(did)){
            birthYear = new Random().nextInt(70) + 1930;
            randLocation = new Random().nextInt(locations.size());
            Iterator<Location> it = locations.iterator();

            for (int i = 0; i < randLocation; i++){
                it.next();
            }
            location = it.next();

            event = new Event(did, pid, location, "birth", birthYear);

            EventDAO.SINGLETON.addEvent(event);
            events.add(event);
        }
        else {
            birthYear = new Random().nextInt(25) + (childBirth - 40);
            deathYear = new Random().nextInt(80) + birthYear + 20;
            if (deathYear > childBirth){
                deathYear = childBirth;
            }
            if (deathYear > 2017){
                deathYear = 2017;
            }

            randLocation = new Random().nextInt(locations.size());
            Iterator<Location> it = locations.iterator();

            for (int i = 0; i < randLocation; i++) {
                it.next();
            }
            location = it.next();

            event = new Event(did, pid, location, "birth", birthYear);
            EventDAO.SINGLETON.addEvent(event);

            events.add(event);

            randLocation = new Random().nextInt(locations.size());
            it = locations.iterator();
            for (int i = 0; i < randLocation; i++) {
                it.next();
            }
            location = it.next();

            event = new Event(did, pid, location, "death", deathYear);
            EventDAO.SINGLETON.addEvent(event);

            events.add(event);
        }

        int otherEvents = new Random().nextInt(2);
        if (otherEvents != 0){
            randLocation = new Random().nextInt(locations.size());
            Iterator<Location> it = locations.iterator();
            for (int i = 0; i < randLocation; i++) {
                it.next();
            }
            location = it.next();
            int otherYear = new Random().nextInt(deathYear - birthYear) + birthYear;
            if (otherYear > deathYear){
                otherYear = deathYear;
            }

            event = new Event(did, pid, location, "christening", otherYear);

            EventDAO.SINGLETON.addEvent(event);
            events.add(event);
        }

        return events;
    }

    private Event generateMarriage(int birth1, int birth2, int death1, int death2, String pid, String did){
        int maxBirth;
        int minDeath;
        int randLocation;
        int marriageYear;
        Event marriage;
        Location location;

        if (birth1 > birth2) {
            maxBirth = birth1;
        }
        else{
            maxBirth = birth2;
        }

        if (death1 > death2){
            minDeath = death1;
        }
        else{
            minDeath = death2;
        }

        randLocation = new Random().nextInt(locations.size());
        Iterator<Location> it = locations.iterator();



        for (int i = 0; i < randLocation; i++) {
            it.next();
        }
        location = it.next();

        marriageYear = new Random().nextInt(minDeath - maxBirth) + maxBirth;

        marriage = new Event(did, pid, location, "marriage", marriageYear);

        return marriage;
    }

    private static Set<String> populateNames(String fileName) {
        Set<String> items = new HashSet<>();
        try {
            FileReader reader = new FileReader(fileName);

            JsonObject json = (JsonObject) new JsonParser().parse(reader);
            JsonArray array = (JsonArray) json.get("data");

            for (int i = 0; i < array.size(); i++){
                items.add(array.get(i).getAsString());
            }
        }catch (IOException e){
            System.err.println("Couldn't open JSON file");
        }


        return items;
    }

    private static Set<Location> populateLocations(String fileName){
        Set<Location> locations = new HashSet<>();
        try{
            FileReader reader = new FileReader(fileName);
            JsonObject jsonObject = (JsonObject) new JsonParser().parse(reader);
            JsonArray jsonArray = (JsonArray) jsonObject.get("data");
            Location location;
            double latitude;
            double longitude;
            String city;
            String country;

            for (int i = 0; i < jsonArray.size(); i++){
                jsonObject = (JsonObject) jsonArray.get(i);
                latitude = jsonObject.get("latitude").getAsDouble();
                longitude = jsonObject.get("longitude").getAsDouble();
                city = jsonObject.get("city").getAsString();
                country = jsonObject.get("country").getAsString();

                location = new Location(latitude, longitude, city, country);
                locations.add(location);
            }
        }catch(IOException e){
            System.err.println("Couldn't load the locations file");
        }
        return locations;
    }

    @Override
    public int hashCode() {
        return personID.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof Person) {
            Person obj = (Person) o;
            if (obj.getPersonID().equals(this.personID)) {
                return true;
            }
        }
        return false;
    }

    public void addEvent(Event event){
        lifeEvents.add(event);
    }

    public String getPersonID() {
        return personID;
    }

    public String getDescendentID() {
        return descendentID;
    }

    public void setDescendentID(String descendentID) {this.descendentID = descendentID; }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

    public Set<Event> getLifeEvents() {
        return lifeEvents;
    }

    public void setLifeEvents(Set<Event> lifeEvents) {
        this.lifeEvents = lifeEvents;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
