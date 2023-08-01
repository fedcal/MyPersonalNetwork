package com.mypersonalnetwork.database.entity;



import java.time.LocalDate;


public class Person {
    private Integer id;
    private String name;
    private String surname;
    private String phoneNumber;
    private String city;
    private String province;
    private String address;
    private String cityBorn;
    private LocalDate dateBarthday;
    private String workPosition;
    private String workCity;
    private String workCompany;
    private String geoLocation;


    public Person(Integer id, String name, String surname, String phoneNumber, String city, String province, String address, String cityBorn, LocalDate dateBarthday, String workPosition, String workCity, String workCompany,String geoLocation) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.province = province;
        this.address = address;
        this.cityBorn = cityBorn;
        this.dateBarthday = dateBarthday;
        this.workPosition = workPosition;
        this.workCity = workCity;
        this.workCompany = workCompany;
        this.geoLocation = geoLocation;
    }

    public String getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(String geoLocation) {
        this.geoLocation = geoLocation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getcityBorn() {
        return cityBorn;
    }

    public void setcityBorn(String cityBorn) {
        this.cityBorn = cityBorn;
    }

    public LocalDate getDateBarthday() {
        return dateBarthday;
    }

    public void setDateBarthday(LocalDate dateBarthday) {
        this.dateBarthday = dateBarthday;
    }

    public String getWorkPosition() {
        return workPosition;
    }

    public void setWorkPosition(String workPosition) {
        this.workPosition = workPosition;
    }

    public String getWorkCity() {
        return workCity;
    }

    public void setWorkCity(String workCity) {
        this.workCity = workCity;
    }

    public String getWorkCompany() {
        return workCompany;
    }

    public void setWorkCompany(String workCompany) {
        this.workCompany = workCompany;
    }
}
