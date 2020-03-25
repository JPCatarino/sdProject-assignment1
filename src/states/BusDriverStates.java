package states;

public enum BusDriverStates {
    PARKING_AT_THE_ARRIVAL_TERMINAL("PKAT"),
    DRIVING_FORWARD("DRFW"),
    PARKING_AT_THE_DEPARTURE_TERMINAL("PKDT"),
    DRIVING_BACKWARD("DRBW");

    BusDriverStates(String state) {
        this.state = state;
    }

    private String state;

    public String getState() {
        return state;
    }
}