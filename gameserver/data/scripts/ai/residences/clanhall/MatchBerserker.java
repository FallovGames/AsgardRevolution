package ai.residences.clanhall;

import l2ar.commons.util.Rnd;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.Skill;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.tables.SkillTable;

public class MatchBerserker extends MatchFighter {
    public static final Skill ATTACK_SKILL = SkillTable.getInstance().getInfo(4032, 6);

    public MatchBerserker(final NpcInstance actor) {
        super(actor);
    }

    @Override
    public void onEvtAttacked(final Creature attacker, final int dam) {
        super.onEvtAttacked(attacker, dam);
        if (Rnd.chance(10)) {
            addTaskCast(attacker, MatchBerserker.ATTACK_SKILL);
        }
    }
}
