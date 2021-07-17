package ai;

import l2ar.commons.util.Rnd;
import l2ar.gameserver.ThreadPoolManager;
import l2ar.gameserver.ai.Priest;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.Skill;
import l2ar.gameserver.model.instances.MinionInstance;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.network.lineage2.serverpackets.MagicSkillUse;
import l2ar.gameserver.utils.Location;

public class HealLeaderMinion extends Priest {
    public HealLeaderMinion(final NpcInstance actor) {
        super(actor);
        MAX_PURSUE_RANGE = 10000;
    }

    @Override
    protected boolean thinkActive() {
        final NpcInstance actor = getActor();
        if (actor.isDead()) {
            return true;
        }
        if (_def_think) {
            if (doTask()) {
                clearTasks();
            }
            return true;
        }
        final Creature desireBuffTarget = getBuffDesireTarget();
        if (desireBuffTarget == null) {
            return false;
        }
        if (actor.getDistance(desireBuffTarget) - desireBuffTarget.getColRadius() - actor.getColRadius() > 200.0) {
            moveOrTeleportToLocation(Location.findFrontPosition(desireBuffTarget, actor, 100, 150));
            return false;
        }
        return !desireBuffTarget.isCurrentHpFull() && doTask() && createNewTask();
    }

    private void moveOrTeleportToLocation(final Location loc) {
        final NpcInstance actor = getActor();
        actor.setRunning();
        if (actor.moveToLocation(loc, 0, true)) {
            return;
        }
        clientStopMoving();
        _pathfindFails = 0;
        actor.broadcastPacketToOthers(new MagicSkillUse(actor, actor, 2036, 1, 500, 600000L));
        ThreadPoolManager.getInstance().schedule(new Teleport(loc), 500L);
    }

    @Override
    protected boolean createNewTask() {
        clearTasks();
        final NpcInstance actor = getActor();
        final Creature desireBuffTarget = getBuffDesireTarget();
        if (actor.isDead() || desireBuffTarget == null) {
            return false;
        }
        if (!desireBuffTarget.isCurrentHpFull()) {
            final Skill skill = _healSkills[Rnd.get(_healSkills.length)];
            if (skill.getAOECastRange() < actor.getDistance(desireBuffTarget)) {
                moveOrTeleportToLocation(Location.findFrontPosition(desireBuffTarget, actor, skill.getAOECastRange() - 30, skill.getAOECastRange() - 10));
            }
            addTaskBuff(desireBuffTarget, skill);
            return true;
        }
        return false;
    }

    private Creature getBuffDesireTarget() {
        final NpcInstance actor = getActor();
        if (actor.isMinion()) {
            return ((MinionInstance) actor).getLeader();
        }
        return null;
    }

    @Override
    protected void onIntentionAttack(final Creature target) {
    }
}
