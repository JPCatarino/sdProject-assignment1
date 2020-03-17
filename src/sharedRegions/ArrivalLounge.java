package sharedRegions;

import entities.Passenger;
import interfaces.ALPassenger;
import interfaces.ALPorter;
import states.PassengerStates;

public class ArrivalLounge implements ALPassenger, ALPorter {
    Repository repo;

    public void ArrivalLounge(Repository repo){
        this.repo = repo;
    }

    @Override
    public synchronized void goCollectABag(){}

    @Override
    public synchronized void takeABus(){}

    @Override
    public synchronized void takeARest(){}

    @Override
    public synchronized void tryToCollectABag(){}

    @Override
    public synchronized void goHome(){
        Passenger p = (Passenger) Thread.currentThread();
        p.setPassengerState(PassengerStates.EXITING_THE_ARRIVAL_TERMINAL);

        // TODO : WAIT FOR ALL PASSENGERS TO BE READY TO LEAVE
    };
}
