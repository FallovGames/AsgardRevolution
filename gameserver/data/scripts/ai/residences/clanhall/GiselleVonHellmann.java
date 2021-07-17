package ai.residences.clanhall;

import ai.residences.SiegeGuardMystic;
import l2ar.commons.util.Rnd;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.Skill;
import l2ar.gameserver.model.Zone;
import l2ar.gameserver.model.entity.events.impl.ClanHallSiegeEvent;
import l2ar.gameserver.model.entity.events.objects.SpawnExObject;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.scripts.Functions;
import l2ar.gameserver.tables.SkillTable;
import l2ar.gameserver.utils.PositionUtils;
import l2ar.gameserver.utils.ReflectionUtils;

public class GiselleVonHellmann extends SiegeGuardMystic {
    private static final Skill DAMAGE_SKILL = SkillTable.getInstance().getInfo(5003, 1);
    private static final Zone ZONE_1 = ReflectionUtils.getZone("lidia_zone1");
    private static final Zone ZONE_2 = ReflectionUtils.getZone("lidia_zone2");

    public GiselleVonHellmann(final NpcInstance actor) {
        super(actor);
    }

    @Override
    public void onEvtSpawn() {
        super.onEvtSpawn();
        GiselleVonHellmann.ZONE_1.setActive(true);
        GiselleVonHellmann.ZONE_2.setActive(true);
        Functions.npcShoutCustomMessage(getActor(), "clanhall.GiselleVonHellmann.ARISE_MY_FAITHFUL_SERVANTS_YOU_MY_PEOPLE_WHO_HAVE_INHERITED_THE_BLOOD");
    }

    @Override
    public void onEvtDead(final Creature killer) {
        final NpcInstance actor = getActor();
        super.onEvtDead(killer);
        GiselleVonHellmann.ZONE_1.setActive(false);
        GiselleVonHellmann.ZONE_2.setActive(false);
        Functions.npcShoutCustomMessage(actor, "clanhall.GiselleVonHellmann.AARGH_IF_I_DIE_THEN_THE_MAGIC_FORCE_FIELD_OF_BLOOD_WILL");
        final ClanHallSiegeEvent siegeEvent = actor.getEvent(ClanHallSiegeEvent.class);
        if (siegeEvent == null) {
            return;
        }
        final SpawnExObject spawnExObject = siegeEvent.getFirstObject("boss");
        final NpcInstance lidiaNpc = spawnExObject.getFirstSpawned();
        if (lidiaNpc.getCurrentHpRatio() == 1.0) {
            lidiaNpc.setCurrentHp((double) (lidiaNpc.getMaxHp() / 2), true);
        }
    }

    @Override
    public void onEvtAttacked(final Creature attacker, final int damage) {
        final NpcInstance actor = getActor();
        super.onEvtAttacked(attacker, damage);
        if (PositionUtils.calculateDistance(attacker, actor, false) > 300.0 && Rnd.chance(0.13)) {
            addTaskCast(attacker, GiselleVonHellmann.DAMAGE_SKILL);
        }
    }
}
