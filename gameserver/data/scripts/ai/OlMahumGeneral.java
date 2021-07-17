package ai;

import l2ar.commons.util.Rnd;
import l2ar.gameserver.ai.Fighter;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.scripts.Functions;

public class OlMahumGeneral extends Fighter {
    private boolean _firstTimeAttacked;

    public OlMahumGeneral(final NpcInstance actor) {
        super(actor);
        _firstTimeAttacked = true;
    }

    @Override
    protected void onEvtAttacked(final Creature attacker, final int damage) {
        final NpcInstance actor = getActor();
        if (_firstTimeAttacked) {
            _firstTimeAttacked = false;
            if (Rnd.chance(25)) {
                Functions.npcSay(actor, "We shall see about that!");
            }
        } else if (Rnd.chance(10)) {
            Functions.npcSay(actor, "I will definitely repay this humiliation!");
        }
        super.onEvtAttacked(attacker, damage);
    }

    @Override
    protected void onEvtDead(final Creature killer) {
        _firstTimeAttacked = true;
        super.onEvtDead(killer);
    }
}
