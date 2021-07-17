package ai.residences.clanhall;

import l2ar.gameserver.model.Skill;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.tables.SkillTable;

public class MatchTrief extends MatchFighter {
    public static final Skill HOLD = SkillTable.getInstance().getInfo(4047, 6);

    public MatchTrief(final NpcInstance actor) {
        super(actor);
    }

    public void hold() {
        final NpcInstance actor = getActor();
        addTaskCast(actor, MatchTrief.HOLD);
        doTask();
    }
}
