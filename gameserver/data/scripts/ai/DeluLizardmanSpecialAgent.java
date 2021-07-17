package ai;

import l2ar.commons.util.Rnd;
import l2ar.gameserver.ai.Ranger;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.scripts.Functions;

public class DeluLizardmanSpecialAgent extends Ranger {
    private boolean _firstTimeAttacked;

    public DeluLizardmanSpecialAgent(final NpcInstance actor) {
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
            if (Rnd.chance(25)) {
                Functions.npcSay(actor, "How dare you interrupt our fight! Hey guys, help!");
            }
        } else if (Rnd.chance(10)) {
            Functions.npcSay(actor, "Hey! Were having a duel here!");
        }
        super.onEvtAttacked(attacker, damage);
    }
}
