package by.salin.apps.logger.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.salin.apps.jems.JEMS;
import by.salin.apps.logger.InnerLogger;

/**
 * Created by satalin on 10/12/14.
 */
public class LogbackLogger implements InnerLogger {
    protected static final Logger logger = LoggerFactory.getLogger(JEMS.TAG);

    @Override
    public void D(String msg) {
        logger.debug(msg);
    }

    @Override
    public void I(String msg) {
        logger.info(msg);
    }

    @Override
    public void W(String msg) {
        logger.warn(msg);
    }

    @Override
    public void W(String msg, Throwable e) {
        logger.warn(msg, e);
    }

    @Override
    public void E(String msg) {
        logger.error(msg);
    }

    @Override
    public void E(String msg, Throwable e) {
        logger.error(msg, e);
    }
}
