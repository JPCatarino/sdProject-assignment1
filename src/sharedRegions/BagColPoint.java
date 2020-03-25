package sharedRegions;

import entities.Passenger;
import interfaces.BCPPassenger;
import interfaces.BCPPorter;
import states.PassengerStates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BagColPoint implements BCPPassenger, BCPPorter {

    private Repository repo;
    private List<Arrays> conveyorBelt;

    public BagColPoint(Repository repo){
        this.repo = repo;
        this.conveyorBelt = new ArrayList<>();
    }

    @Override
    public synchronized void goCollectABag (){
        Passenger p = (Passenger) Thread.currentThread();
        p.setPassengerState(PassengerStates.AT_THE_LUGGAGE_COLLECTION_POINT);



    };


    @Override
    public synchronized void carryItToAppropriateStore(){};
}
