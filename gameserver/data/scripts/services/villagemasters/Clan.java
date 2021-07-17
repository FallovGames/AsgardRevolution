package services.villagemasters;

import l2ar.gameserver.Config;
import l2ar.gameserver.model.Player;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.scripts.Functions;

public class Clan extends Functions {
    public void CheckCreateClan() {
        final Player player = getSelf();
        final NpcInstance npc = getNpc();
        if (npc == null || player == null) {
            return;
        }
        if (player.getLevel() < Config.CHARACTER_MIN_LEVEL_FOR_CLAN_CREATE) {
            show("villagemaster/clan-06.htm", player, npc, "%min_level%", Config.CHARACTER_MIN_LEVEL_FOR_CLAN_CREATE);
        } else if (player.isClanLeader()) {
            show("villagemaster/clan-07.htm", player, npc);
        } else if (player.getClan() != null) {
            show("villagemaster/clan-09.htm", player, npc);
        } else {
            show("villagemaster/clan-02.htm", player, npc);
        }
    }

    public void CheckDissolveClan() {
        if (getNpc() == null || getSelf() == null) {
            return;
        }
        final Player pl = getSelf();
        String htmltext = "clan-01.htm";
        if (pl.isClanLeader()) {
            htmltext = "clan-04.htm";
        } else if (pl.getClan() != null) {
            htmltext = "clan-07.htm";
        } else {
            htmltext = "pl008.htm";
        }
        getNpc().showChatWindow(pl, "villagemaster/" + htmltext);
    }
}