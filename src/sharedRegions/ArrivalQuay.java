package sharedRegions;

import entities.BusDriver;
import entities.Passenger;
import interfaces.ATTQBusDriver;
import interfaces.ATTQPassenger;
import states.BusDriverStates;
import states.PassengerStates;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ArrivalQuay implements ATTQBusDriver, ATTQPassenger {

    Repository repo;
    Queue<Integer> busWaitingLine;
    List<Integer> parkedBus;
    boolean boardingTheBus;             // To let passengers know it's okay to board the bus
    int maxNumberOfSeats;

    ArrivalLounge al;

    public ArrivalQuay(Repository repo, int T_SEATS, ArrivalLounge al){
        this.repo = repo;
        this.boardingTheBus = false;
        this.parkedBus = new ArrayList<>();
        this.busWaitingLine = new LinkedList<>();
        this.maxNumberOfSeats = T_SEATS;
        this.al = al;
    }

    @Override
    public synchronized boolean hasDaysWorkEnded(){
        BusDriver bd = (BusDriver)Thread.currentThread();

        try {
            while (((busWaitingLine.size() != maxNumberOfSeats) && busWaitingLine.isEmpty()) && !al.isDayFinished()) {
                wait(bd.getTTL());                                          // Block while passengers enter queue
            }
        }
        catch(InterruptedException ex){
            System.out.println("hasDaysWorkEnded - Interrupted Thread");
        }

        return al.isDayFinished();
    }

    /**
     * Announcing the boarding, the Bus Driver is letting the Passenger in the queue know it's okay
     * to get inside the bus.
     */
    @Override
    public synchronized void announcingBusBoarding(){
        BusDriver bd = (BusDriver)Thread.currentThread();
        try {
            boardingTheBus = true;

            while(!busWaitingLine.isEmpty() && parkedBus.size() < maxNumberOfSeats){
                notifyAll();                                                // Notify passengers for them to enter the bus.
                wait();
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
        bd.setBusDriverState(BusDriverStates.DRIVING_FORWARD);
        repo.setD_Stat(BusDriverStates.DRIVING_FORWARD.getState());
        repo.reportStatus();

        try {
            bd.sleep(10);
        }
        catch(InterruptedException ex){
            System.err.println("goToDepartureTerminal - Thread Interrupted");
        }
    }

    /**
     * Parks the bus after returning from Departure Terminal
     * It assumes the Bus comes back empty from the terminal.
     */
    @Override
    public synchronized void parkTheBus(){
        BusDriver bd = (BusDriver) Thread.currentThread();
        this.parkedBus = new ArrayList<>();
        bd.setBusDriverState(BusDriverStates.PARKING_AT_THE_ARRIVAL_TERMINAL);
        repo.setD_Stat(BusDriverStates.PARKING_AT_THE_ARRIVAL_TERMINAL.getState());
        repo.reportStatus();
        try {
            bd.sleep(500);
        }
        catch(InterruptedException ex){
            System.err.println("goToArrivalTerminal - Thread Interrupted");
        }
    }

    /**
     * Simulates the entrance of a passenger on the bus.
     * The passenger gets in the queue and waits orders from the BusDriver to board.
     */
    @Override
    public synchronized void enterTheBus(){
        Passenger p = (Passenger) Thread.currentThread();
        boolean notOnBoard = true;

        busWaitingLine.add(p.getID());
        repo.setQIn(busWaitingLine.size()-1,String.valueOf(p.getID()));
        repo.reportStatus();

        while(notOnBoard) {

            if (busWaitingLine.size() == maxNumberOfSeats) {
                notifyAll();
            }
            try {
                while (!boardingTheBus) {
                    wait();
                }
            } catch (InterruptedException ex) {
                System.err.println("enterTheBus - Thread was interrupted");
                System.exit(1);
            }

            if (busWaitingLine.peek() == p.getID() && parkedBus.size() != maxNumberOfSeats) {
                sitOnTheBus(p.getID());
                notOnBoard = false;
            } else {
                try {
                    notifyAll();
                    wait();
                } catch (InterruptedException ex) {
                    System.err.println("Enter the bus- thread was interrupted");
                }
            }
        }

        p.setPassengerState(PassengerStates.TERMINAL_TRANSFER);
        repo.setST(p.getID(), PassengerStates.TERMINAL_TRANSFER.getState());
        repo.reportStatus();

        if(busWaitingLine.size() == 0 || parkedBus.size() == maxNumberOfSeats){
            notifyAll();
        }
    }

    @Override
    public synchronized void sitOnTheBus(int id){

        if(parkedBus.size() < maxNumberOfSeats){
            repo.setQOut();
            busWaitingLine.remove();
            parkedBus.add(id);
            repo.setS(parkedBus.indexOf(id), String.valueOf(id));
            repo.reportStatus();
        }
    }
    
}
