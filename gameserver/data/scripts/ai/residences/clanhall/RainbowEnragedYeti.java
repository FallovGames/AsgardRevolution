package ai.residences.clanhall;

import l2ar.gameserver.ai.Fighter;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.scripts.Functions;

public class RainbowEnragedYeti extends Fighter {
    public RainbowEnragedYeti(final NpcInstance actor) {
        super(actor);
    }

    @Override
    public void onEvtSpawn() {
        super.onEvtSpawn();
        Functions.npcShoutCustomMessage(getActor(), "clanhall.RainbowEnragedYeti.OOOH_WHO_POURED_NECTAR_ON_MY_HEAD_WHILE_I_WAS_SLEEPING");
    }
}
