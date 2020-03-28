package sharedRegions;

import entities.Passenger;
import entities.Porter;
import interfaces.BCPPassenger;
import interfaces.BCPPorter;
import states.PassengerStates;
import states.PorterStates;

import javax.swing.plaf.synth.SynthTextAreaUI;
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
        repo.setST(p.getID(), PassengerStates.AT_THE_LUGGAGE_COLLECTION_POINT.getState());
        repo.toString_debug();
        repo.reportStatus();
        // Logic may be changed
        // While there's bags on the hold the passenger waits
        // If there's bags on the conveyor belt it tries to collect one with it's ID
        // In case the passengers find one, it collects it.

        try{
            while(!noMoreBags && !(p.getnBagsToCollect() == p.getnBagsCollected())){
                if(bagsInTheConveyorBelt){
                    for(int i = 0; i < conveyorBelt.size(); i++){
                        if(conveyorBelt.get(i)[0] == p.getID()){
                            conveyorBelt.remove(i);
                            p.collectedABag();
                            repo.setCB(conveyorBelt.size());
                            repo.setNA(p.getID(), p.getnBagsCollected());
                            repo.toString_debug();
                            repo.reportStatus();
                            break;
                        }
                    }
                }
                if(!(p.getnBagsToCollect() == p.getnBagsCollected())) {
                    wait();
                }
                else{
                    break;
                }
            }
        }
        catch(InterruptedException ex){
            System.err.println("goCollectABag - Thread Interrupted");
        }

    };

    @Override
    public synchronized void carryItToAppropriateStore(int [] bag){
        Porter pt = (Porter) Thread.currentThread();

        this.conveyorBelt.add(bag);
        this.bagsInTheConveyorBelt = true;
        this.noMoreBags = false;
        repo.setP_Stat(PorterStates.AT_THE_LUGGAGE_BELT_CONVEYOR.getState());
        repo.setCB(conveyorBelt.size());
        repo.toString_debug();
        repo.reportStatus();

        notifyAll();
    };

    @Override
    public synchronized void setNoMoreBags(boolean noMoreBags) {
        this.noMoreBags = noMoreBags;
        notifyAll();
    }
}
