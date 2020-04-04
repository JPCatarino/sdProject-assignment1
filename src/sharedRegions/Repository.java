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

import states.BusDriverStates;
import states.PorterStates;

/**
 * Implementation of the Repository Shared Memory.
 * Responsible for logging all changes during the program run.
 *
 * @author Fábio Alves
 * @author Jorge Catarino
 */
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
     * Number of pieces of luggage belonging to passengers in transit presently stored at the storeroom.
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
     * Occupation state for the waiting queue (passenger id / - (empty)).
     *
     * @serialField Q
     */
    private LinkedList<String> Q;

    /**
     * Occupation state for seat in the bus (passenger id / - (empty)).
     *
     * @serialField S
     */
    private String[] S;

    /**
     * State of passenger # (# - 0 .. 5).
     *
     * @serialField ST
     */
    private String[] ST;

    /**
     * Situation of passenger # (# - 0 .. 5) – TRT (in transit) / FDT (has this airport as her final destination).
     *
     * @serialField SI
     */
    private String[] SI;

    /**
     * Number of pieces of luggage the passenger # (# - 0 .. 5) carried at the start of her journey.
     *
     * @serialField NR
     */
    private int[] NR;

    /**
     * Number of pieces of luggage the passenger # (# - 0 .. 5) she has presently collected.
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
     * Set flight number.
     *
     * @param fn number of the actual flight.
     */
    public void setFN(int fn) {
        this.FN = fn;
    }

    /**
     * Set luggage number.
     *
     * @param bn number of pieces of luggage presently at the plane's hold.
     */
    public void setBN(int bn) {
        this.BN = bn;
    }

    /**
     * Set conveyor belt.
     *
     * @param cb number of pieces of luggage presently on the conveyor belt.
     */
    public void setCB(int cb) {
        this.CB = cb;
    }

    /**
     * Set storeroom.
     *
     * @param sr number of pieces of luggage belonging to passengers in transit presently stored at the storeroom.
     */
    public void setSR(int sr) {
        this.SR = sr;
    }

    /**
     * Set porter state.
     *
     * @param p_Stat state of the porter.
     */
    public void setP_Stat(String p_Stat) {
        P_Stat = p_Stat;
    }

    /**
     * Set driver state.
     *
     * @param d_Stat state of the driver.
     */
    public void setD_Stat(String d_Stat) {
        D_Stat = d_Stat;
    }

    /**
     * Set occupation state for the waiting queue (passenger id / - (empty)).
     *
     * @param num position of the queue to add one passenger.
     * @param q passenger to put in the queue position.
     */
    public synchronized void setQIn(int num, String q) {
        this.Q.add(num,q);
    }

    /**
     * Remove first element from the waiting queue and add '-' (empty) to the last position.
     */
    public synchronized void setQOut(){
        this.Q.removeFirst();
        this.Q.add("-");
    }

    /**
     * Set occupation state for seat in the bus (passenger id / - (empty)).
     *
     * @param num seat position to put the passenger.
     * @param s passenger to put in the seat position.
     */
    public void setS(int num, String s) {
        this.S[num] = s;
    }

    /**
     * Set state of passenger # (# - 0 .. 5).
     *
     * @param num passenger to set state.
     * @param st state of the passenger.
     */
    public void setST(int num, String st) {
        this.ST[num] = st;
    }

    /**
     * Set situation of passenger # (# - 0 .. 5) – TRT (in transit) / FDT (has this airport as her final destination).
     * Count total number of passengers in transit and with this airport as final destination (all flights).
     *
     * @param num passenger to set situation.
     * @param si situation of the passenger.
     */
    public void setSI(int num, String si) {
        this.SI[num] = si;
        if ((si.equals("TRT"))) {
            transit++;
        } else {
            finalDest++;
        }
    }

    /**
     * Set number of pieces of luggage the passenger # (# - 0 .. 5) carried at the start of her journey.
     * Count total number of pieces of luggage (all flights).
     *
     * @param num passenger that carried the pieces of luggage at the start of her journey.
     * @param nr number of pieces of luggage the passenger carried at the start of her journey.
     */
    public void setNR(int num, int nr) {
        totalBags += nr;
        this.NR[num] = nr;
    }

    /**
     * Set number of pieces of luggage the passenger # (# - 0 .. 5) he has presently collected.
     *
     * @param num passenger that has presently collected the pieces of luggage.
     * @param na number of pieces of luggage that the passenger has presently collected.
     */
    public void setNA(int num, int na) {
        this.NA[num] = na;
    }

    /**
     * Add one to the number of piece of luggage lost.
     * @param nBagsLost Number of bags the passengers reported lost.
     */
    public void addBagsLost(int nBagsLost) {
        this.bagsLost += nBagsLost;
    }

    /**
     * Count number of pieces of luggage that the passenger has presently collected (all passengers in all flight).
     * Reset passenger to the initial state.
     *
     * @param num passenger to be put in the initial state.
     */
    public void reset_Passenger(int num) {
        this.ST[num]="-";
        this.SI[num]="-";
        this.NR[num]=0;
        this.NA[num]=0;
    }

    /**
     * Header from the airport logger (Easier to analise and understand).
     *
     * @return Header from the airport logger.
     */
    public String header_debug() {
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

    /**
     * State actual from the airport (Easier to analise and understand).
     *
     * @return State actual from the airport.
     */
    public String toString_debug() {
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

    /**
     * Header from the airport logger (Requested).
     *
     * @return Header from the airport logger.
     */
    public String header_requested() {
        String result;
        StringBuilder passagers = new StringBuilder();

        result = "PLANE";
        result += "      PORTER";
        result += "                   DRIVER\n";
        result += "FN BN    Stat  CB SR    Stat  Q1 Q2 Q3 Q4 Q5 Q6  S1 S2 S3\n";
        result += new String(new char[(19 * N_PASSENGERS - 14) / 2]);
        result +="PASSENGERS\n";

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

    /**
     * State actual from the airport (Requested).
     */
    @Override
    public String toString() {

        StringBuilder result;

        result = new StringBuilder(String.format("%-2d %-2d    ", FN, BN));
        result.append(String.format("%-4s  %-2d %-2d    ", P_Stat, CB, SR));
        result.append(String.format("%-4s  %-2s %-2s %-2s %-2s %-2s %-2s  %-2s %-2s %-2s    ", D_Stat, Q.get(0), Q.get(1), Q.get(2), Q.get(3), Q.get(4), Q.get(5), S[0], S[1], S[2]));
        result.append("\n");

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

    /**
     * Write the initial State (Calculate logger file name, create logger file and add header to the logger file).
     */
    public void reportInitialStatus() {

        FileWriter fw;
        long count=0;

        if(!Files.exists(Paths.get("./LOG"))) {
            try{
                Files.createDirectory(Paths.get("./LOG"));
            } catch (IOException e) {
                System.out.print(e.getMessage());
            }
        }

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
     * Append the current State to the logger file.
     */
    public synchronized void reportStatus() {
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

    /**
     * Calculate the number of pieces of luggage missing.
     * Append the final report to the logger file.
     */
    public void finalReport(){
        FileWriter fw;

        try {
            fw = new FileWriter(filename, true);
            try (PrintWriter pw = new PrintWriter(fw)) {
                pw.println();
                pw.println("Final report");
                pw.println("N. of passengers which have this airport as their final destination = " + finalDest);
                pw.println("N. of passengers in transit = " + transit);
                pw.println("N. of bags that should have been transported in the the planes hold = " + totalBags);
                pw.println("N. of bags that were lost = " + bagsLost);
                pw.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
