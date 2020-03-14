package entities;

import sharedRegions.ArrivalQuay;
import sharedRegions.DepartureQuay;
import states.BusDriverStates;

import java.util.List;

public class BusDriver extends Thread {

    private BusDriverStates state;

    private List<Integer> busSeats;
    private int TTL;

    private final ArrivalQuay aq;
    private final DepartureQuay dq;

    public BusDriver(List<Integer> busSeats, int TTL, ArrivalQuay aq, DepartureQuay dq) {
        this.busSeats = busSeats;
        this.TTL = TTL;
        this.aq = aq;
        this.dq = dq;
        this.state = BusDriverStates.PARKING_AT_THE_ARRIVAL_TERMINAL;
    }

    public boolean hasDaysWorkEnded (){
        return true;
    }

    @Override
    public void run(){
        while(!hasDaysWorkEnded()){
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
}
