package zkstudy;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * zookeeper 获取节点数据的demo
 */
public class ZKGetNodeData implements Watcher {
    private ZooKeeper zooKeeper = null;

    public static final String zkServerPath = "localhost:2181";
    public static final Integer timeout = 5000;
    private static Stat stat = new Stat();

    public ZKGetNodeData(){}

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
        ZKGetNodeData.stat = stat;
    }

    public static CountDownLatch getCountDown() {
        return countDown;
    }

    public static void setCountDown(CountDownLatch countDown) {
        ZKGetNodeData.countDown = countDown;
    }


    public ZKGetNodeData(String connectString){
        try {
            zooKeeper = new ZooKeeper(connectString,timeout,new ZKGetNodeData());
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
        ZKGetNodeData zkserver = new ZKGetNodeData(zkServerPath);

        byte[] resByte = zkserver.getZooKeeper().getData("/test",true,stat);
        String result = new String(resByte);
        System.out.println("当前值：" + result);
        countDown.await();
    }

    public void process(WatchedEvent watchedEvent) {

        try {
            if (watchedEvent.getType() == Event.EventType.NodeDataChanged) {
                ZKGetNodeData zkServer = new ZKGetNodeData();
                byte[] resByte = zkServer.getZooKeeper().getData("/tese", false, stat);
                String result = new String(resByte);
                System.out.println("改变后的值：" + result);
                System.out.println("版本号变化deversion" + stat.getAversion());
            } else if (watchedEvent.getType() == Event.EventType.NodeCreated) {

            } else if (watchedEvent.getType() == Event.EventType.NodeChildrenChanged) {

            } else if (watchedEvent.getType() == Event.EventType.NodeDeleted) {

            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
