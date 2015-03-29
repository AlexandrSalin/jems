package by.salin.apps.logger;

/**
 * Created by satalin on 10/12/14.
 */
public interface InnerLogger {

    public void D(String msg);

    public void I(String msg);

    public void W(String msg);

    public void W(String msg, Throwable e);

    public void E(String msg);

    public void E(String msg, Throwable e);
}
