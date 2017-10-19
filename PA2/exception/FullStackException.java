package exception;

public class FullStackException extends Exception {
	public FullStackException() {
		super();
	}

	public FullStackException(String msg) {
		super(msg);
	}

	public String getMessage() {
		return "Stack is Full";

	}

}
