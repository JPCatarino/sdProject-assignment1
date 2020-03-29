package interfaces;

/**
 * Arrival Terminal Exit Passenger Interface
 *
 * <p>
 *     It provides the necessary Passenger methods to be implemented in Arrival Terminal Exit
 *     shared region.
 * </p>
 *
 * @author Fabio Alves
 * @author Jorge Catarino
 */
public interface ATEPassenger {

    /**
     * After finishing whatever he set out to do, the passenger goes home.
     * The function waits for all the other passenger to finish, before transiting into terminal state.
     */
    void goHome();
}
