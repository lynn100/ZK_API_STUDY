package countdown;

import java.util.concurrent.CountDownLatch;

public class StationShanghai extends DangerCenter {
    public StationShanghai(CountDownLatch cuntDown) {
        super(cuntDown, "上海调度站...");
    }

    public void check() {
        System.out.println("正在检查["+ this.getStation()+"]...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("检查["+ this.getStation() + "]完毕，可以发车");
    }
}
