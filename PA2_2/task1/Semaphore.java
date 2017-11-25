package task1;
// Source code for semaphore class:

/**
 * it can be initialized only to a non-negative value.
 * If the value of a semaphore becomes negative (via Wait() operation),
 * then its absolute value indicates the number of processes/threads on its wait queue.
 */
public class Semaphore {
    private int value;

    public Semaphore(int value) {
        if(value < 0){
        	System.out.println("Initial value cannot be negative.");
        	System.exit(-1);
        }else {
            this.value = value;
        }
    }

    public Semaphore() {
        this(0);
    }

    public synchronized void Wait() {
        if (this.value <= 0) {//resources are not available (that is, some threads are waiting for the resources)
            try {
            	this.value--;// put it in the waiting queue
                wait();
                this.value++;
            } catch (InterruptedException e) {
                System.out.println("Semaphore::Wait() - caught InterruptedException: " + e.getMessage());
                e.printStackTrace();
            }
        }
        this.value--;
    }

    public synchronized void Signal() {
        ++this.value;//release one resource
        notify();
    }

    public synchronized void P() {
        this.Wait();
    }

    public synchronized void V() {
        this.Signal();
    }

	public synchronized int getValue(){
		return this.value;
	}
}
