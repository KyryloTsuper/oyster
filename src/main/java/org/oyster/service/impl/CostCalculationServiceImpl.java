package org.oyster.service.impl;

import org.oyster.entity.Station;
import org.oyster.entity.TransportType;
import org.oyster.service.CostCalculationService;
import org.oyster.entity.rules.FairCalculationRule;
import org.oyster.entity.ZoneTrip;
import org.oyster.entity.rules.impl.SingleZoneWithoutZoneOneRule;
import org.oyster.entity.rules.impl.ThreeZonesRule;
import org.oyster.entity.rules.impl.TwoZonesWithZoneOneRule;
import org.oyster.entity.rules.impl.TwoZonesWithoutZoneOneRule;
import org.oyster.entity.rules.impl.ZoneOneRule;

import java.util.ArrayList;
import java.util.List;

public class CostCalculationServiceImpl implements CostCalculationService {

    public static final double MAX_FARE = 3.2;

    public static final double IN_ZONE_ONE = 2.5;
    public static final double SINGLE_ZONE_OUTSIDE_ZONE_ONE = 2.0;
    public static final double TWO_ZONES_INCLUDING_ZONE_ONE = 3.0;
    public static final double TWO_ZONES_EXCLUDING_ZONE_ONE = 2.25;

    public static final double BUS_FARE = 1.8;

    private static List<FairCalculationRule> zoneRules = new ArrayList<>();

    static {
        zoneRules.add(new ZoneOneRule());
        zoneRules.add(new TwoZonesWithZoneOneRule());
        zoneRules.add(new TwoZonesWithoutZoneOneRule());
        zoneRules.add(new SingleZoneWithoutZoneOneRule());
        zoneRules.add(new ThreeZonesRule());
    }

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
        FairCalculationRule rule = zoneRules.stream()
                .filter(z -> z.check(zoneTrip))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Station zones did not match any of zone rules"));
        return rule.getResult();
    }

    public static double getMaxFare() {
        return MAX_FARE;
    }

    public double getMaxFareForTransportType(TransportType type) {
        return type == TransportType.BUS ? BUS_FARE : MAX_FARE;
    }

    public static double getInZoneOne() {
        return IN_ZONE_ONE;
    }

    public static double getSingleZoneOutsideZoneOne() {
        return SINGLE_ZONE_OUTSIDE_ZONE_ONE;
    }

    public static double getTwoZonesIncludingZoneOne() {
        return TWO_ZONES_INCLUDING_ZONE_ONE;
    }

    public static double getTwoZonesExcludingZoneOne() {
        return TWO_ZONES_EXCLUDING_ZONE_ONE;
    }

    public static double getBusFare() {
        return BUS_FARE;
    }
}
