package sharedRegions;

import entities.Passenger;
import interfaces.ALPassenger;
import interfaces.ALPorter;
import states.PassengerDecisions;
import states.PassengerStates;
import states.PorterStates;

import java.util.List;

public class ArrivalLounge implements ALPassenger, ALPorter {
    Repository repo;

    private List<int[]> plainBags;
    private boolean pWake;
    int numberOfPassengers;
    int maxNumberOfPassengers;

    public ArrivalLounge(Repository repo, List<int[]> plainBags){
        this.repo = repo;
        this.maxNumberOfPassengers = repo.getN_PASSENGERS();
        this.plainBags = plainBags;
    }

    @Override
    public synchronized void takeABus(){
        Passenger p = (Passenger) Thread.currentThread();
        p.setPassengerState(PassengerStates.AT_THE_ARRIVAL_TRANSFER_TERMINAL);
        repo.setST(p.getID(), PassengerStates.AT_THE_ARRIVAL_TRANSFER_TERMINAL.getState());
        repo.toString_debug();
        repo.reportStatus();
    }

    @Override
    public synchronized int takeARest() {

        try {
            while (!pWake) {
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (false)
            return 1;
        else
            return 0;
    }

    @Override
    public synchronized int[] tryToCollectABag() {
        int[] bag = null;

        if (!plainBags.isEmpty()) {
            bag = plainBags.remove(0);

        }

        repo.setP_Stat(PorterStates.AT_THE_PLANES_HOLD.getState());
        repo.toString_debug();
        repo.reportStatus();
        notifyAll();

        return bag;
    }

    @Override
    public synchronized void noMoreBagsToCollect() {

        repo.setP_Stat(PorterStates.WAITING_FOR_A_PLANE_TO_LAND.getState());
        repo.toString_debug();
        repo.reportStatus();

    }

    @Override
    public synchronized PassengerDecisions whatShouldIDo(){
        Passenger p = (Passenger) Thread.currentThread();
        p.setPassengerState(PassengerStates.AT_THE_DISEMBARKING_ZONE);
        repo.setST(p.getID(), PassengerStates.AT_THE_DISEMBARKING_ZONE.getState());
        repo.reportStatus();
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
            return p.getnBagsToCollect() != 0 ? PassengerDecisions.COLLECT_A_BAG : PassengerDecisions.GO_HOME;
        }

        return PassengerDecisions.TAKE_A_BUS;

    }
}
