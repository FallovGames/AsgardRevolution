package npc.model.residences.castle;

import l2ar.gameserver.model.Player;
import l2ar.gameserver.model.entity.residence.Castle;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.network.lineage2.serverpackets.CastleSiegeInfo;
import l2ar.gameserver.templates.npc.NpcTemplate;

public class CastleMessengerInstance extends NpcInstance {
    public CastleMessengerInstance(final int objectID, final NpcTemplate template) {
        super(objectID, template);
    }

    @Override
    public void showChatWindow(final Player player, final int val, final Object... arg) {
        final Castle castle = getCastle();
        if (player.isCastleLord(castle.getId())) {
            if (castle.getSiegeEvent().isInProgress()) {
                showChatWindow(player, "residence2/castle/sir_tyron021.htm");
            } else {
                showChatWindow(player, "residence2/castle/sir_tyron007.htm");
            }
        } else if (castle.getSiegeEvent().isInProgress()) {
            showChatWindow(player, "residence2/castle/sir_tyron021.htm");
        } else {
            player.sendPacket(new CastleSiegeInfo(castle, player));
        }
    }

    @Override
    public boolean canInteractWithKarmaPlayer() {
        return true;
    }
}
