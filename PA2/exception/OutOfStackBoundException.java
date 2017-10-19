package exception;

public class OutOfStackBoundException extends Exception {
	public OutOfStackBoundException() {
		super();
	}

	public OutOfStackBoundException(String msg) {
		super(msg);
	}

	public String getMessage() {
		return "Out of stack bound";

	}
}
