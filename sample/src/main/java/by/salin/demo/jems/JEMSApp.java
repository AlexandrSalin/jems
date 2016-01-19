package by.salin.demo.jems;

import android.app.Application;
import android.content.Context;

import by.salin.apps.jems.EventHandlerCallback;
import by.salin.apps.jems.JEMS;
import by.salin.apps.jems.impl.Event;
import by.salin.apps.logger.LOG;

/**
 * App instance.
 * Created by Alexandr.Salin on 1/3/15.
 */
public class JEMSApp extends Application implements EventHandlerCallback {
    private static JEMSApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        LOG.setDebug(true);
        instance = this;
        JEMS.dispatcher().removeListener(this);
    }

    public static Context getInstance() {
        return instance.getApplicationContext();
    }

    @Override
    public void onEvent(Event event) {

    }
}
