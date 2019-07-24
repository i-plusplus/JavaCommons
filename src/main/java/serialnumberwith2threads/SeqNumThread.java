package serialnumberwith2threads;

import java.util.concurrent.Semaphore;

/**
 * Created by paras.mal on 20/7/19.
 */
public class SeqNumThread implements Runnable{

    Semaphore a,b;
    int x = 1;
    int k ;

    public SeqNumThread(Semaphore a, Semaphore b, int x, int k){
        this.a = a;
        this.b = b;
        this.x = x;
        this.k = k;
    }
    public void run(){
        while(x < k){
            try {
                a.acquire();
                System.out.println(x);
                x = x + 2;
                b.release();
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
    }


}
