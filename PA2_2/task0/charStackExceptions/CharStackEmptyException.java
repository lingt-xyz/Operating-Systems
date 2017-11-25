package task0.charStackExceptions;


//Source code for the user defined exception classes:

public class CharStackEmptyException extends Exception {
	/**
	 * Adds a generated serial version ID to the selected type.
	 */
	private static final long serialVersionUID = 2628117607814309097L;

	public CharStackEmptyException() {
		super("Char Stack is empty.");
	}
}
