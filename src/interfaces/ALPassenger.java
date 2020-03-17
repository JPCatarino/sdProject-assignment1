package interfaces;

import states.PassengerDecisions;

public interface ALPassenger {

    /**
     *
     */
    public void takeABus();

    public PassengerDecisions whatShouldIDo();
}
