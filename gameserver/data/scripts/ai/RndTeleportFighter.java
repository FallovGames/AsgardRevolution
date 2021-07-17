package ai;

import l2ar.commons.util.Rnd;
import l2ar.gameserver.Config;
import l2ar.gameserver.ThreadPoolManager;
import l2ar.gameserver.ai.Fighter;
import l2ar.gameserver.geodata.GeoEngine;
import l2ar.gameserver.model.Territory;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.network.lineage2.serverpackets.MagicSkillUse;
import l2ar.gameserver.templates.spawn.SpawnRange;
import l2ar.gameserver.utils.Location;

public class RndTeleportFighter extends Fighter {
    private long _lastTeleport;

    public RndTeleportFighter(final NpcInstance actor) {
        super(actor);
    }

    @Override
    protected boolean maybeMoveToHome() {
        final NpcInstance actor = getActor();
        if (System.currentTimeMillis() - _lastTeleport < 10000L) {
            return false;
        }
        final boolean randomWalk = actor.hasRandomWalk();
        final Location sloc = actor.getSpawnedLoc();
        if (sloc == null) {
            return false;
        }
        if (randomWalk && (!Config.RND_WALK || Rnd.chance(Config.RND_WALK_RATE))) {
            return false;
        }
        if (!randomWalk && actor.isInRangeZ(sloc, (long) Config.MAX_DRIFT_RANGE)) {
            return false;
        }
        final int x = sloc.x + Rnd.get(-Config.MAX_DRIFT_RANGE, Config.MAX_DRIFT_RANGE);
        final int y = sloc.y + Rnd.get(-Config.MAX_DRIFT_RANGE, Config.MAX_DRIFT_RANGE);
        final int z = GeoEngine.getHeight(x, y, sloc.z, actor.getGeoIndex());
        if (sloc.z - z > 64) {
            return false;
        }
        final SpawnRange spawnRange = actor.getSpawnRange();
        boolean isInside = true;
        if (spawnRange != null && spawnRange instanceof Territory) {
            isInside = ((Territory) spawnRange).isInside(x, y);
        }
        if (isInside) {
            actor.broadcastPacketToOthers(new MagicSkillUse(actor, actor, 4671, 1, 500, 0L));
            ThreadPoolManager.getInstance().schedule(new Teleport(new Location(x, y, z)), 500L);
            _lastTeleport = System.currentTimeMillis();
        }
        return isInside;
    }
}
