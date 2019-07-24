package pool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paras.mal on 21/7/19.
 */
public class Test {
    public static void main(String s[]) {
        ConnectionPool<ConnectionTest> cp = new ConnectionPool<ConnectionTest>(new CFTest(), new Configurations().setIdleTime(100).setMaxConnections(10).setMinConnections(2));
        int k =0;
        long l2 = System.currentTimeMillis();
        List<Thread> l = new ArrayList<Thread>(10000);
        while(k++<10000){
            try{
                Thread.sleep(1);
                Thread t = new Thread(new ETest(cp.getConnection(), cp));
                l.add(t);
                t.start();
            }catch (Throwable e){
                System.out.println(e.getMessage());
            }
        }
        for(Thread t : l){
            try {
                t.join();
            }catch (Exception e){

            }

        }
        l2 = System.currentTimeMillis()-l2;
        System.out.println(l2);
    }
}
