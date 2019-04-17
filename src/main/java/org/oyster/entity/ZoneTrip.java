package org.oyster.entity;

public class ZoneTrip {
    private Integer from;
    private Integer to;

    public ZoneTrip(Integer from, Integer to) {
        this.from = from;
        this.to = to;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public int getDistance() {
        return Math.abs(from - to);
    }

    public boolean hasZone(Integer zone) {
        return from.equals(zone) || to.equals(zone);
    }
}
