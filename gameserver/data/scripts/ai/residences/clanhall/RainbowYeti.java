package ai.residences.clanhall;

import npc.model.residences.clanhall.RainbowGourdInstance;
import npc.model.residences.clanhall.RainbowYetiInstance;
import l2ar.commons.threading.RunnableImpl;
import l2ar.commons.util.Rnd;
import l2ar.gameserver.ThreadPoolManager;
import l2ar.gameserver.ai.CharacterAI;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.Player;
import l2ar.gameserver.model.Skill;
import l2ar.gameserver.model.entity.events.impl.ClanHallMiniGameEvent;
import l2ar.gameserver.model.entity.events.objects.CMGSiegeClanObject;
import l2ar.gameserver.model.entity.events.objects.SpawnExObject;
import l2ar.gameserver.model.entity.events.objects.ZoneObject;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.utils.NpcUtils;

import java.util.List;

public class RainbowYeti extends CharacterAI {
    public RainbowYeti(final NpcInstance actor) {
        super(actor);
    }

    @Override
    public void onEvtSeeSpell(final Skill skill, final Creature character) {
        final RainbowYetiInstance actor = (RainbowYetiInstance) getActor();
        final ClanHallMiniGameEvent miniGameEvent = actor.getEvent(ClanHallMiniGameEvent.class);
        if (miniGameEvent == null) {
            return;
        }
        if (!character.isPlayer()) {
            return;
        }
        final Player player = character.getPlayer();
        CMGSiegeClanObject siegeClan = null;
        final List<CMGSiegeClanObject> attackers = miniGameEvent.getObjects("attackers");
        for (final CMGSiegeClanObject $ : attackers) {
            if ($.isParticle(player)) {
                siegeClan = $;
            }
        }
        if (siegeClan == null) {
            return;
        }
        final int index = attackers.indexOf(siegeClan);
        int warIndex;
        RainbowGourdInstance gourdInstance;
        RainbowGourdInstance gourdInstance2;
        switch (skill.getId()) {
            case 2240: {
                if (!Rnd.chance(90)) {
                    actor.addMob(NpcUtils.spawnSingle(35592, actor.getX() + 10, actor.getY() + 10, actor.getZ(), 0L));
                    break;
                }
                gourdInstance = getGourd(index);
                if (gourdInstance == null) {
                    return;
                }
                gourdInstance.doDecrease(player);
                break;
            }
            case 2241: {
                warIndex = rndEx(attackers.size(), index);
                if (warIndex == Integer.MIN_VALUE) {
                    return;
                }
                gourdInstance2 = getGourd(warIndex);
                if (gourdInstance2 == null) {
                    return;
                }
                gourdInstance2.doHeal();
                break;
            }
            case 2242: {
                warIndex = rndEx(attackers.size(), index);
                if (warIndex == Integer.MIN_VALUE) {
                    return;
                }
                gourdInstance = getGourd(index);
                gourdInstance2 = getGourd(warIndex);
                if (gourdInstance2 == null || gourdInstance == null) {
                    return;
                }
                gourdInstance.doSwitch(gourdInstance2);
                break;
            }
            case 2243: {
                warIndex = rndEx(attackers.size(), index);
                if (warIndex == Integer.MIN_VALUE) {
                    return;
                }
                final ZoneObject zone = miniGameEvent.getFirstObject("zone_" + warIndex);
                if (zone == null) {
                    return;
                }
                zone.setActive(true);
                ThreadPoolManager.getInstance().schedule(new ZoneDeactive(zone), 60000L);
                break;
            }
        }
    }

    private RainbowGourdInstance getGourd(final int index) {
        final ClanHallMiniGameEvent miniGameEvent = getActor().getEvent(ClanHallMiniGameEvent.class);
        final SpawnExObject spawnEx = miniGameEvent.getFirstObject("arena_" + index);
        return (RainbowGourdInstance) spawnEx.getSpawns().get(1).getFirstSpawned();
    }

    private int rndEx(final int size, final int ex) {
        int rnd = Integer.MIN_VALUE;
        for (int i = 0; i < 127; ++i) {
            rnd = Rnd.get(size);
            if (rnd != ex) {
                break;
            }
        }
        return rnd;
    }

    private static class ZoneDeactive extends RunnableImpl {
        private final ZoneObject _zone;

        public ZoneDeactive(final ZoneObject zone) {
            _zone = zone;
        }

        @Override
        public void runImpl() {
            _zone.setActive(false);
        }
    }
}
