package by.salin.apps.logger;

import by.salin.apps.logger.impl.AndroidLogger;

/**
 * Created by satalin on 10/12/14.
 */
public class LOG {

    private static boolean debug = false;

    private static final InnerLogger logger = getLogger();

    private static InnerLogger getLogger() {
        return new AndroidLogger();
    }

    public static void setLogger(InnerLogger logger) {
        logger = logger;
    }

    public static void D(String msg) {
        if (debug) {
            logger.D(msg);
        }
    }

    public static void I(String msg) {
        if (debug) {
            logger.I(msg);
        }
    }

    public static void W(String msg) {
        if (debug) {
            logger.W(msg);
        }
    }

    public static void W(String msg, Throwable e) {
        if (debug) {
            logger.W(msg, e);
        }
    }

    public static void E(String msg) {
        if (debug) {
            logger.E(msg);
        }
    }

    public static void E(String msg, Throwable e) {
        if (debug) {
            logger.E(msg, e);
        }
    }

    public static void setDebug(boolean DEBUG) {
        debug = DEBUG;
    }
}
