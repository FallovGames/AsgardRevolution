package ai.custom;

import org.apache.commons.lang3.ArrayUtils;
import l2ar.commons.threading.RunnableImpl;
import l2ar.gameserver.ThreadPoolManager;
import l2ar.gameserver.ai.CtrlEvent;
import l2ar.gameserver.ai.Fighter;
import l2ar.gameserver.model.instances.NpcInstance;

import java.util.List;

public class SSQLilithMinion extends Fighter {
    private final int[] _enemies;

    public SSQLilithMinion(final NpcInstance actor) {
        super(actor);
        _enemies = new int[]{32719, 32720, 32721};
        actor.setHasChatWindow(false);
    }

    @Override
    protected void onEvtSpawn() {
        super.onEvtSpawn();
        ThreadPoolManager.getInstance().schedule(new Attack(), 3000L);
    }

    private NpcInstance getEnemy() {
        final List<NpcInstance> around = getActor().getAroundNpc(1000, 300);
        if (around != null && !around.isEmpty()) {
            for (final NpcInstance npc : around) {
                if (ArrayUtils.contains(_enemies, npc.getNpcId())) {
                    return npc;
                }
            }
        }
        return null;
    }

    @Override
    protected boolean randomWalk() {
        return false;
    }

    public class Attack extends RunnableImpl {
        @Override
        public void runImpl() {
            if (getEnemy() != null) {
                getActor().getAI().notifyEvent(CtrlEvent.EVT_ATTACKED, getEnemy(), 10000000);
            }
        }
    }
}
