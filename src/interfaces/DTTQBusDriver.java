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
     * parkTheBusAndLetPassOff
     */
    public void parkTheBusAndLetPassOff ();

    /**
     * goToArrivalTerminal
     */
    public void goToArrivalTerminal ();
}
