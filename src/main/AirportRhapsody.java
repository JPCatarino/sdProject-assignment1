package main;

import entities.BusDriver;
import entities.Passenger;
import entities.Porter;
import sharedRegions.*;

import java.util.Random;

/**
 * Main class for the Airport Rhapsody Problem
 * It launches the threads for all entities of the problem
 */
public class AirportRhapsody {

    public static void main(String args[]){
        // Initiate Shared Regions
        Repository repository = new Repository();
        ArrivalLounge arrivalLounge = new ArrivalLounge(repository);
        ArrivalQuay arrivalQuay = new ArrivalQuay(repository);
        ArrivalTerminalExit arrivalTerminalExit = new ArrivalTerminalExit(repository);
        BagColPoint bagColPoint = new BagColPoint(repository);
        BagRecOffice bagRecOffice = new BagRecOffice(repository);
        DepartureQuay departureQuay = new DepartureQuay(repository);
        DepartureTerminalEntrance departureTerminalEntrance = new DepartureTerminalEntrance(repository);
        TempStgArea tempStgArea = new TempStgArea(repository);

        // Initiate entities
        BusDriver busDriver = new BusDriver(1000, arrivalQuay, departureQuay);
        // Initiate Porter
        Porter porter = new Porter(arrivalLounge, bagColPoint, tempStgArea);
        // Initiate passengers
        // For each flight
        // initiate N passenger
        Passenger[][] flights = new Passenger [repository.getK_LANDINGS()][repository.getN_PASSENGERS()];
        for(int i = 0; i < flights.length; i++) {
            for (int z = 0; z < flights[i].length; z++){
                flights[i][z] = new Passenger(z, (int) Math.random() * (2 + 1), new Random().nextBoolean(), arrivalLounge, bagColPoint, bagRecOffice, arrivalQuay, departureQuay, departureTerminalEntrance, arrivalTerminalExit);
            }
        }

        // Join BusDriver and Porter
        porter.start();
        busDriver.start();
        // for each flight join the different passengers.

        // We start the threads for a flight and wait till the flight as ended
        // before starting another batch of passengers.
        // After, we wait till the porter and bus driver finished and close the program successfully
        for(int i = 0; i < flights.length; i++){
            for(int z = 0; z < flights[i].length; z++){
                flights[i][z].start();
            }
            try{
                for(Passenger passenger: flights[i]){
                    passenger.join();
                }
            }
            catch(InterruptedException ex){}
        }

        try {
            porter.join();
        }
        catch(InterruptedException ex){}

        try {
            busDriver.join();
        }
        catch(InterruptedException ex){}

        // TODO Ver se é necessário meter aqui alguma coisa do repo
    }
}
