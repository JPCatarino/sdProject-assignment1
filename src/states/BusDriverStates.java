package states;

public enum BusDriverStates {
    PARKING_AT_THE_ARRIVAL_TERMINAL(""),
    DRIVING_FORWARD(""),
    PARKING_AT_THE_DEPARTURE_TERMINAL(""),
    DRIVING_BACKWARD("");

    BusDriverStates(String state) {
        this.state = state;
    }

    private String state;

    public String getState() {
        return state;
    }
}