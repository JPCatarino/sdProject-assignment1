package sharedRegions;

import interfaces.TSAPorter;

public class TempStgArea implements TSAPorter {

    Repository repo;

    public TempStgArea(Repository repo){
        this.repo = repo;
    }

    @Override
    public void carryItToAppropriateStore() {
    }
}
