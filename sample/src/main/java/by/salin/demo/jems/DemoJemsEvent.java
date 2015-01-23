package by.salin.demo.jems;

import by.salin.apps.jems.impl.Event;

/**
 * Created by satalin on 1/24/15.
 */
public class DemoJemsEvent extends Event {

    private int timer;

    public DemoJemsEvent(int timer) {
        this.timer = timer;
    }

    @Override
    public String toString() {
        return "DemoJemsEvent{" +
                "timer=" + timer +
                '}';
    }
}
