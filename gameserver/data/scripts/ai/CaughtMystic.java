package ai;

import l2ar.commons.util.Rnd;
import l2ar.gameserver.ai.Mystic;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.scripts.Functions;

public class CaughtMystic extends Mystic {
    private static final int TIME_TO_LIVE = 60000;
    private final long TIME_TO_DIE;

    public CaughtMystic(final NpcInstance actor) {
        super(actor);
        TIME_TO_DIE = System.currentTimeMillis() + 60000L;
    }

    @Override
    public boolean isGlobalAI() {
        return true;
    }

    @Override
    protected void onEvtSpawn() {
        super.onEvtSpawn();
        if (Rnd.chance(75)) {
            Functions.npcSayCustomMessage(getActor(), "scripts.ai.CaughtMob.spawn");
        }
    }

    @Override
    protected void onEvtDead(final Creature killer) {
        if (Rnd.chance(75)) {
            Functions.npcSayCustomMessage(getActor(), "scripts.ai.CaughtMob.death");
        }
        super.onEvtDead(killer);
    }

    @Override
    protected boolean thinkActive() {
        final NpcInstance actor = getActor();
        if (System.currentTimeMillis() >= TIME_TO_DIE) {
            actor.deleteMe();
            return false;
        }
        return super.thinkActive();
    }
}
