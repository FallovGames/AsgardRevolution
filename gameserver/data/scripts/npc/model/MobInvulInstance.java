package npc.model;

import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.Player;
import l2ar.gameserver.model.Skill;
import l2ar.gameserver.model.instances.MonsterInstance;
import l2ar.gameserver.templates.npc.NpcTemplate;

public final class MobInvulInstance extends MonsterInstance {
    public MobInvulInstance(final int objectId, final NpcTemplate template) {
        super(objectId, template);
    }

    @Override
    public void showChatWindow(final Player player, final int val, final Object... arg) {
    }

    @Override
    public void showChatWindow(final Player player, final String filename, final Object... replace) {
    }

    @Override
    public void reduceCurrentHp(final double i, final Creature attacker, final Skill skill, final boolean awake, final boolean standUp, final boolean directHp, final boolean canReflect, final boolean transferDamage, final boolean isDot, final boolean sendMessage) {
    }

    @Override
    public boolean isInvul() {
        return false;
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
