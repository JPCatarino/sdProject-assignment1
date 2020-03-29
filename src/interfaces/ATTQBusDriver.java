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
     *   Let's the BusDriver know his shift as ended, so he can enter terminal state.
     *
     *   @return True, if the day has ended.
     */
    boolean hasDaysWorkEnded();

    /**
     * The bus driver waits until there's passengers in the queue or it's time to leave.
     * After this, he notifies all passengers that the bus is ready to board.
     */
    void announcingBusBoarding ();

    /**
     * After boarding all passengers, the bus driver then drives to the DepartureTerminal.
     * This function changes state to DRIVING_FORWARD and unparks the bus.
     */
    void goToDepartureTerminal ();

    /**
     * Parks the bus after returning from Departure Terminal.
     * It assumes the Bus comes back empty from the terminal.
     */
    void parkTheBus ();
}
