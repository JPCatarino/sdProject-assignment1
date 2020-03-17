package sharedRegions;

import entities.Passenger;
import interfaces.BROPassenger;
import states.PassengerStates;

public class BagRecOffice implements BROPassenger {

    Repository repo;

    public BagRecOffice(Repository repo){
        this.repo = repo;
    }

    @Override
    public synchronized void goHome(){
        Passenger p = (Passenger) Thread.currentThread();
        p.setPassengerState(PassengerStates.EXITING_THE_ARRIVAL_TERMINAL);

        // TODO : WAIT FOR ALL PASSENGERS TO BE READY TO LEAVE
    };
}
