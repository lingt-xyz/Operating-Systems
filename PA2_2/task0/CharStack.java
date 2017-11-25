package task0;

import task0.charStackExceptions.*;

public class CharStack {
	/*
	 * Some class constants.
	 */
	public static final int MIN_SIZE = 7; // Minimum stack size
	public static final int MAX_SIZE = 32; // # of letters in the English
											// alphabet + 6
	public static final int DEFAULT_SIZE = 10; // Default stack size
	/*
	 * Class variables
	 */
	private static int iSize = DEFAULT_SIZE;
	private static int iTop = 3; // stack[0:9] with four defined values
	private static char[] aCharStack = new char[] { 'a', 'b', 'c', 'd', '$', '$', '$', '$', '$', '$' };

	/**
	 * recored access count
	 */
	private static long accessCounter = 0l;

	// Default constructor
	public CharStack() {
		// Just do nothing
	}

	// Constructor with supplied size
	public CharStack(int piSize) throws CharStackInvalidSizeException {
		if (piSize < MIN_SIZE || piSize > MAX_SIZE)
			throw new CharStackInvalidSizeException(piSize);
		if (piSize != DEFAULT_SIZE) {
			CharStack.aCharStack = new char[piSize];
			// Fill in with letters of the alphabet and keep
			// 6 free blocks
			for (int i = 0; i < piSize - 6; i++)
				CharStack.aCharStack[i] = (char) ('a' + i);
			for (int i = 1; i <= 6; i++)
				CharStack.aCharStack[piSize - i] = '$';
			CharStack.iTop = piSize - 7;
			CharStack.iSize = piSize;
		}
	}

	/*
	 * Picks a value from the top without modifying the stack
	 */
	public static char pick() throws CharStackEmptyException {
		if (iTop == -1)
			throw new CharStackEmptyException();
		++accessCounter;
		return aCharStack[iTop];
	}

	/*
	 * Returns arbitrary value from the stack array
	 */
	public char getAt(int piPosition) throws CharStackInvalidAceessException {
		if (piPosition < 0 || piPosition >= iSize)
			throw new CharStackInvalidAceessException();
		++accessCounter;
		return CharStack.aCharStack[piPosition];
	}

	/*
	 * Standard push operation
	 */
	public static void push(char pcChar) throws CharStackFullException {
		if (iTop == iSize - 1)
			throw new CharStackFullException();
		++accessCounter;
		aCharStack[++iTop] = pcChar;
	}

	/*
	 * Standard pop operation
	 */
	public static char pop() throws CharStackEmptyException {
		if (iTop == -1)
			throw new CharStackEmptyException();
		++accessCounter;
		char cChar = aCharStack[iTop];
		aCharStack[iTop--] = '$'; // Leave prev. value undefined
		return cChar;
	}

	/* Getters */
	public int getSize() {
		return CharStack.iSize;
	}

	public int getTop() {
		return CharStack.iTop;
	}

	public long getAccessCounter() {
		return accessCounter;
	}
}