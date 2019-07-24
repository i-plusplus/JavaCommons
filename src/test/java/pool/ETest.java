package pool;

/**
 * Created by paras.mal on 21/7/19.
 */
public class ETest implements Runnable {

    ConnectionTest test;
    ConnectionPool<ConnectionTest> cp;
    public ETest(ConnectionTest test, ConnectionPool<ConnectionTest> cp){
        this.test = test;
        this.cp = cp;
    }

    public void run(){
        test.execute();
        cp.release(test);

    }
}
