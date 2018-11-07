package countdown;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CheckStartUp {
    private static List<DangerCenter> stationList;
    private static CountDownLatch countDown;

    public CheckStartUp(){
    }

    public static boolean checkAllStation() throws Exception{
        //初始化3个调度站
        countDown = new CountDownLatch(3);

        stationList = new ArrayList<DangerCenter>();
        stationList.add(new StationBeijing(countDown));
        stationList.add(new StationShanghai(countDown));
        stationList.add(new StationHefei(countDown));

        //使用线程池
        Executor executor = Executors.newFixedThreadPool(stationList.size());

        for (DangerCenter center : stationList) {
            executor.execute(center);
        }
        //等待线程执行完毕
        countDown.await();

        for (DangerCenter center : stationList) {
            if (!center.isOk()) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) throws Exception {
        boolean result = CheckStartUp.checkAllStation();
        System.out.println("监控中心检测的结果为：" + result);
    }
}
