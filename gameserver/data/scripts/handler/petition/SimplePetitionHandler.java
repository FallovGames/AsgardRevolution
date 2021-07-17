package handler.petition;

import l2ar.gameserver.handler.petition.IPetitionHandler;
import l2ar.gameserver.model.Player;

public class SimplePetitionHandler implements IPetitionHandler {
    @Override
    public void handle(final Player player, final int id, final String txt) {
        player.sendMessage(txt);
    }
}
