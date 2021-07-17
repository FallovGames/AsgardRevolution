package npc.model;

import l2ar.gameserver.model.Player;
import l2ar.gameserver.model.entity.Reflection;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.network.lineage2.serverpackets.NpcHtmlMessage;
import l2ar.gameserver.templates.npc.NpcTemplate;
import l2ar.gameserver.utils.ReflectionUtils;

public final class BatracosInstance extends NpcInstance {
    private static final int urogosIzId = 505;

    public BatracosInstance(final int objectId, final NpcTemplate template) {
        super(objectId, template);
    }

    @Override
    public void showChatWindow(final Player player, final int val, final Object... arg) {
        if (val == 0) {
            String htmlpath;
            if (getReflection().isDefault()) {
                htmlpath = "default/32740.htm";
            } else {
                htmlpath = "default/32740-4.htm";
            }
            player.sendPacket(new NpcHtmlMessage(player, this, htmlpath, val));
        } else {
            super.showChatWindow(player, val);
        }
    }

    @Override
    public void onBypassFeedback(final Player player, final String command) {
        if (!canBypassCheck(player, this)) {
            return;
        }
        if ("request_seer".equalsIgnoreCase(command)) {
            final Reflection r = player.getActiveReflection();
            if (r != null) {
                if (player.canReenterInstance(505)) {
                    player.teleToLocation(r.getTeleportLoc(), r);
                }
            } else if (player.canEnterInstance(505)) {
                ReflectionUtils.enterReflection(player, 505);
            }
        } else if ("leave".equalsIgnoreCase(command)) {
            if (!getReflection().isDefault()) {
                getReflection().collapse();
            }
        } else {
            super.onBypassFeedback(player, command);
        }
    }
}
