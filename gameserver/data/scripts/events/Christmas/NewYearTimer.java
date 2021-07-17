package events.Christmas;

import l2ar.commons.threading.RunnableImpl;
import l2ar.gameserver.Announcements;
import l2ar.gameserver.ThreadPoolManager;
import l2ar.gameserver.manager.ServerVariables;
import l2ar.gameserver.model.GameObjectsStorage;
import l2ar.gameserver.model.Skill;
import l2ar.gameserver.network.lineage2.serverpackets.MagicSkillUse;
import l2ar.gameserver.tables.SkillTable;

import java.util.Calendar;

public class NewYearTimer {
    private static NewYearTimer instance;

    public NewYearTimer() {
        if (instance != null) {
            return;
        }
        instance = this;
        if (!isActive()) {
            return;
        }
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
        c.set(Calendar.MONTH, 0);
        c.set(Calendar.DATE, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        while (getDelay(c) < 0L) {
            c.set(Calendar.YEAR, c.get(Calendar.YEAR) + 1);
        }
        ThreadPoolManager.getInstance().schedule(new NewYearAnnouncer("\u0421 \u041d\u043e\u0432\u044b\u043c, " + c.get(Calendar.YEAR) + ", \u0413\u043e\u0434\u043e\u043c!!!"), getDelay(c));
        c.add(Calendar.SECOND, -1);
        ThreadPoolManager.getInstance().schedule(new NewYearAnnouncer("1"), getDelay(c));
        c.add(Calendar.SECOND, -1);
        ThreadPoolManager.getInstance().schedule(new NewYearAnnouncer("2"), getDelay(c));
        c.add(Calendar.SECOND, -1);
        ThreadPoolManager.getInstance().schedule(new NewYearAnnouncer("3"), getDelay(c));
        c.add(Calendar.SECOND, -1);
        ThreadPoolManager.getInstance().schedule(new NewYearAnnouncer("4"), getDelay(c));
        c.add(Calendar.SECOND, -1);
        ThreadPoolManager.getInstance().schedule(new NewYearAnnouncer("5"), getDelay(c));
    }

    public static NewYearTimer getInstance() {
        if (instance == null) {
            new NewYearTimer();
        }
        return instance;
    }

    private static boolean isActive() {
        return "on".equalsIgnoreCase(ServerVariables.getString("Christmas", "off"));
    }

    private long getDelay(final Calendar c) {
        return c.getTime().getTime() - System.currentTimeMillis();
    }

    

    private class NewYearAnnouncer extends RunnableImpl {
        private final String message;

        private NewYearAnnouncer(final String message) {
            this.message = message;
        }

        @Override
        public void runImpl() {
            Announcements.getInstance().announceToAll(message);
            if (message.length() == 1) {
                return;
            }
            GameObjectsStorage.getPlayers().forEach(player -> {
                final Skill skill = SkillTable.getInstance().getInfo(3266, 1);
                final MagicSkillUse msu = new MagicSkillUse(player, player, 3266, 1, skill.getHitTime(), 0L);
                player.broadcastPacket(msu);
            });
            instance = null;
            new NewYearTimer();
        }
    }
}
