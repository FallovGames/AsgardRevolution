package ai;


import l2ar.commons.util.Rnd;
import l2ar.gameserver.ai.CtrlIntention;
import l2ar.gameserver.ai.Fighter;
import l2ar.gameserver.geodata.GeoEngine;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.GameObjectsStorage;
import l2ar.gameserver.model.Zone;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.network.lineage2.serverpackets.MagicSkillUse;
import l2ar.gameserver.utils.Location;
import l2ar.gameserver.utils.ReflectionUtils;

import java.util.ArrayList;
import java.util.List;

public class Archangel extends Fighter {
    private final Zone _zone = ReflectionUtils.getZone("[baium_epic]");
    private long _new_target = System.currentTimeMillis() + 20000;

    public Archangel(NpcInstance actor) {
        super(actor);
    }

    @Override
    public boolean isGlobalAI() {
        return true;
    }

    @Override
    protected boolean thinkActive() {
        final NpcInstance baiumBoss = GameObjectsStorage.getByNpcId(29020);
        if (baiumBoss == null) {
            getActor().deleteMe();
            return false;
        }
        return super.thinkActive();
    }

    @Override
    protected void thinkAttack() {
        NpcInstance actor = getActor();
        if (actor == null) {
            return;
        }
        if (_new_target < System.currentTimeMillis()) {
            List<Creature> alive = new ArrayList<>();
            actor.getAroundCharacters(2000, 200).stream().filter(target -> !target.isDead()).forEach(target -> {
                if (target.getNpcId() == 29020) {
                    if (Rnd.chance(5))
                        alive.add(target);
                } else
                    alive.add(target);
            });
            if (!alive.isEmpty()) {
                Creature rndTarget = alive.get(Rnd.get(alive.size()));
                if (rndTarget != null && (rndTarget.getNpcId() == 29020 || rndTarget.isPlayer())) {
                    setIntention(CtrlIntention.AI_INTENTION_ATTACK, rndTarget);
                    actor.getAggroList().addDamageHate(rndTarget, 100, 10);
                }
            }
            _new_target = System.currentTimeMillis() + 20000;
        }
        super.thinkAttack();
    }

    @Override
    protected void onEvtAttacked(final Creature attacker, final int damage) {
        NpcInstance actor = getActor();
        if (actor != null && !actor.isDead()) {
            if (attacker != null) {
                if (attacker.getNpcId() == 29020) {
                    actor.getAggroList().addDamageHate(attacker, damage, 10);
                    setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
                }
            }
        }
        super.onEvtAttacked(attacker, damage);
    }

    @Override
    protected boolean maybeMoveToHome() {
        NpcInstance actor = getActor();
        if (actor != null && !_zone.checkIfInZone(actor))
            returnHome();
        return false;
    }

    @Override
    protected void returnHome() {
        NpcInstance actor = getActor();
        Location sloc = actor.getSpawnedLoc();
        clearTasks();
        actor.stopMove();
        actor.getAggroList().clear(true);
        setAttackTimeout(Long.MAX_VALUE);
        setAttackTarget(null);
        changeIntention(CtrlIntention.AI_INTENTION_ACTIVE, null, null);
        actor.broadcastPacketToOthers(new MagicSkillUse(actor, actor, 2036, 1, 500, 0));
        actor.teleToLocation(sloc.x, sloc.y, GeoEngine.getHeight(sloc, actor.getGeoIndex()));
    }
}