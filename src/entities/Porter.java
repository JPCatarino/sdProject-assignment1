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
    private int[] bag;

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
        bag = new int[2];
    }

    /**
     *  Porter life cycle.
     */
    @Override
    public void run() {

        while (al.takeARest() != END_OF_STATE) {
            System.out.print("O");
            planeHoldEmpty = false;
            while (!planeHoldEmpty) {
                bag = al.tryToCollectABag();
                System.out.print("M");
                if (bag == null) {
                    System.out.print("N");
                    planeHoldEmpty = true;
                    noMoreBagsToCollect();
                    System.out.print("N1");
                } else if ( bag[1] == TRANSIT) {
                    System.out.print("T");
                    tsa.carryItToAppropriateStore(bag);
                    System.out.print("T1");
                } else {
                    System.out.print("B");
                    bcp.carryItToAppropriateStore(bag);
                    System.out.print("B1");

                }
            }
            System.out.print("E");

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
