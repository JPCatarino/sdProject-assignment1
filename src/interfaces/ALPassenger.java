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

    public void goHome();

    public PassengerDecisions whatShouldIDo();
}
