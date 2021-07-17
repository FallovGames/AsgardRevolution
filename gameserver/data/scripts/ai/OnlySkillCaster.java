package ai;

import l2ar.commons.util.Rnd;
import l2ar.gameserver.ai.Mystic;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.Playable;
import l2ar.gameserver.model.Skill;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.tables.SkillTable;

public class OnlySkillCaster extends Mystic {

    private static final Skill skillToCast = SkillTable.getInstance().getInfo(4100, 1);
    private static final double chanceToCast = 99.9;

    public OnlySkillCaster(final NpcInstance actor) {
        super(actor);
    }

    @Override
    protected boolean createNewTask() {
        final NpcInstance actor = getActor();
        if (actor == null) {
            return false;
        }

        clearTasks();

        final Creature target;
        if ((target = prepareTarget()) == null) {
            return false;
        }

        if (Rnd.chance(chanceToCast) && !actor.isCastingNow() && actor.isInRange(target, skillToCast.getCastRange())) {
            addTaskCast(target, skillToCast);
        }


        return true;
    }

    @Override
    protected boolean randomWalk() {
        return true;
    }

    @Override
    protected boolean randomAnimation() {
        return true;
    }

    @Override
    public boolean canSeeInSilentMove(final Playable target) {
        return true;
    }

    @Override
    public boolean canSeeInHide(final Playable target) {
        return true;
    }
}
