package exceptions;

public class EmptyStackException extends Exception{
	/**
	 * EmptyStackException.java
	 *
	 * <p>Thrown when an operation is attempted on an empty stack that requires
	 * at least one element to be present (e.g., {@code pop()} or {@code peek()}).
	 *
	 * @author Team Mineru
	 * @version 1.0
	 */
	
	
	    private static final long serialVersionUID = 1L;
	 
	    /** Constructs an EmptyStackException with no detail message. */
	    public EmptyStackException()
	    {
	        super();
	    }
	 
	    /**
	     * Constructs an EmptyStackException with the specified detail message.
	     *
	     * @param message the detail message
	     */
	    public EmptyStackException( String message )
	    {
	        super( message );
	    }

}
