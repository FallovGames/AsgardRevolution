package ai;

import l2ar.commons.util.Rnd;
import l2ar.gameserver.ai.Fighter;
import l2ar.gameserver.geodata.GeoEngine;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.utils.Location;

public class Elpy extends Fighter {
    public Elpy(final NpcInstance actor) {
        super(actor);
    }

    @Override
    protected void onEvtAttacked(final Creature attacker, final int damage) {
        final NpcInstance actor = getActor();
        if (attacker != null && Rnd.chance(50)) {
            final Location pos = Location.findPointToStay(actor, 150, 200);
            if (GeoEngine.canMoveToCoord(actor.getX(), actor.getY(), actor.getZ(), pos.x, pos.y, pos.z, actor.getGeoIndex())) {
                actor.setRunning();
                addTaskMove(pos, false);
            }
        }
    }

    @Override
    public boolean checkAggression(final Creature target) {
        return false;
    }

    @Override
    protected void onEvtAggression(final Creature target, final int aggro) {
    }
}
