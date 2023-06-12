package com.example.lab5_20173118_20181667_v2.model;

public class Doctor {
    private String gender;
    private Name name;
    private Location location;
    private String email;
    private String phone;
    private Picture picture;
    private String nat;
    private Dob dob;
    private Login login;

    public String getGender() {
        return gender;
    }

    public Name getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Picture getPicture() {
        return picture;
    }

    public String getNat() {
        return nat;
    }

    public Dob getDob() {
        return dob;
    }

    public Login getLogin() {
        return login;
    }
}
