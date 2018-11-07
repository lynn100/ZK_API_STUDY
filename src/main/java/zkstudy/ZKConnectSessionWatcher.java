package zkstudy;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 会话重连的类
 */
public class ZKConnectSessionWatcher implements Watcher {

   final static Logger log = LoggerFactory.getLogger(ZKConnectSessionWatcher.class);

    public static final String zkServerPath = "localhost:2181";
    public static final Integer timeout = 5000;

    public static void main(String[] args) throws Exception{
        ZooKeeper zooKeeper = new ZooKeeper(zkServerPath,timeout,new ZKConnectSessionWatcher());
        long sessionId = zooKeeper.getSessionId();
        //将sessionId转化为16进制
        String sid = "0x" + Long.toHexString(sessionId);
        System.out.println(sid);
        byte[] sessionPassword = zooKeeper.getSessionPasswd();

        log.warn("正在连接...");
        log.warn("连接状态：",zooKeeper.getState());
        zooKeeper.getState();
        new Thread().sleep(1000);

        log.warn("连接状态：",zooKeeper.getState());
        zooKeeper.getState();
        new Thread().sleep(200);


        //会话重连
        log.warn("会话从重连...");
        ZooKeeper zk = new ZooKeeper(zkServerPath,timeout,new ZKConnectSessionWatcher(),sessionId,sessionPassword);
        log.warn("连接状态：",zk.getState());
        new Thread().sleep(1000);

    }

    public void process(WatchedEvent watchedEvent) {
        log.warn("",watchedEvent);
    }
}
