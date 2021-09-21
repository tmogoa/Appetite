package com.loct.appetite.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public boolean isAdmin;
    public double budget;

    //required do not delete
    public User(){

    }

    public User(boolean isAdmin, double budget){
        this.isAdmin = isAdmin;
        this.budget = budget;
    }
}
