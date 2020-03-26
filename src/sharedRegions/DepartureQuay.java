package sharedRegions;

import entities.BusDriver;
import entities.Passenger;
import interfaces.DTTQBusDriver;
import interfaces.DTTQPassenger;
import states.BusDriverStates;
import states.PassengerStates;

import java.util.List;

public class DepartureQuay implements DTTQBusDriver, DTTQPassenger {

    Repository repo;
    boolean busHasArrived;          // To let passengers knows it's okay to leave the bus
    List<Integer> parkedBus;

    public DepartureQuay(Repository repo){
        this.repo = repo;
        this.busHasArrived = false;
    }

    @Override
    public synchronized void parkTheBusAndLetPassOff(){
        BusDriver bd = (BusDriver) Thread.currentThread();
        bd.setBusDriverState(BusDriverStates.PARKING_AT_THE_DEPARTURE_TERMINAL);
        repo.setD_Stat(BusDriverStates.PARKING_AT_THE_DEPARTURE_TERMINAL.getState());
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

        if(parkedBus.size() == 0){
            notifyAll();
        }
    }

    @Override
    public synchronized void getOffTheSeat(int id){
        repo.setS(parkedBus.indexOf(id), "-");
        parkedBus.remove(Integer.valueOf(id));
    }
}
