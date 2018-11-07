package zkstudy;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

/**
 * 节点增加类
 */
public class ZKNodeOperator implements Watcher {

    private ZooKeeper zooKeeper = null;
    public static final String zkSererPath = "localhost:2181";
    public static final Integer timeout = 5000;

    public ZKNodeOperator(){}

    public static void main(String[] args) throws Exception{
        ZKNodeOperator zkServer = new ZKNodeOperator(zkSererPath);

//        zkServer.createZKNode("/testnode","testnode".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE);
        /**
         * 修改数据
         * 参数：path：节点路径  data：数据 version：数据状态
         * 这是同步方法，还有异步方法
         */
        Stat status = zkServer.getZooKeeper().setData("/testnode","xyz".getBytes(),1);
        System.out.println(status.getAversion());
        //删除一个节点 同步  异步
        zkServer.createZKNode("/hello","1234".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE);
//        zkServer.getZooKeeper().delete("/hello",0);

        String ctx = "{'delete':'success'}";
        zkServer.getZooKeeper().delete("/hello",0, new DeleteCallBack(),ctx);
        Thread.sleep(1000);
    }

    public ZKNodeOperator(String connectString){
        try {
            zooKeeper = new ZooKeeper(connectString,timeout,new ZKNodeOperator());
        } catch (IOException e) {
            e.printStackTrace();
            if(zooKeeper != null){
                try {
                    zooKeeper.close();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 创建节点的方法
     */
    public void createZKNode(String path, byte[] data, List<ACL> acls){
        String result = "";
        /**
         *同步或者异步创建节点，都不支持子节点的递归创建，异步有一个callback函数
         * 参数：
         * path：创建的路径    data：存储的数据byte[]
         * acl：控制权限策略  Ids.OPEN_ACL_UNSAFE  -->word:anyone:cdrwa
         *                  CREATOR_ALL_ACL --> auth:user:password:cdrwa
         * cerateMode:节点类型，是一个枚举
         *           PERSISTENT:持久节点   PERSISTENT_SEQUENTIAL:持久顺序节点  EPHMERAL:临时节点 EPHMERAL_SEQUENTIAL临时顺序节点:
         */
        try {
            //同步
//            result = zooKeeper.create(path,data,acls, CreateMode.EPHEMERAL);

            //异步
            String ctx = "{'create':'success'}";
            zooKeeper.create(path,data,acls,CreateMode.EPHEMERAL,new CreateCallBack(),ctx);

            System.out.println("创建节点：\t" + result + "\t成功...");
            new Thread().sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public ZooKeeper getZooKeeper() {
        return zooKeeper;
    }

    public void setZooKeeper(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }

    public void process(WatchedEvent watchedEvent) {

    }
}
