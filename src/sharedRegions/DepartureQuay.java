package sharedRegions;

import entities.BusDriver;
import interfaces.DTTQBusDriver;
import interfaces.DTTQPassenger;
import states.BusDriverStates;

public class DepartureQuay implements DTTQBusDriver, DTTQPassenger {

    Repository repo;
    boolean busHasArrived;          // To let passengers knows it's okay to leave the bus

    public DepartureQuay(Repository repo){
        this.repo = repo;
        this.busHasArrived = false;
    }

    @Override
    public synchronized void parkTheBusAndLetPassOff(){
        BusDriver bd = (BusDriver) Thread.currentThread();
        bd.setBusDriverState(BusDriverStates.PARKING_AT_THE_DEPARTURE_TERMINAL);

        busHasArrived = true;

        try{
            while(!bd.getBusSeats().isEmpty()){
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
    }

    @Override
    public synchronized void leaveTheBus(){}
}
