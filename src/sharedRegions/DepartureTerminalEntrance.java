package sharedRegions;

import interfaces.DTEPassenger;

public class DepartureTerminalEntrance implements DTEPassenger {

    Repository repo;

    public DepartureTerminalEntrance(Repository repo){
        this.repo = repo;
    }

    @Override
    public synchronized void prepareNextLeg() {

    }
}
