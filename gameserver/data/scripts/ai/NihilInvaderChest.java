package ai;

import l2ar.commons.util.Rnd;
import l2ar.gameserver.ai.DefaultAI;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.network.lineage2.serverpackets.MagicSkillUse;

public class NihilInvaderChest extends DefaultAI {
    private static final int[] _firstLevelItems = {4039, 4040, 4041, 4042, 4043, 4044};
    private static final int[] _secondLevelItems = {9628, 9629, 9630};

    public NihilInvaderChest(final NpcInstance actor) {
        super(actor);
        actor.startImmobilized();
    }

    @Override
    protected void onEvtAttacked(final Creature attacker, final int damage) {
        final NpcInstance actor = getActor();
        if (actor.getNpcId() == 18820) {
            if (Rnd.chance(40)) {
                actor.broadcastPacket(new MagicSkillUse(actor, actor, 2025, 1, 0, 10L));
                actor.dropItem(attacker.getPlayer(), NihilInvaderChest._firstLevelItems[Rnd.get(0, NihilInvaderChest._firstLevelItems.length - 1)], (long) Rnd.get(10, 20));
                actor.doDie(null);
            }
        } else if (actor.getNpcId() == 18823 && Rnd.chance(40)) {
            actor.broadcastPacket(new MagicSkillUse(actor, actor, 2025, 1, 0, 10L));
            actor.dropItem(attacker.getPlayer(), NihilInvaderChest._secondLevelItems[Rnd.get(0, NihilInvaderChest._secondLevelItems.length - 1)], (long) Rnd.get(10, 20));
            actor.doDie(null);
        }
        for (final NpcInstance npc : actor.getReflection().getNpcs()) {
            if (npc.getNpcId() == actor.getNpcId()) {
                npc.deleteMe();
            }
        }
        super.onEvtAttacked(attacker, damage);
    }

    @Override
    protected void onEvtAggression(final Creature target, final int aggro) {
    }
}
