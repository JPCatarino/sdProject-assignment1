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

    public String WhatShouldIDo(){
        return "";
    }

    @Override
    public void run(){
        switch(WhatShouldIDo()){
            case "Home":
                al.goHome();
            case "Bag":
                // if else
            case "NextFlight":
                al.takeABus();
                aq.enterTheBus();
                dq.leaveTheBus();
                dte.prepareNextLeg();
        }
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
