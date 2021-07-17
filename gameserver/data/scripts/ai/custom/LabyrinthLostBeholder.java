package ai.custom;

import l2ar.gameserver.ai.Fighter;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.entity.Reflection;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.model.instances.ReflectionBossInstance;
import l2ar.gameserver.stats.Stats;
import l2ar.gameserver.stats.funcs.FuncSet;

public class LabyrinthLostBeholder extends Fighter {
    public LabyrinthLostBeholder(final NpcInstance actor) {
        super(actor);
    }

    @Override
    protected void onEvtDead(final Creature killer) {
        final NpcInstance actor = getActor();
        final Reflection r = actor.getReflection();
        if (!r.isDefault() && checkMates(actor.getNpcId()) && findLostCaptain() != null) {
            findLostCaptain().addStatFunc(new FuncSet(Stats.MAGIC_DEFENCE, 48, this, findLostCaptain().getTemplate().getBaseMDef() * 0.66));
        }
        super.onEvtDead(killer);
    }

    private boolean checkMates(final int id) {
        return getActor().getReflection().getNpcs().stream().noneMatch(n -> n.getNpcId() == id && !n.isDead());
    }

    private NpcInstance findLostCaptain() {
        return getActor().getReflection().getNpcs().stream().filter(n -> n instanceof ReflectionBossInstance).findFirst().orElse(null);
    }
}
