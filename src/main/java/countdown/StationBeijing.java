package countdown;

import java.util.concurrent.CountDownLatch;

public class StationBeijing extends DangerCenter {
    public StationBeijing(CountDownLatch cuntDown) {
        super(cuntDown, "北京调度站");
    }

    public void check() {
        System.out.println("正在检查["+ this.getStation()+"]...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("检查["+ this.getStation() + "]完毕，可以发车");
    }
}
