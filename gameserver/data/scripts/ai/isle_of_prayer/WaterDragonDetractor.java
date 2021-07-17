package ai.isle_of_prayer;

import l2ar.commons.util.Rnd;
import l2ar.gameserver.ai.Fighter;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.Player;
import l2ar.gameserver.model.instances.NpcInstance;

public class WaterDragonDetractor extends Fighter {
    private static final int SPIRIT_OF_LAKE = 9689;
    private static final int BLUE_CRYSTAL = 9595;

    public WaterDragonDetractor(final NpcInstance actor) {
        super(actor);
    }

    @Override
    protected void onEvtDead(final Creature killer) {
        if (killer != null) {
            final Player player = killer.getPlayer();
            if (player != null) {
                final NpcInstance actor = getActor();
                actor.dropItem(player, 9689, 1L);
                if (Rnd.chance(10)) {
                    actor.dropItem(player, 9595, 1L);
                }
            }
        }
        super.onEvtDead(killer);
    }
}
