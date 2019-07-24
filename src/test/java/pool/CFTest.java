package pool;

/**
 * Created by paras.mal on 21/7/19.
 */
public class CFTest implements ConnectionFactory<ConnectionTest> {
    public ConnectionTest getNewConnection() {
        return new ConnectionTest();
    }
}
