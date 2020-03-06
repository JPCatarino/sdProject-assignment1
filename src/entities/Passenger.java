package entities;

import sharedRegions.*;

public class Passenger extends Thread {

    ArrivalLounge al;
    BagColPoint bcp;
    BagRecOffice bro;
    ArrivalQuay aq;
    DepartureQuay dq;
    DepartureTerminalEntrance dte;

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

}
