package ai;

import l2ar.gameserver.ai.CtrlIntention;
import l2ar.gameserver.ai.Mystic;
import l2ar.gameserver.geodata.GeoEngine;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.Playable;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.scripts.Functions;

public class PaganGuard extends Mystic {
    public PaganGuard(final NpcInstance actor) {
        super(actor);
        actor.startImmobilized();
    }

    @Override
    protected boolean canSeeInSilentMove(final Playable target) {
        return !target.isSilentMoving() || getActor().getParameter("canSeeInSilentMove", true);
    }

    @Override
    public boolean checkAggression(final Creature target) {
        final NpcInstance actor = getActor();
        if (actor.isDead()) {
            return false;
        }
        if (getIntention() != CtrlIntention.AI_INTENTION_ACTIVE || !isGlobalAggro()) {
            return false;
        }
        if (target.isAlikeDead() || !target.isPlayable()) {
            return false;
        }
        if (!target.isInRangeZ(actor.getSpawnedLoc(), (long) actor.getAggroRange())) {
            return false;
        }
        if (target.isPlayable() && !canSeeInSilentMove((Playable) target)) {
            return false;
        }
        if (actor.getNpcId() == 18343 && (Functions.getItemCount((Playable) target, 8067) != 0L || Functions.getItemCount((Playable) target, 8064) != 0L)) {
            return false;
        }
        if (!GeoEngine.canSeeTarget(actor, target, false)) {
            return false;
        }
        if (getIntention() != CtrlIntention.AI_INTENTION_ATTACK) {
            actor.getAggroList().addDamageHate(target, 0, 1);
            setIntention(CtrlIntention.AI_INTENTION_ATTACK, target);
        }
        return true;
    }

    @Override
    protected boolean randomWalk() {
        return false;
    }
}
