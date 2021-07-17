package npc.model.residences.castle;

import l2ar.gameserver.model.Player;
import l2ar.gameserver.model.entity.residence.Castle;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.templates.npc.NpcTemplate;

public class VenomTeleporterInstance extends NpcInstance {
    public VenomTeleporterInstance(final int objectId, final NpcTemplate template) {
        super(objectId, template);
    }

    @Override
    public void onBypassFeedback(final Player player, final String command) {
        final Castle castle = getCastle();
        if (castle.getSiegeEvent().isInProgress()) {
            showChatWindow(player, "residence2/castle/rune_massymore_teleporter002.htm");
        } else {
            player.teleToLocation(12589, -49044, -3008);
        }
    }

    @Override
    public boolean canInteractWithKarmaPlayer() {
        return true;
    }

    @Override
    public void showChatWindow(final Player player, final int val, final Object... arg) {
        showChatWindow(player, "residence2/castle/rune_massymore_teleporter001.htm");
    }
}
