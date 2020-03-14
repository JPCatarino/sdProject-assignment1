package entities;

import sharedRegions.*;

public class Passenger extends Thread {

    /**
     * Passengers's id
     * @serialField id
     */
    private int id; // Same as bag id?

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
}
