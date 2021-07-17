package ai;

import l2ar.commons.util.Rnd;
import l2ar.gameserver.ai.CtrlEvent;
import l2ar.gameserver.ai.Fighter;
import l2ar.gameserver.data.xml.holder.NpcTemplateHolder;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.instances.MonsterInstance;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.scripts.Functions;

public class TimakOrcTroopLeader extends Fighter {
    private static final int[] BROTHERS = {20768, 20769, 20770};

    private boolean _firstTimeAttacked;

    public TimakOrcTroopLeader(final NpcInstance actor) {
        super(actor);
        _firstTimeAttacked = true;
    }

    @Override
    protected void onEvtAttacked(final Creature attacker, final int damage) {
        final NpcInstance actor = getActor();
        if (!actor.isDead() && _firstTimeAttacked) {
            _firstTimeAttacked = false;
            Functions.npcSay(actor, "Show yourselves!");
            for (final int bro : TimakOrcTroopLeader.BROTHERS) {
                try {
                    final NpcInstance npc = NpcTemplateHolder.getInstance().getTemplate(bro).getNewInstance();
                    npc.setSpawnedLoc(((MonsterInstance) actor).getMinionPosition());
                    npc.setReflection(actor.getReflection());
                    npc.setCurrentHpMp((double) npc.getMaxHp(), (double) npc.getMaxMp(), true);
                    npc.spawnMe(npc.getSpawnedLoc());
                    npc.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, attacker, Rnd.get(1, 100));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        super.onEvtAttacked(attacker, damage);
    }

    @Override
    protected void onEvtDead(final Creature killer) {
        _firstTimeAttacked = true;
        super.onEvtDead(killer);
    }
}
