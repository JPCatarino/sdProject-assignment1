package interfaces;

/**
 * Interface for the Bus Driver entity.
 * This interface contains the signatures of the functions to be instantiated on
 * shared memory regions.
 *
 * @author Jorge Catarino
 * @author Fabio Alves
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
