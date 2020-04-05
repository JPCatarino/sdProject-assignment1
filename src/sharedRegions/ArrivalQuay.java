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
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Implementation of the Arrival Quay Shared Memory
 * The BusDriver waits here for passengers to come
 * so he can drive them to departure terminal.
 *
 * @author Fábio Alves
 * @author Jorge Catarino
 */
public class ArrivalQuay implements ATTQBusDriver, ATTQPassenger {

    /**
     * Information Repository.
     *
     * @serialField repo
     */
    private Repository repo;

    /**
     * Bus Queue, where the passengers reside while waiting to board.
     *
     * @serialField  busWaitingLine
     */
    private ArrayBlockingQueue<Integer> busWaitingLine;

    /**
     * Represents a parked bus, which the passengers board.
     *
     * @serialField parkedBus
     */
    private List<Integer> parkedBus;

    /**
     * Tells the passengers that it's okay to board the bus.
     *
     * @serialField boardingTheBus
     */
    private boolean boardingTheBus;

    /**
     * Dictates the capacity of the bus.
     *
     * @serialField maxNumberOfSeats
     */
    private int maxNumberOfSeats;

    /**
     * Shared Memory ArrivalLounge that has the latest information on the flights.
     *
     * @serialField al
     */
    private ArrivalLounge al;

    /**
     * Arrival Quay Constructor.
     *
     * @param repo General Repository of information.
     * @param T_SEATS Maximum capacity of the bus.
     * @param al Arrival Lounge Shared Memory.
     */
    public ArrivalQuay(Repository repo, int T_SEATS, ArrivalLounge al){
        this.repo = repo;
        this.boardingTheBus = false;
        this.parkedBus = new ArrayList<>();
        this.busWaitingLine = new ArrayBlockingQueue<>(6);
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

    @Override
    public synchronized void goToDepartureTerminal(){
        BusDriver bd = (BusDriver) Thread.currentThread();
        bd.setBusSeats(parkedBus);
        bd.setBusDriverState(BusDriverStates.DRIVING_FORWARD);
        repo.setD_Stat(BusDriverStates.DRIVING_FORWARD.getState());
        repo.reportStatus();

        try {
            Thread.sleep(10);
        }
        catch(InterruptedException ex){
            System.err.println("goToDepartureTerminal - Thread Interrupted");
        }
    }

    @Override
    public synchronized void parkTheBus(){
        BusDriver bd = (BusDriver) Thread.currentThread();
        this.parkedBus = new ArrayList<>();
        bd.setBusDriverState(BusDriverStates.PARKING_AT_THE_ARRIVAL_TERMINAL);
        repo.setD_Stat(BusDriverStates.PARKING_AT_THE_ARRIVAL_TERMINAL.getState());
        repo.reportStatus();
        try {
            Thread.sleep(500);
        }
        catch(InterruptedException ex){
            System.err.println("goToArrivalTerminal - Thread Interrupted");
        }
    }

    @Override
    public void enterTheBus(){
        Passenger p = (Passenger) Thread.currentThread();
        boolean notOnBoard = true;

        synchronized (this) {
            busWaitingLine.add(p.getID());
            repo.setQIn(busWaitingLine.size() - 1, String.valueOf(p.getID()));
            repo.reportStatus();
        }
        while(notOnBoard) {
            synchronized (this) {
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
            }

            synchronized (this) {
                try {
                    if (busWaitingLine.peek() == p.getID() && parkedBus.size() != maxNumberOfSeats) {
                        sitOnTheBus();
                        notOnBoard = false;
                    } else {
                        synchronized (this) {
                            try {
                                notifyAll();
                                wait();
                            } catch (InterruptedException ex) {
                                System.err.println("Enter the bus- thread was interrupted");
                            }
                        }
                    }
                }
                catch(NullPointerException ignored){
                }
            }
        }

        // TODO check this function
        synchronized (this) {
            p.setPassengerState(PassengerStates.TERMINAL_TRANSFER);
            repo.setST(p.getID(), PassengerStates.TERMINAL_TRANSFER.getState());
            repo.reportStatus();
            if (busWaitingLine.size() == 0 || parkedBus.size() == maxNumberOfSeats) {
                notifyAll();
            }
        }
    }

    @Override
    public synchronized void sitOnTheBus(){
        Passenger p = (Passenger) Thread.currentThread();
        if(parkedBus.size() < maxNumberOfSeats){
            repo.setQOut();
            busWaitingLine.remove();
            parkedBus.add(p.getID());
            p.setBusSeat(parkedBus.indexOf(p.getID()));
            repo.setS(p.getBusSeat(), String.valueOf(p.getID()));
            repo.reportStatus();
        }
    }
}
