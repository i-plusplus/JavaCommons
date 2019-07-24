package pool;

import java.util.Map;

/**
 * Created by paras.mal on 21/7/19.
 */
public class Configurations {

    private int maxConnections;
    private int minConnections;
    private int idleTime;

    public Configurations setIdleTime(int idleTime) {
        this.idleTime = idleTime;
        return this;
    }

    public int getIdleTime() {
        return idleTime;
    }

    public Configurations setMaxConnections(int maxConnections){
        this.maxConnections = maxConnections;
        return this;
    }
    public Configurations setMinConnections(int minConnections){
        this.minConnections = minConnections;
        return this;
    }

    public int getMinConnections() {
        return minConnections;
    }

    public int getMaxConnections(){
        return maxConnections;
    }

    public Configurations(){

    }



}
