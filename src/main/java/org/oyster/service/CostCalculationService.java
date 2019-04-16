package org.oyster.service;

import org.oyster.entity.Station;

public interface CostCalculationService {

    double calculateTripCost(Station from, Station to);
}
