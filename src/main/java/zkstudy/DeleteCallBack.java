package zkstudy;

import org.apache.zookeeper.AsyncCallback;

public class DeleteCallBack implements  AsyncCallback.VoidCallback {

    public void processResult(int i, String path, Object ctx) {
        System.out.println("删除的节点：" + path);
        System.out.println((String) ctx);
    }
}
