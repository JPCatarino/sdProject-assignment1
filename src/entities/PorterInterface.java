package entities;

/**
 * Interface for the Porter entity.
 * This interface contains the signatures of the functions to be instantiated on
 * shared memory regions.
 *
 * @author Jorge Catarino
 * @author Fabio Alves
 */

public interface PorterInterface {

    /**
     * TakeARest
     */
    public void takeARest ();

    /**
     * tryToCollectABag
     */
    public void tryToCollectABag ();

    /**
     * noMoreBagsToCollect
     */
    public void noMoreBagsToCollect ();

    /**
     * carryItToAppropriateStore
     */
    public void carryItToAppropriateStore ();

}
