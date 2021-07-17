package ai.primeval_isle;

import l2ar.gameserver.ai.DefaultAI;
import l2ar.gameserver.model.Skill;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.tables.SkillTable;

public class SprigantPoison extends DefaultAI {
    private static final int TICK_IN_MILISECONDS = 15000;
    private final Skill SKILL;
    private long _waitTime;

    public SprigantPoison(final NpcInstance actor) {
        super(actor);
        SKILL = SkillTable.getInstance().getInfo(5086, 1);
    }

    @Override
    protected boolean thinkActive() {
        if (System.currentTimeMillis() > _waitTime) {
            final NpcInstance actor = getActor();
            actor.doCast(SKILL, actor, false);
            _waitTime = System.currentTimeMillis() + 15000L;
            return true;
        }
        return false;
    }
}
