package npc.model.residences.clanhall;

import l2ar.gameserver.model.Player;
import l2ar.gameserver.model.entity.events.impl.ClanHallSiegeEvent;
import l2ar.gameserver.model.entity.residence.ClanHall;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.network.lineage2.serverpackets.CastleSiegeInfo;
import l2ar.gameserver.templates.npc.NpcTemplate;

public class MessengerInstance extends NpcInstance {
    private final String _siegeDialog;
    private final String _ownerDialog;

    public MessengerInstance(final int objectId, final NpcTemplate template) {
        super(objectId, template);
        _siegeDialog = template.getAIParams().getString("siege_dialog");
        _ownerDialog = template.getAIParams().getString("owner_dialog");
    }

    @Override
    public void showChatWindow(final Player player, final int val, final Object... arg) {
        final ClanHall clanHall = getClanHall();
        final ClanHallSiegeEvent siegeEvent = clanHall.getSiegeEvent();
        if (clanHall.getOwner() != null && clanHall.getOwner() == player.getClan()) {
            showChatWindow(player, _ownerDialog);
        } else if (siegeEvent.isInProgress()) {
            showChatWindow(player, _siegeDialog);
        } else {
            player.sendPacket(new CastleSiegeInfo(clanHall, player));
        }
    }
}
