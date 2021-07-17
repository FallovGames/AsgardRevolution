package npc.model;

import l2ar.gameserver.model.Player;
import l2ar.gameserver.model.instances.MerchantInstance;
import l2ar.gameserver.network.lineage2.serverpackets.PackageToList;
import l2ar.gameserver.templates.npc.NpcTemplate;
import l2ar.gameserver.utils.WarehouseFunctions;

public class FreightSenderInstance extends MerchantInstance {
    public FreightSenderInstance(final int objectId, final NpcTemplate template) {
        super(objectId, template);
    }

    @Override
    public void onBypassFeedback(final Player player, final String command) {
        if (!canBypassCheck(player, this)) {
            return;
        }
        if ("deposit_items".equalsIgnoreCase(command)) {
            player.sendPacket(new PackageToList(player));
        } else if ("withdraw_items".equalsIgnoreCase(command)) {
            WarehouseFunctions.showFreightWindow(player);
        } else {
            super.onBypassFeedback(player, command);
        }
    }
}
