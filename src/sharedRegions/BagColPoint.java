package sharedRegions;

import entities.Passenger;
import interfaces.BCPPassenger;
import interfaces.BCPPorter;
import states.PassengerStates;

import java.util.ArrayList;
import java.util.List;

public class BagColPoint implements BCPPassenger, BCPPorter {

    private Repository repo;
    private List<int[]> conveyorBelt;

    private boolean noMoreBags;

    private boolean bagsInTheConveyorBelt;

    public BagColPoint(Repository repo){
        this.repo = repo;
        this.conveyorBelt = new ArrayList<>();
    }

    @Override
    public synchronized void goCollectABag (){
        Passenger p = (Passenger) Thread.currentThread();
        p.setPassengerState(PassengerStates.AT_THE_LUGGAGE_COLLECTION_POINT);

        // Logic may be changed
        // While there's bags on the hold the passenger waits
        // If there's bags on the conveyor belt it tries to collect one with it's ID
        // In case the passengers find one, it collects it.
        try{
            while(!noMoreBags){
                if(bagsInTheConveyorBelt){
                    for(int i = 0; i < conveyorBelt.size(); i++){
                        if(conveyorBelt.get(i)[0] == p.getID()){
                            conveyorBelt.remove(i);
                            p.collectedABag();
                            break;
                        }
                    }
                }
                else
                    wait();
            }
        }
        catch(InterruptedException ex){
            System.err.println("goCollectABag - Thread Interrupted");
        }

    };


    @Override
    public synchronized void carryItToAppropriateStore(){};
}
