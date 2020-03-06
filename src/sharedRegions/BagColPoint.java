package sharedRegions;

import interfaces.BCPPassenger;
import interfaces.BCPPorter;

public class BagColPoint implements BCPPassenger, BCPPorter {

    Repository repo;

    public BagColPoint(Repository repo){
        this.repo = repo;
    }

    @Override
    public synchronized void goCollectABag (){};

    @Override
    public synchronized void reportMissingBags(){};

    @Override
    public synchronized void goHome(){};

    @Override
    public synchronized void carryItToAppropriateStore(){};
}
