package entities;

import sharedRegions.ArrivalLounge;
import sharedRegions.ArrivalQuay;
import sharedRegions.DepartureQuay;
import states.BusDriverStates;

import java.util.ArrayList;
import java.util.List;

/**
 * Bus Driver thread and life cycle.
 * It stores all relevant information about the BusDriver.
 * @author Fábio Alves
 * @author Jorge Catarino
 */
public class BusDriver extends Thread {

    private BusDriverStates state;

    private List<Integer> busSeats;
    private int TTL;

    private final ArrivalQuay aq;
    private final DepartureQuay dq;
    private final ArrivalLounge al;


    /**
     * BusDriver Constructor.
     * It initiates a new BusDriver thread.
     * @param TTL Time the bus driver waits before leaving the Arrival Quay.
     * @param aq  Arrival Quay Shared Region
     * @param dq  Departure Quay Shared Region
     */
    public BusDriver(int TTL, ArrivalQuay aq, DepartureQuay dq, ArrivalLounge al) {
        this.busSeats = new ArrayList<>();
        this.TTL = TTL;
        this.aq = aq;
        this.dq = dq;
        this.al = al;
        this.state = BusDriverStates.PARKING_AT_THE_ARRIVAL_TERMINAL;
    }

    /**
     * BusDriver lifecycle
     * <p>
     * While he is on is shift, the BusDriver follows a routine: <p>
     * First he announces the bus is available to board.<p>
     * When it's time to leave, he drives to the departure terminal.<p>
     * After parking he let's the passengers off and drives back to the Arrival Terminal.<p>
     * If his shift hasn't ended by then, he repeats.
     *
     */
    @Override
    public void run(){
        while(!aq.hasDaysWorkEnded()){
            aq.announcingBusBoarding();
            aq.goToDepartureTerminal();
            dq.parkTheBusAndLetPassOff();
            dq.goToArrivalTerminal();
            aq.parkTheBus();
        }
    }

    public List<Integer> getBusSeats() {
        return busSeats;
    }

    public void setBusSeats(List<Integer> busSeats){
        this.busSeats = busSeats;
    }

    /**
     * Set Bus Driver state
     * @param state new state of the Bus Driver
     */
    public void setBusDriverState(BusDriverStates state){
        this.state = state;
    }

    public int getTTL() {
        return TTL;
    }
}
