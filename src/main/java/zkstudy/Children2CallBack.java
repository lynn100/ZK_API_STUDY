package zkstudy;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.data.Stat;

import java.util.List;

public class Children2CallBack implements AsyncCallback.Children2Callback {
    public void processResult(int i, String path, Object ctx, List<String> children, Stat stat) {
        for (String str : children) {
            System.out.println(str);
        }
        System.out.println("ChildrenCallBack" + path);
        System.out.println((String)ctx);
        System.out.println(stat.toString());
    }
}
