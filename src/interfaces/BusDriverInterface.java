package interfaces;

/**
 * Interface for the Bus Driver entity.
 * This interface contains the signatures of the functions to be instantiated on
 * shared memory regions.
 *
 * @author Jorge Catarino
 * @author Fabio Alves
 */

public interface BusDriverInterface {

    /**
     * announcingBusBoarding
     */
    public void announcingBusBoarding ();

    /**
     * goToDepartureTerminal
     */
    public void goToDepartureTerminal ();

    /**
     * parkTheBusAndLetPassOff
     */
    public void parkTheBusAndLetPassOff ();

    /**
     * goToArrivalTerminal
     */
    public void goToArrivalTerminal ();

    /**
     * parkTheBus
     */
    public void parkTheBus ();
}
