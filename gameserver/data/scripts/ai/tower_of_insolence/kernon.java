package ai.tower_of_insolence;


import l2ar.gameserver.ai.Fighter;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.utils.NpcUtils;

/**
 * @author Mangol
 */
public class kernon extends Fighter {
    private final int chest_of_kernon = 31028;

    public kernon(final NpcInstance actor) {
        super(actor);
    }

    @Override
    protected void onEvtDead(final Creature killer) {
        NpcUtils.spawnSingle(chest_of_kernon, getActor().getX(), getActor().getY(), getActor().getZ());
        super.onEvtDead(killer);
    }
}
