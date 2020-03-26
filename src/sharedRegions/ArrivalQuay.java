package sharedRegions;

import entities.BusDriver;
import entities.Passenger;
import interfaces.ATTQBusDriver;
import interfaces.ATTQPassenger;
import states.BusDriverStates;
import states.PassengerStates;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class ArrivalQuay implements ATTQBusDriver, ATTQPassenger {

    Repository repo;
    Queue<Integer> busWaitingLine;
    List<Integer> parkedBus;
    int timeForDeparture;
    boolean boardingTheBus;             // To let passengers know it's okay to board the bus

    public ArrivalQuay(Repository repo){
        this.repo = repo;
        boardingTheBus = false;
        parkedBus = new ArrayList<>();
    }

    /**
     * Announcing the boarding, the Bus Driver is letting the Passenger in the queue know it's okay
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
                wait(bd.getTTL());                                                     // Block while passengers enter bus;
            }
        }
        catch(InterruptedException iex){
            System.err.println("announcingBusBoarding - Thread was interrupted.");
            System.exit(1);
        }
        boardingTheBus = false;
    }

    /**
     * After boarding all passangers, the bus driver then drives to the DepartureTerminal
     * This function changes state to DRIVING_FORWARD and unparks the bus.
     */
    @Override
    public synchronized void goToDepartureTerminal(){
        BusDriver bd = (BusDriver) Thread.currentThread();
        bd.setBusSeats(parkedBus);
        bd.setBusDriverState(BusDriverStates.DRIVING_FORWARD);      // TODO Add function to update REPO
    }

    /**
     * Parks the bus after returning from Departure Terminal
     * It assumes the Bus comes back empty from the terminal.
     */
    @Override
    public synchronized void parkTheBus(){
        BusDriver bd = (BusDriver) Thread.currentThread();
        this.parkedBus = new ArrayList<>();
        bd.setBusDriverState(BusDriverStates.PARKING_AT_THE_ARRIVAL_TERMINAL);      // TODO Add function to update REPO
    }

    /**
     * Simulates the entrance of a passenger on the bus.
     * The passenger gets in the queue and waits orders from the BusDriver to board.
     */
    @Override
    public synchronized void enterTheBus(){
        Passenger p = (Passenger) Thread.currentThread();

        busWaitingLine.add(p.getID());

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

        sitOnTheBus(p.getID());

        p.setPassengerState(PassengerStates.TERMINAL_TRANSFER);


        if(busWaitingLine.size() == 0){
            notifyAll();
        }
    }

    @Override
    public synchronized void sitOnTheBus(int id){
        if(parkedBus.size() < repo.getT_SEATS()){
            busWaitingLine.remove();
            parkedBus.add(id);
        }
    }
    
}
