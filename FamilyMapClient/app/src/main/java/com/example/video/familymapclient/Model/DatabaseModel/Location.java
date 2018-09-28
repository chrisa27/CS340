package com.example.video.familymapclient.Model.DatabaseModel;

/**
 * Created by video on 3/6/2017.
 */

public class Location {
    private double latitude;
    private double longitude;
    private String city;
    private String country;

    public Location(double latitude, double longitude, String city, String country){
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.country = country;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}
