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
     *
     */
    public char takeARest();

    /**
     *
     */
    public Object[] tryToCollectABag();

    /**
     *
     */
    public void noMoreBagsToCollect();
}

