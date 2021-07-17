package npc.model.residences;

import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.Player;
import l2ar.gameserver.model.base.Experience;
import l2ar.gameserver.model.entity.events.impl.SiegeEvent;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.model.pledge.Clan;
import l2ar.gameserver.model.reward.RewardItem;
import l2ar.gameserver.model.reward.RewardList;
import l2ar.gameserver.model.reward.RewardType;
import l2ar.gameserver.stats.Stats;
import l2ar.gameserver.templates.npc.NpcTemplate;

import java.util.List;
import java.util.Map.Entry;

public class SiegeGuardInstance extends NpcInstance {
    public SiegeGuardInstance(final int objectId, final NpcTemplate template) {
        super(objectId, template);
        setHasChatWindow(false);
    }

    @Override
    public boolean isSiegeGuard() {
        return true;
    }

    @Override
    public int getAggroRange() {
        return 1200;
    }

    @Override
    public boolean isAutoAttackable(final Creature attacker) {
        final Player player = attacker.getPlayer();
        if (player == null) {
            return false;
        }
        final SiegeEvent<?, ?> siegeEvent = (SiegeEvent<?, ?>) getEvent(SiegeEvent.class);
        final SiegeEvent<?, ?> siegeEvent2 = (SiegeEvent<?, ?>) attacker.getEvent(SiegeEvent.class);
        final Clan clan = player.getClan();
        return siegeEvent != null && (clan == null || siegeEvent != siegeEvent2 || siegeEvent.getSiegeClan("defenders", clan) == null);
    }

    @Override
    public boolean hasRandomAnimation() {
        return false;
    }

    @Override
    public boolean isInvul() {
        return false;
    }

    @Override
    protected void onDeath(final Creature killer) {
        final SiegeEvent<?, ?> siegeEvent = (SiegeEvent<?, ?>) getEvent(SiegeEvent.class);
        if (killer != null) {
            final Player player = killer.getPlayer();
            if (siegeEvent != null && player != null) {
                final Clan clan = player.getClan();
                final SiegeEvent<?, ?> siegeEvent2 = (SiegeEvent<?, ?>) killer.getEvent(SiegeEvent.class);
                if (clan != null && siegeEvent == siegeEvent2 && siegeEvent.getSiegeClan("defenders", clan) == null) {
                    Creature topdam = getAggroList().getTopDamager();
                    if (topdam == null) {
                        topdam = killer;
                    }
                    for (final Entry<RewardType, RewardList> entry : getTemplate().getRewards().entrySet()) {
                        rollRewards(entry, killer, topdam);
                    }
                }
            }
        }
        super.onDeath(killer);
    }

    public void rollRewards(final Entry<RewardType, RewardList> entry, final Creature lastAttacker, final Creature topDamager) {
        final RewardList list = entry.getValue();
        final Player activePlayer = topDamager.getPlayer();
        if (activePlayer == null) {
            return;
        }
        final int diff = calculateLevelDiffForDrop(topDamager.getLevel());
        double mod = calcStat(Stats.ITEM_REWARD_MULTIPLIER, 1.0, topDamager, null);
        mod *= Experience.penaltyModifier((long) diff, 9.0);
        final List<RewardItem> rewardItems = list.roll(activePlayer, mod, false, true);
        for (final RewardItem drop : rewardItems) {
            dropItem(activePlayer, drop.itemId, drop.count);
        }
    }

    @Override
    public boolean isFearImmune() {
        return true;
    }

    @Override
    public boolean isParalyzeImmune() {
        return true;
    }

    @Override
    public Clan getClan() {
        return null;
    }
}
