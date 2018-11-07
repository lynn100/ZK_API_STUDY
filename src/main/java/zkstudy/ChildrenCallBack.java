package zkstudy;

import org.apache.zookeeper.AsyncCallback;

import java.util.List;

public class ChildrenCallBack implements AsyncCallback.ChildrenCallback {
    public void processResult(int i, String path, Object ctx, List<String> children) {
        for (String str : children) {
            System.out.println(str);
        }
        System.out.println("ChildrenCallBack" + path);
        System.out.println((String)ctx);
    }
}
