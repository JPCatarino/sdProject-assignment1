package interfaces;

/**
 * Baggage Collection Point Passenger Interface
 *
 * <p>
 *     It provides the necessary Passenger methods to be implemented in Baggage Collection Point
 *     shared region.
 * </p>
 *
 * @author Fabio Alves
 * @author Jorge Catarino
 */
public interface BCPPassenger {

    /**
     * Passenger goes to Baggage Collection Point trying to collect it's bags.
     * It waits for the Porter to put the bag on the conveyor belt.
     * If it's one of their bags, they go try to collect it.
     * A passenger can only collect one bag per attempt.
     */
    void goCollectABag ();
}
