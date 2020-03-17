package entities;

import sharedRegions.*;
import states.PassengerStates;

public class Passenger extends Thread {

    /**
     * Passengers's id
     * @serialField id
     */
    private int id; // Same as bag id?

    /**
     * Passenger's current state
     * @serialField PassengerStates
     */
    private PassengerStates state;


    private ArrivalLounge al;
    private BagColPoint bcp;
    private BagRecOffice bro;
    private ArrivalQuay aq;
    private DepartureQuay dq;
    private DepartureTerminalEntrance dte;

    private enum PassengerDecisions{
        COLLECT_A_BAG,
        GO_HOME,
        TAKE_A_BUS
    }

    public Passenger(int id, ArrivalLounge al, BagColPoint bcp, BagRecOffice bro, ArrivalQuay aq, DepartureQuay dq, DepartureTerminalEntrance dte) {
        this.id = id;
        this.state = PassengerStates.AT_THE_DISEMBARKING_ZONE;
        this.al = al;
        this.bcp = bcp;
        this.bro = bro;
        this.aq = aq;
        this.dq = dq;
        this.dte = dte;
    }

    @Override
    public void run(){
        switch(WhatShouldIDo()){
            case "GO_HOME":
                al.goHome();
            case "COLLECT_A_BAG":
                // if else
            case "TAKE_A_BUS":
                al.takeABus();
                aq.enterTheBus();
                dq.leaveTheBus();
                dte.prepareNextLeg();
        }
    }

    private String WhatShouldIDo(){
        return "";
    }


    public int getID() {
        return id;
    }

    /**
     * Set Passenger state
     * @param state new state of the Passenger
     */
    public void setPassengerState(PassengerStates state) {
        this.state = state;
    }
}
