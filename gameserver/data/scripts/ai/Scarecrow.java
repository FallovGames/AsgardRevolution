package ai;

import l2ar.gameserver.ai.Fighter;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.instances.NpcInstance;

public class Scarecrow extends Fighter {
    public Scarecrow(final NpcInstance actor) {
        super(actor);
        actor.block();
        actor.setIsInvul(true);
    }

    @Override
    protected void onIntentionAttack(final Creature target) {
    }

    @Override
    protected void onEvtAttacked(final Creature attacker, final int damage) {
    }

    @Override
    protected void onEvtAggression(final Creature attacker, final int aggro) {
    }
}
