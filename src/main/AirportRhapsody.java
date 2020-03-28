package main;

import entities.BusDriver;
import entities.Passenger;
import entities.Porter;
import sharedRegions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Main class for the Airport Rhapsody Problem
 * It launches the threads for all entities of the problem
 */
public class AirportRhapsody {

    public static void main(String args[]){
        // Initiate Shared Regions

        int K_LANDINGS = 5;
        int N_PASSENGERS = 6;
        int M_LUGGAGE = 2;
        int T_SEATS = 3;

        List<int[]> plainBags=new ArrayList<>();

        int [] arr1=  {1,2};
        int [] arr2=  {2,2};
        int [] arr3=  {3,2};
        int [] arr4=  {4,2};
        int [] arr5=  {5,2};
        int [] arr6=  {0,2};

        plainBags.add(arr1);
        plainBags.add(arr2);
        plainBags.add(arr3);
        plainBags.add(arr4);
        plainBags.add(arr5);
        plainBags.add(arr6);

        Repository repository = new Repository(N_PASSENGERS, T_SEATS,K_LANDINGS, M_LUGGAGE);
        ArrivalLounge arrivalLounge = new ArrivalLounge(repository,plainBags,N_PASSENGERS, K_LANDINGS);
        ArrivalQuay arrivalQuay = new ArrivalQuay(repository,T_SEATS);
        ArrivalTerminalExit arrivalTerminalExit = new ArrivalTerminalExit(repository, arrivalLounge);
        BagColPoint bagColPoint = new BagColPoint(repository);
        BagRecOffice bagRecOffice = new BagRecOffice(repository);
        DepartureQuay departureQuay = new DepartureQuay(repository);
        DepartureTerminalEntrance departureTerminalEntrance = new DepartureTerminalEntrance(repository, arrivalLounge, arrivalTerminalExit);
        arrivalTerminalExit.setDte(departureTerminalEntrance);
        TempStgArea tempStgArea = new TempStgArea(repository);

        // Initiate entities
        BusDriver busDriver = new BusDriver(100, arrivalQuay, departureQuay, arrivalLounge);
        // Initiate Porter
        Porter porter = new Porter(arrivalLounge, bagColPoint, tempStgArea);
        // Initiate passengers
        // For each flight
        // initiate N passenger
        Passenger[][] flights = new Passenger [K_LANDINGS][N_PASSENGERS];
        for(int i = 0; i < flights.length; i++) {
            for (int z = 0; z < flights[i].length; z++){
                flights[i][z] = new Passenger(z, 1, new Random().nextBoolean(), arrivalLounge, bagColPoint, bagRecOffice, arrivalQuay, departureQuay, departureTerminalEntrance, arrivalTerminalExit);
            }                                                                         // change to 2+1
        }

        // Join BusDriver and Porter
        porter.start();
        busDriver.start();
        // for each flight join the different passengers.

        // We start the threads for a flight and wait till the flight as ended
        // before starting another batch of passengers.
        // After, we wait till the porter and bus driver finished and close the program successfully
        for(int i = 0; i < flights.length; i++){
            repository.setFN(i + 1);
            arrivalLounge.setFlightNumber(i+1);
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
