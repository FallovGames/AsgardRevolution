package ai;

import l2ar.commons.util.Rnd;
import l2ar.gameserver.ai.Fighter;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.Player;
import l2ar.gameserver.model.Skill;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.model.quest.QuestEventType;
import l2ar.gameserver.model.quest.QuestState;
import l2ar.gameserver.tables.SkillTable;

import java.util.List;

public class Quest421FairyTree extends Fighter {
    private static final Skill s_quest_vicious_poison = SkillTable.getInstance().getInfo(4243, 1);

    public Quest421FairyTree(final NpcInstance actor) {
        super(actor);
        actor.startImmobilized();
    }

    @Override
    protected void onEvtAttacked(final Creature attacker, final int damage) {
        final NpcInstance actor = getActor();
        if (attacker != null) {
            if (attacker.isPlayer() && Rnd.chance(29)) {
                s_quest_vicious_poison.getEffects(actor, attacker, false, false);
            } else if (attacker.isPet()) {
                final Player player = attacker.getPlayer();
                if (player != null) {
                    final List<QuestState> quests = player.getQuestsForEvent(actor, QuestEventType.ATTACKED_WITH_QUEST);
                    if (quests != null) {
                        quests.forEach(qs -> qs.getQuest().notifyAttack(actor, qs));
                    }
                }
            }
        }
    }

    @Override
    protected boolean randomWalk() {
        return false;
    }

}
