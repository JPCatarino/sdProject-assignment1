package sharedRegions;

import interfaces.ALPassenger;
import interfaces.ALPorter;

public class ArrivalLounge implements ALPassenger, ALPorter {
    Repository repo;

    public void ArrivalLounge(Repository repo){
        this.repo = repo;
    }

    @Override
    public synchronized void goCollectABag(){}

    @Override
    public synchronized void takeABus(){}

    @Override
    public synchronized void takeARest(){}

    @Override
    public synchronized void tryToCollectABag(){}

    @Override
    public synchronized void goHome(){};
}
