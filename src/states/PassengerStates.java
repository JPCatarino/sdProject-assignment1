package states;

public enum PassengerStates {
    AT_THE_LUGGAGE_COLLECTION_POINT("LCP"),
    AT_THE_BAGGAGE_RECLAIM_OFFICE("BRO"),
    EXITING_THE_ARRIVAL_TERMINAL("EAT"),
    AT_THE_ARRIVAL_TRANSFER_TERMINAL("ATT"),
    TERMINAL_TRANSFER("TRT"),
    AT_THE_DEPARTURE_TRANSFER_TERMINAL("DTT"),
    AT_THE_DISEMBARKING_ZONE("ATD"),
    ENTERING_THE_DEPARTURE_TERMINAL("EDT"),
    IN_TRANSIT("TRT"),
    FINAL_DESTINATION("FDT");

    PassengerStates(String state) {
        this.state = state;
    }

    private String state;

    public String getState() {
        return state;
    }
}