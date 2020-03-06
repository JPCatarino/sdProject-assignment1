package sharedRegions;

import interfaces.ATTQBusDriver;
import interfaces.ATTQPassenger;

public class ArrivalQuay implements ATTQBusDriver, ATTQPassenger {

    Repository repo;

    public ArrivalQuay(Repository repo){
        this.repo = repo;
    }

    @Override
    public synchronized void announcingBusBoarding(){}

    @Override
    public synchronized void goToDepartureTerminal(){}

    @Override
    public synchronized void parkTheBus(){}

    @Override
    public synchronized void enterTheBus(){}
    
}
