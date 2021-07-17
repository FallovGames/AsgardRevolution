package npc.model.residences.castle;

import l2ar.gameserver.model.instances.MerchantInstance;
import l2ar.gameserver.templates.npc.NpcTemplate;

public class MercenaryManagerInstance extends MerchantInstance {
    public MercenaryManagerInstance(final int objectId, final NpcTemplate template) {
        super(objectId, template);
    }

    @Override
    public boolean canInteractWithKarmaPlayer() {
        return true;
    }
}
