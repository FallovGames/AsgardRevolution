package ai;

import l2ar.gameserver.ai.CtrlEvent;
import l2ar.gameserver.ai.CtrlIntention;
import l2ar.gameserver.ai.Fighter;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.instances.NpcInstance;

import java.util.List;

public class KanadisFollower extends Fighter {
    public KanadisFollower(final NpcInstance actor) {
        super(actor);
    }

    @Override
    protected void onEvtSpawn() {
        super.onEvtSpawn();
        final NpcInstance actor = getActor();
        final List<NpcInstance> around = actor.getAroundNpc(7000, 300);
        if (around != null && !around.isEmpty()) {
            for (final NpcInstance npc : around) {
                if (npc.getNpcId() == 36562) {
                    actor.getAI().notifyEvent(CtrlEvent.EVT_ATTACKED, npc, 500);
                }
            }
        }
    }

    @Override
    protected void onEvtAttacked(final Creature attacker, final int damage) {
        final NpcInstance actor = getActor();
        if (attacker.getNpcId() == 36562) {
            actor.getAggroList().addDamageHate(attacker, 0, 100);
            startRunningTask(2000L);
            setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
        }
        super.onEvtAttacked(attacker, damage);
    }

    @Override
    protected boolean maybeMoveToHome() {
        return false;
    }
}
