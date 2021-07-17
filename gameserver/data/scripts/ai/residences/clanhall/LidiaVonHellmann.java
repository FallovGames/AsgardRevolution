package ai.residences.clanhall;

import ai.residences.SiegeGuardFighter;
import l2ar.commons.util.Rnd;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.Skill;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.scripts.Functions;
import l2ar.gameserver.tables.SkillTable;
import l2ar.gameserver.utils.PositionUtils;

public class LidiaVonHellmann extends SiegeGuardFighter {
    private static final Skill DRAIN_SKILL = SkillTable.getInstance().getInfo(4999, 1);
    private static final Skill DAMAGE_SKILL = SkillTable.getInstance().getInfo(4998, 1);

    public LidiaVonHellmann(final NpcInstance actor) {
        super(actor);
    }

    @Override
    public void onEvtSpawn() {
        super.onEvtSpawn();
        Functions.npcShoutCustomMessage(getActor(), "clanhall.LidiaVonHellmann.HMM_THOSE_WHO_ARE_NOT_OF_THE_BLOODLINE_ARE_COMING_THIS_WAY_TO_TAKE_OVER_THE_CASTLE__HUMPH__THE_BITTER_GRUDGES_OF_THE_DEAD");
    }

    @Override
    public void onEvtDead(final Creature killer) {
        super.onEvtDead(killer);
        Functions.npcShoutCustomMessage(getActor(), "clanhall.LidiaVonHellmann.GRARR_FOR_THE_NEXT_2_MINUTES_OR_SO_THE_GAME_ARENA_ARE_WILL_BE_CLEANED");
    }

    @Override
    public void onEvtAttacked(final Creature attacker, final int damage) {
        final NpcInstance actor = getActor();
        super.onEvtAttacked(attacker, damage);
        if (Rnd.chance(0.22)) {
            addTaskCast(attacker, LidiaVonHellmann.DRAIN_SKILL);
        } else if (actor.getCurrentHpPercents() < 20.0 && Rnd.chance(0.22)) {
            addTaskCast(attacker, LidiaVonHellmann.DRAIN_SKILL);
        }
        if (PositionUtils.calculateDistance(actor, attacker, false) > 300.0 && Rnd.chance(0.13)) {
            addTaskCast(attacker, LidiaVonHellmann.DAMAGE_SKILL);
        }
    }
}
