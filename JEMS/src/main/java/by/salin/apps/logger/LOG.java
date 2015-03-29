package by.salin.apps.logger;

import by.salin.apps.logger.impl.AndroidLogger;

/**
 * Created by satalin on 10/12/14.
 */
public class LOG {

    private static final boolean DEBUG = false;

    private static final InnerLogger logger = getLogger();

    private static InnerLogger getLogger() {
        return new AndroidLogger();
    }

    public static void setLogger(InnerLogger logger) {
        logger = logger;
    }

    public static void D(String msg) {
        if (DEBUG) {
            logger.D(msg);
        }
    }

    public static void I(String msg) {
        if (DEBUG) {
            logger.I(msg);
        }
    }

    public static void W(String msg) {
        if (DEBUG) {
            logger.W(msg);
        }
    }

    public static void W(String msg, Throwable e) {
        if (DEBUG) {
            logger.W(msg, e);
        }
    }

    public static void E(String msg) {
        if (DEBUG) {
            logger.E(msg);
        }
    }

    public static void E(String msg, Throwable e) {
        if (DEBUG) {
            logger.E(msg, e);
        }
    }

}
