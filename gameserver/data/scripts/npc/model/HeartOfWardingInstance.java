package npc.model;

import bosses.AntharasManager;
import l2ar.gameserver.model.Player;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.templates.npc.NpcTemplate;

public final class HeartOfWardingInstance extends NpcInstance {
    public HeartOfWardingInstance(final int objectId, final NpcTemplate template) {
        super(objectId, template);
    }

    @Override
    public void onBypassFeedback(final Player player, final String command) {
        if (!canBypassCheck(player, this)) {
            return;
        }
        if ("enter_lair".equalsIgnoreCase(command)) {
            AntharasManager.enterTheLair(player);
            return;
        }
        super.onBypassFeedback(player, command);
    }
}
