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

    /**
     * Current state of the Porter.
     *
     *    @serialField state
     */
    private PorterStates state;

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
     *  Report if the plane hold is empty.                                      ?? descriçao para editar
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
        this.state = PorterStates.WAITING_FOR_A_PLANE_TO_LAND;
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
        this.state = PorterStates.WAITING_FOR_A_PLANE_TO_LAND;
    }

    /**
     * Set Porter state
     * @param state new state of the Porter
     */
    public void setState(PorterStates state) {
        this.state = state;
    }
}
