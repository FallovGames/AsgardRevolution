package ai;

import l2ar.gameserver.ai.Fighter;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.network.lineage2.serverpackets.MagicSkillUse;

public class SealDevice extends Fighter {
    private boolean _firstAttack;

    public SealDevice(final NpcInstance actor) {
        super(actor);
        _firstAttack = false;
        actor.block();
    }

    @Override
    protected void onEvtAttacked(final Creature attacker, final int damage) {
        final NpcInstance actor = getActor();
        if (!_firstAttack) {
            actor.broadcastPacket(new MagicSkillUse(actor, actor, 5980, 1, 0, 0L));
            _firstAttack = true;
        }
    }

    @Override
    protected void onEvtAggression(final Creature target, final int aggro) {
    }
}
