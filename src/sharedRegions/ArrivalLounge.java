package sharedRegions;

import entities.Passenger;
import entities.Porter;
import interfaces.ALPassenger;
import interfaces.ALPorter;
import states.PassengerDecisions;
import states.PassengerStates;
import states.PorterStates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrivalLounge implements ALPassenger, ALPorter {
    Repository repo;

    private List<int[]> plainBags;
    private boolean pWake;
    private int numberOfPassengers;
    private final int maxNumberOfPassengers;
    private final int maxNumberOfFlights;
    private int flightNumber;
    private int finishedPassengers;
    private boolean finishedFlight;

    public ArrivalLounge(Repository repo, int N_PASSENGERS, int K_LANDINGS){
        this.repo = repo;
        this.maxNumberOfPassengers = N_PASSENGERS;
        this.maxNumberOfFlights = K_LANDINGS;
        this.plainBags = new ArrayList<>();
    }

    @Override
    public synchronized void takeABus(){
        Passenger p = (Passenger) Thread.currentThread();
        p.setPassengerState(PassengerStates.AT_THE_ARRIVAL_TRANSFER_TERMINAL);
        repo.setST(p.getID(), PassengerStates.AT_THE_ARRIVAL_TRANSFER_TERMINAL.getState());
        repo.reportStatus();
    }

    @Override
    public synchronized int takeARest() {
        Porter pt = (Porter) Thread.currentThread();

        try {
            while (!pWake) {
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (flightNumber == maxNumberOfFlights && plainBags.isEmpty()) {
            return 2;
        }
        else
            return 0;
    }

    @Override
    public synchronized int[] tryToCollectABag() {
        int[] bag = null;

        repo.setP_Stat(PorterStates.AT_THE_PLANES_HOLD.getState());

        if (!plainBags.isEmpty()) {
            bag = plainBags.remove(0);
            repo.setBN(plainBags.size());
        }

        repo.reportStatus();
        notifyAll();

        return bag;
    }

    @Override
    public synchronized void noMoreBagsToCollect() {

        repo.setP_Stat(PorterStates.WAITING_FOR_A_PLANE_TO_LAND.getState());
        repo.reportStatus();
        notifyAll();
        if(flightNumber != maxNumberOfFlights)
            pWake = false;

    }

    @Override
    public synchronized PassengerDecisions whatShouldIDo(){
        Passenger p = (Passenger) Thread.currentThread();
        p.setPassengerState(PassengerStates.AT_THE_DISEMBARKING_ZONE);
        repo.setST(p.getID(), PassengerStates.AT_THE_DISEMBARKING_ZONE.getState());
        repo.setNR(p.getID(),p.getnBagsToCollect());
        // Increment number of passengers on the ArrivalLounge
        numberOfPassengers++;

        // Wake up Porter
        if(numberOfPassengers == maxNumberOfPassengers){
            pWake = true;
            numberOfPassengers = 0;
            notifyAll();
        }

        // If journey is ending, passenger should either collect bags or go home
        // otherwise, he takes a bus
        if(p.isJourneyEnding()){
            repo.setSI(p.getID(), PassengerStates.FINAL_DESTINATION.getState());
            repo.reportStatus();
            return p.getnBagsToCollect() != 0 ? PassengerDecisions.COLLECT_A_BAG : PassengerDecisions.GO_HOME;
        }
        repo.setSI(p.getID(), PassengerStates.IN_TRANSIT.getState());
        repo.reportStatus();
        return PassengerDecisions.TAKE_A_BUS;

    }

    public synchronized void updateFinishedPassenger(){
        finishedPassengers++;
    }

    public synchronized void gonePassenger(){
        finishedPassengers--;
    }

    public synchronized boolean isFlightFinished(){
        if(finishedPassengers == maxNumberOfPassengers){
            this.finishedFlight = true;
            return true;
        }
        return false;
    }

    public synchronized void setPlainBags(List<int[]> plainBags) {
        this.plainBags = plainBags;
    }

    public synchronized int getFlightNumber() {
        return flightNumber;
    }

    public synchronized void setFlightNumber(int flightNumber) {
        this.flightNumber = flightNumber;
        this.finishedFlight = false;
    }

    public synchronized int getMaxNumberOfPassengers() {
        return maxNumberOfPassengers;
    }

    public int getMaxNumberOfFlights() {
        return maxNumberOfFlights;
    }

    public boolean isDayFinished() {
        if((this.maxNumberOfFlights == this.flightNumber) && this.finishedFlight){
            return true;
        }
        return false;
    }
}
