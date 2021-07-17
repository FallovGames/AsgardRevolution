package services;

import l2ar.gameserver.Config;
import l2ar.gameserver.Config.RateBonusInfo;
import l2ar.gameserver.dao.AccountBonusDAO;
import l2ar.gameserver.model.Player;
import l2ar.gameserver.network.lineage2.serverpackets.SocialAction;
import l2ar.gameserver.scripts.Functions;
import l2ar.gameserver.utils.ItemFunctions;

public class GlobalServices extends Functions {
    public static boolean makeCustomHero(final Player player, final long customHeroDuration) {
        if (player.isHero() || customHeroDuration <= 0L) {
            return false;
        }
        player.setCustomHero(true, customHeroDuration, true);
        player.broadcastPacket(new SocialAction(player.getObjectId(), 16));
        player.broadcastUserInfo(true);
        return true;
    }

    public static boolean makeCustomPremium(final Player player, final int id) {

        RateBonusInfo rateBonusInfo = null;
        for (final RateBonusInfo rbi : Config.SERVICES_RATE_BONUS_INFO_RB) {
            if (rbi.id == id) {
                rateBonusInfo = rbi;
            }
        }
        AccountBonusDAO.getInstance().store(player.getAccountName(), rateBonusInfo.makeBonus());
        player.stopBonusTask();
        player.startBonusTask();
        rateBonusInfo.rewardItem.forEach(rewardPair -> ItemFunctions.addItem(player, rewardPair.getLeft(), rewardPair.getRight(), true));
        if (rateBonusInfo.nameColor != null) {
            player.setNameColor(rateBonusInfo.nameColor);
        }
        if (player.getParty() != null) {
            player.getParty().recalculatePartyData();
        }
        player.broadcastUserInfo(true);
        return true;
    }
}
