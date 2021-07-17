package ai.custom;

import l2ar.commons.util.Rnd;
import l2ar.gameserver.ai.Mystic;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.entity.Reflection;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.network.lineage2.serverpackets.MagicSkillUse;
import l2ar.gameserver.scripts.Functions;

public class SSQLilith extends Mystic {
    private final String[] chat;
    private long _lastChatTime;
    private long _lastSkillTime;

    public SSQLilith(final NpcInstance actor) {
        super(actor);
        chat = new String[]{"You, such a fool! The victory over this war belongs to Shilen!!!", "How dare you try to contend against me in strength? Ridiculous.", "Anakim! In the name of Great Shilien, I will cut your throat!", "You cannot be the match of Lilith. I'll teach you a lesson!"};
        _lastChatTime = 0L;
        _lastSkillTime = 0L;
        actor.setHasChatWindow(false);
    }

    @Override
    protected boolean thinkActive() {
        if (_lastChatTime < System.currentTimeMillis()) {
            Functions.npcSay(getActor(), chat[Rnd.get(chat.length)]);
            _lastChatTime = System.currentTimeMillis() + 15000L;
        }
        if (_lastSkillTime < System.currentTimeMillis()) {
            final Reflection ref = getActor().getReflection();
            if (ref != null) {
                NpcInstance anakim = null;
                for (final NpcInstance npc : ref.getNpcs()) {
                    if (npc.getNpcId() == 32718) {
                        anakim = npc;
                        break;
                    }
                }
                if (anakim != null) {
                    getActor().broadcastPacket(new MagicSkillUse(getActor(), anakim, 6187, 1, 5000, 10L));
                }
            }
            _lastSkillTime = System.currentTimeMillis() + 6500L;
        }
        return true;
    }

    @Override
    protected boolean randomWalk() {
        return false;
    }

    @Override
    protected void onEvtAttacked(final Creature attacker, final int damage) {
    }

    @Override
    protected void onEvtAggression(final Creature attacker, final int aggro) {
    }
}
