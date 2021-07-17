package quests;

import java.util.HashMap;
import l2ar.commons.threading.RunnableImpl;
import l2ar.gameserver.ThreadPoolManager;
import l2ar.gameserver.cache.HtmCache;
import l2ar.gameserver.model.Player;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.model.quest.Quest;
import l2ar.gameserver.model.quest.QuestState;

public class _999_T1Tutorial extends Quest
{
	private static final String qnTutorial = "_255_Tutorial";
	private static final int RECOMMENDATION_01 = 1067;
	private static final int RECOMMENDATION_02 = 1068;
	private static final int LEAF_OF_MOTHERTREE = 1069;
	private static final int BLOOD_OF_JUNDIN = 1070;
	private static final int LICENSE_OF_MINER = 1498;
	private static final int VOUCHER_OF_FLAME = 1496;
	private static final int SOULSHOT_NOVICE = 5789;
	private static final int SPIRITSHOT_NOVICE = 5790;
	private static final int BLUE_GEM = 6353;
	private static final int TOKEN = 8542;
	private static final int SCROLL = 8594;
	private static final int DIPLOMA = 9881;

	private static class Event
	{
		public String htm;
		public int radarX;
		public int radarY;
		public int radarZ;
		public int item;
		public int classId1;
		public int gift1;
		public int count1;
		public int classId2;
		public int gift2;
		public int count2;

		public Event(String htm, int radarX, int radarY, int radarZ, int item, int classId1, int gift1, int count1, int classId2, int gift2, int count2)
		{
			this.htm = htm;
			this.radarX = radarX;
			this.radarY = radarY;
			this.radarZ = radarZ;
			this.item = item;
			this.classId1 = classId1;
			this.gift1 = gift1;
			this.count1 = count1;
			this.classId2 = classId2;
			this.gift2 = gift2;
			this.count2 = count2;
		}
	}

	private static class Talk
	{
		public int raceId;
		public String[] htmlfiles;
		public int npcTyp;
		public int item;

		public Talk(int raceId, String[] htmlfiles, int npcTyp, int item)
		{
			this.raceId = raceId;
			this.htmlfiles = htmlfiles;
			this.npcTyp = npcTyp;
			this.item = item;
		}
	}

	private static final HashMap<String, Event> events = new HashMap<>();
	static
	{
		events.put("32133_02", new Event("32133-03.htm", -119692, 44504, 380, DIPLOMA, 0x7b, SOULSHOT_NOVICE, 200, 0x7c, SOULSHOT_NOVICE, 200));
		events.put("30008_02", new Event("30008-03.htm", 0, 0, 0, RECOMMENDATION_01, 0x00, SOULSHOT_NOVICE, 200, 0x00, 0, 0));
		events.put("30008_04", new Event("30008-04.htm", -84058, 243239, -3730, 0, 0x00, 0, 0, 0, 0, 0));
		events.put("30017_02", new Event("30017-03.htm", 0, 0, 0, RECOMMENDATION_02, 0x0a, SPIRITSHOT_NOVICE, 100, 0x00, 0, 0));
		events.put("30017_04", new Event("30017-04.htm", -84058, 243239, -3730, 0, 0x0a, 0, 0, 0x00, 0, 0));
		events.put("30370_02", new Event("30370-03.htm", 0, 0, 0, LEAF_OF_MOTHERTREE, 0x19, SPIRITSHOT_NOVICE, 100, 0x12, SOULSHOT_NOVICE, 200));
		events.put("30370_04", new Event("30370-04.htm", 45491, 48359, -3086, 0, 0x19, 0, 0, 0x12, 0, 0));
		events.put("30129_02", new Event("30129-03.htm", 0, 0, 0, BLOOD_OF_JUNDIN, 0x26, SPIRITSHOT_NOVICE, 100, 0x1f, SOULSHOT_NOVICE, 200));
		events.put("30129_04", new Event("30129-04.htm", 12116, 16666, -4610, 0, 0x26, 0, 0, 0x1f, 0, 0));
		events.put("30528_02", new Event("30528-03.htm", 0, 0, 0, LICENSE_OF_MINER, 0x35, SOULSHOT_NOVICE, 200, 0x00, 0, 0));
		events.put("30528_04", new Event("30528-04.htm", 115642, -178046, -941, 0, 0x35, 0, 0, 0x00, 0, 0));
		events.put("30573_02", new Event("30573-03.htm", 0, 0, 0, VOUCHER_OF_FLAME, 0x31, SPIRITSHOT_NOVICE, 100, 0x2c, SOULSHOT_NOVICE, 200));
		events.put("30573_04", new Event("30573-04.htm", -45067, -113549, -235, 0, 0x31, 0, 0, 0x2c, 0, 0));
	}

