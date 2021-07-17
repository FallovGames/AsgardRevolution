package ai;

import l2ar.commons.util.Rnd;
import l2ar.gameserver.ai.Mystic;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.scripts.Functions;

public class SoulofTreeGuardian extends Mystic {
    private boolean _firstTimeAttacked;

    public SoulofTreeGuardian(final NpcInstance actor) {
        super(actor);
        _firstTimeAttacked = true;
    }

    @Override
    protected void onEvtSpawn() {
        _firstTimeAttacked = true;
        super.onEvtSpawn();
    }

    @Override
    protected void onEvtAttacked(final Creature attacker, final int damage) {
        final NpcInstance actor = getActor();
        if (_firstTimeAttacked) {
            _firstTimeAttacked = false;
            if (Rnd.chance(10)) {
                Functions.npcSayInRangeCustomMessage(actor, 500, "scripts.ai.SoulofTreeGuardian.WE_MUST_PROTECT");
            }
        } else if (Rnd.chance(10)) {
            Functions.npcSayInRangeCustomMessage(actor, 500, "scripts.ai.SoulofTreeGuardian.GET_OUT");
        }
    }
}
