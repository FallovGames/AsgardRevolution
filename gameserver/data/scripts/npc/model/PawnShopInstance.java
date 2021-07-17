package npc.model;

import l2ar.gameserver.Config;
import l2ar.gameserver.model.Player;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.templates.npc.NpcTemplate;
import services.pawnshop.PawnShop;

public class PawnShopInstance extends NpcInstance {
    public PawnShopInstance(final int objectId, final NpcTemplate template) {
        super(objectId, template);
    }

    @Override
    public void showChatWindow(final Player player, final int val, final Object... replace) {
        if (val == 0 && Config.PAWNSHOP_ENABLED) {
            PawnShop.showStartPage(player, this);
            return;
        }
        super.showChatWindow(player, val, replace);
    }
}