	private static final HashMap<Integer, Talk> talks = new HashMap<>();
	static
	{
        talks.put(30017, new Talk(0, new String[]{"30017-01.htm", "30017-02.htm", "30017-04.htm"}, 0, 0));
        talks.put(30008, new Talk(0, new String[]{"30008-01.htm", "30008-02.htm", "30008-04.htm"}, 0, 0));
        talks.put(30370, new Talk(1, new String[]{"30370-01.htm", "30370-02.htm", "30370-04.htm"}, 0, 0));
        talks.put(30129, new Talk(2, new String[]{"30129-01.htm", "30129-02.htm", "30129-04.htm"}, 0, 0));
        talks.put(30573, new Talk(3, new String[]{"30573-01.htm", "30573-02.htm", "30573-04.htm"}, 0, 0));
        talks.put(30528, new Talk(4, new String[]{"30528-01.htm", "30528-02.htm", "30528-04.htm"}, 0, 0));
        talks.put(30018, new Talk(0, new String[]{"30131-01.htm", "", "30019-03a.htm", "30019-04.htm"}, 1, RECOMMENDATION_02));
        talks.put(30019, new Talk(0, new String[]{"30131-01.htm", "", "30019-03a.htm", "30019-04.htm"}, 1, RECOMMENDATION_02));
        talks.put(30020, new Talk(0, new String[]{"30131-01.htm", "", "30019-03a.htm", "30019-04.htm"}, 1, RECOMMENDATION_02));
        talks.put(30021, new Talk(0, new String[]{"30131-01.htm", "", "30019-03a.htm", "30019-04.htm"}, 1, RECOMMENDATION_02));
        talks.put(30009, new Talk(0, new String[]{"30530-01.htm", "30009-03.htm", "", "30009-04.htm"}, 1, RECOMMENDATION_01));
        talks.put(30011, new Talk(0, new String[]{"30530-01.htm", "30009-03.htm", "", "30009-04.htm"}, 1, RECOMMENDATION_01));
        talks.put(30400, new Talk(1, new String[]{"30131-01.htm", "30400-03.htm", "30400-03a.htm", "30400-04.htm"}, 1, LEAF_OF_MOTHERTREE));
        talks.put(30401, new Talk(1, new String[]{"30131-01.htm", "30400-03.htm", "30400-03a.htm", "30400-04.htm"}, 1, LEAF_OF_MOTHERTREE));
        talks.put(30402, new Talk(1, new String[]{"30131-01.htm", "30400-03.htm", "30400-03a.htm", "30400-04.htm"}, 1, LEAF_OF_MOTHERTREE));
        talks.put(30403, new Talk(1, new String[]{"30131-01.htm", "30400-03.htm", "30400-03a.htm", "30400-04.htm"}, 1, LEAF_OF_MOTHERTREE));
        talks.put(30131, new Talk(2, new String[]{"30131-01.htm", "30131-03.htm", "30131-03a.htm", "30131-04.htm"}, 1, BLOOD_OF_JUNDIN));
        talks.put(30404, new Talk(2, new String[]{"30131-01.htm", "30131-03.htm", "30131-03a.htm", "30131-04.htm"}, 1, BLOOD_OF_JUNDIN));
        talks.put(30574, new Talk(3, new String[]{"30575-01.htm", "30575-03.htm", "30575-03a.htm", "30575-04.htm"}, 1, VOUCHER_OF_FLAME));
        talks.put(30575, new Talk(3, new String[]{"30575-01.htm", "30575-03.htm", "30575-03a.htm", "30575-04.htm"}, 1, VOUCHER_OF_FLAME));
        talks.put(30530, new Talk(4, new String[]{"30530-01.htm", "30530-03.htm", "", "30530-04.htm"}, 1, LICENSE_OF_MINER));
		talks.put(30598, new Talk(0, null, 0, 0));
		talks.put(30599, new Talk(0, null, 0, 0));
		talks.put(30600, new Talk(0, null, 0, 0));
		talks.put(30601, new Talk(0, null, 0, 0));
		talks.put(30602, new Talk(0, null, 0, 0));
	}

