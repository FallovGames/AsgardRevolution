package ai;

import l2ar.commons.util.Rnd;
import l2ar.gameserver.ai.Mystic;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.instances.MonsterInstance;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.stats.Env;
import l2ar.gameserver.stats.Stats;
import l2ar.gameserver.stats.funcs.Func;
import l2ar.gameserver.templates.npc.MinionData;

public class GraveRobberSummoner extends Mystic {
    private static final int[] Servitors = {22683, 22684, 22685, 22686};

    private int _lastMinionCount;

    public GraveRobberSummoner(final NpcInstance actor) {
        super(actor);
        _lastMinionCount = 1;
        actor.addStatFunc(new FuncMulMinionCount(Stats.MAGIC_DEFENCE, 48, actor));
        actor.addStatFunc(new FuncMulMinionCount(Stats.POWER_DEFENCE, 48, actor));
    }

    @Override
    protected void onEvtSpawn() {
        super.onEvtSpawn();
        final NpcInstance actor = getActor();
        actor.getMinionList().addMinion(new MinionData(GraveRobberSummoner.Servitors[Rnd.get(GraveRobberSummoner.Servitors.length)], Rnd.get(2)));
        _lastMinionCount = Math.max(actor.getMinionList().getAliveMinions().size(), 1);
    }

    @Override
    protected void onEvtAttacked(final Creature attacker, final int damage) {
        final MonsterInstance actor = (MonsterInstance) getActor();
        if (actor.isDead()) {
            return;
        }
        _lastMinionCount = Math.max(actor.getMinionList().getAliveMinions().size(), 1);
        super.onEvtAttacked(attacker, damage);
    }

    @Override
    protected void onEvtDead(final Creature killer) {
        final NpcInstance actor = getActor();
        actor.getMinionList().deleteMinions();
        super.onEvtDead(killer);
    }

    private class FuncMulMinionCount extends Func {
        public FuncMulMinionCount(final Stats stat, final int order, final Object owner) {
            super(stat, order, owner);
        }

        @Override
        public void calc(final Env env) {
            env.value *= _lastMinionCount;
        }
    }
}
