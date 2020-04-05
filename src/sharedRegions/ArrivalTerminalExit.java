package sharedRegions;

import entities.Passenger;
import interfaces.ATEPassenger;
import states.PassengerStates;

/**
 * Implementation of the Arrival Terminal Exit Shared Memory
 * The Passengers arrive here and wait other passengers to
 * reach final state so they can progress.
 *
 * @author Fábio Alves
 * @author Jorge Catarino
 */
public class ArrivalTerminalExit implements ATEPassenger{

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
     * DepartureTerminalEntrance Shared Memory.
     *
     * @serialField dte
     */
    private DepartureTerminalEntrance dte;

    /**
     * Number of passengers currently waiting on this terminal.
     * @serialField passengersATE
     */
    private int passengersATE;

    /**
     * Maximum number of passengers on this flight.
     * @serialField maxNumberOfPassengers
     */
    private int maxNumberOfPassengers;

    /**
     * ArrivalTerminalExit Constructor.
     *
     * @param repo General Repository of Information.
     * @param al Arrival Lounge for the latest information on flights.
     */
    public ArrivalTerminalExit(Repository repo, ArrivalLounge al) {
        this.repo = repo;
        this.al = al;
        this.maxNumberOfPassengers = al.getMaxNumberOfPassengers();
        this.allPassengersFinished = false;
        this.passengersATE = 0;
    }

    @Override
    public synchronized void goHome(){
        Passenger p = (Passenger) Thread.currentThread();
        p.setPassengerState(PassengerStates.EXITING_THE_ARRIVAL_TERMINAL);
        repo.setST(p.getID(), PassengerStates.EXITING_THE_ARRIVAL_TERMINAL.getState());
        repo.reportStatus();

        passengersATE++;
        this.allPassengersFinished = ((dte.getPassengersDTE() + passengersATE) == maxNumberOfPassengers);
        if(allPassengersFinished) {
            al.setFinishedFlight(true);
        }
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
        this.passengersATE--;
        if (passengersATE == 0){
            this.allPassengersFinished = false;
        }
    }

    /**
     * Setter for Departure Terminal Entrance.
     *
     * @param dte Departure Terminal Entrance object.
     */
    public void setDte(DepartureTerminalEntrance dte) {
        this.dte = dte;
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

    /**
     * Getter for number of passengers on this terminal.
     * Used by DTE to check the number of passengers here.
     * @return number of passengers on this terminal.
     */
    public int getPassengersATE() {
        return passengersATE;
    }
}
