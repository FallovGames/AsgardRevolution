package npc.model;

import l2ar.gameserver.model.Player;
import l2ar.gameserver.model.instances.WarehouseInstance;
import l2ar.gameserver.network.lineage2.components.SystemMsg;
import l2ar.gameserver.scripts.Functions;
import l2ar.gameserver.tables.SkillTable;
import l2ar.gameserver.templates.npc.NpcTemplate;

public class ArenaManagerInstance extends WarehouseInstance {
    private static final int RECOVER_CP_SKILLID = 4380;

    public ArenaManagerInstance(final int objectId, final NpcTemplate template) {
        super(objectId, template);
    }

    @Override
    public void onBypassFeedback(final Player player, final String command) {
        if (!canBypassCheck(player, this)) {
            return;
        }
        if (!player.isInPeaceZone() || player.isCursedWeaponEquipped()) {
            return;
        }
        if (command.startsWith("CPRecovery")) {
            if (Functions.getItemCount(player, 57) >= 100L) {
                Functions.removeItem(player, 57, 100L);
                doCast(SkillTable.getInstance().getInfo(4380, 1), player, true);
            } else {
                player.sendPacket(SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
            }
        } else {
            super.onBypassFeedback(player, command);
        }
    }
}
