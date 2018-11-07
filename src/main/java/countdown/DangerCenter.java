package countdown;

import java.util.concurrent.CountDownLatch;

/**
 * 抽象类，用于演示危险品化工车监控中心统一检查
 */
public abstract class DangerCenter implements Runnable{

    private CountDownLatch cuntDown;  //计数器
    private String station;           //调度站
    private boolean ok;               //调度站针对当前自己的站点进行检查，是否检查ok的标志

    public DangerCenter(CountDownLatch cuntDown, String station) {
        this.cuntDown = cuntDown;
        this.station = station;
        this.ok = false;
    }

    public void run(){
        try{
            check();
            ok = true;
        }catch (Exception e){
            e.printStackTrace();
            ok = false;
        }finally {
            if (cuntDown != null){
                cuntDown.countDown();
            }
        }

    }

    /**
     * 检查的方法
     */
    public abstract void  check();

    public CountDownLatch getCuntDown() {
        return cuntDown;
    }

    public void setCuntDown(CountDownLatch cuntDown) {
        this.cuntDown = cuntDown;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
}
