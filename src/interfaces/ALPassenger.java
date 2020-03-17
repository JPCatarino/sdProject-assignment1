package interfaces;

import states.PassengerDecisions;

public interface ALPassenger {

    /**
     * goCollectABag
     */
    public void goCollectABag ();

    /**
     *
     */
    public void takeABus();

    public PassengerDecisions whatShouldIDo();
}
