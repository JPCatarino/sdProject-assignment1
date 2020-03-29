package states;

/**
 * Implements the typical Passenger States.
 * <ul>
 *     <li>AT_THE_LUGGAGE_COLLECTION_POINT - The passenger is waiting to collect a bag</li>
 *     <li>AT_THE_BAGGAGE_RECLAIM_OFFICE - After failing to collect a bag, the passenger goes to complain</li>
 *     <li>EXITING_THE_ARRIVAL_TERMINAL - Terminal state after passenger goes home</li>
 *     <li>AT_THE_ARRIVAL_TRANSFER_TERMINAL - The passenger is waiting for the bus</li>
 *     <li>TERMINAL_TRANSFER - On the bus, the passenger is being driven to the next terminal</li>
 *     <li>AT_THE_DEPARTURE_TRANSFER_TERMINAL - The bus driver drops off the passenger here</li>
 *     <li>AT_THE_DISEMBARKING_ZONE - Initial State, the passenger just arrived at the airport</li>
 *     <li>ENTERING_DEPARTURE_TERMINAL - Terminal State, the passenger is getting ready to enter another flight</li>
 *     <li>IN_TRANSIT - Signals the passenger is catching another plane</li>
 *     <li>FINAL_DESTINATION - Signals this is the passenger last flight and he is going home</li>
 * </ul>
 */
public enum PassengerStates {

    /**
     * The passenger is waiting to collect a bag.
     */
    AT_THE_LUGGAGE_COLLECTION_POINT("LCP"),
    /**
     * After failing to collect a bag, the passenger goes to complain.
     */
    AT_THE_BAGGAGE_RECLAIM_OFFICE("BRO"),
    /**
     * Terminal state after passenger goes home.
     */
    EXITING_THE_ARRIVAL_TERMINAL("EAT"),
    /**
     * The passenger is waiting for the bus.
     */
    AT_THE_ARRIVAL_TRANSFER_TERMINAL("ATT"),
    /**
     * On the bus, the passenger is being driven to the next terminal.
     */
    TERMINAL_TRANSFER("TRT"),
    /**
     * The bus driver drops off the passenger here.
     */
    AT_THE_DEPARTURE_TRANSFER_TERMINAL("DTT"),
    /**
     * Initial State, the passenger just arrived at the airport.
     */
    AT_THE_DISEMBARKING_ZONE("ATD"),
    /**
     * Terminal State, the passenger is getting ready to enter another flight.
     */
    ENTERING_THE_DEPARTURE_TERMINAL("EDT"),
    /**
     * Signals the passenger is catching another plane.
     */
    IN_TRANSIT("TRT"),
    /**
     * Signals this is the passenger last flight and he is going home.
     */
    FINAL_DESTINATION("FDT");

    PassengerStates(String state) {
        this.state = state;
    }

    private String state;

    public String getState() {
        return state;
    }
}