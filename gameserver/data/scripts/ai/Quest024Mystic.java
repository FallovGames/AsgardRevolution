package ai;

import quests._024_InhabitantsOfTheForestOfTheDead;
import l2ar.gameserver.ai.Mystic;
import l2ar.gameserver.manager.QuestManager;
import l2ar.gameserver.model.Player;
import l2ar.gameserver.model.World;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.model.quest.Quest;
import l2ar.gameserver.model.quest.QuestState;

public class Quest024Mystic extends Mystic {
    public Quest024Mystic(final NpcInstance actor) {
        super(actor);
    }

    @Override
    protected boolean thinkActive() {
        final Quest q = QuestManager.getQuest(_024_InhabitantsOfTheForestOfTheDead.class);
        if (q != null) {
            for (final Player player : World.getAroundPlayers(getActor(), 300, 200)) {
                final QuestState questState = player.getQuestState(_024_InhabitantsOfTheForestOfTheDead.class);
                if (questState != null && questState.getCond() == 3) {
                    q.notifyEvent("see_creature", questState, getActor());
                }
            }
        }
        return super.thinkActive();
    }
}
