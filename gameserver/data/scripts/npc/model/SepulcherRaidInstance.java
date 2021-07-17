package npc.model;

import bosses.FourSepulchersSpawn;
import l2ar.commons.threading.RunnableImpl;
import l2ar.gameserver.ThreadPoolManager;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.Player;
import l2ar.gameserver.model.instances.RaidBossInstance;
import l2ar.gameserver.model.quest.QuestState;
import l2ar.gameserver.scripts.Functions;
import l2ar.gameserver.templates.npc.NpcTemplate;

import java.util.concurrent.Future;

public class SepulcherRaidInstance extends RaidBossInstance {
    public int mysteriousBoxId;
    private Future<?> _onDeadEventTask;

    public SepulcherRaidInstance(final int objectId, final NpcTemplate template) {
        super(objectId, template);
    }

    @Override
    protected void onDeath(final Creature killer) {
        super.onDeath(killer);
        final Player player = killer.getPlayer();
        if (player != null) {
            giveCup(player);
        }
        if (_onDeadEventTask != null) {
            _onDeadEventTask.cancel(false);
        }
        _onDeadEventTask = ThreadPoolManager.getInstance().schedule(new OnDeadEvent(this), 5000L);
    }

    @Override
    protected void onDelete() {
        _onDeadEventTask = null;
        super.onDelete();
    }

    private void giveCup(final Player player) {
        final String questId = "_620_FourGoblets";
        int cupId = 0;
        final int oldBrooch = 7262;
        switch (getNpcId()) {
            case 25339: {
                cupId = 7256;
                break;
            }
            case 25342: {
                cupId = 7257;
                break;
            }
            case 25346: {
                cupId = 7258;
                break;
            }
            case 25349: {
                cupId = 7259;
                break;
            }
        }
        if (player.getParty() != null) {
            for (final Player mem : player.getParty().getPartyMembers()) {
                final QuestState qs = mem.getQuestState(questId);
                if (qs != null && (qs.isStarted() || qs.isCompleted()) && mem.getInventory().getItemByItemId(oldBrooch) == null && player.isInRange(mem, 700L)) {
                    Functions.addItem(mem, cupId, 1L);
                }
            }
        } else {
            final QuestState qs2 = player.getQuestState(questId);
            if (qs2 != null && (qs2.isStarted() || qs2.isCompleted()) && player.getInventory().getItemByItemId(oldBrooch) == null) {
                Functions.addItem(player, cupId, 1L);
            }
        }
    }

    @Override
    public boolean canChampion() {
        return false;
    }

    private class OnDeadEvent extends RunnableImpl {
        SepulcherRaidInstance _activeChar;

        public OnDeadEvent(final SepulcherRaidInstance activeChar) {
            _activeChar = activeChar;
        }

        @Override
        public void runImpl() {
            FourSepulchersSpawn.spawnEmperorsGraveNpc(_activeChar.mysteriousBoxId);
        }
    }
}
