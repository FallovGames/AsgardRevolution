package ai.isle_of_prayer;

import l2ar.commons.util.Rnd;
import l2ar.gameserver.ai.Fighter;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.Player;
import l2ar.gameserver.model.instances.NpcInstance;

public class Shade extends Fighter {
    private static final int DESPAWN_TIME = 300000;
    private static final int BLUE_CRYSTAL = 9595;
    private long _wait_timeout;
    private boolean _wait;

    public Shade(final NpcInstance actor) {
        super(actor);
        _wait_timeout = 0L;
        _wait = false;
    }

    @Override
    protected boolean thinkActive() {
        final NpcInstance actor = getActor();
        if (actor.isDead()) {
            return true;
        }
        if (_def_think) {
            doTask();
            _wait = false;
            return true;
        }
        if (!_wait) {
            _wait = true;
            _wait_timeout = System.currentTimeMillis() + 300000L;
        }
        if (_wait_timeout != 0L && _wait && _wait_timeout < System.currentTimeMillis()) {
            actor.deleteMe();
            return true;
        }
        return super.thinkActive();
    }

    @Override
    protected boolean randomWalk() {
        return false;
    }

    @Override
    protected void onEvtDead(final Creature killer) {
        if (killer != null) {
            final Player player = killer.getPlayer();
            if (player != null) {
                final NpcInstance actor = getActor();
                if (Rnd.chance(10)) {
                    actor.dropItem(player, 9595, 1L);
                }
            }
        }
        super.onEvtDead(killer);
    }
}
