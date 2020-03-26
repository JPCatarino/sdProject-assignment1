package interfaces;

/**
 * Baggage Collection Point Porter Interface
 *
 * <p>
 *     It provides the necessary Porter methods to be implemented in Baggage Collection Point
 *     shared region.
 * </p>
 *
 * @author Fabio Alves
 * @author Jorge Catarino
 */

public interface BCPPorter {
    /**
     * Move a bag from the plane hold to the bag collection point.
     * @param bag Bag to be moved to the bag collection point.
     */
    public void carryItToAppropriateStore(Object [] bag);

}
