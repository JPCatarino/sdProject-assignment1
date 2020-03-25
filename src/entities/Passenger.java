package entities;

import sharedRegions.*;
import states.PassengerDecisions;
import states.PassengerStates;

public class Passenger extends Thread {

    /**
     * Passengers's id
     * @serialField id
     */
    private int id; // Same as bag id?

    /**
     * Passenger's current state
     * @serialField PassengerStates
     */
    private PassengerStates state;

    private int nBagsToCollect;

    private int nBagsCollected;

    private boolean journeyEnding;


    private ArrivalLounge al;
    private BagColPoint bcp;
    private BagRecOffice bro;
    private ArrivalQuay aq;
    private DepartureQuay dq;
    private DepartureTerminalEntrance dte;
    private ArrivalTerminalExit ate;

    public Passenger(int id, int nBagsToCollect, int nBagsCollected, boolean journeyEnding, ArrivalLounge al, BagColPoint bcp, BagRecOffice bro, ArrivalQuay aq, DepartureQuay dq, DepartureTerminalEntrance dte, ArrivalTerminalExit ate) {
        this.id = id;
        this.nBagsToCollect = nBagsToCollect;
        this.nBagsCollected = 0;
        this.journeyEnding = journeyEnding;
        this.state = PassengerStates.AT_THE_DISEMBARKING_ZONE;
        this.al = al;
        this.bcp = bcp;
        this.bro = bro;
        this.aq = aq;
        this.dq = dq;
        this.dte = dte;
        this.ate = ate;
    }

    @Override
    public void run(){
        switch(al.whatShouldIDo()){
            case GO_HOME:
                ate.goHome();
            case COLLECT_A_BAG:
                bcp.goCollectABag();
                if(nBagsCollected != nBagsToCollect)
                    bro.reportMissingBags();
                ate.goHome();
            case TAKE_A_BUS:
                al.takeABus();
                aq.enterTheBus();
                dq.leaveTheBus();
                dte.prepareNextLeg();
        }
    }



    public int getID() {
        return id;
    }

    public void collectedABag(){
        this.nBagsCollected++;
    }

    public int getnBagsToCollect() {
        return nBagsToCollect;
    }

    public boolean isJourneyEnding() {
        return journeyEnding;
    }

    /**
     * Set Passenger state
     * @param state new state of the Passenger
     */
    public void setPassengerState(PassengerStates state) {
        this.state = state;
    }
}
