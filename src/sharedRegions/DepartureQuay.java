package sharedRegions;

import interfaces.DTTQBusDriver;
import interfaces.DTTQPassenger;

public class DepartureQuay implements DTTQBusDriver, DTTQPassenger {

    @Override
    public synchronized void parkTheBusAndLetPassOff(){}

    @Override
    public synchronized void goToArrivalTerminal(){}

    @Override
    public synchronized void leaveTheBus(){}
}
