package npc.model;

import l2ar.gameserver.model.Player;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.templates.npc.NpcTemplate;

public class FakeObeliskInstance extends NpcInstance {
    public FakeObeliskInstance(final int objectId, final NpcTemplate template) {
        super(objectId, template);
    }

    @Override
    public void showChatWindow(final Player player, final int val, final Object... arg) {
    }

    @Override
    public void onAction(final Player player, final boolean shift) {
        player.sendActionFailed();
    }
}
