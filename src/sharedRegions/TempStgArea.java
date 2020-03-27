package sharedRegions;

import interfaces.TSAPorter;

public class TempStgArea implements TSAPorter {

    Repository repo;
    private List<int[]> storeroom;

    public TempStgArea(Repository repo){
        this.repo = repo;
    }

    @Override
    public synchronized void carryItToAppropriateStore(int [] bag) {

        this.storeroom.add(bag);

        repo.setP_Stat(PorterStates.AT_THE_STOREROOM.getState());
        repo.setSR(storeroom.size());
        repo.toString_debug();
        repo.reportStatus();
    }
}
