package ai.freya;

import bosses.AntharasManager;
import l2ar.gameserver.ai.CtrlEvent;
import l2ar.gameserver.ai.Fighter;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.instances.NpcInstance;

public class AntharasMinion extends Fighter {
    public AntharasMinion(final NpcInstance actor) {
        super(actor);
        actor.startDebuffImmunity();
    }

    @Override
    protected void onEvtSpawn() {
        super.onEvtSpawn();
        AntharasManager.getZone().getInsidePlayers().forEach(player -> notifyEvent(CtrlEvent.EVT_AGGRESSION, player, 5000));
    }

    @Override
    protected void onEvtDead(final Creature killer) {
        getActor().doCast(getSkillInfo(5097, 1), getActor(), true);
        super.onEvtDead(killer);
    }

    @Override
    protected void returnHome(final boolean clearAggro, final boolean teleport) {
    }
}
