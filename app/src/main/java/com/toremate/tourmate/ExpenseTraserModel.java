package com.toremate.tourmate;

/**
 * Created by jack on 10/26/2017.
 */

public class ExpenseTraserModel {
    String description;
    String amount;
    String uid;
    String budget;
    String destination;


    public String getDescription() {
        return description;
    }

    public String getAmount() {
        return amount;
    }

    public String getUid() {
        return uid;
    }

    public String getBudget() {
        return budget;
    }

    public String getDestination() {
        return destination;
    }

    public ExpenseTraserModel(String description, String amount, String uid, String budget, String destination) {
        this.description = description;
        this.amount = amount;
        this.uid = uid;
        this.budget = budget;
        this.destination = destination;
    }
    public ExpenseTraserModel(){

    }
}
