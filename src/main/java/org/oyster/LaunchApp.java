package org.oyster;

import org.oyster.entity.OysterCard;
import org.oyster.entity.Station;
import org.oyster.entity.TransportType;
import org.oyster.service.TripManagementService;
import org.oyster.service.impl.CostCalculationServiceImpl;
import org.oyster.service.impl.TripManagementServiceImpl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.InputMismatchException;
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

    public static TripManagementService tripManagementService = new TripManagementServiceImpl();

    private static DecimalFormat numberFormat = new DecimalFormat("#0.00");

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        while(true) {
            System.out.println();
            System.out.println("(T)est scenario or (M)anual trip? (B)reak.");
            String tripType = in.next();
            switch (tripType.toLowerCase()) {
                case "t":
                    runTestScenario();
                    break;
                case "m":
                    runManualTrip(in);
                    break;
                case "b":
                    break;
                default:
                    System.out.println("You've mistyped. Try again.");
                    break;
            }
            if (tripType.toLowerCase().equals("b")) break;
        }
    }

    private static void runManualTrip(Scanner scanner) {
        OysterCard card = new OysterCard(1.7);
        System.out.println("Your balance: " + numberFormat.format(card.getBalance()));
        boolean success = true;
        while(true) {
            if (!success) {
                while(true) {
                    double addedFunds;
                    System.out.println("You don't have enough money on your card. Please add some funds on it. Balance: " + numberFormat.format(card.getBalance()));
                    try {
                        addedFunds = scanner.nextDouble();
                    } catch (InputMismatchException e) {
                        scanner.nextLine();
                        System.out.println("Wrong input.");
                        addedFunds = -1;
                    }
                    if (addedFunds > 0) {
                        if (card.getBalance() + addedFunds - CostCalculationServiceImpl.getMaxFare() >= 0) {
                            card.addFunds(addedFunds);
                            break;
                        }
                        card.addFunds(addedFunds);
                    }
                    System.out.println("Please enter a positive digit.");
                }
            }
            Station startStation;
            String stationChosen;
            while(true) {
                System.out.println("On which station you want to check in?");
                getStationsNames();
                stationChosen = scanner.next();
                startStation = getStationByChosenName(stationChosen);
                if (startStation != null) break;

                System.out.println("Station wasn't found, please enter correct station.");
            }
            TransportType transportType = null;
            while(true) {
                System.out.println("What type of transport? (T)ube or (B)us?");
                String transport = scanner.next();

                switch (transport.toLowerCase()) {
                    case "t":
                        transportType = TransportType.TUBE;
                        break;
                    case "b":
                        transportType = TransportType.BUS;
                        break;
                    default:
                        System.out.println("You've mistyped. Try again.");
                        break;
                }
                if (transportType != null) break;
            }
            success = tripManagementService.checkIn(transportType, card, startStation);
            if (success) {
                System.out.println("On which station you want to check out? Or you'll (f)orget to checkout?");
                getStationsNames();
                stationChosen = scanner.next();
                if (!stationChosen.toLowerCase().equals("f")) {
                    Station endStation = getStationByChosenName(stationChosen);
                    tripManagementService.checkOut(card, endStation);
                }
            }

            String cont;
            while (true) {
                System.out.println("Would you like to continue the trip? (Y)es or (N)o?");
                cont = scanner.next();
                switch (cont.toLowerCase()) {
                    case "y":
                    case "n":
                        break;
                    default:
                        System.out.println("Wrong input.");
                }
                if (cont.toLowerCase().equals("y") || cont.toLowerCase().equals("n")) {
                    break;
                }
            }
            if (cont.toLowerCase().equals("n")) break;
        }
    }

    private static Station getStationByChosenName(String stationChosen) {

        switch (stationChosen.toLowerCase()) {
            case "h":
                return stationsDefault.get(0);
            case "e":
                return stationsDefault.get(1);
            case "w":
                return stationsDefault.get(2);
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
