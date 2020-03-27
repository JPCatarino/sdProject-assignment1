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
    public synchronized void reportMissingBags(){
        Passenger p = (Passenger) Thread.currentThread();
        p.setPassengerState(PassengerStates.AT_THE_BAGGAGE_RECLAIM_OFFICE);
        repo.setST(p.getID(), PassengerStates.AT_THE_BAGGAGE_RECLAIM_OFFICE.getState());
        repo.toString_debug();
        repo.reportStatus();
        try {
            Thread.sleep(100);
        }
        catch(InterruptedException ex){
            System.err.println("reportMissingBags - Thread Interrupted");
        }
    };
}
