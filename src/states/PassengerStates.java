package states;

public enum PassengerStates {
    AT_THE_DISEMBARKING_ZONE(""),
    AT_THE_LUGGAGE_COLLECTION_POINT(""),
    AT_THE_BAGGAGE_RECLAIM_OFFICE(""),
    EXITING_THE_ARRIVAL_TERMINAL(""),
    AT_THE_ARRIVAL_TRANSFER_TERMINAL(""),
    TERMINAL_TRANSFER(""),
    AT_THE_DEPARTURE_TRANSFER_TERMINAL(""),
    ENTERING_THE_DEPARTURE_TERMINAL("");

    PassengerStates(String state) {
        this.state = state;
    }

    private String state;

    public String getState() {
        return state;
    }
}