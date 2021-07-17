package events.mobBuffs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import l2ar.commons.util.Rnd;
import l2ar.gameserver.listener.actor.OnDeathListener;
import l2ar.gameserver.listener.script.OnInitScriptListener;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.actor.listener.CharListenerList;
import l2ar.gameserver.model.instances.MonsterInstance;
import l2ar.gameserver.scripts.Functions;

/**
 * Solution
 * 23.08.2018
 * 11:12
 */

public class mobBuffs extends Functions implements OnInitScriptListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(mobBuffs.class);
    private static final String EVENT_NAME = "MOB_BUFFS";
    private static boolean ACTIVE;
    private OnDeathListenerImpl deathListener = new OnDeathListenerImpl();



    @Override
    public void onInit() {
        CharListenerList.addGlobal(deathListener);
        ACTIVE = true;
        LOGGER.info("Loaded Event: MOB_BUFFS [state: activated]");
    }

    private class OnDeathListenerImpl implements OnDeathListener {


        @Override
        public void onDeath(final Creature actor, final Creature killer) {
            try {
                if (!Functions.SimpleCheckDrop(actor, killer)) {
                    return;
                }
                final MonsterInstance npc = (MonsterInstance) actor;
                if (Rnd.get(100) < 5 && !npc.isChampionRed() && !npc.isChampionRed()) {
                    npc.mobBuffs(killer.getPlayer(), 8300, 1, 15, 4350, 3);
                }
                if (npc.isChampionRed()) {
                    npc.mobBuffs(killer.getPlayer(), 8300, 1, 15 * 4, 4350, 3);
                }
                if (npc.isChampionBlue()) {
                    npc.mobBuffs(killer.getPlayer(), 8300, 1, 15 * 2, 4350, 3);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
