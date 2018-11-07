package countdown;

import java.util.concurrent.CountDownLatch;

public class StationHefei extends DangerCenter{
    public StationHefei(CountDownLatch cuntDown) {
        super(cuntDown, "合肥调度站...");
    }

    public void check() {
        System.out.println("正在检查["+ this.getStation()+"]...");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("检查["+ this.getStation() + "]完毕，可以发车");
    }
}
