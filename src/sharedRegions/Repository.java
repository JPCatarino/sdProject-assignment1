package sharedRegions;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import UI.Color;
import states.BusDriverStates;
import states.PorterStates;

public class Repository {

    /**
     * Number of landings for this simulation.
     *
     * @serialField K_LANDINGS
     */
    private int K_LANDINGS = 5;

    /**
     * Number of passengers for this simulation.
     *
     * @serialField N_PASSENGERS
     */
    private int N_PASSENGERS = 6;

    /**
     * Number of luggage allowed on plane hold for this simulation.
     *
     * @serialField M_LUGGAGE
     */
    private int M_LUGGAGE = 2;

    /**
     * Capacity of the transfer bus.
     *
     * @serialField T_SEATS
     */
    private int T_SEATS = 3;

    /**
     * Flight number.
     *
     * @serialField FN
     */
    private int FN;

    /**
     * Number of pieces of luggage presently at the plane's hold.
     *
     * @serialField BN
     */
    private int BN;

    /**
     * State of the porter.
     *
     * @serialField P_Stat
     */
    private String P_Stat = PorterStates.WAITING_FOR_A_PLANE_TO_LAND.getState();

    /**
     * Number of pieces of luggage presently on the conveyor belt.
     *
     * @serialField CB
     */
    private int CB;

    /**
     * Number of pieces of luggage belonging to passengers in transit presently stored at the storeroom
     *
     * @serialField SR
     */
    private int SR;

    /**
     * State of the driver.
     *
     * @serialField D_Stat
     */
    private String D_Stat = BusDriverStates.PARKING_AT_THE_ARRIVAL_TERMINAL.getState();

    /**
     * Occupation state for the waiting queue (passenger id / - (empty))
     *
     * @serialField Q
     */
    private String[] Q;

    /**
     * Occupation state for seat in the bus (passenger id / - (empty))
     *
     * @serialField S
     */
    private String[] S;

    /**
     * State of passenger # (# - 0 .. 5)
     *
     * @serialField ST
     */
    private String[] ST;

    /**
     * Situation of passenger # (# - 0 .. 5) – TRT (in transit) / FDT (has this airport as her final destination)
     *
     * @serialField SI
     */
    private String[] SI;

    /**
     * Number of pieces of luggage the passenger # (# - 0 .. 5) carried at the start of her journey
     *
     * @serialField NR
     */
    private int[] NR;

    /**
     * Number of pieces of luggage the passenger # (# - 0 .. 5) she has presently collected
     *
     * @serialField NA
     */
    private int[] NA;

    /**
     * Repository Instanciation.
     */
    public Repository() {
        this.Q = new String[N_PASSENGERS];
        Arrays.fill(this.Q, "-");
        this.S = new String[T_SEATS];
        Arrays.fill(this.S, "-");
        this.ST = new String[N_PASSENGERS];
        Arrays.fill(this.ST, "-");
        this.SI = new String[N_PASSENGERS];
        Arrays.fill(this.SI, "-");
        this.NR = new int[N_PASSENGERS];
        this.NA = new int[N_PASSENGERS];
        reportInitialStatus();
    }

