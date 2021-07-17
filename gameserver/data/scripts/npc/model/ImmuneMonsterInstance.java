package npc.model;

import l2ar.gameserver.model.instances.MonsterInstance;
import l2ar.gameserver.templates.npc.NpcTemplate;

public class ImmuneMonsterInstance extends MonsterInstance {
    public ImmuneMonsterInstance(final int objectId, final NpcTemplate template) {
        super(objectId, template);
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