	public _999_T1Tutorial()
	{
		super(false);
		addStartNpc(30009, 30019, 30131, 30575, 30530, 30400, 30401, 30402, 30403, 30404);
		addTalkId(30008, 30009, 30017, 30019, 30129, 30131, 30573, 30575, 30370, 30528, 30530, 30400, 30401, 30402, 30403, 30404);
		addFirstTalkId(30009, 30600, 30601, 30602, 30598, 30599);
        addKillId(18342, 20001);
	}

    @Override
    public String onEvent(final String event, final QuestState st, final NpcInstance npc) {
        final QuestState qs = st.getPlayer().getQuestState(_255_Tutorial.class);
		if(qs == null)
			return null;
		String htmltext;
		int Ex = qs.getInt("Ex");
		int classId = st.getPlayer().getClassId().getId();
		if(event.equalsIgnoreCase("TimerEx_NewbieHelper"))
		{
			if(Ex == 0)
			{
				if(st.getPlayer().getClassId().isMage())
					st.playTutorialVoice("tutorial_voice_009b");
				else
					st.playTutorialVoice("tutorial_voice_009a");
				qs.set("Ex", "1");
			}
			else if(Ex == 3)
			{
				st.playTutorialVoice("tutorial_voice_010a");
				qs.set("Ex", "4");
			}
			return null;
		}
		else if(event.equalsIgnoreCase("TimerEx_GrandMaster"))
		{
			if(Ex >= 4)
			{
				st.showQuestionMark(7);
				st.playSound(SOUND_TUTORIAL);
				st.playTutorialVoice("tutorial_voice_025");
			}
			return null;
		}
		else if(event.equalsIgnoreCase("isle"))
		{
			st.addRadar(-119692, 44504, 380);
			st.getPlayer().teleToLocation(-120050, 44500, 360, 50);
			htmltext = "<html><body><br>Go to the <font color=\"LEVEL\">Isle of Souls</font> and meet the <font color=\"LEVEL\">Newbie Guide</font> there to learn a number of important tips. He will also give you an item to assist your development. <br>Follow the direction arrow above your head and it will lead you to the Newbie Guide. Good luck!</body></html>";
		}
		else
		{
			Event e = events.get(event);
			if(e.radarX != 0)
				st.addRadar(e.radarX, e.radarY, e.radarZ);
			htmltext = e.htm;
			if(st.getQuestItemsCount(e.item) > 0)
			{
				st.addExpAndSp(0, 50);
				st.startQuestTimer("TimerEx_GrandMaster", 60000);
				st.takeItems(e.item, 1);
				if(Ex <= 3)
					qs.set("Ex", "4");
				if(classId == e.classId1)
				{
					st.giveItems(e.gift1, e.count1);
					if(e.gift1 == SPIRITSHOT_NOVICE)
						st.playTutorialVoice("tutorial_voice_027");
					else
						st.playTutorialVoice("tutorial_voice_026");
				}
				else if(classId == e.classId2)
					if(e.gift2 != 0)
					{
						st.giveItems(e.gift2, e.count2);
						st.playTutorialVoice("tutorial_voice_026");
					}
			}
		}
		return htmltext;
    }

	@Override
	public String onFirstTalk(NpcInstance npc, Player player)
	{
		int npcId = npc.getNpcId();
		if(npcId == 30600 || npcId == 30601 || npcId == 30602 || npcId == 30598 || npcId == 30599)
		{
			QuestState qs = player.getQuestState(qnTutorial);
			if(qs == null || qs.isCompleted())
			{
				return HtmCache.getInstance().getNotNull("default/"+ npc.getNpcId() + ".htm", player);
			}
			if(player.getClassId().isMage())
			{
				qs.playTutorialVoice("tutorial_voice_027");
				qs.giveItems(SPIRITSHOT_NOVICE, 100);
			}
			else
			{
				qs.playTutorialVoice("tutorial_voice_026");
				qs.giveItems(SOULSHOT_NOVICE, 200);
			}
			qs.giveItems(TOKEN, 12);
			qs.giveItems(SCROLL, 2);
			qs.exitCurrentQuest(false);
		}
		return HtmCache.getInstance().getNotNull("default/"+ npc.getNpcId() + ".htm", player);
	}

