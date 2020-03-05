package entities;

import sharedRegions.ArrivalQuay;
import sharedRegions.DepartureQuay;

import java.util.List;

public class BusDriver {

    private List<Passenger> busSeats;
    private int TTL;

    private final ArrivalQuay aq;
    private final DepartureQuay dq;

    public BusDriver(List<Passenger> busSeats, int TTL, ArrivalQuay aq, DepartureQuay dq) {
        this.busSeats = busSeats;
        this.TTL = TTL;
        this.aq = aq;
        this.dq = dq;
    }

    public boolean hasDaysWorkEnded (){
        return true;
    }

    public void run(){
        while(!hasDaysWorkEnded()){
            aq.announcingBusBoarding();
            aq.goToDepartureTerminal();
            dq.parkTheBusAndLetPassOff();
            dq.goToArrivalTerminal();
            aq.parkTheBus();
        }
    }
}
