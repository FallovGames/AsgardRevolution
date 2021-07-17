package services;

import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;
import l2ar.commons.threading.RunnableImpl;
import l2ar.commons.time.cron.SchedulingPattern;
import l2ar.gameserver.Config;
import l2ar.gameserver.ThreadPoolManager;
import l2ar.gameserver.listener.script.OnInitScriptListener;
import l2ar.gameserver.manager.ReflectionManager;
import l2ar.gameserver.model.instances.DoorInstance;
import l2ar.gameserver.scripts.Functions;

import java.util.concurrent.ScheduledFuture;

public class DoorTools extends Functions implements OnInitScriptListener {
    private static long getMillsRemainingToPattern(final String pattern) {
        final long now = System.currentTimeMillis();
        return new SchedulingPattern(pattern).next(now) - now;
    }

    private static Mutable<ScheduledFuture<?>> scheduleAtCron(final RunnableImpl r, final String p) {
        final MutableObject<ScheduledFuture<?>> futureRef = new MutableObject<>();
        futureRef.setValue(ThreadPoolManager.getInstance().schedule(new RunnableImpl() {
            @Override
            public void runImpl() {
                try {
                    r.run();
                } finally {
                    futureRef.setValue(ThreadPoolManager.getInstance().schedule(this, getMillsRemainingToPattern(p)));
                }
            }
        }, getMillsRemainingToPattern(p)));
        return futureRef;
    }

    @Override
    public void onInit() {
        if (Config.ENABLE_ON_TIME_DOOR_OPEN) {
            scheduleAtCron(new RunnableImpl() {
                @Override
                public void runImpl() {
                    final DoorInstance theRoor = ReflectionManager.DEFAULT.getDoor(Config.ON_TIME_OPEN_DOOR_ID);
                    if (theRoor != null) {
                        theRoor.openMe(null, true);
                    }
                }
            }, Config.ON_TIME_OPEN_PATTERN);
        }
    }
}
