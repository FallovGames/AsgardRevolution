package ai;

import l2ar.gameserver.ai.Fighter;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.network.lineage2.serverpackets.MagicSkillUse;
import l2ar.gameserver.utils.Location;

/**
 * Created by JunkyFunky
 * on 06.07.2018 17:16
 * group j2dev
 */
public class AntiPK extends Fighter {

    public AntiPK(NpcInstance actor) {
        super(actor);
    }

    @Override
    public boolean checkAggression(final Creature target) {
        return target.isPlayable() && target.getKarma() > 0 && super.checkAggression(target);
    }

    @Override
    public void onEvtAggression(final Creature target, final int agro) {
        NpcInstance actor = getActor();
        actor.teleToLocation(Location.findAroundPosition(target, 500, 1000));
        actor.broadcastPacket(new MagicSkillUse(actor, target, 2036, 1, 0, 0L));
        super.onEvtAggression(target, agro);

    }

}
