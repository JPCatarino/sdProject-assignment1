package interfaces;

import entities.Passenger;
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

    /**
     *  If the passenger journey hasn't ended, he takes a bus.
     *  This function makes sure he transits to the next state.
     */
    void takeABus();

    /**
     * When the passenger arrives at the airport,
     * this function is called so he decides what to do next.
     * Check the Passenger.run() documentation for the logic behind this.
     *
     * @see Passenger#run()
     * @return the passenger decision of what to do next.
     */
    PassengerDecisions whatShouldIDo();
}
