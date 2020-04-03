package exceptions;

/**
 * Shared Region Exception.
 * Defines an exception for the incorrect setting of a shared region parameters.
 * Based on the MemException implementation.
 * @author Fábio Alves
 * @author Jorge Catarino
 */
public class SharedRegException extends Exception{
    /**
     * Conventional exception instantiation
     * @param errMessage error message
     */
    public SharedRegException (String errMessage){
        super (errMessage);
    }

    /**
     * Exception instantiation with the raising cause.
     * @param errMessage error message
     * @param cause exception that generated this exception
     */
    public SharedRegException (String errMessage, Throwable cause){
        super (errMessage, cause);
    }
}
