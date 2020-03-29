package sharedRegions;

import entities.BusDriver;
import entities.Passenger;
import interfaces.DTTQBusDriver;
import interfaces.DTTQPassenger;
import states.BusDriverStates;
import states.PassengerStates;

import java.util.List;

/**
 * Implementation of the Departure Quay Shared Memory.
 * The bus driver drops off the transiting passengers here
 * and goes back to the Arrival Quay.
 */
public class DepartureQuay implements DTTQBusDriver, DTTQPassenger {

    /**
     * General Repository of Information
     * @serialField repo
     */
    Repository repo;

    /**
     * Flag that lets the passenger know it's okay to drop off the bus.
     * True if the bus is parked.
     * @serialField busHasArrived
     */
    boolean busHasArrived;          // To let passengers knows it's okay to leave the bus

    /**
     * Represents the parked bus, from where the passengers drop
     * @serialField parkedBus
     */
    List<Integer> parkedBus;

    /**
     * Constructor method for DepartureQuay
     * @param repo General Information Repository
     */
    public DepartureQuay(Repository repo){
        this.repo = repo;
        this.busHasArrived = false;
    }

    @Override
    public synchronized void parkTheBusAndLetPassOff(){
        BusDriver bd = (BusDriver) Thread.currentThread();
        bd.setBusDriverState(BusDriverStates.PARKING_AT_THE_DEPARTURE_TERMINAL);
        repo.setD_Stat(BusDriverStates.PARKING_AT_THE_DEPARTURE_TERMINAL.getState());
        repo.reportStatus();
        parkedBus = bd.getBusSeats();
        busHasArrived = true;

        try{
            while(!parkedBus.isEmpty()){
                notifyAll();
                wait();
            }
        }
        catch(InterruptedException ex){
            System.err.println("parkTheBusAndLetPassOff - Thread Interrupted");
            System.exit(1);
        }

        busHasArrived = false;
    }

    @Override
    public synchronized void goToArrivalTerminal(){
        BusDriver bd = (BusDriver) Thread.currentThread();
        bd.setBusDriverState(BusDriverStates.DRIVING_BACKWARD);
        repo.setD_Stat(BusDriverStates.DRIVING_BACKWARD.getState());
        repo.reportStatus();
        try {
            bd.sleep(10);
        }
        catch(InterruptedException ex){
            System.err.println("goToArrivalTerminal - Thread Interrupted");
        }
    }

    @Override
    public synchronized void leaveTheBus(){
        Passenger p = (Passenger) Thread.currentThread();

        try{
            while(!busHasArrived){
                wait();
            }
        }
        catch(InterruptedException ex){
            System.err.println("leaveTheBus - Thread Interrupted");
            System.exit(1);
        }
        getOffTheSeat(p.getID());
        p.setPassengerState(PassengerStates.AT_THE_DEPARTURE_TRANSFER_TERMINAL);
        repo.setST(p.getID(), PassengerStates.AT_THE_DEPARTURE_TRANSFER_TERMINAL.getState());
        repo.reportStatus();

        if(parkedBus.size() == 0){
            notifyAll();
        }
    }

    @Override
    public synchronized void getOffTheSeat(int id){
        try{
        repo.setS(parkedBus.indexOf(id), "-");
        repo.reportStatus();
        parkedBus.remove(Integer.valueOf(id));
        }
        catch(ArrayIndexOutOfBoundsException ex){
            System.out.println("Passenger id " + id);
            System.out.println(parkedBus.indexOf(id));
            System.out.println(parkedBus.toString());
            System.out.println("Passengers probably ended");
        }
    }
}
