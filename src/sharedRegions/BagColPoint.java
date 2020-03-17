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
    public synchronized void goCollectABag (){};

    @Override
    public synchronized void reportMissingBags(){};

    @Override
    public synchronized void goHome(){
        Passenger p = (Passenger) Thread.currentThread();
        p.setPassengerState(PassengerStates.EXITING_THE_ARRIVAL_TERMINAL);

        // TODO : WAIT FOR ALL PASSENGERS TO BE READY TO LEAVE
    };

    @Override
    public synchronized void carryItToAppropriateStore(){};
}
