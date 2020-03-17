package sharedRegions;

import entities.Passenger;
import interfaces.ALPassenger;
import interfaces.ALPorter;
import states.PassengerDecisions;
import states.PassengerStates;

public class ArrivalLounge implements ALPassenger, ALPorter {
    Repository repo;

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
    }

    @Override
    public synchronized void takeARest(){}

    @Override
    public synchronized void tryToCollectABag(){}

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
