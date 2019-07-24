package serialnumberwith2threads;

import java.util.concurrent.Semaphore;

/**
 * Created by paras.mal on 20/7/19.
 */
public class SequenceNumbers {

    public void execute(int k) throws Exception{
        Semaphore odd = new Semaphore(1);
        Semaphore even = new Semaphore(1);
        Thread e = new Thread(new SeqNumThread(odd,even, 1, k));
        Thread o = new Thread(new SeqNumThread(even,odd, 2, k));
        even.acquire();
        o.start();
        e.start();
        o.join();
        e.join();
    }

}
