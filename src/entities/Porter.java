package entities;

import sharedRegions.ArrivalLounge;
import sharedRegions.BagColPoint;
import sharedRegions.TempStgArea;
import states.PorterStates;

/**
 * Porter thread and life cycle.
 * It stores all relevant information about the Porter.
 * @author Fábio Alves
 * @author Jorge Catarino
 */
public class Porter extends Thread {

    private final static int TRANSIT = 0,
            END_OF_STATE = 1;

    /**
     *  Arrival Lounge.
     *
     *    @serialField al
     */
    private ArrivalLounge al;

    /**
     *  Baggage collection point.
     *
     *    @serialField bcp
     */
    private BagColPoint bcp;

    /**
     *  Temporary Storage Area.
     *
     *    @serialField tsa
     */
    private TempStgArea tsa;

    /**
     *  Bag being carried from the plane hold to the baggage collection point or to the temporary storage area.
     *
     *    @serialField bag
     */
    private Object[] bag;

    /**
     *  Report if the plane hold is empty.
     *
     *    @serialField planeHoldEmpty
     */
    private boolean planeHoldEmpty;

    /**
     * Porter Constructor.
     * It initiates a new BusDriver Porter.
     * @param al Arrival Lounge.
     * @param bcp Baggage collection point.
     * @param tsa Temporary Storage Area.
     */
    public Porter(ArrivalLounge al, BagColPoint bcp, TempStgArea tsa) {
        this.al = al;
        this.bcp = bcp;
        this.tsa = tsa;
        bag = new Object[2];
    }

    /**
     *  Porter life cycle.
     */
    @Override
    public void run() {

        while (al.takeARest() != 'E') {
            planeHoldEmpty = false;
            while (!planeHoldEmpty) {
                bag = al.tryToCollectABag();
                if (bag == null) {
                    planeHoldEmpty = true;
                    noMoreBagsToCollect();
                } else if ((char) bag[1] == 'T') {
                    tsa.carryItToAppropriateStore(bag);
                } else {
                    bcp.carryItToAppropriateStore(bag);
                }
            }
            al.noMoreBagsToCollect();
        }
    }

    /**
     *  If there are noo more bags to collect set the Porter state to WAITING_FOR_A_PLANE_TO_LAND.
     */
    private void noMoreBagsToCollect() {
        bcp.setNoMoreBags(true);
    }
}
