package sharedRegions;

import entities.BusDriver;
import entities.Passenger;
import interfaces.ATTQBusDriver;
import interfaces.ATTQPassenger;
import states.BusDriverStates;

import java.util.Queue;

public class ArrivalQuay implements ATTQBusDriver, ATTQPassenger {

    Repository repo;
    Queue<Integer> busWaitingLine;
    int timeForDeparture;
    boolean boardingTheBus;             // To let passengers know it's okay to board the bus

    public ArrivalQuay(Repository repo){
        this.repo = repo;
        boardingTheBus = false;
    }

    /**
     * Annoucing the boarding, the Bus Driver is letting the Passenger in the queue know it's okay
     * to get inside the bus.
     */
    @Override
    public synchronized void announcingBusBoarding(){
        BusDriver bd = (BusDriver)Thread.currentThread();
        try {
            while (busWaitingLine.size() != repo.getT_SEATS()) {            // TODO: Add departure time as a condition
                wait();
            }

            boardingTheBus = true;

            while(!busWaitingLine.isEmpty()){
                notifyAll();                                                // Notify passengers for them to enter the bus.
                wait();                                                     // Block while passengers enter bus;
            }
        }
        catch(InterruptedException iex){
            System.err.println("announcingBusBoarding - Thread was interrupted.");
            System.exit(1);
        }
        boardingTheBus = false;
    }

    @Override
    public synchronized void goToDepartureTerminal(){
        BusDriver bd = (BusDriver) Thread.currentThread();
        bd.setBusDriverState(BusDriverStates.DRIVING_FORWARD);      // TODO Add function to update REPO
    }

    @Override
    public synchronized void parkTheBus(){
        BusDriver bd = (BusDriver) Thread.currentThread();
        bd.setBusDriverState(BusDriverStates.PARKING_AT_THE_ARRIVAL_TERMINAL);      // TODO Add function to update REPO
    }

    @Override
    public synchronized void enterTheBus(){
        Passenger p = (Passenger) Thread.currentThread();
        if(busWaitingLine.size() == repo.getT_SEATS()){
            notifyAll();
        }

        try {
            while (!boardingTheBus) {
                wait();
            }
        }
        catch(InterruptedException ex){
            System.err.println("enterTheBus - Thread was interrupted");
            System.exit(1);
        }

        busWaitingLine.remove();
        sitOnTheBus(p.getID());

        if(busWaitingLine.size() == 0){
            notifyAll();
        }
    }

    @Override
    public synchronized void sitOnTheBus(int id){
        BusDriver bd = (BusDriver) Thread.currentThread();
        if(bd.getBusSeats().size() < repo.getT_SEATS()){
            bd.addPassengerToBus(id);
        }
    }
    
}
