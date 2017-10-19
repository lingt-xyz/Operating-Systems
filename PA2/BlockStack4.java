import exception.OutOfStackBoundException;
import exception.EmptyStackException;
import exception.FullStackException;

/**
 * Class BlockStack Implements character block stack and operations upon it.
 *
 * $Revision: 1.4 $ $Last Revision Date: 2017/02/08 $
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca; Inspired by an earlier
 *         code by Prof. D. Probst
 * 
 */

/**
 * 1. Make the iSize, iTop, acStack, and possibly your stack access counter
 * private and create methods to retrieve their values. Do appropriate changes
 * in the main code. <br>
 * 2. Modify the push() operation of the BlockStack class to handle the case
 * when the stack is empty (last element was popped). Calling push() on empty
 * stack should place an ‘a’ on top. <br>
 * 3. Implement boundaries, empty/full stack checks and alike using the Java's
 * exception handling mechanism. Declare your own exception, or a set of
 * exceptions. Make appropriate changes in the main code to catch those
 * exceptions.
 *
 */
class BlockStack4 {
	/**
	 * # of letters in the English alphabet + 2
	 */
	public static final int MAX_SIZE = 28;

	/**
	 * Default stack size
	 */
	public static final int DEFAULT_SIZE = 6;

	/**
	 * Current size of the stack
	 */
	private int iSize = DEFAULT_SIZE;

	/**
	 * Current top of the stack
	 */
	private int iTop = 3;

	/**
	 * stack[0:5] with four defined values
	 */
	private char acStack[] = new char[] { 'a', 'b', 'c', 'd', '$', '$' };

	/**
	 * Task1: Have it incremented by 1 every time the stack is accessed (i.e. via
	 * push(), pop(), pick(), or getAt() methods)
	 */
	private int accessCounter = 0;

	/**
	 * Default constructor
	 */
	public BlockStack4() {
	}

	/**
	 * Supplied size
	 */
	public BlockStack4(final int piSize) {

		if (piSize != DEFAULT_SIZE) {
			this.acStack = new char[piSize];

			// Fill in with letters of the alphabet and keep
			// 2 free blocks
			for (int i = 0; i < piSize - 2; i++)
				this.acStack[i] = (char) ('a' + i);

			this.acStack[piSize - 2] = this.acStack[piSize - 1] = '$';

			this.iTop = piSize - 3;
			this.iSize = piSize;
		}
	}

	/**
	 * Picks a value from the top without modifying the stack
	 * 
	 * @return top element of the stack, char
	 * @throws EmptyStackException
	 */
	public char pick() throws EmptyStackException {
		accessCounter++;
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		return this.acStack[this.iTop];
	}

	/**
	 * Returns arbitrary value from the stack array
	 * 
	 * @return the element, char
	 * @throws OutOfStackBoundException
	 */
	public char getAt(final int piPosition) throws OutOfStackBoundException {
		accessCounter++;
		if (piPosition >= iSize || piPosition < 0) {
			throw new OutOfStackBoundException();
		}
		return this.acStack[piPosition];
	}

	/**
	 * Standard push operation
	 * 
	 * Modify the push() operation of the BlockStack class to handle the case when
	 * the stack is empty (last element was popped). Calling push() on empty stack
	 * should place an ‘a’ on top.
	 * @throws OutOfStackBoundException 
	 */
	public void push(final char pcBlock) throws FullStackException {
		accessCounter++;
		if (isEmpty()) {
			this.acStack[++this.iTop] = 'a';
		} else if (iTop >= iSize) {
			throw new FullStackException();
		} else {
			this.acStack[++this.iTop] = pcBlock;
		}
	}

	/**
	 * Standard pop operation
	 * 
	 * @return ex-top element of the stack, char
	 * @throws EmptyStackException 
	 */
	public char pop() throws EmptyStackException {
		accessCounter++;
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		char cBlock = this.acStack[this.iTop];
		this.acStack[this.iTop--] = '$'; // Leave prev. value undefined
		return cBlock;
	}

	public int getTop() {
		return this.iTop;
	}

	public int getSize() {
		return this.iSize;
	}

	public boolean isEmpty() {
		return this.iTop == -1;
	}

	public int getAccessCounter() {
		return this.accessCounter;
	}
}

// EOF
