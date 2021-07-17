package handler.items;

import l2ar.gameserver.model.Playable;
import l2ar.gameserver.model.items.ItemInstance;
import l2ar.gameserver.network.lineage2.serverpackets.ShowCalc;

public class Calculator extends ScriptItemHandler {
    private static final int CALCULATOR = 4393;

    @Override
    public boolean useItem(final Playable playable, final ItemInstance item, final boolean ctrl) {
        if (!playable.isPlayer()) {
            return false;
        }
        playable.sendPacket(new ShowCalc(item.getItemId()));
        return true;
    }

    @Override
    public int[] getItemIds() {
        return new int[]{CALCULATOR};
    }
}
