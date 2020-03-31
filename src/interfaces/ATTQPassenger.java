package interfaces;

/**
 * Arrival Terminal Transfer Quay Passenger Interface
 *
 * <p>
 *     It provides the necessary Passenger methods to be implemented in Arrival Terminal Transfer Quay
 *     shared region.
 * </p>
 *
 * @author Fabio Alves
 * @author Jorge Catarino
 */
public interface ATTQPassenger {

    /**
     * Simulates the entrance of a passenger on the bus.
     * The passenger gets in the queue and waits orders from the BusDriver to board.
     */
    void enterTheBus();

    /**
     * Simulated the entrance of a passenger on the bus.
     */
    void sitOnTheBus();
}
