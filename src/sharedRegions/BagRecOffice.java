package sharedRegions;

import interfaces.BROPassenger;

public class BagRecOffice implements BROPassenger {

    Repository repo;

    public BagRecOffice(Repository repo){
        this.repo = repo;
    }

    @Override
    public synchronized void goHome(){};
}
