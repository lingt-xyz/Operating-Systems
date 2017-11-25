package task2;


import task0.charStackExceptions.CharStackEmptyException;
import task0.charStackExceptions.CharStackFullException;
import task0.charStackExceptions.CharStackInvalidAceessException;
import task1.Semaphore;
import task0.BaseThread;
import task0.CharStack;

// Source code for stack manager:

public class StackManager {
	// The Stack
	private static CharStack stack = new CharStack();
	private static final int NUM_ACQREL = 4; // Number of Producer/Consumer
												// threads
	private static final int NUM_PROBERS = 1; // Number of threads dumping stack
	private static int iThreadSteps = 3; // Number of steps they take
	// Semaphore declarations. Insert your code in the following:
	
	private static Semaphore MUTEX = new Semaphore(1);
	// The main()

	public static void main(String[] argv) {
		// Some initial stats...
		try {
			System.out.println("Main thread starts executing.");
			System.out.println("Initial value of top = " + stack.getTop() + ".");
			System.out.println("Initial value of stack top = " + CharStack.pick() + ".");
			System.out.println("Main thread will now fork several threads.");
		} catch (CharStackEmptyException e) {
			System.out.println("Caught exception: StackCharEmptyException");
			System.out.println("Message : " + e.getMessage());
			System.out.println("Stack Trace : ");
			e.printStackTrace();
		}
		/*
		 * The birth of threads
		 */
		Consumer ab1 = new Consumer();
		Consumer ab2 = new Consumer();
		System.out.println("Two Consumer threads have been created.");
		Producer rb1 = new Producer();
		Producer rb2 = new Producer();
		System.out.println("Two Producer threads have been created.");
		CharStackProber csp = new CharStackProber();
		System.out.println("One CharStackProber thread has been created.");
		/*
		 * start executing
		 */
		ab1.start();
		rb1.start();
		ab2.start();
		rb2.start();
		csp.start();
		/*
		 * Wait by here for all forked threads to die
		 */
		try {
			ab1.join();
			ab2.join();
			rb1.join();
			rb2.join();
			csp.join();
			// Some final stats after all the child threads terminated...
			System.out.println("System terminates normally.");
			System.out.println("Final value of top = " + stack.getTop() + ".");
			System.out.println("Final value of stack top = " + CharStack.pick() + ".");
			System.out.println("Final value of stack top-1 = " + stack.getAt(stack.getTop() - 1) + ".");
			System.out.println("Stack access count = " + stack.getAccessCounter());
		} catch (InterruptedException e) {
			System.out.println("Caught InterruptedException: " + e.getMessage());
			System.exit(1);
		} catch (Exception e) {
			System.out.println("Caught exception: " + e.getClass().getName());
			System.out.println("Message : " + e.getMessage());
			System.out.println("Stack Trace : ");
			e.printStackTrace();
		}
	} // main()
	/*
	 * Inner Consumer thread class
	 */

	static class Consumer extends BaseThread {
		private char copy; // A copy of a block returned by pop()

		public void run() {
			System.out.println("Consumer thread [TID=" + this.iTID + "] starts executing.");
			for (int i = 0; i < StackManager.iThreadSteps; i++) {
				// Insert your code in the following:
				StackManager.MUTEX.P();
				try {
					this.copy = CharStack.pop();
					System.out.println("Consumer thread [TID=" + this.iTID + "] pops character = " + this.copy);
				} catch (CharStackEmptyException e) {
					System.out.println("CharStack is empty.");
				}
				StackManager.MUTEX.V();
			}
			System.out.println("Consumer thread [TID=" + this.iTID + "] terminates.");
		}
	} // class Consumer
	
	/*
	 * Inner class Producer
	 */
	static class Producer extends BaseThread {
		private char block; // block to be returned

		public void run() {
			System.out.println("Producer thread [TID=" + this.iTID + "] starts executing.");
			for (int i = 0; i < StackManager.iThreadSteps; i++) {
				// Insert your code in the following:
				StackManager.MUTEX.P();
				try {
					this.block = (char)(CharStack.pick() + 1);
				} catch (CharStackEmptyException e) {
					this.block = 'a';
				} 
				try {
					CharStack.push(this.block);
					System.out.println("Producer thread [TID=" + this.iTID + "] pushes character = " + this.block);
				} catch (CharStackFullException e) {
					System.out.println("CharStack is full.");
				}
				StackManager.MUTEX.V();
			}
			System.out.println("Producer thread [TID=" + this.iTID + "] terminates.");
		}
	} // class Producer
	/*
	 * Inner class CharStackProber to dump stack contents
	 */

	static class CharStackProber extends BaseThread {
		public void run() {
			System.out.println("CharStackProber thread [TID=" + this.iTID + "] starts executing.");
			for (int i = 0; i < 2 * StackManager.iThreadSteps; i++) {
				// Insert your code in the following. Note that the stack state
				StackManager.MUTEX.P();
				//“Stack S = ([a],[b],[c],[$],[$],[$],[$],[$],[$],[$])”
				System.out.print("Stack S = (");
				for (int j = 0; j < stack.getSize(); j++) {
					try {
						if(j!=0){
							System.out.print(",");
						}
						System.out.print("[" + stack.getAt(j) + "]");
					} catch (CharStackInvalidAceessException e) {
						//e.printStackTrace();
					}
				}
				System.out.println(")");
				StackManager.MUTEX.V();
			}
		}
	} // class CharStackProber
} // class StackManager
