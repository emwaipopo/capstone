package com.launchcode.kids_events.models;

import java.time.LocalDate;


public class Profile {
    private int id;

    private String firstName;

    private String lastName;

    private LocalDate dob;

    private String gender;

    private int contactId;

    private int roleId;

    public Profile(){}

    public Profile(String firstName, String lastName, LocalDate dob, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}