package zkstudy;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ZKGetChildrenList implements Watcher {

    private ZooKeeper zooKeeper = null;

    public static final String zkServerPath = "localhost:2181";
    public static final Integer timeout = 5000;
    private static Stat stat = new Stat();

    public ZKGetChildrenList(){}

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
        ZKGetChildrenList.stat = stat;
    }

    public static CountDownLatch getCountDown() {
        return countDown;
    }

    public static void setCountDown(CountDownLatch countDown) {
        ZKGetChildrenList.countDown = countDown;
    }

    public ZKGetChildrenList(String connectString){
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
        ZKGetChildrenList zkServer = new ZKGetChildrenList(zkServerPath);

        List<String> strChildList = zkServer.getZooKeeper().getChildren("/test",true);
        for (String str : strChildList) {
            System.out.println(str);
        }
        //异步，回调函数
        String ctx = "{'test123':'test123CallBack'}";
        zkServer.getZooKeeper().getChildren("/test",true,new ChildrenCallBack(),ctx);
        //zkServer.getZooKeeper().getChildren("/test",true,new Children2CallBack(),ctx);
        countDown.countDown();
    }

    public void process(WatchedEvent watchedEvent) {
        try {
            if (watchedEvent.getType() == Event.EventType.NodeChildrenChanged) {
                System.out.println("NodeChildrenChanged");
                ZKGetChildrenList zkServer = new ZKGetChildrenList();
                List<String> strChildList = zkServer.getZooKeeper().getChildren(watchedEvent.getPath(),false);
                for (String str : strChildList) {
                    System.out.println(str);
                }
                countDown.countDown();
            } else if (watchedEvent.getType() == Event.EventType.NodeCreated) {
                System.out.println("NodeCreated");
            } else if (watchedEvent.getType() == Event.EventType.NodeDataChanged) {
                System.out.println("NodeDataChanged");
            } else if (watchedEvent.getType() == Event.EventType.NodeDeleted) {
                System.out.println("NodeDeleted");
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
