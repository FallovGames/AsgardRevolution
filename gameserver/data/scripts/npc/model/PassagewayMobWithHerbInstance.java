package npc.model;

import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.instances.MonsterInstance;
import l2ar.gameserver.templates.npc.NpcTemplate;

public final class PassagewayMobWithHerbInstance extends MonsterInstance {
    public static final int FieryDemonBloodHerb = 9849;

    public PassagewayMobWithHerbInstance(final int objectId, final NpcTemplate template) {
        super(objectId, template);
    }

    @Override
    public void calculateRewards(final Creature lastAttacker) {
        if (lastAttacker == null) {
            return;
        }
        super.calculateRewards(lastAttacker);
        if (lastAttacker.isPlayable()) {
            dropItem(lastAttacker.getPlayer(), 9849, 1L);
        }
    }
}
