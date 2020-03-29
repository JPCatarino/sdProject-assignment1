package interfaces;

/**
 * Arrival Lounge Porter Interface
 *
 * <p>
 *     It provides the necessary Porter methods to be implemented in Arrival Lounger
 *     shared region.
 * </p>
 *
 * @author Fabio Alves
 * @author Jorge Catarino
 */
public interface ALPorter {

    /**
     * Wait until the last passenger to reach the arrival lounge do the operation what should i do.
     * After carry all the bags for the appropriate store wait until the operation mentioned before from the next flight.
     * If all the operations where done to all the flights, he finish running.
     *
     * @return False if all the operations where done on all the flights. Otherwise True.
     */
    boolean takeARest();

    /**
     * Go get a bag to the plane hold.
     *
     * @return The bag collected from the plane hold.
     */
    int[] tryToCollectABag();

    /**
     * Report the status and update variable that wake the porter.
     */
    void noMoreBagsToCollect();
}

