package org.oyster.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.oyster.entity.Station;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class CostCalculationServiceImplTest {

    public static List<Station> stations = new ArrayList<>();

    public CostCalculationServiceImpl costService = new CostCalculationServiceImpl();

    @Before
    public void setUp() {
        stations.add(new Station("Holborn", new HashSet<>(Collections.singletonList(1))));
        stations.add(new Station("Earl’s Court", new HashSet<>(Arrays.asList(1, 2))));
        stations.add(new Station("Wimbledon", new HashSet<>(Collections.singletonList(3))));
        stations.add(new Station("Hammersmith", new HashSet<>(Collections.singletonList(2))));
    }

    @Test
    public void testCalculateTripCostAnywareInZoneOne() {
        Assert.assertEquals(costService.calculateTripCost(stations.get(0), stations.get(1)), costService.getInZoneOne(), 0.);
        Assert.assertEquals(costService.calculateTripCost(stations.get(0), stations.get(0)), costService.getInZoneOne(), 0.);
    }

    @Test
    public void testCalculateTripCostSingleZoneOutsideZoneOne() {
        Assert.assertEquals(costService.calculateTripCost(stations.get(2), stations.get(2)), costService.getSingleZoneOutsideZoneOne(), 0.);
        Assert.assertEquals(costService.calculateTripCost(stations.get(3), stations.get(3)), costService.getSingleZoneOutsideZoneOne(), 0.);
    }

    @Test
    public void testCalculateTripCostAnyTwoZonesIncludingZoneOne() {
        Assert.assertEquals(costService.calculateTripCost(stations.get(0), stations.get(3)), costService.getTwoZonesIncludingZoneOne(), 0.);
    }

    @Test
    public void testCalculateTripCostAnyTwoZonesExcludingZoneOne() {
        Assert.assertEquals(costService.calculateTripCost(stations.get(1), stations.get(2)), costService.getTwoZonesExcludingZoneOne(), 0.);
        Assert.assertEquals(costService.calculateTripCost(stations.get(2), stations.get(3)), costService.getTwoZonesExcludingZoneOne(), 0.);
    }

    @Test
    public void testCalculateTripCostAnyThreeZones() {
        Assert.assertEquals(costService.calculateTripCost(stations.get(0), stations.get(2)), costService.getMaxFare(), 0.);
    }
}