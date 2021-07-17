package ai;

import l2ar.commons.util.Rnd;
import l2ar.gameserver.ai.CtrlIntention;
import l2ar.gameserver.ai.Fighter;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.instances.NpcInstance;

public class MoSMonk extends Fighter {
    public MoSMonk(final NpcInstance actor) {
        super(actor);
    }

    @Override
    protected void onIntentionAttack(final Creature target) {
        final NpcInstance actor = getActor();
        if (getIntention() == CtrlIntention.AI_INTENTION_ACTIVE && Rnd.chance(20)) {
            actor.MakeFString(1121006); //offlike pts fstring
        }
        super.onIntentionAttack(target);
    }

    @Override
    public boolean checkAggression(final Creature target) {
        return target.getActiveWeaponInstance() != null && super.checkAggression(target);
    }
}
