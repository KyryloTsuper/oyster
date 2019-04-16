package org.oyster.entity;

import java.util.Set;

public class Station {

    private String name;
    private Set<Integer> zones;

    public Station(String name, Set<Integer> zones) {
        this.name = name;
        this.zones = zones;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Integer> getZones() {
        return zones;
    }

    public void setZones(Set<Integer> zones) {
        this.zones = zones;
    }
}