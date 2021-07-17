package ai;

import l2ar.gameserver.ai.Fighter;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.scripts.Functions;

public class Tiberias extends Fighter {
    public Tiberias(final NpcInstance actor) {
        super(actor);
    }

    @Override
    protected void onEvtDead(final Creature killer) {
        final NpcInstance actor = getActor();
        Functions.npcShoutCustomMessage(actor, "scripts.ai.Tiberias.kill");
        super.onEvtDead(killer);
    }
}
