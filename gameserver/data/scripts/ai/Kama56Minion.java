package ai;

import l2ar.gameserver.ai.Fighter;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.instances.NpcInstance;

public class Kama56Minion extends Fighter {
    public Kama56Minion(final NpcInstance actor) {
        super(actor);
        actor.setIsInvul(true);
    }

    @Override
    protected void onEvtAggression(final Creature attacker, final int aggro) {
        if (aggro < 10000000) {
            return;
        }
        super.onEvtAggression(attacker, aggro);
    }

    @Override
    protected void onEvtAttacked(final Creature attacker, final int damage) {
    }
}
