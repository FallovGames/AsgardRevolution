package ai.the_cemetery;


import l2ar.gameserver.ai.Fighter;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.utils.NpcUtils;

/**
 * @author Mangol
 */
public class domb_death_cabrio extends Fighter {
    private final int coffer_of_the_dead = 31027;

    public domb_death_cabrio(final NpcInstance actor) {
        super(actor);
    }

    @Override
    protected void onEvtDead(final Creature killer) {
        NpcUtils.spawnSingle(coffer_of_the_dead, getActor().getX(), getActor().getY(), getActor().getZ());
        super.onEvtDead(killer);
    }
}
