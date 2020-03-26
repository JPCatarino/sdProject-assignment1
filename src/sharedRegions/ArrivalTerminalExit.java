package sharedRegions;

import entities.Passenger;
import interfaces.ATEPassenger;
import states.PassengerStates;

public class ArrivalTerminalExit implements ATEPassenger{

    private Repository repo;

    @Override
    public synchronized void goHome(){
        Passenger p = (Passenger) Thread.currentThread();
        p.setPassengerState(PassengerStates.EXITING_THE_ARRIVAL_TERMINAL);
        repo.setST(p.getID(), PassengerStates.EXITING_THE_ARRIVAL_TERMINAL.getState());
        // TODO : WAIT FOR ALL PASSENGERS TO BE READY TO LEAVE
    }
}
