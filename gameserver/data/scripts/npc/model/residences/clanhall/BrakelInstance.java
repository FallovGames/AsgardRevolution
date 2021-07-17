package npc.model.residences.clanhall;

import l2ar.gameserver.data.xml.holder.ResidenceHolder;
import l2ar.gameserver.model.Player;
import l2ar.gameserver.model.entity.residence.ClanHall;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.network.lineage2.serverpackets.NpcHtmlMessage;
import l2ar.gameserver.templates.npc.NpcTemplate;
import l2ar.gameserver.utils.TimeUtils;

public class BrakelInstance extends NpcInstance {
    public BrakelInstance(final int objectId, final NpcTemplate template) {
        super(objectId, template);
    }

    @Override
    public void showChatWindow(final Player player, final int val, final Object... arg) {
        final ClanHall clanhall = ResidenceHolder.getInstance().getResidence(ClanHall.class, 21);
        if (clanhall == null) {
            return;
        }
        final NpcHtmlMessage html = new NpcHtmlMessage(player, this);
        html.setFile("residence2/clanhall/partisan_ordery_brakel001.htm");
        html.replace("%next_siege%", TimeUtils.toSimpleFormat(clanhall.getSiegeDate().getTimeInMillis()));
        player.sendPacket(html);
    }
}
