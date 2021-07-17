package ai.freya;

import bosses.ValakasManager;
import l2ar.gameserver.ai.CtrlEvent;
import l2ar.gameserver.ai.Mystic;
import l2ar.gameserver.model.instances.NpcInstance;

public class ValakasMinion extends Mystic {
    public ValakasMinion(final NpcInstance actor) {
        super(actor);
        actor.startImmobilized();
    }

    @Override
    protected void onEvtSpawn() {
        super.onEvtSpawn();
        ValakasManager.getZone().getInsidePlayers().forEach(player -> notifyEvent(CtrlEvent.EVT_AGGRESSION, player, 5000));
    }
}
