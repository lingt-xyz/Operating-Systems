/**
 * Class DiningPhilosophers The main starter.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class DiningPhilosophers {
	/*
	 * ------------ Data members ------------
	 */

	/**
	 * This default may be overridden from the command line
	 */
	public static final int DEFAULT_NUMBER_OF_PHILOSOPHERS = 4;

	/**
	 * Dining "iterations" per philosopher thread while they are socializing
	 * there
	 */
	public static final int DINING_STEPS = 10;

	/**
	 * Our shared monitor for the philosophers to consult
	 */
	public static Monitor soMonitor = null;

	/*
	 * ------- Methods -------
	 */

	/**
	 * Main system starts up right here
	 */
	public static void main(String[] argv) {
		try {
			/*
			 * should be settable from the command line or the default if no
			 * arguments supplied.
			 */
			int iPhilosophers = 0;
			int number = getInputNumber(argv);
			if (number == 0) {
				iPhilosophers = DEFAULT_NUMBER_OF_PHILOSOPHERS;
			} else {
				iPhilosophers = number;
			}

			// Make the monitor aware of how many philosophers there are
			soMonitor = new Monitor(iPhilosophers);

			// Space for all the philosophers
			Philosopher aoPhilosophers[] = new Philosopher[iPhilosophers];
			System.out.println(iPhilosophers + " philosopher(s) came in for a dinner.");

			// Let'em sit down
			for (int j = 0; j < iPhilosophers; j++) {
				aoPhilosophers[j] = new Philosopher();
				aoPhilosophers[j].start();
			}

			// Philosophers to finish their dinner.
			for (int j = 0; j < iPhilosophers; j++)
				aoPhilosophers[j].join();

			System.out.println("All philosophers have left. System terminates normally.");
		} catch (InterruptedException e) {
			System.err.println("main():");
			reportException(e);
			System.exit(1);
		}
	} // main()

	/**
	 * Outputs exception information to STDERR
	 * 
	 * @param poException
	 *            Exception object to dump to STDERR
	 */
	public static void reportException(Exception poException) {
		System.err.println("Caught exception : " + poException.getClass().getName());
		System.err.println("Message          : " + poException.getMessage());
		System.err.println("Stack Trace      : ");
		poException.printStackTrace(System.err);
	}

	/**
	 * get a number from the input parameters
	 * if on input is given, return 0 as default
	 * @param argv
	 * @return
	 */
	private static int getInputNumber(String[] argv) {
		if(argv.length == 0){
			return 0;
		}else{
			try {
				int number = Integer.parseInt(argv[0]);
				if (number <= 1) {
					System.out.println("Invalid number, the value would be set to default.");
					return 0;
				} else {
					return number;
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid number, the value would be set to default.");
				return 0;
			}
			
		}
	}
}

// EOF
