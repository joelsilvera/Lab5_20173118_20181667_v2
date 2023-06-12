package com.example.lab5_20173118_20181667_v2;

public class Users {
    private String userId;
    private String name;
    private String profile;

    public Users(String userId, String name, String profile) {
        this.setUserId(userId);
        this.setName(name);
        this.setProfile(profile);
    }

    public Users() {
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
