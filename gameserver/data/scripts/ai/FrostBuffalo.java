package ai;

import l2ar.commons.util.Rnd;
import l2ar.gameserver.ai.CtrlEvent;
import l2ar.gameserver.ai.Fighter;
import l2ar.gameserver.data.xml.holder.NpcTemplateHolder;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.SimpleSpawner;
import l2ar.gameserver.model.Skill;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.utils.Location;

public class FrostBuffalo extends Fighter {
    private static final int MOBS = 22093;
    private static final int MOBS_COUNT = 4;
    private boolean _mobsNotSpawned;

    public FrostBuffalo(final NpcInstance actor) {
        super(actor);
        _mobsNotSpawned = true;
    }

    @Override
    protected void onEvtSeeSpell(final Skill skill, final Creature caster) {
        final NpcInstance actor = getActor();
        if (skill.isMagic()) {
            return;
        }
        if (_mobsNotSpawned) {
            _mobsNotSpawned = false;
            for (int i = 0; i < 4; ++i) {
                try {
                    final SimpleSpawner sp = new SimpleSpawner(NpcTemplateHolder.getInstance().getTemplate(22093));
                    sp.setLoc(Location.findPointToStay(actor, 100, 120));
                    final NpcInstance npc = sp.doSpawn(true);
                    if (caster.isPet() || caster.isSummon()) {
                        npc.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, caster, Rnd.get(2, 100));
                    }
                    npc.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, caster.getPlayer(), Rnd.get(1, 100));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onEvtDead(final Creature killer) {
        _mobsNotSpawned = true;
        super.onEvtDead(killer);
    }

    @Override
    protected boolean randomWalk() {
        return _mobsNotSpawned;
    }
}
