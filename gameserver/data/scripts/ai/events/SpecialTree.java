package ai.events;

import l2ar.commons.math.random.RndSelector;
import l2ar.commons.util.Rnd;
import l2ar.gameserver.ai.DefaultAI;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.network.lineage2.serverpackets.MagicSkillUse;

public class SpecialTree extends DefaultAI {
    private static final RndSelector<Integer> SOUNDS = new RndSelector<>(5);

    static {
        SOUNDS.add(2140, 20);
        SOUNDS.add(2142, 20);
        SOUNDS.add(2145, 20);
        SOUNDS.add(2147, 20);
        SOUNDS.add(2149, 20);
    }

    private boolean _buffsEnabled;
    private int _timer;

    public SpecialTree(final NpcInstance actor) {
        super(actor);
        _buffsEnabled = false;
        _timer = 0;
    }

    @Override
    protected boolean thinkActive() {
        if (_buffsEnabled) {
            _timer++;
            if (_timer >= 180) {
                _timer = 0;
                final NpcInstance actor = getActor();
                if (actor == null) {
                    return false;
                }
                addTaskBuff(actor, getSkillInfo(2139, 1));
                if (Rnd.chance(33)) {
                    actor.broadcastPacketToOthers(new MagicSkillUse(actor, actor, SOUNDS.select(), 1, 500, 0L));
                }
            }
        }
        return super.thinkActive();
    }

    @Override
    protected void onEvtSpawn() {
        super.onEvtSpawn();
        _buffsEnabled = !getActor().isInZonePeace();
        _timer = 0;
    }
}
