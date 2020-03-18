package states;

public enum PorterStates {
    WAITING_FOR_A_PLANE_TO_LAND("WPTL"),
    AT_THE_PLANES_HOLD("APLH"),
    AT_THE_LUGGAGE_BELT_CONVEYOR("ALCB"),
    AT_THE_STOREROOM("ASTR");

    PorterStates(String state) {
        this.state = state;
    }

    private String state;

    public String getState() {
        return state;
    }
}