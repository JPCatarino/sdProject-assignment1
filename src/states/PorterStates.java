package states;

public enum PorterStates {
    WAITING_FOR_A_PLANE_TO_LAND(""),
    AT_THE_PLANES_HOLD(""),
    AT_THE_LUGGAGE_BELT_CONVEYOR(""),
    AT_THE_STOREROOM("");

    PorterStates(String state) {
        this.state = state;
    }

    private String state;

    public String getState() {
        return state;
    }
}