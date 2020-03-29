package interfaces;

/**
 * Baggage Reclaim Office Passenger Interface
 *
 * <p>
 *     It provides the necessary Passenger methods to be implemented in Baggage Reclaim Office
 *     shared region.
 * </p>
 *
 * @author Fabio Alves
 * @author Jorge Catarino
 */
public interface BROPassenger {

    /**
     *  Simulates a passenger reporting a missing bag.
     *  It puts the Passenger to sleep for some milliseconds before transitioning to the next state.
     */
    void reportMissingBags();
}
