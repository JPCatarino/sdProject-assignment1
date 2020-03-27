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

    public void ArrivalLounge(Repository repo){
        this.repo = repo;
        this.maxNumberOfPassengers = repo.getN_PASSENGERS();
    }

    @Override
    public synchronized void takeABus(){
        Passenger p = (Passenger) Thread.currentThread();
        p.setPassengerState(PassengerStates.AT_THE_ARRIVAL_TRANSFER_TERMINAL);
        repo.setST(p.getID(), PassengerStates.AT_THE_ARRIVAL_TRANSFER_TERMINAL.getState());
    }

    @Override
    public synchronized int takeARest() {
        if (false)
            return 1;
        else
            return 0;
    }

    @Override
    public synchronized int[] tryToCollectABag() {
        int[] bag= new int[2];

        if (!plainBags.isEmpty()) {
            bag = plainBags.get(0);
        }

        repo.setP_Stat(PorterStates.AT_THE_PLANES_HOLD.getState());
        repo.toString_debug();
        repo.reportStatus();

        return bag;
    }

    @Override
    public synchronized void noMoreBagsToCollect() {

        repo.setP_Stat(PorterStates.WAITING_FOR_A_PLANE_TO_LAND.getState());
        repo.toString_debug();
        repo.reportStatus();

        try {
            while (!pWake) {
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized PassengerDecisions whatShouldIDo(){
        Passenger p = (Passenger) Thread.currentThread();

        // Increment number of passengers on the ArrivalLounge
        numberOfPassengers++;

        try{
            while(numberOfPassengers < maxNumberOfPassengers){
                wait();
            }

            // Wake up Porter
            if(numberOfPassengers == maxNumberOfPassengers){
                pWake = true;
                notifyAll();
            }
        }
        catch(InterruptedException ex){
            System.err.println("whatShouldIDo - Thread Interrupted");
        }

        numberOfPassengers--;
        // If journey is ending, passenger should either collect bags or go home
        // otherwise, he takes a bus
        if(p.isJourneyEnding()){
            return p.getnBagsToCollect() != 0 ? PassengerDecisions.COLLECT_A_BAG : PassengerDecisions.GO_HOME;
        }

        return PassengerDecisions.TAKE_A_BUS;

    }
}
