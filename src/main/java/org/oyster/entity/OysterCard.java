package org.oyster.entity;

public class OysterCard {

    private double balance;
    private Trip trip;

    public OysterCard(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void addFunds(double amount) {
        balance += amount;
    }

    public void reduceFunds(double amount) {
        balance -= amount;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
}
