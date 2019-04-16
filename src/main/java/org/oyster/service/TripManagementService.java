package org.oyster.service;

import org.oyster.entity.OysterCard;
import org.oyster.entity.Station;
import org.oyster.entity.TransportType;

public interface TripManagementService {

    boolean checkIn(TransportType type, OysterCard card, Station from);
    void checkOut(OysterCard card, Station to);
}
