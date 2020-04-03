package interfaces;

import exceptions.SharedRegException;

/**
 * Departure Terminal Transfer Quay Passenger Interface
 *
 * <p>
 *     It provides the necessary Passenger methods to be implemented in Departure Terminal Transfer Quay
 *     shared region.
 * </p>
 *
 * @author Fabio Alves
 * @author Jorge Catarino
 */
public interface DTTQPassenger {

    /**
     * Arriving at the Departure Terminal Transfer Quay, the passenger leaves the bus.
     */
    void leaveTheBus();

    /**
     * Simulates a passenger leaving the bus.
     * @throws SharedRegException When it tries to remove a non existent passenger
     */
    void getOffTheSeat() throws SharedRegException;
}
