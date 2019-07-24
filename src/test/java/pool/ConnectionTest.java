package pool;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by paras.mal on 21/7/19.
 */
public class ConnectionTest implements Closeable {
    static int i = 0;
    public void execute(){
        try{
            Thread.sleep(10);
            System.out.println("finished " + i++);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void close() throws IOException {

    }
}
