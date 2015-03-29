package by.salin.apps.logger.impl;

import android.util.Log;

import by.salin.apps.jems.JEMS;
import by.salin.apps.logger.InnerLogger;

/**
 * Created by satalin on 10/12/14.
 */
public class AndroidLogger implements InnerLogger {
    public static final String TAG = JEMS.TAG;

    @Override
    public void D(String msg) {
        Log.d(TAG, msg);
    }

    @Override
    public void I(String msg) {
        Log.i(TAG, msg);
    }

    @Override
    public void W(String msg) {
        Log.w(TAG, msg);
    }

    @Override
    public void W(String msg, Throwable e) {
        Log.w(TAG, msg, e);
    }

    @Override
    public void E(String msg) {
        Log.e(TAG, msg);
    }

    @Override
    public void E(String msg, Throwable e) {
        Log.e(TAG, msg, e);
    }
}
