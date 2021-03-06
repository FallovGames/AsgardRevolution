package npc.model.residences.clanhall;

import l2ar.commons.util.Rnd;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.Skill;
import l2ar.gameserver.model.instances.residences.clanhall.CTBBossInstance;
import l2ar.gameserver.templates.npc.NpcTemplate;

public class MatchScoutInstance extends CTBBossInstance {
    private long _massiveDamage;

    public MatchScoutInstance(final int objectId, final NpcTemplate template) {
        super(objectId, template);
    }

    @Override
    public void reduceCurrentHp(double damage, final Creature attacker, final Skill skill, final boolean awake, final boolean standUp, final boolean directHp, final boolean canReflect, final boolean transferDamage, final boolean isDot, final boolean sendMessage) {
        if (_massiveDamage > System.currentTimeMillis()) {
            damage = 10000.0;
        } else if (getCurrentHpPercents() > 50.0) {
            if (attacker.isPlayer()) {
                damage = damage / getMaxHp() / 0.05 * 100.0;
            } else {
                damage = damage / getMaxHp() / 0.05 * 10.0;
            }
        } else if (getCurrentHpPercents() > 30.0) {
            if (Rnd.chance(90)) {
                if (attacker.isPlayer()) {
                    damage = damage / getMaxHp() / 0.05 * 100.0;
                } else {
                    damage = damage / getMaxHp() / 0.05 * 10.0;
                }
            } else {
                _massiveDamage = System.currentTimeMillis() + 5000L;
            }
        } else {
            _massiveDamage = System.currentTimeMillis() + 5000L;
        }
        super.reduceCurrentHp(damage, attacker, skill, awake, standUp, directHp, canReflect, transferDamage, isDot, sendMessage);
    }
}
