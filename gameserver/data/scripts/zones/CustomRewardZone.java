package zones;

import l2ar.commons.util.Rnd;
import l2ar.gameserver.handler.admincommands.AdminCommandHandler;
import l2ar.gameserver.handler.admincommands.IAdminCommandHandler;
import l2ar.gameserver.listener.actor.player.OnPvpPkKillListener;
import l2ar.gameserver.listener.script.OnInitScriptListener;
import l2ar.gameserver.listener.zone.OnZoneEnterLeaveListener;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.Player;
import l2ar.gameserver.model.Zone;
import l2ar.gameserver.utils.ItemFunctions;
import l2ar.gameserver.utils.ReflectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by JunkyFunky
 * on 09.03.2018 20:38
 * group j2dev
 */
public class CustomRewardZone implements IAdminCommandHandler, OnInitScriptListener {

    private final static List<RewardItemInfo> pvpRewardItems = new ArrayList<>();
    private final static List<RewardItemInfo> pkRewardItems = new ArrayList<>();
    private static PvpPkListener pvpPkListener;
    private static List<String> zones = Collections.emptyList();

    private static boolean hwidIpCheck(String val1, String val2) {
        return (val1 == null && val2 != null) || (val1 != null && !val1.equals(val2));
    }

    @Override
    public void onInit() {
        CustomRewardZoneConfig.load();
        if (CustomRewardZoneConfig.allowCustomRewardZones) {
            loadRewardsAndZones();
            AdminCommandHandler.getInstance().registerAdminCommandHandler(this);
            pvpPkListener = new PvpPkListener();
            final ZoneListener zoneListener = new ZoneListener();
            for (String zone_name : zones) {
                final Zone zone = ReflectionUtils.getZone(zone_name);
                if (zone != null) {
                    zone.addListener(zoneListener);
                }
            }
        }
    }

    private void loadRewardsAndZones() {
        pvpRewardItems.clear();
        pkRewardItems.clear();
        zones.clear();
        for (String reward : CustomRewardZoneConfig.pvpRewardItems) {
            final String[] rewardArray = reward.split(":");
            pvpRewardItems.add(new RewardItemInfo(rewardArray));
        }
        for (String reward : CustomRewardZoneConfig.pkRewardItems) {
            final String[] rewardArray = reward.split(":");
            pkRewardItems.add(new RewardItemInfo(rewardArray));
        }
        zones = Arrays.asList(CustomRewardZoneConfig.rewardZones);
    }

    @Override
    public boolean useAdminCommand(final Enum comm, final String[] wordList, final String fullString, final Player activeChar) {
        final Commands c = (Commands) comm;
        switch (c) {
            case admin_custom_reload: {
                onInit();
                activeChar.sendMessage("Custom Reward Zones Reloaded!");
                break;
            }
        }
        return false;
    }

    @Override
    public Enum[] getAdminCommandEnum() {
        return Commands.values();
    }

    enum Commands {
        admin_custom_reload
    }

    public static class PvpPkListener implements OnPvpPkKillListener {
        @Override
        public void onPvpPkKill(Player killer, Player victim, boolean isPk) {
            boolean ipCheck = true;
            boolean hwidCheck = true;
            if (CustomRewardZoneConfig.ipCheck) {
                ipCheck = (hwidIpCheck(victim.getIP(), killer.getIP()));
            }
            if (CustomRewardZoneConfig.hwidCheck) {
                hwidCheck = hwidIpCheck(victim.getNetConnection().getHwid(), killer.getNetConnection().getHwid());
            }
            if (ipCheck && hwidCheck) {
                for (RewardItemInfo itemInfo : pvpRewardItems) {
                    if (Rnd.chance(itemInfo.getItemChance())) {
                        ItemFunctions.addItem(killer, itemInfo.getItemId(), itemInfo.getItemCount(), true);
                    }
                }
                if (isPk) {
                    for (RewardItemInfo itemInfo : pkRewardItems) {
                        if (Rnd.chance(itemInfo.getItemChance())) {
                            ItemFunctions.addItem(killer, itemInfo.getItemId(), itemInfo.getItemCount(), true);
                        }
                    }
                }
            }
        }
    }

    private static class RewardItemInfo {
        private final int itemId;
        private final long itemCount;
        private final double itemChance;

        RewardItemInfo(String... rewards) {
            itemId = Integer.parseInt(rewards[0]);
            itemCount = Long.parseLong(rewards[1]);
            itemChance = Double.parseDouble(rewards[2]);
        }

        public int getItemId() {
            return itemId;
        }

        public long getItemCount() {
            return itemCount;
        }

        public double getItemChance() {
            return itemChance;
        }
    }

    public static class ZoneListener implements OnZoneEnterLeaveListener {

        @Override
        public void onZoneEnter(final Zone zone, final Creature cha) {
            if (!cha.isPlayer()) {
                return;
            }
            final Player player = cha.getPlayer();
            if (player != null) {
                player.addListener(pvpPkListener);
            }
        }

        @Override
        public void onZoneLeave(final Zone zone, final Creature cha) {
            if (!cha.isPlayer()) {
                return;
            }
            final Player player = cha.getPlayer();
            if (player != null) {
                player.removeListener(pvpPkListener);
            }
        }
    }
}
