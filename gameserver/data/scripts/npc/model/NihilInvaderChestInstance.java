package npc.model;

import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.Skill;
import l2ar.gameserver.model.instances.MonsterInstance;
import l2ar.gameserver.templates.npc.NpcTemplate;

public final class NihilInvaderChestInstance extends MonsterInstance {
    public NihilInvaderChestInstance(final int objectId, final NpcTemplate template) {
        super(objectId, template);
        setHasChatWindow(true);
    }

    @Override
    public void reduceCurrentHp(final double i, final Creature attacker, final Skill skill, final boolean awake, final boolean standUp, final boolean directHp, final boolean canReflect, final boolean transferDamage, final boolean isDot, final boolean sendMessage) {
        super.reduceCurrentHp(1.0, attacker, skill, awake, standUp, directHp, canReflect, transferDamage, isDot, sendMessage);
    }

    @Override
    public boolean isFearImmune() {
        return true;
    }

    @Override
    public boolean isParalyzeImmune() {
        return true;
    }

    @Override
    public boolean isLethalImmune() {
        return true;
    }
}
