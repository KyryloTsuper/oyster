package org.oyster.service.impl;

import org.oyster.entity.Station;
import org.oyster.entity.TransportType;
import org.oyster.service.CostCalculationService;
import org.oyster.utils.FairCalculationRule;
import org.oyster.utils.ZoneTrip;

import java.util.ArrayList;
import java.util.List;

public class CostCalculationServiceImpl implements CostCalculationService {

    public static final double MAX_FARE = 3.2;

    public static final double IN_ZONE_ONE = 2.5;
    public static final double SINGLE_ZONE_OUTSIDE_ZONE_ONE = 2.0;
    public static final double TWO_ZONES_INCLUDING_ZONE_ONE = 3.0;
    public static final double TWO_ZONES_EXCLUDING_ZONE_ONE = 2.25;

    public static final double BUS_FARE = 1.8;

    @Override
    public double calculateTripCost(Station from, Station to) {
        int minimalDistance = Integer.MAX_VALUE;
        List<ZoneTrip> zoneTrips = new ArrayList<>();
        for (Integer fromZone : from.getZones()) {
            for (Integer toZone : to.getZones()) {
                int distance = Math.abs(fromZone - toZone);
                if (distance < minimalDistance) {
                    minimalDistance = Math.abs(fromZone - toZone);
                    zoneTrips.clear();
                }
                if (distance == minimalDistance) {
                    zoneTrips.add(new ZoneTrip(fromZone, toZone));
                }
            }
        }
        return calculateMinimalFareInZones(zoneTrips);
    }

    private double calculateMinimalFareInZones(List<ZoneTrip> zoneTrips) {
        double minimalFare = Double.MAX_VALUE;
        for (ZoneTrip zoneTrip : zoneTrips) {
            double fare = calculateFareByRule(zoneTrip);
            if (fare < minimalFare) {
                minimalFare = fare;
            }
        }
        return minimalFare;
    }

    private double calculateFareByRule(ZoneTrip zoneTrip) {
        List<FairCalculationRule> calculationRules = new ArrayList<>();
        calculationRules.add(zT -> {
            if (zT.getDistance() >= 2) {
                return MAX_FARE;
            }
            return null;
        });
        calculationRules.add(zT -> {
            if (zT.getFrom() == 1 || zT.getTo() == 1) {
                if (zT.getDistance() == 0) {
                    return IN_ZONE_ONE;
                }
                if (zT.getDistance() == 1) {
                    return TWO_ZONES_INCLUDING_ZONE_ONE;
                }
            }
            return null;
        });
        calculationRules.add(zT -> {
            if (zT.getDistance() == 0) {
                return SINGLE_ZONE_OUTSIDE_ZONE_ONE;
            }
            if (zT.getDistance() == 1) {
                return TWO_ZONES_EXCLUDING_ZONE_ONE;
            }
            return null;
        });

        Double resultFare;
        for (FairCalculationRule fairCalculationRule : calculationRules) {
            resultFare = fairCalculationRule.calculate(zoneTrip);
            if (resultFare != null) {
                return resultFare;
            }
        }
        throw new IllegalArgumentException("Station zones did not match any of zone rules");
    }

    public double getMaxFare() {
        return MAX_FARE;
    }

    public double getMaxFareForTransportType(TransportType type) {
        return type == TransportType.BUS ? BUS_FARE : MAX_FARE;
    }

    public double getInZoneOne() {
        return IN_ZONE_ONE;
    }

    public double getSingleZoneOutsideZoneOne() {
        return SINGLE_ZONE_OUTSIDE_ZONE_ONE;
    }

    public double getTwoZonesIncludingZoneOne() {
        return TWO_ZONES_INCLUDING_ZONE_ONE;
    }

    public double getTwoZonesExcludingZoneOne() {
        return TWO_ZONES_EXCLUDING_ZONE_ONE;
    }

    public double getBusFare() {
        return BUS_FARE;
    }
}
