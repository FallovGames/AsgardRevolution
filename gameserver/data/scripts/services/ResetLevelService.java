package services;

import l2ar.gameserver.Config;
import l2ar.gameserver.model.Playable;
import l2ar.gameserver.model.Player;
import l2ar.gameserver.model.base.Experience;
import l2ar.gameserver.network.lineage2.serverpackets.NpcHtmlMessage;
import l2ar.gameserver.scripts.Functions;

public class ResetLevelService extends Functions {
    public void reset_service_page() {
        final Player player = getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICE_RESET_LEVEL_ENABLED) {
            player.sendPacket(new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        final NpcHtmlMessage msg = new NpcHtmlMessage(5).setFile("scripts/services/reset_level_services.htm");
        msg.replace("%reward_item_id%", String.valueOf(Config.SERVICE_RESET_LEVEL_ITEM_ID));
        msg.replace("%reward_item_count%", String.valueOf(Config.SERVICE_RESET_LEVEL_ITEM_COUNT));
        msg.replace("%current_level%", String.valueOf(player.getLevel()));
        player.sendPacket(msg);
    }

    public void reset_service() {
        final Player player = getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICE_RESET_LEVEL_ENABLED) {
            player.sendPacket(new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        if (player.getLevel() < Config.SERVICE_RESET_LEVEL_NEED || player.getLevel() > player.getMaxExp()) {
            return;
        }
        addItem((Playable) player, Config.SERVICE_RESET_LEVEL_ITEM_ID, (long) Config.SERVICE_RESET_LEVEL_ITEM_COUNT);
        player.addExpAndSp(Experience.LEVEL[player.getLevel() - Config.SERVICE_RESET_LEVEL_REMOVE] - player.getExp(), 0L, false, false);
    }
}
