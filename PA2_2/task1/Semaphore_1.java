package task1;
// Source code for semaphore class:

/**
 * it can be initialized only to a non-negative value.
 * If the value of a semaphore becomes negative (via Wait() operation),
 * then its absolute value indicates the number of processes/threads on its wait queue.
 */
public class Semaphore_1 {
    private int value;
    private int counter;

    public Semaphore_1(int value) {
        this.value = value;
        this.counter = 0;
    }

    public Semaphore_1() {
        this(0);
    }

    public synchronized void Wait() {
        while (this.value <= 0) {//resources are not available (that is, some threads are waiting for the resources)
            try {
                this.counter++;
                wait();
                this.counter--;
            } catch (InterruptedException e) {
                System.out.println("Semaphore::Wait() - caught InterruptedException: " + e.getMessage());
                e.printStackTrace();
            }
        }
        this.value--;// put it in the waiting queue
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
}
