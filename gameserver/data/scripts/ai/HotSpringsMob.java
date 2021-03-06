package ai;

import l2ar.commons.util.Rnd;
import l2ar.gameserver.Config;
import l2ar.gameserver.ai.Mystic;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.Effect;
import l2ar.gameserver.model.Skill;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.tables.SkillTable;

import java.util.List;

public class HotSpringsMob extends Mystic {
    private static final int[] AltDeBuffs = {4554, 4553, 4552, 4551};
    private static final int[] DeBuffs = {4554, 4552};

    public HotSpringsMob(final NpcInstance actor) {
        super(actor);
    }

    @Override
    protected void onEvtAttacked(final Creature attacker, final int damage) {
        final NpcInstance actor = getActor();
        if (attacker != null && Rnd.chance(Config.ALT_HOT_SPIRIT_CHANCE_DEBUFF)) {
            int[] debuffs = HotSpringsMob.DeBuffs;
            if (Config.ALT_USE_HOT_SPIRIT_DEBUFF) {
                debuffs = HotSpringsMob.AltDeBuffs;
            }
            final int DeBuff = debuffs[Rnd.get(debuffs.length)];
            final List<Effect> effect = attacker.getEffectList().getEffectsBySkillId(DeBuff);
            if (effect != null) {
                final int level = effect.get(0).getSkill().getLevel();
                if (level < 10) {
                    effect.get(0).exit();
                    final Skill skill = SkillTable.getInstance().getInfo(DeBuff, level + 1);
                    skill.getEffects(actor, attacker, false, false);
                }
            } else {
                final Skill skill2 = SkillTable.getInstance().getInfo(DeBuff, 1);
                if (skill2 != null) {
                    skill2.getEffects(actor, attacker, false, false);
                } else {
                    System.out.println("Skill " + DeBuff + " is null, fix it.");
                }
            }
        }
        super.onEvtAttacked(attacker, damage);
    }
}
