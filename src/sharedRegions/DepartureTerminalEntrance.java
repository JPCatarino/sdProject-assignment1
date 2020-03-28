package sharedRegions;

import entities.Passenger;
import interfaces.DTEPassenger;
import states.PassengerStates;

public class DepartureTerminalEntrance implements DTEPassenger {

    private Repository repo;
    private ArrivalLounge al;
    private boolean allPassengersFinished;
    private ArrivalTerminalExit ate;


    public DepartureTerminalEntrance(Repository repo, ArrivalLounge al, ArrivalTerminalExit ate){
        this.repo = repo;
        this.al = al;
        this.allPassengersFinished = false;
        this.ate = ate;
    }

    @Override
    public synchronized void prepareNextLeg() {
        Passenger p = (Passenger) Thread.currentThread();
        p.setPassengerState(PassengerStates.ENTERING_THE_DEPARTURE_TERMINAL);
        repo.setST(p.getID(), PassengerStates.ENTERING_THE_DEPARTURE_TERMINAL.getState());

        al.updateFinishedPassenger();
        this.allPassengersFinished = al.isFlightFinished();

        if(this.allPassengersFinished){
            notifyAll();
            ate.setAllPassengersFinished(this.allPassengersFinished);
        }
        else {
            try {
                while (!allPassengersFinished) wait();
            } catch (InterruptedException ex) {
                System.out.println("prepareNextLeg - Interrupted Thread");
            }
        }
        al.gonePassenger();
    }

    public synchronized void setAllPassengersFinished(boolean allPassengersFinished) {
        this.allPassengersFinished = allPassengersFinished;
        if(this.allPassengersFinished){
            notifyAll();
        }
    }

}
