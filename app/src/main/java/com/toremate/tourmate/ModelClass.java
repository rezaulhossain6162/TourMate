package com.toremate.tourmate;

/**
 * Created by jack on 11/20/2017.
 */

public class ModelClass {
    String Destination;
    String budget;
    String cost;
    String becauseOfCost;
    String Balance;

    public ModelClass() {

    }

    public ModelClass(String destination, String budget, String cost, String becauseOfCost, String balance) {
        this.Destination = destination;
        this.budget = budget;
        this.cost = cost;
        this.becauseOfCost = becauseOfCost;
        this.Balance = balance;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getBecauseOfCost() {
        return becauseOfCost;
    }

    public void setBecauseOfCost(String becauseOfCost) {
        this.becauseOfCost = becauseOfCost;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }
}
