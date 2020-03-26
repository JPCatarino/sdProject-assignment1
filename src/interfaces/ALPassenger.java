package interfaces;

import states.PassengerDecisions;

/**
 * Arrival Lounge Passenger Interface
 *
 * <p>
 *     It provides the necessary Passenger methods to be implemented in Arrival Lounger
 *     shared region.
 * </p>
 *
 * @author Fabio Alves
 * @author Jorge Catarino
 */
public interface ALPassenger {

    public void takeABus();

    public PassengerDecisions whatShouldIDo();
}
