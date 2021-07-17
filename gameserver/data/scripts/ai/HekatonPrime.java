package ai;

import l2ar.gameserver.ai.Fighter;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.instances.NpcInstance;

public class HekatonPrime extends Fighter {
    private long _lastTimeAttacked;

    public HekatonPrime(final NpcInstance actor) {
        super(actor);
    }

    @Override
    protected void onEvtSpawn() {
        super.onEvtSpawn();
        _lastTimeAttacked = System.currentTimeMillis();
    }

    @Override
    protected boolean thinkActive() {
        if (_lastTimeAttacked + 600000L < System.currentTimeMillis()) {
            if (getActor().getMinionList().hasMinions()) {
                getActor().getMinionList().deleteMinions();
            }
            getActor().deleteMe();
            return true;
        }
        return false;
    }

    @Override
    protected void onEvtAttacked(final Creature attacker, final int damage) {
        _lastTimeAttacked = System.currentTimeMillis();
        super.onEvtAttacked(attacker, damage);
    }
}
