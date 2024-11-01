package com.EDISKASUMOVICipia.example;

public class User {

    public String name;
    public String address;
    public String userEmail;
    public String userPassword;

    private static User currentUser;

    public User() {}

    public User(String name, String address, String userEmail, String userPassword) {
        this.name = name;
        this.address = address;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    @Override
    public String toString() {
        return "Ime korisnika: " + name + ", Adresa: " + address + ", Email: " + userEmail + ", Password: " + userPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }
}
