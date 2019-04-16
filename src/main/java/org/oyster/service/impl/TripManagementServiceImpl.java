package org.oyster.service.impl;

import org.oyster.entity.OysterCard;
import org.oyster.entity.Station;
import org.oyster.entity.TransportType;
import org.oyster.entity.Trip;
import org.oyster.service.TripManagementService;

import java.text.DecimalFormat;

public class TripManagementServiceImpl implements TripManagementService {

    CostCalculationServiceImpl costService = new CostCalculationServiceImpl();

    public boolean checkIn(TransportType type, OysterCard card, Station from) {
        double initialCost = costService.getMaxFareForTransportType(type);
        if (card.getBalance() - initialCost >= 0) {
            Trip trip = new Trip(from, type);
            card.setTrip(trip);
            card.reduceFunds(initialCost);
            System.out.println("Checked in on Station: " + from.getName() + ". Type: " + card.getTrip().getTransport() + ". Reduced funds: -" + initialCost + ". Balance: " + card.getBalance());
            return true;
        }
        return false;
    }

    public void checkOut(OysterCard card, Station to) {
        double actualCost = costService.getMaxFare();
        if (card.getTrip().getTransport() == TransportType.TUBE) {
            actualCost = costService.calculateTripCost(card.getTrip().getFrom(), to);
        }
        double compensation = costService.getMaxFare() - actualCost;
        card.addFunds(compensation);
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        System.out.println("Checked out on Station: " + to.getName() + ". Compensation: " + numberFormat.format(compensation) + ". Balance: " + card.getBalance());
    }
}
