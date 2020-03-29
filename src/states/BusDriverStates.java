package states;

/**
 * Enum implementing the typical bus driver states:
 *
 * <ul>
 *     <li>PARKING_AT_THE_ARRIVAL_TERMINAL - When the BusDriver is parked on the ArrivalQuay waiting for passengers</li>
 *     <li>DRIVING_FORWARD - Purely a transitional state, it represent when the bus driver is driving to the Departure Terminal</li>
 *     <li>PARKING_AT_THE_DEPARTURE_TERMINAL - When the BusDriver is parked on the DepartureQuay waiting for passengers to drop off</li>
 *     <li>DRIVING_BACKWARD - Represents the drive back to the ArrivalQuay</li>
 * </ul>
 */
public enum BusDriverStates {

    /**
     * When the BusDriver is parked on the ArrivalQuay waiting for passengers.
     */
    PARKING_AT_THE_ARRIVAL_TERMINAL("PKAT"),
    /**
     * Purely a transitional state, it represent when the bus driver is driving to the Departure Terminal.
     */
    DRIVING_FORWARD("DRFW"),
    /**
     * When the BusDriver is parked on the DepartureQuay waiting for passengers to drop off.
     */
    PARKING_AT_THE_DEPARTURE_TERMINAL("PKDT"),
    /**
     * Represents the drive back to the ArrivalQuay.
     */
    DRIVING_BACKWARD("DRBW");

    BusDriverStates(String state) {
        this.state = state;
    }

    private String state;

    public String getState() {
        return state;
    }
}