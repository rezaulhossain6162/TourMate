package com.toremate.tourmate;

/**
 * Created by jack on 10/26/2017.
 */

public class TravelEventModel {
    String destination;
    String budget;
    String fromdate;
    String todata;
    String uid;

    public String getDestination() {
        return destination;
    }

    public String getBudget() {
        return budget;
    }

    public String getFromdate() {
        return fromdate;
    }

    public String getTodata() {
        return todata;
    }
    public String getUid(){
        return uid;
    }

    public TravelEventModel(String uid,String destination, String budget, String fromdate, String todata) {
        this.destination = destination;
        this.budget = budget;
        this.fromdate = fromdate;
        this.todata = todata;
        this.uid=uid;
    }
    public TravelEventModel(){

    }
}
