package task0.charStackExceptions;


public class CharStackFullException extends Exception {
	/**
	 * Adds a generated serial version ID to the selected type.
	 */
	private static final long serialVersionUID = -7328105359405215517L;

	public CharStackFullException() {
		super("Char Stack has reached its capacity of CharStack.MAX_SIZE.");
	}
}