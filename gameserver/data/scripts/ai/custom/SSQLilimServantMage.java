package ai.custom;

import l2ar.commons.util.Rnd;
import l2ar.gameserver.ai.Mystic;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.scripts.Functions;

public class SSQLilimServantMage extends Mystic {
    private boolean _attacked;

    public SSQLilimServantMage(final NpcInstance actor) {
        super(actor);
        _attacked = false;
    }

    @Override
    protected void onEvtAttacked(final Creature attacker, final int damage) {
        super.onEvtAttacked(attacker, damage);
        if (Rnd.chance(30) && !_attacked) {
            Functions.npcSay(getActor(), "Who dares enter this place?");
            _attacked = true;
        }
    }

    @Override
    protected void onEvtDead(final Creature killer) {
        if (Rnd.chance(30)) {
            Functions.npcSay(getActor(), "Lord Shilen... some day... you will accomplish... this mission...");
        }
        super.onEvtDead(killer);
    }
}
