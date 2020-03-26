package interfaces;


/**
 * Arrival Terminal Transfer Quay Passenger Interface
 *
 * <p>
 *     It provides the necessary Passenger methods to be implemented in Arrival Terminal Transfer Quay
 *     shared region.
 * </p>
 *
 * @author Fabio Alves
 * @author Jorge Catarino
 */
public interface ATTQPassenger {
    /**
     *
     */
    public void enterTheBus();

    public void sitOnTheBus(int id);
}
