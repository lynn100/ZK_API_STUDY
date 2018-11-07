package zkstudy;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import sun.security.util.Pem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 操作节点acl演示
 */
public class ZKNodeAcl implements Watcher {
    private ZooKeeper zooKeeper =null;

    public static String zkServerPath = "localhost:2181";
    public static Integer timeout = 5000;

    public ZKNodeAcl(){}

    public ZooKeeper getZooKeeper() {
        return zooKeeper;
    }

    public void setZooKeeper(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }

    public ZKNodeAcl(String connectString){
        try {
            zooKeeper = new ZooKeeper(connectString,timeout,new ZKNodeAcl());
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

    public void createZKNode(String path, byte[] data, List<ACL> acls){
        String result = "";
        try {
            result = zooKeeper.create(path,data,acls, CreateMode.PERSISTENT);
            System.out.println("创建节点：\t" + result + "\t成功。。。");
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws KeeperException, InterruptedException {
        ZKNodeAcl zkServer = new ZKNodeAcl(zkServerPath);
        //acl任何人都可以访问
//        zkServer.createZKNode("/study","test".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE);

        //自定义用户认证访问
//        List<ACL> acls = new ArrayList<ACL>();
//        Id lee1001= new Id("digest",AclUtils.getDigestUserPwd("study:12345"));
//        Id lee1002= new Id("digest",AclUtils.getDigestUserPwd("study:12345"));
//        acls.add(new ACL(ZooDefs.Perms.ALL,lee1001));
//        acls.add(new ACL(ZooDefs.Perms.READ,lee1002));
//        zkServer.createZKNode("/qq","3456774".getBytes(),acls);

        //注册过的用户必须通过addAuthIfo才能操作节点，参考节点addauth
//        zkServer.getZooKeeper().addAuthInfo("/qq","qq:3456774".getBytes());
//        zkServer.createZKNode("/sasdss","wewe".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL);
//        Stat stat = new Stat();
//        byte[] data = zkServer.getZooKeeper().getData("/wsefrw",false,stat);
//        System.out.println(new String(data));

        //ip方式的acl
        List<ACL> aclsIP = new ArrayList<ACL>();
        Id ipId1 = new Id("ip","192.168.1.1");
        zkServer.createZKNode("/gjfdkf","rer".getBytes(),aclsIP);

        //验证ip是否有权限
//        zkServer.getZooKeeper().setData("/gjfdkf","sadwew".getBytes(),0);

    }

    public void process(WatchedEvent watchedEvent) {

    }
}
