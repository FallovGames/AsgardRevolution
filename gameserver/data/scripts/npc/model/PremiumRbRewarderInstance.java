package npc.model;

import l2ar.gameserver.Config;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.Party;
import l2ar.gameserver.model.Player;
import l2ar.gameserver.model.instances.RaidBossInstance;
import l2ar.gameserver.templates.npc.NpcTemplate;
import services.GlobalServices;

/**
 * Solution
 * 01.09.2018
 * 18:36
 */

public class PremiumRbRewarderInstance extends RaidBossInstance {
    public PremiumRbRewarderInstance(int objectId, NpcTemplate template) {
        super(objectId, template);
    }

    @Override
    protected void onDeath(Creature killer) {
        super.onDeath(killer);
        if (killer != null && killer.isPlayable()) {
            Player player = killer.getPlayer();
            if (player.isDead()) {
                return;
            }
            Party partyPlayer = player.getParty();
            if (partyPlayer == null) {
                GlobalServices.makeCustomPremium(player,1);
            }
            if (partyPlayer != null) {
                for (Player playerParty : partyPlayer) {
                    if (partyPlayer != null && !playerParty.isDead()) {
                        if (getDistance3D(playerParty) > Config.ALT_PARTY_DISTRIBUTION_RANGE) {
                            continue;
                        }
                        GlobalServices.makeCustomPremium(player, 2);
                    }
                }
            }
        }
    }
}
