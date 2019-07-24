package pool;

import javafx.util.Pair;

import java.io.Closeable;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by paras.mal on 20/7/19.
 */
public class Producer<T extends Closeable> {

    Queue<Pair<T,Long>> concurrentLinkedQueue;
    ConnectionFactory<T> connectionFactory;
    Set<T> inUseConnections;
    Configurations configurations;
    public Producer(Queue<Pair<T,Long>> concurrentLinkedQueue, Set<T> inUseConnections, Configurations configurations, ConnectionFactory<T> connectionFactory){
        this.concurrentLinkedQueue = concurrentLinkedQueue;
        this.connectionFactory = connectionFactory;
        this.inUseConnections = inUseConnections;
        this.configurations = configurations;
    }

    public void produce(){
        System.out.println("current " + (concurrentLinkedQueue.size() + inUseConnections.size()));
        synchronized (concurrentLinkedQueue) {
        while (concurrentLinkedQueue.size() < configurations.getMinConnections() && (concurrentLinkedQueue.size() + inUseConnections.size()) < configurations.getMaxConnections()) {

                concurrentLinkedQueue.add(new Pair<T, Long>(connectionFactory.getNewConnection(), System.currentTimeMillis()));
            }
        }
        System.out.println("current after " + (concurrentLinkedQueue.size() + inUseConnections.size()));
    }

    public void release(){
        synchronized (concurrentLinkedQueue) {
            while (concurrentLinkedQueue.size() > configurations.getMinConnections() && (System.currentTimeMillis() - concurrentLinkedQueue.peek().getValue()) > configurations.getIdleTime()) {
                try {
                    concurrentLinkedQueue.remove().getKey().close();

                } catch (Throwable e) {

                }
            }
        }

    }



}
