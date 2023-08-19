package com.mypersonalnetwork.database.entity;

public class Place {
    private int idPlace;
    private String name;
    private String geoLocation;
    private String address;
    private String city;
    private String cap;
    private String typePlace;

    public Place(int idPlace, String name, String geoLocation, String address, String city, String cap, String typePlace) {
        this.idPlace = idPlace;
        this.name = name;
        this.geoLocation = geoLocation;
        this.address = address;
        this.city = city;
        this.cap = cap;
        this.typePlace = typePlace;
    }

    public int getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(int idPlace) {
        this.idPlace = idPlace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(String geoLocation) {
        this.geoLocation = geoLocation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getTypePlace() {
        return typePlace;
    }

    public void setTypePlace(String typePlace) {
        this.typePlace = typePlace;
    }
}
