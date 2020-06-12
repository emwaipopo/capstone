package com.launchcode.kids_events.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Events {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    private String date;

    private String Description;

    @ManyToOne(cascade = CascadeType.ALL)
    private Address address;

    @ManyToMany(mappedBy = "events")
    private List<Profile> profiles = new ArrayList<Profile>();

    public Events() {
    }

    public Events(Address address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
