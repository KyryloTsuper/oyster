package org.oyster.entity;

import java.util.function.Predicate;

public class RuleWithCost {

    Predicate<ZoneTrip> rule;
    double cost;

    public RuleWithCost(Predicate<ZoneTrip> rule, double cost) {
        this.rule = rule;
        this.cost = cost;
    }

    public Predicate<ZoneTrip> getRule() {
        return rule;
    }

    public void setRule(Predicate<ZoneTrip> rule) {
        this.rule = rule;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