    /**
     * Repository Instanciation with custom number of passengers and seats.
     *
     * @param N_passengers number of passengers for this simulation.
     * @param T_seats      Capacity of the transfer bus.
     */
    public Repository(int N_passengers, int T_seats) {
        this.N_PASSENGERS = N_passengers;
        this.T_SEATS = T_seats;
        this.Q = new String[N_PASSENGERS];
        Arrays.fill(this.Q, "-");
        this.S = new String[T_SEATS];
        Arrays.fill(this.S, "-");
        this.ST = new String[N_PASSENGERS];
        Arrays.fill(this.ST, "-");
        this.SI = new String[N_PASSENGERS];
        Arrays.fill(this.SI, "-");
        this.NR = new int[N_PASSENGERS];
        this.NA = new int[N_PASSENGERS];
        reportInitialStatus();

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

    public void setP_Stat(String p_Stat) {
        P_Stat = p_Stat;
    }

    public void setD_Stat(String d_Stat) {
        D_Stat = d_Stat;
    }

    public void setQ(int num, String q) {
        this.Q[num] = q;
    }

    public void setS(int num, String s) {
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

    public String header_requested() {
        String result;
        StringBuilder passagers = new StringBuilder();

        result = "PLANE";
        result += "      PORTER";
        result += "                     DRIVER";
        result += new String(new char[(19 * N_PASSENGERS - 14) / 2]);
        result += "                 PASSENGERS" + "\n";
        result += "FN BN    Stat  CB SR    Stat  Q1 Q2 Q3 Q4 Q5 Q6  S1 S2 S3    ";

        for (int num_passager = 1; num_passager <= N_PASSENGERS; num_passager++) {
            passagers.append("St").append(num_passager)
                    .append(" Si").append(num_passager)
                    .append(" NR").append(num_passager)
                    .append(" NA").append(num_passager)
                    .append("    ");
        }
        result += passagers + "\n";

        return result;
    }

    public String header_debug() {
        String result;
        StringBuilder passagers = new StringBuilder();
        int count = (19 * N_PASSENGERS - 14) / 2;

        result = Color.BB_GREEN + "PLANE" + Color.RESET + "    ";
        result += Color.BB_BLUE + "  PORTER   " + Color.RESET + "    ";
        result += Color.BB_RED + "              DRIVER             " + Color.RESET + "    ";
        result += Color.BB_MAGENTA + new String(new char[count]) + "PASSENGERS";
        result += new String(new char[(19 * N_PASSENGERS - 14) - count]) + Color.RESET + "\n";
        result += Color.BACK_BLACK + "FN BN" + Color.RESET + "    ";
        result += Color.BACK_BLACK + "Stat  CB SR" + Color.RESET + "    ";
        result += Color.BACK_BLACK + "Stat  Q1 Q2 Q3 Q4 Q5 Q6  S1 S2 S3" + Color.RESET + "    ";

        for (int num_passager = 1; num_passager < N_PASSENGERS; num_passager++) {
            passagers.append("St").append(num_passager)
                    .append(" Si").append(num_passager)
                    .append(" NR").append(num_passager)
                    .append(" NA").append(num_passager)
                    .append("    ");
        }

        passagers.append("St").append(N_PASSENGERS)
                .append(" Si").append(N_PASSENGERS)
                .append(" NR").append(N_PASSENGERS)
                .append(" NA").append(N_PASSENGERS);

        result += Color.BACK_BLACK + passagers.toString() + Color.RESET + "\n";

        return result;
    }

    @Override
    public String toString() {
        StringBuilder result;

        result = new StringBuilder(String.format("%-2d %-2d    ", FN, BN));
        result.append(String.format("%-4s  %-2d %-2d    ", P_Stat, CB, SR));
        result.append(String.format("%-4s  %-2s %-2s %-2s %-2s %-2s %-2s  %-2s %-2s %-2s    ", D_Stat, Q[0], Q[1], Q[2], Q[3], Q[4], Q[5], S[0], S[1], S[2]));

        for (int num_passager = 1; num_passager <= N_PASSENGERS; num_passager++) {
            result.append(String.format("%-3s", ST[num_passager - 1]))
                    .append(String.format(" %-3s", SI[num_passager - 1]))
                    .append(String.format(" %-3s", NR[num_passager - 1]))
                    .append(String.format(" %-3s", NA[num_passager - 1]))
                    .append("    ");
        }

        result.append("\n");

        return result.toString();
    }

    public String toString_debug() {
        StringBuilder result;

        result = new StringBuilder(Color.BACK_BLACK + String.format("%-2d %-2d", FN, BN) + Color.RESET + "    ");

        result.append(Color.BACK_BLACK)
                .append(String.format("%-4s  %-2d %-2d", P_Stat, CB, SR))
                .append(Color.RESET)
                .append("    ");

        result.append(Color.BACK_BLACK)
                .append(String.format("%-4s  %-2s %-2s %-2s %-2s %-2s %-2s  %-2s %-2s %-2s", D_Stat, Q[0], Q[1], Q[2], Q[3], Q[4], Q[5], S[0], S[1], S[2]))
                .append(Color.RESET)
                .append("    ");

        result.append(Color.BACK_BLACK);

        for (int num_passager = 1; num_passager < N_PASSENGERS; num_passager++) {
            result.append(String.format("%-3s", ST[num_passager - 1]))
                    .append(String.format(" %-3s", SI[num_passager - 1]))
                    .append(String.format(" %-3s", NR[num_passager - 1]))
                    .append(String.format(" %-3s", NA[num_passager - 1]))
                    .append("    ");
        }

        result.append(String.format("%-3s", ST[N_PASSENGERS - 1]))
                .append(String.format(" %-3s", SI[N_PASSENGERS - 1]))
                .append(String.format(" %-3s", NR[N_PASSENGERS - 1]))
                .append(String.format(" %-3s", NA[N_PASSENGERS - 1]));

        result.append(Color.RESET);

        result.append("\n");

        return result.toString();
    }

    /**
     * Write the initial State (Create file and header).
     */
    public void reportInitialStatus() {
        FileWriter fw;
        try {
            fw = new FileWriter("Log.txt", false);
            try (PrintWriter pw = new PrintWriter(fw)) {
                pw.print(new String(new char[31 + (19 * N_PASSENGERS - 4) / 2 - 34]) + "AIRPORT RHAPSODY - Description of the internal state of the problem\n");
                pw.println();
                pw.print(header_requested());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        reportStatus();
    }

    /**
     * Write the current State.
     */
    public void reportStatus() {
        FileWriter fw;
        try {
            fw = new FileWriter("Log.txt", true);
            try (PrintWriter pw = new PrintWriter(fw)) {
                pw.print(toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void finalReport(){
        FileWriter fw;
        int finalDest = 0;
        int transit = 0;
        int totalbags = 0;
        int bagslost = 0;

        for (String tmp : SI){
            if (tmp.equals("FDT"))
                finalDest++;
        }

        transit = N_PASSENGERS -  finalDest;

        try {
            fw = new FileWriter("Log.txt", true);
            try (PrintWriter pw = new PrintWriter(fw)) {
                pw.println();
                pw.println("Final report");
                pw.println("N. of passengers which have this airport as their final destination = " + finalDest);
                pw.println("N. of passengers in transit = " + transit);
                pw.println("N. of bags that should have been transported in the the planes hold = " + totalbags);
                pw.println("N. of bags that were lost = " + bagslost);
                pw.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
