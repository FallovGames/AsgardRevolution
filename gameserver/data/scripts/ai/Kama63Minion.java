package ai;

import l2ar.commons.threading.RunnableImpl;
import l2ar.commons.util.Rnd;
import l2ar.gameserver.ThreadPoolManager;
import l2ar.gameserver.ai.CtrlEvent;
import l2ar.gameserver.ai.Fighter;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.World;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.scripts.Functions;

import java.util.concurrent.ScheduledFuture;

public class Kama63Minion extends Fighter {
    private static final int BOSS_ID = 18571;
    private static final int MINION_DIE_TIME = 25000;
    ScheduledFuture<?> _dieTask;
    private long _wait_timeout;
    private NpcInstance _boss;
    private boolean _spawned;

    public Kama63Minion(final NpcInstance actor) {
        super(actor);
        _wait_timeout = 0L;
        _spawned = false;
        _dieTask = null;
    }

    @Override
    protected void onEvtSpawn() {
        _boss = findBoss(18571);
        super.onEvtSpawn();
    }

    @Override
    protected boolean thinkActive() {
        if (_boss == null) {
            _boss = findBoss(18571);
        } else if (!_spawned) {
            _spawned = true;
            Functions.npcSayCustomMessage(_boss, "Kama63Boss");
            final NpcInstance minion = getActor();
            minion.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, _boss.getAggroList().getRandomHated(), Rnd.get(1, 100));
            _dieTask = ThreadPoolManager.getInstance().schedule(new DieScheduleTimerTask(minion, _boss), 25000L);
        }
        return super.thinkActive();
    }

    private NpcInstance findBoss(final int npcId) {
        if (System.currentTimeMillis() < _wait_timeout) {
            return null;
        }
        _wait_timeout = System.currentTimeMillis() + 15000L;
        final NpcInstance minion = getActor();
        if (minion == null) {
            return null;
        }
        for (final NpcInstance npc : World.getAroundNpc(minion)) {
            if (npc.getNpcId() == npcId) {
                return npc;
            }
        }
        return null;
    }

    @Override
    protected void onEvtDead(final Creature killer) {
        _spawned = false;
        if (_dieTask != null) {
            _dieTask.cancel(false);
            _dieTask = null;
        }
        super.onEvtDead(killer);
    }

    public class DieScheduleTimerTask extends RunnableImpl {
        NpcInstance _minion;
        NpcInstance _master;

        public DieScheduleTimerTask(final NpcInstance minion, final NpcInstance master) {
            _minion = null;
            _master = null;
            _minion = minion;
            _master = master;
        }

        @Override
        public void runImpl() {
            if (_master != null && _minion != null && !_master.isDead() && !_minion.isDead()) {
                _master.setCurrentHp(_master.getCurrentHp() + _minion.getCurrentHp() * 5.0, false);
            }
            Functions.npcSayCustomMessage(_minion, "Kama63Minion");
            _minion.doDie(_minion);
        }
    }
}
