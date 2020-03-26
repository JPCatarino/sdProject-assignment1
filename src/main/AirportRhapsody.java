package main;

import entities.BusDriver;
import sharedRegions.*;

/**
 * Main class for the Airport Rhapsody Problem
 * It launches the threads for all entities of the problem
 */
public class AirportRhapsody {

    public static void main(String args[]){
        // Initiate Shared Regions
        Repository repository = new Repository();
        ArrivalLounge arrivalLounge = new ArrivalLounge();
        ArrivalQuay arrivalQuay = new ArrivalQuay(repository);
        ArrivalTerminalExit arrivalTerminalExit = new ArrivalTerminalExit();
        BagColPoint bagColPoint = new BagColPoint(repository);
        BagRecOffice bagRecOffice = new BagRecOffice(repository);
        DepartureQuay departureQuay = new DepartureQuay(repository);
        DepartureTerminalEntrance departureTerminalEntrance = new DepartureTerminalEntrance(repository);
        TempStgArea tempStgArea = new TempStgArea(repository);

        // Initiate entities
        BusDriver busDriver = new BusDriver(1000, arrivalQuay, departureQuay);
        // Initiate Porter
        // Initiate passengers
        // For each flight
        // initiate 6 passenger

        // Join BusDriver and Porter
        // for each flight join the different passengers.

    }
}
