package interfaces;

/**
 * Baggage Reclaim Office BusDriver Interface
 *
 * <p>
 *     It provides the necessary BusDriver methods to be implemented in Departure Terminal Transfer Quay
 *     shared region.
 * </p>
 *
 * @author Fabio Alves
 * @author Jorge Catarino
 */
public interface DTTQBusDriver {

    /**
     * The busDriver parks the bus and waits till every passenger has gotten off.
     */
    void parkTheBusAndLetPassOff ();

    /**
     * After letting the passengers off, the bus driver goes back to the Arrival Terminal.
     */
    void goToArrivalTerminal ();
}
