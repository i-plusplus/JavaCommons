package pool;

import javafx.util.Pair;

import java.io.Closeable;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

/**
 * Created by paras.mal on 20/7/19.
 */
public class ConnectionPool<T extends Closeable> {

    ConnectionFactory<T> connectionFactory;
    Queue<Pair<T,Long>> queue = new ArrayDeque<Pair<T, Long>>(10);
    Set<T> inUseConnections = new HashSet<T>();
    Producer<T> producer;
    Semaphore semaphore;
    Thread asyncHandler;
    Configurations configurations;
    public ConnectionPool(ConnectionFactory<T> connectionFactory, Configurations configurations){
        this.connectionFactory = connectionFactory;
        this.configurations = configurations;
        this.producer = new Producer<T>(queue, inUseConnections, configurations, connectionFactory);
        this.semaphore = new Semaphore(1);
        this.asyncHandler = new Thread(new AyncHandler<T>(producer, semaphore));
        asyncHandler.start();
    }
    public T getConnection(){
        try {
            System.out.println("get before " + (queue.size()+inUseConnections.size()));
            T connection;
            synchronized (queue) {
                connection = queue.remove().getKey();
                inUseConnections.add(connection);
            }
            System.out.println("get after " + (queue.size()+inUseConnections.size()));
            return connection;
        }catch (NoSuchElementException e){
            if(inUseConnections.size() + queue.size() >= configurations.getMaxConnections()){
                throw new RuntimeException("max connections in use");
            }
            producer.produce();
            return getConnection();
        }
    }

    public void release(T connection){
        System.out.println("rel before " + (queue.size()+inUseConnections.size()));
        synchronized (queue) {
            inUseConnections.remove(connection);
            queue.add(new Pair<T, Long>(connection, System.currentTimeMillis()));
        }
        System.out.println("rel after " + (queue.size()+inUseConnections.size()));
        if((queue.size() + inUseConnections.size())>=configurations.getMaxConnections()){
            try {
                semaphore.release();
            }catch (Throwable e){

            }
        }
    }

    public static void main(String s[]) throws Exception{
        long l1 = System.nanoTime();
        for(int i = 0;i<10000;i++) {
          //  long l =  System.nanoTime();
            for(InetAddress ni : Collections.list(NetworkInterface.getNetworkInterfaces().nextElement().getInetAddresses())){
               System.out.println(ni.getAddress());
            }
         //   System.out.println(UUID.randomUUID().toString());
        }
        System.out.println(System.nanoTime() - l1);
    }
}
