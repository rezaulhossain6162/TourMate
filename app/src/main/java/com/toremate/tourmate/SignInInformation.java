package com.toremate.tourmate;

/**
 * Created by jack on 10/25/2017.
 */

public class SignInInformation {
    String name;
    String email;
    String password;
    String uid;
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUid(){
        return uid;
    }

    public SignInInformation(String name, String email, String password, String uid) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.uid=uid;
    }
    public SignInInformation(){

    }
}
