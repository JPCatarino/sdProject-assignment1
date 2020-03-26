package entities;

import sharedRegions.*;
import states.PassengerDecisions;
import states.PassengerStates;


/**
 * Passenger thread and life cycle.
 * It stores all relevant information about the Passenger.
 * @author Fábio Alves
 * @author Jorge Catarino
 */
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

    /**
     * Passenger Constructor.
     * It initiates a passenger thread.
     * @param id Passenger Identifier.
     * @param nBagsToCollect Number of Bags the Passenger has to collect.
     * @param journeyEnding  True if Passenger journey is ending.
     * @param al  Arrival Lounge Shared Region
     * @param bcp Baggage Collection Point Shared Region
     * @param bro Baggage Reclaim Office Shared Region
     * @param aq  Arrival Quay Shared Region
     * @param dq  Departure Quay Shared Region
     * @param dte Departure Terminal Exit Shared Region
     * @param ate Arrival Terminal Exit Shared Region
     */
    public Passenger(int id, int nBagsToCollect, boolean journeyEnding, ArrivalLounge al, BagColPoint bcp, BagRecOffice bro, ArrivalQuay aq, DepartureQuay dq, DepartureTerminalEntrance dte, ArrivalTerminalExit ate) {
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

    /**
     * Passenger Life cycle.
     * <p>
     * When the passenger arrives to arrival lounge, he ask himself what to do.
     * If his journey has ended and he has no bags, he'll go home.
     * If he has bags, he goes to collect them.
     * In case his journey hasn't ended, he goes to catch a bus.
     * </p>
     */
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