    @Override
    public String onTalk(NpcInstance npc, QuestState st) {
        String htmltext = "noquest";
        final QuestState qs = st.getPlayer().getQuestState(qnTutorial);
        if (qs == null) {
            return htmltext;
        }
        if (st == null) {
            newQuestState(st.getPlayer(), 1);
            st = st.getPlayer().getQuestState(getClass());
        }
		int npcId = npc.getNpcId();
		int Ex = qs.getInt("Ex");
		int step = st.getInt("step");
		int level = st.getPlayer().getLevel();
		boolean isMage = st.getPlayer().getClassId().isMage();

		Talk t = talks.get(npcId);
		if(t == null)
			return null;

		if(level >= 10 && t.npcTyp == 1)
			htmltext = "30575-05.htm";
		else if(!(npcId == 30600 || npcId == 30601 || npcId == 30602 || npcId == 30598 || npcId == 30599 || npcId == 32135))
		{
			if(st.getPlayer().getRace().ordinal() == t.raceId)
			{
				htmltext = t.htmlfiles[0];
				if(t.npcTyp == 1)
				{
					if(step == 0 && Ex < 0)
					{
						qs.set("Ex", "0");
						qs.startQuestTimer("TimerEx_NewbieHelper", 30000);
						if(isMage)
						{
							st.set("step", "1");
							st.setState(STARTED);
						}
						else
						{
							htmltext = "30530-01.htm";
							st.set("step", "1");
							st.setState(STARTED);
						}
					}
					else if(step == 1 && st.getQuestItemsCount(t.item) == 0 && Ex <= 2)
					{
						if(st.getQuestItemsCount(BLUE_GEM) > 0)
						{
							st.takeItems(BLUE_GEM, st.getQuestItemsCount(BLUE_GEM));
							st.giveItems(t.item, 1);
							st.set("step", "2");
							qs.set("Ex", "3");
							qs.startQuestTimer("TimerEx_NewbieHelper", 30000);
							qs.set("ucMemo", "3");
							if(isMage)
							{
								st.playTutorialVoice("tutorial_voice_027");
								st.giveItems(SPIRITSHOT_NOVICE, 100);
								htmltext = t.htmlfiles[2];
								if(htmltext.isEmpty())
									htmltext = "<html><body>I am sorry. I only help warriors. Please go to another Newbie Helper who may assist you.</body></html>";
							}
							else
							{
								st.playTutorialVoice("tutorial_voice_026");
								st.giveItems(SOULSHOT_NOVICE, 200);
								htmltext = t.htmlfiles[1];
								if(htmltext.isEmpty())
									htmltext = "<html><body>I am sorry. I only help mystics. Please go to another Newbie Helper who may assist you.</body></html>";
							}
						}
						else if(isMage)
						{
							htmltext = "30131-02.htm";
							if(st.getPlayer().getRace().ordinal() == 3)
								htmltext = "30575-02.htm";
						}
						else
							htmltext = "30530-02.htm";
					}
					else if(step == 2)
						htmltext = t.htmlfiles[3];
				}
				else if(t.npcTyp == 0)
					if(step == 1)
						htmltext = t.htmlfiles[0];
					else if(step == 2)
						htmltext = t.htmlfiles[1];
					else if(step == 3)
						htmltext = t.htmlfiles[2];
			}
		}
		else
			htmltext = "<html><body>You are too experienced now.</body></html>";

		return htmltext;
	}

	@Override
	public String onKill(NpcInstance npc, QuestState st)
	{
		if (st == null)
			return null;
		QuestState qs = st.getPlayer().getQuestState(qnTutorial);
		if(qs == null)
			return null;
		int Ex = qs.getInt("Ex");
		if(Ex <= 1)
		{
			st.playTutorialVoice("tutorial_voice_011");
			st.showQuestionMark(3);
			qs.set("Ex", "2");
		}
        if (Ex <= 2 && st.getQuestItemsCount(BLUE_GEM) < 1L) {
            ThreadPoolManager.getInstance().schedule(new DropGem(npc, st), 3000L);
        }
		return null;
	}

    public static class DropGem extends RunnableImpl {
        private final NpcInstance _npc;
        private final QuestState _st;

        public DropGem(final NpcInstance npc, final QuestState st) {
            _npc = npc;
            _st = st;
        }

        @Override
        public void runImpl() {
            if (_st != null && _npc != null) {
				QuestState _gt = _st.getPlayer().getQuestState("_255_Tutorial");;
				if(_st.getQuestItemsCount(BLUE_GEM) < 1L)
				{
					_gt.playTutorialVoice("tutorial_voice_013");
					_gt.playSound("ItemSound.quest_tutorial");
					_gt.set("Gemstone", "1");
					_gt.showQuestionMark(5);
					_st.giveItems(BLUE_GEM, 1L);
					_st.playSound("ItemSound.quest_tutorial");
				}
            }
        }
    }
}