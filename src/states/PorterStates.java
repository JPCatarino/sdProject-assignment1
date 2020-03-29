package states;

/**
 * Implements the typical Porter states.
 * <ul>
 *     <li>WAITING_FOR_A_PLANE_TO_LAND - Initial/terminal state. The porter eagerly awaits a landing.</li>
 *     <li>AT_THE_PLANES_HOLD - The Porter is fetching a bag at the plane hold.</li>
 *     <li>AT_THE_LUGGAGE_BELT_CONVEYOR - The Porter is placing a bag on the conveyor belt, waking up passengers.</li>
 *     <li>AT_THE_STOREROOM - The porter is storing a bag on the store room.</li>
 * </ul>
 */
public enum PorterStates {

    /**
     * Initial/terminal state. The porter eagerly awaits a landing.
     */
    WAITING_FOR_A_PLANE_TO_LAND("WPTL"),
    /**
     * The Porter is fetching a bag at the plane hold.
     */
    AT_THE_PLANES_HOLD("APLH"),
    /**
     * The Porter is placing a bag on the conveyor belt, waking up passengers.
     */
    AT_THE_LUGGAGE_BELT_CONVEYOR("ALCB"),
    /**
     * The porter is storing a bag on the store room.
     */
    AT_THE_STOREROOM("ASTR");

    PorterStates(String state) {
        this.state = state;
    }

    private String state;

    public String getState() {
        return state;
    }
}