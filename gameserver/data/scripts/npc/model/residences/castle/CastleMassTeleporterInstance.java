package npc.model.residences.castle;

import l2ar.commons.threading.RunnableImpl;
import l2ar.gameserver.ThreadPoolManager;
import l2ar.gameserver.model.Player;
import l2ar.gameserver.model.World;
import l2ar.gameserver.model.entity.events.impl.SiegeEvent;
import l2ar.gameserver.model.entity.events.objects.SiegeToggleNpcObject;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.scripts.Functions;
import l2ar.gameserver.templates.npc.NpcTemplate;
import l2ar.gameserver.utils.Location;

import java.util.List;
import java.util.concurrent.Future;

public class CastleMassTeleporterInstance extends NpcInstance {
    private final Location _teleportLoc;
    private Future<?> _teleportTask;

    public CastleMassTeleporterInstance(final int objectId, final NpcTemplate template) {
        super(objectId, template);
        _teleportTask = null;
        _teleportLoc = Location.parseLoc(template.getAIParams().getString("teleport_loc"));
    }

    @Override
    public void onBypassFeedback(final Player player, final String command) {
        if (!canBypassCheck(player, this)) {
            return;
        }
        if (_teleportTask != null) {
            showChatWindow(player, "residence2/castle/CastleTeleportDelayed.htm");
            return;
        }
        _teleportTask = ThreadPoolManager.getInstance().schedule(new TeleportTask(), isAllTowersDead() ? 480000L : 30000L);
        showChatWindow(player, "residence2/castle/CastleTeleportDelayed.htm");
    }

    @Override
    public boolean canInteractWithKarmaPlayer() {
        return true;
    }

    @Override
    public void showChatWindow(final Player player, final int val, final Object... arg) {
        if (_teleportTask != null) {
            showChatWindow(player, "residence2/castle/CastleTeleportDelayed.htm");
        } else if (isAllTowersDead()) {
            showChatWindow(player, "residence2/castle/gludio_mass_teleporter002.htm");
        } else {
            showChatWindow(player, "residence2/castle/gludio_mass_teleporter001.htm");
        }
    }

    private boolean isAllTowersDead() {
        final SiegeEvent siegeEvent = getEvent(SiegeEvent.class);
        if (siegeEvent == null || !siegeEvent.isInProgress()) {
            return false;
        }
        final List<SiegeToggleNpcObject> towers = siegeEvent.getObjects("control_towers");
        for (final SiegeToggleNpcObject t : towers) {
            if (t.isAlive()) {
                return false;
            }
        }
        return true;
    }

    private class TeleportTask extends RunnableImpl {
        @Override
        public void runImpl() {
            Functions.npcShoutCustomMessage(CastleMassTeleporterInstance.this, "NpcString.THE_DEFENDERS_OF_S1_CASTLE_WILL_BE_TELEPORTED_TO_THE_INNER_CASTLE", getCastle().getName());
            for (final Player p : World.getAroundPlayers(CastleMassTeleporterInstance.this, 200, 50)) {
                p.teleToLocation(Location.findPointToStay(_teleportLoc, 10, 100, p.getGeoIndex()));
            }
            _teleportTask = null;
        }
    }
}
