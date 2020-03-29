package interfaces;

/**
 * Departure Terminal Exit Passenger Interface
 *
 * <p>
 *     It provides the necessary Passenger methods to be implemented in Departure Terminal Exit
 *     shared region.
 * </p>
 *
 * @author Fabio Alves
 * @author Jorge Catarino
 */
public interface DTEPassenger {

    /**
     *  Waits till every passenger is finished to transit to the terminal state.
     */
    void prepareNextLeg();
}
