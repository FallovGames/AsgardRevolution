package ai;

import l2ar.commons.util.Rnd;
import l2ar.gameserver.ai.Fighter;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.scripts.Functions;

public class FrightenedOrc extends Fighter {
    private boolean _sayOnAttack;

    public FrightenedOrc(final NpcInstance actor) {
        super(actor);
    }

    @Override
    protected void onEvtSpawn() {
        _sayOnAttack = true;
        super.onEvtSpawn();
    }

    @Override
    protected void onEvtAttacked(final Creature attacker, final int damage) {
        final NpcInstance actor = getActor();
        if (attacker != null && Rnd.chance(10) && _sayOnAttack) {
            Functions.npcSay(actor, "Don't kill me! If you show mercy I will pay you 10000 adena!");
            _sayOnAttack = false;
        }
        super.onEvtAttacked(attacker, damage);
    }
}
