package by.salin.demo.jems;

import android.app.Application;
import android.content.Context;

/**
 * App instance.
 * Created by Alexandr.Salin on 1/3/15.
 */
public class JEMSApp extends Application {
    private static JEMSApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Context getInstance() {
        return instance.getApplicationContext();
    }
}
