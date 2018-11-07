package zkstudy;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * 判断节点是否存在demo
 */
public class ZKNodeExist implements Watcher {
    private ZooKeeper zooKeeper = null;

    public static final String zkServerPath = "localhost:2181";
    public static final Integer timeout = 5000;
    private static Stat stat = new Stat();

    public ZKNodeExist(){}

    public ZooKeeper getZooKeeper() {
        return zooKeeper;
    }

    public void setZooKeeper(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }

    public static String getZkServerPath() {
        return zkServerPath;
    }

    public static Integer getTimeout() {
        return timeout;
    }

    public static Stat getStat() {
        return stat;
    }

    public static void setStat(Stat stat) {
        ZKNodeExist.stat = stat;
    }

    public static CountDownLatch getCountDown() {
        return countDown;
    }

    public static void setCountDown(CountDownLatch countDown) {
        ZKNodeExist.countDown = countDown;
    }

    public ZKNodeExist(String connectString){
        try {
            zooKeeper = new ZooKeeper(connectString,timeout,new ZKNodeExist());
        } catch (IOException e) {
            e.printStackTrace();
            if (zooKeeper != null) {
                try {
                    zooKeeper.close();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private static CountDownLatch countDown = new CountDownLatch(1);

    public static void main(String[] args) throws KeeperException, InterruptedException {
        ZKNodeExist zkServer = new ZKNodeExist(zkServerPath);
        stat = zkServer.getZooKeeper().exists("/test",true);
        if (stat != null) {
            System.out.println("查询的节点版本为datavaesion:" + stat.getAversion());
        }else {
            System.out.println("该节点不存在...");
        }
        countDown.countDown();
    }

    public void process(WatchedEvent event) {
        if (event.getType() == Event.EventType.NodeCreated) {
            System.out.println("NodeCreated");
            countDown.countDown();
        } else if (event.getType() == Event.EventType.NodeDataChanged) {
            System.out.println("NodeDataChanged");
            countDown.countDown();
        } else if (event.getType() == Event.EventType.NodeDeleted) {
            System.out.println("NodeDeleted");
            countDown.countDown();
        }
    }
}
