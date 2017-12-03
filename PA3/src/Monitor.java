import java.util.Arrays;
import java.util.LinkedList;

/**
 * Class Monitor To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Monitor {
    /*
     * ------------ Data members ------------
	 */

    private LinkedList<Integer> eatWaitingQueue;
    private LinkedList<Thread> talkWaitingQueue;

    private Boolean[] chopstick;

    // At the beginning, no one is talking
    private Boolean isSomeoneTalking = false;

    private int numberOfChopsticks = 0;

    /**
     * Constructor Set appropriate number of chopsticks based on the # of
     * philosophers
     */
    public Monitor(int piNumberOfPhilosophers) {
        this.numberOfChopsticks = piNumberOfPhilosophers;
        init();
    }

	/*
     * -------- User-defined monitor procedures -----------
	 */

    /**
     * Grants request (returns) to eat when both chopsticks/forks are available.
     * Else forces the philosopher to wait()
     */
    public synchronized void pickUp(final int piTID) {
        int left = piTID - 1;
        int right = piTID % this.numberOfChopsticks;
        // if resources are not available or someone has reserved these resources
        if (this.chopstick[left] || this.chopstick[right] || resourcesIsNeededByOthers(piTID) != 0) {
            try {
                this.eatWaitingQueue.add(piTID);

                // ----------------------------------------------------debug-----------------------------------------------------------------------
                String availability = (this.chopstick[left] ? left + "" : "") + (this.chopstick[right] ? right + "" : "");
                if (availability.equals("")) {
                    System.out.println("Thread " + piTID + ": Chopstick is reserved by " + resourcesIsNeededByOthers(piTID) + ", add " + piTID + " into eatWaitingQueue" + Arrays.toString(this.eatWaitingQueue.toArray()));
                } else {
                    System.out.println("Thread " + piTID + ": Chopstick " + availability + " are not available, add " + piTID + " into eatWaitingQueue" + Arrays.toString(this.eatWaitingQueue.toArray()));
                }
                // ----------------------------------------------------debug-----------------------------------------------------------------------

                this.wait();
                while (getFirstRunnable() != piTID) {// I am not the first one who reserved these resources
                    //System.out.println("Thread " + piTID + ": Not the first one can run, keep " + piTID + " at its position and wait." + Arrays.toString(this.eatWaitingQueue.toArray()));
                    this.wait();
                }
                this.eatWaitingQueue.remove(new Integer(piTID));// I am the first one who reserved these resources
                //System.out.println("Thread " + piTID + ": Is the first one can run, pop " + piTID + " out." + Arrays.toString(this.eatWaitingQueue.toArray()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.chopstick[left] = true;
        this.chopstick[right] = true;
    }

    /**
     * When a given philosopher's done eating, they put the chopsticks/forks
     * down and let others know they are available.
     */
    public synchronized void putDown(final int piTID) {
        int left = piTID - 1;
        int right = piTID % this.numberOfChopsticks;
        this.chopstick[left] = false;
        this.chopstick[right] = false;
        this.notifyAll();
    }

    /**
     * Only one philosopher at a time is allowed to philosophy (while she is not
     * eating).
     */
    public synchronized void requestTalk() {
        // check is there any philosopher talking or waiting to talk
        // if so, wait
        // otherwise, set state to be talking
        if (isSomeoneTalking || !talkWaitingQueue.isEmpty()) {
            try {
                this.talkWaitingQueue.add(Thread.currentThread());
                this.wait();
                while (this.talkWaitingQueue.peek() != Thread.currentThread()) {
                    // get notified, then check whether I am the first one who wants to talk
                    this.wait();
                }
                this.talkWaitingQueue.pop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        isSomeoneTalking = true;
    }

    /**
     * When one philosopher is done talking stuff, others can feel free to start
     * talking.
     */
    public synchronized void endTalk() {
        // reset talking state
        // notify
        isSomeoneTalking = false;
        this.notifyAll();
    }

    /**
     * Init waiting queues and chopsticks states
     */
    private void init() {
        eatWaitingQueue = new LinkedList<Integer>();
        talkWaitingQueue = new LinkedList<Thread>();
        chopstick = new Boolean[this.numberOfChopsticks];
        for (int i = 0; i < this.numberOfChopsticks; i++) {
            chopstick[i] = false;// no chopstick is in using
        }
    }

    /**
     * Check whether the resources requested by piTID has already be reserved by other threads
     * @param piTID
     * @return
     */
    private Integer resourcesIsNeededByOthers(int piTID) {
        for (Integer id : eatWaitingQueue) {
            int leftId = piTID - 1 == 0 ? this.numberOfChopsticks : piTID - 1;
            int rightId = (piTID + 1) % this.numberOfChopsticks;
            if (id == leftId || id == rightId) {
                return id;
            }
        }
        return 0;
    }

    /**
     * check the waiting queue, and return the id of the first thread runnable now
     * @return
     */
    private Integer getFirstRunnable() {
        for (Integer id : eatWaitingQueue) {
            int left = id - 1;
            int right = id % this.numberOfChopsticks;
            if (!chopstick[left] && !chopstick[right]) {
                return id;
            }
        }
        return 0;
    }
}

// EOF
