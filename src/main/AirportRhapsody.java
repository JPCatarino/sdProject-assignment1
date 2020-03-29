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

        int K_landings = 5;
        int N_passengers = 6;
        int M_luggage = 2;
        int T_seats = 3;

        final int TRANSIT = 0,
                  FINAL_DESTINATION = 1;

        int[][] statePassenger = new int[K_landings][N_passengers];
        int[][][] numBagsPassenger = new int[K_landings][N_passengers][2];

        List<int[]> plainBags = new ArrayList<>();

        for (int i=0;i<K_landings;i++){
            for (int j=0;j<N_passengers;j++) {
                statePassenger[i][j] = (Math.random() < 0.40) ? TRANSIT : FINAL_DESTINATION;
                numBagsPassenger[i][j][0] = new Random().nextInt(2 + 1);
                if( numBagsPassenger[i][j][0] > 0){
                    if(Math.random() < 0.10)
                        numBagsPassenger[i][j][1] = new Random().nextInt(numBagsPassenger[i][j][0]);
                    else
                        numBagsPassenger[i][j][1] = numBagsPassenger[i][j][0];
                }
                else{
                    numBagsPassenger[i][j][1] = numBagsPassenger[i][j][0];
                }
            }
        }

        // Initiate Shared Regions

        Repository repository = new Repository(N_passengers, T_seats,K_landings, M_luggage);
        ArrivalLounge arrivalLounge = new ArrivalLounge(repository,N_passengers,K_landings);
        ArrivalQuay arrivalQuay = new ArrivalQuay(repository,T_seats, arrivalLounge);
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
        Passenger[][] flights = new Passenger [K_landings][N_passengers];
        for(int i = 0; i < flights.length; i++) {
            for (int z = 0; z < flights[i].length; z++){
                flights[i][z] = new Passenger(z, numBagsPassenger[i][z][0], statePassenger[i][z] == 1, arrivalLounge, bagColPoint, bagRecOffice, arrivalQuay, departureQuay, departureTerminalEntrance, arrivalTerminalExit);
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
            repository.setFN(i + 1);
            arrivalLounge.setFlightNumber(i+1);
            System.out.println("Flight  "+ (i+1));
            for (int j=0;j<N_passengers;j++) {
                for (int l=0;l<numBagsPassenger[i][j][1];l++){
                    int[] tmpArr = new int[2];
                    tmpArr[0] = j;
                    tmpArr[1] = statePassenger[i][j];
                    plainBags.add(tmpArr);
                }
                repository.reset_Passenger(j);
            }
            tempStgArea.clearStoreroom();
            repository.reset_Porter();
            arrivalLounge.setPlainBags(plainBags);
            repository.setBN(plainBags.size());
            bagColPoint.setNoMoreBags(false);

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

        repository.finalReport();

        // TODO Ver se é necessário meter aqui alguma coisa do repo
    }
}
