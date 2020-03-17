package sharedRegions;

import entities.Passenger;
import interfaces.BCPPassenger;
import interfaces.BCPPorter;
import states.PassengerStates;

public class BagColPoint implements BCPPassenger, BCPPorter {

    Repository repo;

    public BagColPoint(Repository repo){
        this.repo = repo;
    }

    @Override
    public synchronized void goCollectABag (){
        Passenger p = (Passenger) Thread.currentThread();
        p.setPassengerState(PassengerStates.AT_THE_LUGGAGE_COLLECTION_POINT);
    };

    @Override
    public synchronized void reportMissingBags(){};

    @Override
    public synchronized void carryItToAppropriateStore(){};
}
