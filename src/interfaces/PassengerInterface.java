package interfaces;

/**
 * Interface for the Passenger entity.
 * This interface contains the signatures of the functions to be instantiated on
 * shared memory regions.
 *
 * @author Jorge Catarino
 * @author Fabio Alves
 */

public interface PassengerInterface {

    /**
     * whatShouldIDo
     */
    public void whatShouldIDo ();

    /**
     * goCollectABag
     */
    public void goCollectABag ();

    /**
     * reportMissingBags
     */
    public void reportMissingBags ();

    /**
     * goHome
     */
    public void goHome ();

    /**
     * takeABus
     */
    public void takeABus ();

    /**
     * enterTheBus
     */
    public void enterTheBus ();

    /**
     * leaveTheBus
     */
    public void leaveTheBus ();

    /**
     * prepareNextLeg
     */
    public void prepareNextLeg ();
}
