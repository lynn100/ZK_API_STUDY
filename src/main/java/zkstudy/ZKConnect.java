package zkstudy;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 会话连接类
 */
public class ZKConnect implements Watcher {
    final static Logger log = LoggerFactory.getLogger(ZKConnectSessionWatcher.class);

    public static final String zkServerPath = "localhost:2181";
    public static final Integer timeout = 5000;

    public static void main(String[] args) throws Exception {
        /**
         * 参数：
         * connectString：连接服务器的IP字符串，比如："192.168.1.1:2181，192.168.1.2:2181"
         *      可以是一个ip，也可以是多个ip，一个ip代表单机，多个ip代表集群；也可以是路径
         * sessionTimeout：超时时间，心跳接收不到了，那就是超时了
         * watcher：通知事件，如果有对应的事件触发，则会收到一个通知：如果不需要，那就设置为null。
         * canBeReadOnly：可读，当这个物理机节点断开后，还可以读到数据的，只是不能写，此时数据被读取到的可能是旧数据，此处设为false，不推存使用
         * sessionId：会话id
         * sessionPasswd：会话密码  当会话丢失后，可以依据sessionId和sessionPasswd重新获取会话
         */
        //建立连接
        ZooKeeper zooKeeper = new ZooKeeper(zkServerPath,timeout,new ZKConnect());

        log.warn("客户端开始连接..");
        log.warn("连接状态：｛｝",zooKeeper.getState());

        new Thread().sleep(1000);
        log.warn("连接状态：｛｝",zooKeeper.getState());
    }



    public void process(WatchedEvent watchedEvent) {
        log.warn("触发的事件...",watchedEvent);
    }
}
