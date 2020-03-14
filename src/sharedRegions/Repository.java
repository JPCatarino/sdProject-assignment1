package sharedRegions;

public class Repository {
    /**
     * Number of landings for this simulation.
     */
    private int K_LANDINGS = 5;

    /**
     * Number of passengers for this simulation.
     */
    private int N_PASSENGERS = 6;

    /**
     * Number of luggage allowed on plane hold for this simulation.
     */
    private int M_LUGGAGE = 2;

    /**
     * Capacity of the transfer bus.
     */
    private int T_SEATS = 3;

    public int getK_LANDINGS() {
        return K_LANDINGS;
    }

    public int getN_PASSENGERS() {
        return N_PASSENGERS;
    }

    public int getM_LUGGAGE() {
        return M_LUGGAGE;
    }

    public int getT_SEATS() {
        return T_SEATS;
    }
}
