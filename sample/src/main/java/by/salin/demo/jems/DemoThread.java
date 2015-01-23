package by.salin.demo.jems;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import by.salin.apps.jems.JEMS;

/**
 * Created by satalin on 1/24/15.
 */
public class DemoThread extends Thread {
    public static final String TAG = "JEMS-demo";
    public static void launchDemo(){
        new DemoThread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0;i<100;i++) {
                    JEMS.dispatcher().sendEvent(new DemoJemsEvent(i));
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        Log.d(TAG,"interrupt",e);
                    }
                }
            }
        },TAG).start();
    }
    private DemoThread(Runnable runnable, String threadName) {
        super(runnable, threadName);
    }

}
