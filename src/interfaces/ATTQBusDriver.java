package interfaces;

/**
 * Arrival Terminal Transfer Quay BusDriver Interface
 *
 * <p>
 *     It provides the necessary BusDriver methods to be implemented in Arrival Terminal Transfer Quay
 *     shared region.
 * </p>
 *
 * @author Fabio Alves
 * @author Jorge Catarino
 */

public interface ATTQBusDriver {

    /**
     * announcingBusBoarding
     */
    public void announcingBusBoarding ();

    /**
     * goToDepartureTerminal
     */
    public void goToDepartureTerminal ();

    /**
     * parkTheBus
     */
    public void parkTheBus ();
}
