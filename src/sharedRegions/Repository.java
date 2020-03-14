package sharedRegions;

import java.util.Arrays;

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

    private int FN;
    private int BN;
    private int CB;
    private int SR;
    private String Stat;
    private int[] Q;
    private int[] S;
    private String[] ST;
    private String[] SI;
    private int[] NR;
    private int[] NA;

    public Repository() {
        this.Q = new int[N_PASSENGERS];
        this.S = new int[T_SEATS];
        this.ST = new String[N_PASSENGERS];
        this.SI = new String[N_PASSENGERS];
        this.NR = new int[N_PASSENGERS];
        this.NA = new int[N_PASSENGERS];
    }

    public void setFN(int FN) {
        this.FN = FN;
    }

    public void setBN(int BN) {
        this.BN = BN;
    }

    public void setCB(int CB) {
        this.CB = CB;
    }

    public void setSR(int SR) {
        this.SR = SR;
    }

    public void setStat(String stat) {
        Stat = stat;
    }

    public void setQ(int num, int q) {
        this.Q[num] = q;
    }

    public void setS(int num, int s) {
        this.S[num] = s;
    }

    public void setST(int num, String ST) {
        this.ST[num] = ST;
    }

    public void setSI(int num, String SI) {
        this.SI[num] = SI;
    }

    public void setNR(int num, int NR) {
        this.NR[num] = NR;
    }

    public void setNA(int num, int NA) {
        this.NA[num] = NA;
    }

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

    @Override
    public String toString() {
        return  "  FN=" + FN +
                ", BN=" + BN +
                ", CB=" + CB +
                ", SR=" + SR +
                ", Stat='" + Stat + '\'' +
                ", Q=" + Arrays.toString(Q) +
                ", S=" + Arrays.toString(S) +
                ", ST=" + Arrays.toString(ST) +
                ", SI=" + Arrays.toString(SI) +
                ", NR=" + Arrays.toString(NR) +
                ", NA=" + Arrays.toString(NA) +
                '}';
    }
}
