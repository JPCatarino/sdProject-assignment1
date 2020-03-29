package sharedRegions;

import entities.Passenger;
import entities.Porter;
import interfaces.BCPPassenger;
import interfaces.BCPPorter;
import states.PassengerStates;
import states.PorterStates;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the Baggage Collection Point Shared Memory
 * The passenger come here to fetch their baggage, put on the
 * conveyor belt by the porter. It waits till the Porter
 * flags that there's no more baggage on the plane hold.
 *
 * @author Fábio Alves
 * @author Jorge Catarino
 */
public class BagColPoint implements BCPPassenger, BCPPorter {

    /**
     * General Repository of Information.
     *
     * @serialField repo
     */
    private Repository repo;

    /**
     * Data structure that simulates the conveyor belt.
     *
     * @serialField conveyorBelt
     */
    private List<int[]> conveyorBelt;

    /**
     * Flag that signal the passenger that there's no more bags.
     *
     * @serialField noMoreBags
     */
    private boolean noMoreBags;

    /**
     * Flag that signals the passenger that there's a bag available to collect
     * on the conveyor belt.
     *
     * @serialField bagsInTheConveyorBelt
     */
    private boolean bagsInTheConveyorBelt;

    /**
     * Constructor for the BagColPoint.
     *
     * @param repo General repository of information.
     */
    public BagColPoint(Repository repo){
        this.repo = repo;
        this.conveyorBelt = new ArrayList<>();
    }

    @Override
    public synchronized void goCollectABag (){
        Passenger p = (Passenger) Thread.currentThread();
        p.setPassengerState(PassengerStates.AT_THE_LUGGAGE_COLLECTION_POINT);
        repo.setST(p.getID(), PassengerStates.AT_THE_LUGGAGE_COLLECTION_POINT.getState());
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

        this.conveyorBelt.add(bag);
        this.bagsInTheConveyorBelt = true;
        this.noMoreBags = false;
        repo.setP_Stat(PorterStates.AT_THE_LUGGAGE_BELT_CONVEYOR.getState());
        repo.setCB(conveyorBelt.size());
        repo.reportStatus();

        notifyAll();
    };

    /**
     * Set the variable no more bags.
     * Notifies all threads.
     *
     * @param noMoreBags True if there are no more bags in the plane hold or False otherwise.
     */
    public synchronized void setNoMoreBags(boolean noMoreBags) {
        this.noMoreBags = noMoreBags;
        notifyAll();
    }
}
