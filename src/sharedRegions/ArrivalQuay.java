package sharedRegions;

import entities.BusDriver;
import entities.Passenger;
import interfaces.ATTQBusDriver;
import interfaces.ATTQPassenger;

import java.util.Queue;

public class ArrivalQuay implements ATTQBusDriver, ATTQPassenger {

    Repository repo;
    Queue<Passenger> busWaitingLine;
    int timeForDeparture;

    public ArrivalQuay(Repository repo){
        this.repo = repo;
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

            while(!busWaitingLine.isEmpty()){
                notifyAll();                                                // Notify passengers for them to enter the bus.
                wait();                                                     // Block while passengers enter bus;
            }
        }
        catch(InterruptedException iex){
            System.err.println("announcingBusBoarding - Thread was interrupted.");
        }
    }

    @Override
    public synchronized void goToDepartureTerminal(){}

    @Override
    public synchronized void parkTheBus(){}

    @Override
    public synchronized void enterTheBus(){}
    
}
