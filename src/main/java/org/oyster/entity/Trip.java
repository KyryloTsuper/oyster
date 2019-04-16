package org.oyster.entity;

public class Trip {

    private Station from;
    private TransportType transport;

    public Trip(Station from, TransportType transport) {
        this.from = from;
        this.transport = transport;
    }

    public Station getFrom() {
        return from;
    }

    public void setFrom(Station from) {
        this.from = from;
    }

    public TransportType getTransport() {
        return transport;
    }

    public void setTransport(TransportType transport) {
        this.transport = transport;
    }
}
