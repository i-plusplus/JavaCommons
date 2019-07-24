package pool;

import java.io.Closeable;

/**
 * Created by paras.mal on 20/7/19.
 */
public interface ConnectionFactory<T extends Closeable> {

    T getNewConnection();

}
