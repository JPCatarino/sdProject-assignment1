package entities;

import sharedRegions.ArrivalQuay;
import sharedRegions.DepartureQuay;
import states.BusDriverStates;

import java.util.ArrayList;
import java.util.List;

/**
 * Bus Driver thread and life cycle.
 * It stores all relevant information about the BusDriver.
 *
 * @author Fábio Alves
 * @author Jorge Catarino
 */
public class BusDriver extends Thread {

    /**
     * Bus Driver current state.
     *
     * @serialField state
     */
    private BusDriverStates state;

    /**
     * List of bus seats.
     *
     * @serialField busSeats
     */
    private List<Integer> busSeats;

    /**
     * The time that the bus driver wait until do the journey.
     *
     * @serialField TTL
     */
    private int TTL;

    /**
     * ArrivalQuay Shared Memory.
     *
     * @serialField aq
     */
    private final ArrivalQuay aq;

    /**
     * DepartureQuay Shared Memory.
     *
     * @serialField dq
     */
    private final DepartureQuay dq;

    /**
     * BusDriver Constructor.
     * It initiates a new BusDriver thread.
     *
     * @param TTL Time the bus driver waits before leaving the Arrival Quay.
     * @param aq  Arrival Quay Shared Region.
     * @param dq  Departure Quay Shared Region.
     */
    public BusDriver(int TTL, ArrivalQuay aq, DepartureQuay dq) {
        this.busSeats = new ArrayList<>();
        this.TTL = TTL;
        this.aq = aq;
        this.dq = dq;
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

    /**
     * Get Bus Seats.
     *
     * @return The list of bus seats.
     */
    public List<Integer> getBusSeats() {
        return busSeats;
    }

    /**
     * Set Bus Seats.
     *
     * @param busSeats New list of bus seats.
     */
    public void setBusSeats(List<Integer> busSeats){
        this.busSeats = busSeats;
    }

    /**
     * Set Bus Driver state.
     *
     * @param state New state of the Bus Driver.
     */
    public void setBusDriverState(BusDriverStates state){
        this.state = state;
    }

    /**
     * Get time to leave.
     *
     * @return The time that the bus driver wait until do the journey.
     */
    public int getTTL() {
        return TTL;
    }
}
