package org.oyster.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.oyster.entity.OysterCard;
import org.oyster.entity.Station;
import org.oyster.entity.TransportType;
import org.oyster.service.TripManagementService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class TripManagementServiceImplTest {

    public static List<Station> stations = new ArrayList<>();

    public TripManagementService tripService = new TripManagementServiceImpl();


    @Before
    public void setUp() {
        stations.add(new Station("Holborn", new HashSet<>(Collections.singletonList(1))));
        stations.add(new Station("Earl’s Court", new HashSet<>(Arrays.asList(1, 2))));
        stations.add(new Station("Wimbledon", new HashSet<>(Collections.singletonList(3))));
        stations.add(new Station("Hammersmith", new HashSet<>(Collections.singletonList(2))));
    }

    @Test
    public void testCheckInWithLowBalanceBus() {
        OysterCard card = new OysterCard(1.7);
        Assert.assertFalse(tripService.checkIn(TransportType.BUS, card, new Station("", null)));
    }

    @Test
     public void testCheckInWithLowBalanceTube() {
        OysterCard card = new OysterCard(3.1);
        Assert.assertFalse(tripService.checkIn(TransportType.TUBE, card, new Station("", null)));
    }

    @Test
    public void testCheckInWithBus() {
        double expected = 28.2;
        OysterCard card = new OysterCard(30.);
        tripService.checkIn(TransportType.BUS, card, new Station("", null));
        Assert.assertEquals(card.getBalance(), expected, 0.);
    }

    @Test
    public void testCheckInWithTube() {
        double expected = 26.8;
        OysterCard card = new OysterCard(30.);
        tripService.checkIn(TransportType.TUBE, card, new Station("", null));
        Assert.assertEquals(card.getBalance(), expected, 0.);
    }

    @Test
    public void testCheckInWithoutCheckout() {
        double expected = 21.8;
        OysterCard card = new OysterCard(30.);
        tripService.checkIn(TransportType.TUBE, card, new Station("", null));
        tripService.checkIn(TransportType.BUS, card, new Station("", null));
        tripService.checkIn(TransportType.TUBE, card, new Station("", null));
        Assert.assertEquals(card.getBalance(), expected, 0.);
    }

    @Test
    public void testCheckOutForBus() {
        double expected = 28.2;
        OysterCard card = new OysterCard(30.);
        tripService.checkIn(TransportType.BUS, card, stations.get(1));
        tripService.checkOut(card, stations.get(0));
        Assert.assertEquals(expected, card.getBalance(), 0.);
    }

    @Test
    public void testCheckoutDefaultScenario() {
        double expected = 23.7;
        OysterCard card = new OysterCard(30.);
        tripService.checkIn(TransportType.TUBE, card, stations.get(0));
        tripService.checkOut(card, stations.get(1));

        tripService.checkIn(TransportType.BUS, card, stations.get(1));
        tripService.checkOut(card, stations.get(3));

        tripService.checkIn(TransportType.TUBE, card, stations.get(1));
        tripService.checkOut(card, stations.get(3));

        Assert.assertEquals(expected, card.getBalance(), 0.);
    }
}