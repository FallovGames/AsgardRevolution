package ai.residences.clanhall;

import ai.residences.SiegeGuardFighter;
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

public class AlfredVonHellmann extends SiegeGuardFighter {
    public static final Skill DAMAGE_SKILL = SkillTable.getInstance().getInfo(5000, 1);
    public static final Skill DRAIN_SKILL = SkillTable.getInstance().getInfo(5001, 1);
    private static final Zone ZONE_3 = ReflectionUtils.getZone("lidia_zone3");

    public AlfredVonHellmann(final NpcInstance actor) {
        super(actor);
    }

    @Override
    public void onEvtSpawn() {
        super.onEvtSpawn();
        AlfredVonHellmann.ZONE_3.setActive(true);
        Functions.npcShoutCustomMessage(getActor(), "clanhall.AlfredVonHellmann.HEH_HEH_I_SEE_THAT_THE_FEAST_HAS_BEGAN_BE_WARY_THE_CURSE_OF_THE_HELLMANN_FAMILY_HAS_POISONED_THIS_LAND");
    }

    @Override
    public void onEvtDead(final Creature killer) {
        final NpcInstance actor = getActor();
        super.onEvtDead(killer);
        AlfredVonHellmann.ZONE_3.setActive(false);
        Functions.npcShoutCustomMessage(actor, "clanhall.AlfredVonHellmann.AARGH_IF_I_DIE_THEN_THE_MAGIC_FORCE_FIELD_OF_BLOOD_WILL");
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
            addTaskCast(attacker, AlfredVonHellmann.DRAIN_SKILL);
        }
        final Creature target = actor.getAggroList().getMostHated();
        if (target == attacker && Rnd.chance(0.3)) {
            addTaskCast(attacker, AlfredVonHellmann.DAMAGE_SKILL);
        }
    }
}
