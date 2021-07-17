package ai.moveroute;

import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.instances.NpcInstance;

public class NotAggressiveNpc extends MoveRouteDefaultAI {
    public NotAggressiveNpc(final NpcInstance actor) {
        super(actor);
    }

    @Override
    protected void onEvtAttacked(final Creature attacker, final int damage) {
    }

    @Override
    protected void onEvtAggression(final Creature target, final int aggro) {
    }
}
