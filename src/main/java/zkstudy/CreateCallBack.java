package zkstudy;

import org.apache.zookeeper.AsyncCallback;

/**
 * 回调函数
 */
public class CreateCallBack implements AsyncCallback.StringCallback {

    public void processResult(int i, String path, Object ctx, String s1) {
        System.out.println("创建节点：" + path);
        System.out.println((String) ctx);
    }
}
