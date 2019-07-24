package pool;

import java.io.Closeable;
import java.util.concurrent.Semaphore;

/**
 * Created by paras.mal on 21/7/19.
 */
public class AyncHandler<T extends Closeable> implements Runnable {

    Producer<T> producer;
    Semaphore semaphore;
    public AyncHandler(Producer<T> producer, Semaphore semaphore){
        this.producer = producer;
        this.semaphore = semaphore;
    }
    public void run(){
        while(true){
            producer.produce();
            producer.release();
            try {
                semaphore.acquire();
            }catch (Exception e){

            }
        }
    }
}
