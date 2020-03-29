package interfaces;

/**
 * Temporary Storage Area Porter Interface
 *
 * <p>
 *     It provides the necessary Porter methods to be implemented in Temporary Storage Area
 *     shared region.
 * </p>
 *
 * @author Fabio Alves
 * @author Jorge Catarino
 */
public interface TSAPorter {

    /**
     * Move a bag from the plane hold to the temporary storage area.
     *
     * @param bag Bag to be moved to the temporary storage area.
     */
    void carryItToAppropriateStore(int [] bag);
}
