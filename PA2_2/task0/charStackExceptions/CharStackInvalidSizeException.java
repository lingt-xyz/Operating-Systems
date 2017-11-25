package task0.charStackExceptions;



public class CharStackInvalidSizeException extends Exception {
	/**
	 * Adds a generated serial version ID to the selected type.
	 */
	private static final long serialVersionUID = -2746916824413577335L;

	public CharStackInvalidSizeException() {
		super("Invalid stack size specified.");
	}

	public CharStackInvalidSizeException(int piStackSize) {
		super("Invalid stack size specified: " + piStackSize);
	}
}