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
     *
     *
     */
    public void carryItToAppropriateStore(Object [] bag);
}
