package ai;

import l2ar.commons.threading.RunnableImpl;
import l2ar.commons.util.Rnd;
import l2ar.gameserver.ThreadPoolManager;
import l2ar.gameserver.ai.DefaultAI;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.instances.NpcInstance;

public class Furance extends DefaultAI {
    public Furance(final NpcInstance actor) {
        super(actor);
        actor.startImmobilized();
    }

    @Override
    protected void onEvtSpawn() {
        super.onEvtSpawn();
        final NpcInstance actor = getActor();
        if (Rnd.chance(50)) {
            actor.setNpcState(1);
        }
        ThreadPoolManager.getInstance().scheduleAtFixedRate(new Switch(), 300000L, 300000L);
    }

    @Override
    protected void onEvtAttacked(final Creature attacker, final int damage) {
    }

    @Override
    protected void onEvtAggression(final Creature target, final int aggro) {
    }

    @Override
    protected boolean randomAnimation() {
        return false;
    }

    @Override
    public boolean isGlobalAI() {
        return true;
    }

    public class Switch extends RunnableImpl {
        @Override
        public void runImpl() {
            final NpcInstance actor = getActor();
            if (actor.getNpcState() == 1) {
                actor.setNpcState(2);
            } else {
                actor.setNpcState(1);
            }
        }
    }
}
