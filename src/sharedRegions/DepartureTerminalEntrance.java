package sharedRegions;

import entities.Passenger;
import interfaces.DTEPassenger;
import states.PassengerStates;

/**
 * Implementation of the Departure Terminal Entrance Shared Memory
 * The Passengers arrive here and wait other passengers to
 * reach final state so they can progress.
 *
 * @author Fábio Alves
 * @author Jorge Catarino
 */
public class DepartureTerminalEntrance implements DTEPassenger {

    /**
     * Information Repository.
     *
     * @serialField repo
     */
    private Repository repo;

    /**
     * ArrivalLounge Shared Memory.
     *
     * @serialField al
     */
    private ArrivalLounge al;

    /**
     * True if all the passengers have arrived to the exit zones.
     *
     * @serialField allPassengersFinished
     */
    private boolean allPassengersFinished;

    /**
     * ArrivalTerminalExit Shared Memory.
     *
     * @serialField ate
     */
    private ArrivalTerminalExit ate;

    /**
     * DepartureTerminalEntrance Shared Memory.
     *
     * @param repo General Information Repository.
     * @param al Arrival Lounge for the latest information on flights.
     * @param ate Arrival Terminal for synchronization.
     */
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

    /**
     * Setter to all passenger finished.
     * If all passenger are finished notifies all threads.
     *
     * @param allPassengersFinished True if all the passengers have arrived to the exit zones.
     */
    public synchronized void setAllPassengersFinished(boolean allPassengersFinished) {
        this.allPassengersFinished = allPassengersFinished;
        if(this.allPassengersFinished){
            notifyAll();
        }
    }
}
