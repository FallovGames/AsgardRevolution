package ai;

import l2ar.gameserver.ai.CtrlEvent;
import l2ar.gameserver.ai.DefaultAI;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.scripts.Functions;

import java.util.List;

public class FieldMachine extends DefaultAI {
    private long _lastAction;

    public FieldMachine(final NpcInstance actor) {
        super(actor);
    }

    @Override
    protected void onEvtAttacked(final Creature attacker, final int damage) {
        final NpcInstance actor = getActor();
        if (attacker == null || attacker.getPlayer() == null) {
            return;
        }
        if (System.currentTimeMillis() - _lastAction > 15000L) {
            _lastAction = System.currentTimeMillis();
            Functions.npcSayCustomMessage(actor, "scripts.ai.FieldMachine." + actor.getNpcId());
            final List<NpcInstance> around = actor.getAroundNpc(1500, 300);
            if (around != null && !around.isEmpty()) {
                for (final NpcInstance npc : around) {
                    if (npc.isMonster() && npc.getNpcId() >= 22656 && npc.getNpcId() <= 22659) {
                        npc.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, attacker, 5000);
                    }
                }
            }
        }
    }
}
