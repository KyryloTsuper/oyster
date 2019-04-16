package org.oyster;

import org.oyster.entity.OysterCard;
import org.oyster.entity.Station;
import org.oyster.entity.TransportType;
import org.oyster.service.TripManagementService;
import org.oyster.service.impl.TripManagementServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class LaunchApp {

    static List<Station> stationsDefault = new ArrayList<>();

    static {
        stationsDefault.add(new Station("Holborn", new HashSet<>(Collections.singletonList(1))));
        stationsDefault.add(new Station("Earl’s Court", new HashSet<>(Arrays.asList(1, 2))));
        stationsDefault.add(new Station("Wimbledon", new HashSet<>(Collections.singletonList(3))));
        stationsDefault.add(new Station("Hammersmith", new HashSet<>(Collections.singletonList(2))));
    }

    static TripManagementService tripManagementService = new TripManagementServiceImpl();

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        while(true) {
            System.out.println();
            System.out.println("(T)est scenario or (M)anual trip? (B)reak.");
            String tripType = in.next();
            switch (tripType) {
                case "T":
                case "t":
                    runTestScenario();
                    break;
                case "M":
                case "m":
                    runManualTrip(in);
                    break;
                default:
                    System.out.println("You've mistyped. Try again.");
                    break;
            }
            if (tripType.toLowerCase().equals("b")) break;
        }
    }

    private static void runManualTrip(Scanner scanner) {
        OysterCard card = new OysterCard(30.);
        while(true) {
            System.out.println("On which station you want to check in?");
            getStationsNames();
            String stationChosen = scanner.next();
            Station startStation = getStationByChosenName(stationChosen);
            TransportType transportType = null;
            while(true) {
                System.out.println("What type of transport? (T)ube or (B)us?");
                String transport = scanner.next();

                switch (transport) {
                    case "T":
                    case "t":
                        transportType = TransportType.TUBE;
                        break;
                    case "B":
                    case "b":
                        transportType = TransportType.BUS;
                        break;
                    default:
                        System.out.println("You've mistyped. Try again.");
                        break;
                }
                if (transportType != null) break;
            }
            tripManagementService.checkIn(transportType, card, startStation);
            System.out.println("On which station you want to check out? Or you'll (f)orget to checkout?");
            getStationsNames();
            stationChosen = scanner.next();
            if (!stationChosen.toLowerCase().equals("f")) {
                Station endStation = getStationByChosenName(stationChosen);
                tripManagementService.checkOut(card, endStation);
            }
            System.out.println("Would you like to end the trip? (Y)es or (N)o?");
            String cont = scanner.next();
            if (cont.toLowerCase().equals("y")) break;
        }
    }

    private static Station getStationByChosenName(String stationChosen) {

        switch (stationChosen) {
            case "H":
            case "h":
                return stationsDefault.get(0);
            case "E":
            case "e":
                return stationsDefault.get(1);
            case "W":
            case "w":
                return stationsDefault.get(2);
            case "A":
            case "a":
                return stationsDefault.get(3);
            default:
                return null;
        }
    }

    private static void getStationsNames() {
        System.out.println("(H)olborn");
        System.out.println("(E)arl’s Court");
        System.out.println("(W)imbledon");
        System.out.println("H(a)mmersmith");
    }

    private static void runTestScenario() {
        OysterCard card = new OysterCard(0);
        card.addFunds(30);

        tripManagementService.checkIn(TransportType.TUBE, card, stationsDefault.get(0));
        tripManagementService.checkOut(card, stationsDefault.get(1));
        tripManagementService.checkIn(TransportType.BUS, card, stationsDefault.get(1));

        tripManagementService.checkIn(TransportType.TUBE, card, stationsDefault.get(1));
        tripManagementService.checkOut(card, stationsDefault.get(3));
    }
}
