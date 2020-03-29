package entities;

import sharedRegions.ArrivalLounge;
import sharedRegions.BagColPoint;
import sharedRegions.TempStgArea;
import states.PorterStates;

import java.util.Arrays;

/**
 * Porter thread and life cycle.
 * It stores all relevant information about the Porter.
 * @author Fábio Alves
 * @author Jorge Catarino
 */
public class Porter extends Thread {

    /**
     *  Constant that characterize the state of the piece of luggage.
     */
    private final static int TRANSIT = 0;

    /**
     *  Arrival Lounge.
     *
     *  @serialField al
     */
    private ArrivalLounge al;

    /**
     *  Baggage collection point.
     *
     *  @serialField bcp
     */
    private BagColPoint bcp;

    /**
     *  Temporary Storage Area.
     *
     *  @serialField tsa
     */
    private TempStgArea tsa;

    /**
     *  Bag being carried from the plane hold to the baggage collection point or to the temporary storage area.
     *
     *  @serialField bag
     */
    private int[] bag;

    /**
     *  Report if the plane hold is empty.
     *
     *  @serialField planeHoldEmpty
     */
    private boolean planeHoldEmpty;

    /**
     * Porter Constructor.
     * It initiates a new BusDriver Porter.
     *
     * @param al Arrival Lounge.
     * @param bcp Baggage collection point.
     * @param tsa Temporary Storage Area.
     */
    public Porter(ArrivalLounge al, BagColPoint bcp, TempStgArea tsa) {
        this.al = al;
        this.bcp = bcp;
        this.tsa = tsa;
        bag = new int[2];
    }

    /**
     *  Porter life cycle.
     * <p>
     * When the last passenger to reach the arrival lounge do the operation what should i do.
     * If there are bags in the plane hold, carry it to the appropriate store.
     * Else the porter wait until there are bags in the plane hold.
     * If he already do all the operations in all the flights he can die.
     * </p>
     */
    @Override
    public void run() {

        while (al.takeARest()) {
            planeHoldEmpty = false;
            while (!planeHoldEmpty) {
                bag = al.tryToCollectABag();
                if (bag == null) {
                    planeHoldEmpty = true;
                    noMoreBagsToCollect();
                } else if ( bag[1] == TRANSIT) {
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

    /**
     *  Verify if there are bags in the plane hold.
     *
     *  @return True, if the Plane Hold is empty. False, if the Plane Hold is not empty.
     */
    public boolean isPlaneHoldEmpty() {
        return planeHoldEmpty;
    }

    /**
     *  Set the value of the plane hold.
     *
     *  @param planeHoldEmpty True, if the Plane Hold is empty or to false, if the Plane Hold is not empty.
     */
    public void setPlaneHoldEmpty(boolean planeHoldEmpty) {
        this.planeHoldEmpty = planeHoldEmpty;
    }
}
