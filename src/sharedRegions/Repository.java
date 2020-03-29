package sharedRegions;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Stream;

import UI.Color;
import states.BusDriverStates;
import states.PorterStates;

public class Repository {

    /**
     * Number of passengers for this simulation.
     *
     * @serialField N_PASSENGERS
     */
    private int N_PASSENGERS;

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
    private String P_Stat;

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
    private String D_Stat;

    /**
     * Occupation state for the waiting queue (passenger id / - (empty))
     *
     * @serialField Q
     */
    private LinkedList<String> Q;

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
     * Name of the file where the log file will be saved.
     *
     * @serialField filename
     */
    private String filename;

    /**
     * Number of passengers that arrive to the destination airport.
     *
     * @serialField finalDest
     */
    private int finalDest = 0;

    /**
     * Number of passengers that are doing scale in this airport.
     *
     * @serialField transit
     */
    private int transit = 0;

    /**
     * Total number of bags from all passengers in in all the landings.
     *
     * @serialField totalBags
     */
    private int totalBags = 0;

    /**
     * Total number of bags lost from all passengers in in all the landings.
     *
     * @serialField bagsLost
     */
    private int bagsLost = 0;

    /**
     * Repository Instantiation.
     *
     * @param N_passengers Number of passengers for this simulation.
     * @param T_seats      Capacity of the transfer bus.
     */
    public Repository(int N_passengers, int T_seats) {

        this.N_PASSENGERS = N_passengers;
        this.Q = new LinkedList<>();
        this.S = new String[T_seats];
        this.ST = new String[N_PASSENGERS];
        this.SI = new String[N_PASSENGERS];
        this.NR = new int[N_PASSENGERS];
        this.NA = new int[N_PASSENGERS];

        for(int i = 0; i < N_PASSENGERS; i++)
            Q.add("-");

        Arrays.fill(this.S, "-");
        Arrays.fill(this.ST, "-");
        Arrays.fill(this.SI, "-");

        this.P_Stat = PorterStates.WAITING_FOR_A_PLANE_TO_LAND.getState();
        this.D_Stat = BusDriverStates.PARKING_AT_THE_ARRIVAL_TERMINAL.getState();

        reportInitialStatus();
    }

    /**
     * Set flight number
     *
     * @param FN number of the actual flight.
     */
    public void setFN(int FN) {
        this.FN = FN;
    }

    /**
     * Set flight number
     *
     * @param BN number of the actual flight.
     */
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

    public void setQIn(int num,String q) {
        this.Q.add(num,q);
    }

    public synchronized void setQOut(){
        this.Q.removeFirst();
        this.Q.add("-");
    }

    public void setS(int num, String s) {
        this.S[num] = s;
    }

    public void setST(int num, String ST) {
        this.ST[num] = ST;
    }

    public void setSI(int num, String SI) {
        this.SI[num] = SI;
        if ((SI.equals("TRT"))) {
            transit++;
        } else {
            finalDest++;
        }
    }

    public void setNR(int num, int NR) {
        totalBags += NR;
        this.NR[num] = NR;
    }

    public void setNA(int num, int NA) {
        this.NA[num] = NA;
    }

    public void reset_Porter() {
        bagsLost += SR;
        this.SR = 0;
    }
    public void reset_Passenger(int num) {
        bagsLost += NA[num];
        this.ST[num]="-";
        this.SI[num]="-";
        this.NR[num]=0;
        this.NA[num]=0;
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
        result.append(String.format("%-4s  %-2s %-2s %-2s %-2s %-2s %-2s  %-2s %-2s %-2s    ", D_Stat, Q.get(0), Q.get(1), Q.get(2), Q.get(3), Q.get(4), Q.get(5), S[0], S[1], S[2]));

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
                .append(String.format("%-4s  %-2s %-2s %-2s %-2s %-2s %-2s  %-2s %-2s %-2s", D_Stat, Q.get(0), Q.get(1), Q.get(2), Q.get(3), Q.get(4), Q.get(5), S[0], S[1], S[2]))
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

        long count=0;

        try (Stream<Path> walk = Files.walk(Paths.get("./LOG"))) {
            count = walk.filter(Files::isRegularFile).count();

        } catch (IOException e) {
            e.printStackTrace();
        }

        filename="./LOG/log"+count+".txt";

        try {
            fw = new FileWriter(filename, false);
            try (PrintWriter pw = new PrintWriter(fw)) {
                pw.print(new String(new char[31 + (19 * N_PASSENGERS - 4) / 2 - 34]) + "AIRPORT RHAPSODY - Description of the internal state of the problem\n");
                pw.println();
                pw.print(header_requested());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Write the current State.
     */
    public void reportStatus() {
        FileWriter fw;
        try {
            fw = new FileWriter(filename, true);
            try (PrintWriter pw = new PrintWriter(fw)) {
                pw.print(toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void finalReport(){
        FileWriter fw;
        int tmp=SR;

        for (int k=0; k<N_PASSENGERS;k++ ){
            tmp += NA[k];
        }

        try {
            fw = new FileWriter(filename, true);
            try (PrintWriter pw = new PrintWriter(fw)) {
                pw.println();
                pw.println("Final report");
                pw.println("N. of passengers which have this airport as their final destination = " + finalDest);
                pw.println("N. of passengers in transit = " + transit);
                pw.println("N. of bags that should have been transported in the the planes hold = " + totalBags);
                pw.println("N. of bags that were lost = " + (totalBags -(bagsLost + tmp)));
                pw.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
