package ai;

import l2ar.commons.lang.reference.HardReference;
import l2ar.commons.lang.reference.HardReferences;
import l2ar.commons.util.Rnd;
import l2ar.gameserver.ai.CtrlEvent;
import l2ar.gameserver.ai.Fighter;
import l2ar.gameserver.model.MinionList;
import l2ar.gameserver.model.Player;
import l2ar.gameserver.model.World;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.scripts.Functions;

import java.util.List;
import java.util.stream.Collectors;

public class Kama56Boss extends Fighter {
    private long _nextOrderTime;
    private HardReference<Player> _lastMinionsTargetRef;

    public Kama56Boss(final NpcInstance actor) {
        super(actor);
        _nextOrderTime = 0L;
        _lastMinionsTargetRef = HardReferences.emptyRef();
    }

    private void sendOrderToMinions(final NpcInstance actor) {
        if (!actor.isInCombat()) {
            _lastMinionsTargetRef = HardReferences.emptyRef();
            return;
        }
        final MinionList ml = actor.getMinionList();
        if (ml == null || !ml.hasMinions()) {
            _lastMinionsTargetRef = HardReferences.emptyRef();
            return;
        }
        final long now = System.currentTimeMillis();
        if (_nextOrderTime > now && _lastMinionsTargetRef.get() != null) {
            final Player old_target = _lastMinionsTargetRef.get();
            if (old_target != null && !old_target.isAlikeDead()) {
                ml.getAliveMinions().stream().filter(m -> m.getAI().getAttackTarget() != old_target).forEach(m -> m.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, old_target, 10000000));
                return;
            }
        }
        _nextOrderTime = now + 30000L;
        final List<Player> pl = World.getAroundPlayers(actor);
        if (pl.isEmpty()) {
            _lastMinionsTargetRef = HardReferences.emptyRef();
            return;
        }
        final List<Player> alive = pl.stream().filter(p -> !p.isAlikeDead()).collect(Collectors.toList());
        if (alive.isEmpty()) {
            _lastMinionsTargetRef = HardReferences.emptyRef();
            return;
        }
        final Player target = alive.get(Rnd.get(alive.size()));
        _lastMinionsTargetRef = target.getRef();
        Functions.npcSayCustomMessage(actor, "Kama56Boss.attack", target.getName());
        ml.getAliveMinions().forEach(i -> {
            i.getAggroList().clear();
            i.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, target, 10000000);
        });
    }

    @Override
    protected void thinkAttack() {
        final NpcInstance actor = getActor();
        if (actor == null) {
            return;
        }
        sendOrderToMinions(actor);
        super.thinkAttack();
    }
}
