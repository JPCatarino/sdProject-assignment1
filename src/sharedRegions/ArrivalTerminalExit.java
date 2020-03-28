package sharedRegions;

import entities.Passenger;
import interfaces.ATEPassenger;
import states.PassengerStates;

public class ArrivalTerminalExit implements ATEPassenger{

    private Repository repo;
    private ArrivalLounge al;
    private boolean allPassengersFinished;
    private DepartureTerminalEntrance dte;

    public ArrivalTerminalExit(Repository repo, ArrivalLounge al) {
        this.repo = repo;
        this.al = al;
        this.allPassengersFinished = false;
    }

    @Override
    public synchronized void goHome(){
        Passenger p = (Passenger) Thread.currentThread();
        p.setPassengerState(PassengerStates.EXITING_THE_ARRIVAL_TERMINAL);
        repo.setST(p.getID(), PassengerStates.EXITING_THE_ARRIVAL_TERMINAL.getState());
        repo.toString_debug();
        repo.reportStatus();

        al.updateFinishedPassenger();
        this.allPassengersFinished = al.isFlightFinished();

        if(this.allPassengersFinished){
            notifyAll();
            dte.setAllPassengersFinished(this.allPassengersFinished);
        }
        else {
            try {
                while (!allPassengersFinished) {
                    wait();
                }
            } catch (InterruptedException ex) {
                System.out.println("goHome - Interrupted Thread");
            }
        }
        al.gonePassenger();
    }

    public void setDte(DepartureTerminalEntrance dte) {
        this.dte = dte;
    }

    public synchronized void setAllPassengersFinished(boolean allPassengersFinished) {
        this.allPassengersFinished = allPassengersFinished;
        if(this.allPassengersFinished){
            notifyAll();
        }
    }
}
