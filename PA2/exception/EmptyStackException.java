package exception;


public class EmptyStackException extends Exception {
	public EmptyStackException() {
		super();
	}

	public EmptyStackException(String msg) {
		super(msg);
	}

	public String getMessage() {
		return "Stack is Empty";

	}
}
