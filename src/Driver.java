import java.util.concurrent.Semaphore;

public class Driver {

	public static void main(String[] args) {

	}

	int S1 = 0;
	int R1 = 0;

	Semaphore mutex = new Semaphore(1);// binary lock
	Semaphore block = new Semaphore(0);// resource lock

	void wait_() throws InterruptedException {// S1, R1
		
		// get the resource sheet, I want to reserve some resource
		mutex.acquire();// acquire lock
		S1--;// reserve resource
		R1--;// reserve resource
		mutex.release();// release lock
		// I am done with the reservation, now others can have this sheet.
		
		if (S1 < 0 || R1 < 0) {// but when I check the availability of resource, found no resource are available
			block.acquire();// I want my resources! But I have to wait! So I put my name on the waiting list.
		}else{
			// no need to wait because resource are already there, you can do whatever you would like do now.
		}
	}

	void signal_() throws InterruptedException {// S1, R1
		// I have to take a look at the resource sheet and check the availability of resource.
		// after producing if it's still none positive, that means someone is on the waiting list, so I have to notify him or her.
		// or, if it's positive, means I have produced more products than reservation, so no one would be on the waiting list.
		mutex.acquire();// acquire lock
		S1++;// produce
		R1++;// produce
		if (S1 <= 0 || R1 <= 0) {
			block.release();// sufficient resource have been produced
		}
		// what would happen if we do not check?
		// think of a scenario that multiple threads have invoked signal_() so the block would be positive
		// at when a same number of threads have invoked wait_(), and then one more thread has invoked wait_() again.
		// The block is positive, but the resource is empty. so the resource become non-consistent.
		mutex.release();// release lock
	}
}
