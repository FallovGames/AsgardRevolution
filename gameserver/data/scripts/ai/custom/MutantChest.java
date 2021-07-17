package ai.custom;

import l2ar.commons.util.Rnd;
import l2ar.gameserver.ai.Fighter;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.scripts.Functions;

public class MutantChest extends Fighter {
    public MutantChest(final NpcInstance actor) {
        super(actor);
        actor.startImmobilized();
    }

    @Override
    protected void onEvtDead(final Creature killer) {
        final NpcInstance actor = getActor();
        if (Rnd.chance(30)) {
            Functions.npcSay(actor, "\u0412\u0440\u0430\u0433\u0438! \u0412\u0441\u044e\u0434\u0443 \u0432\u0440\u0430\u0433\u0438! \u0412\u0441\u0435 \u0441\u044e\u0434\u0430, \u0432\u0440\u0430\u0433\u0438 \u0437\u0434\u0435\u0441\u044c!");
        }
        actor.deleteMe();
    }
}
