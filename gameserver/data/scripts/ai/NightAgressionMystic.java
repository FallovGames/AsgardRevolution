package ai;

import l2ar.gameserver.GameTimeController;
import l2ar.gameserver.ai.Mystic;
import l2ar.gameserver.listener.game.OnDayNightChangeListener;
import l2ar.gameserver.model.instances.NpcInstance;

public class NightAgressionMystic extends Mystic {
    public NightAgressionMystic(final NpcInstance actor) {
        super(actor);
        GameTimeController.getInstance().addListener(new NightAgressionDayNightListener());
    }

    private class NightAgressionDayNightListener implements OnDayNightChangeListener {
        private NightAgressionDayNightListener() {
            if (GameTimeController.getInstance().isNowNight()) {
                onNight();
            } else {
                onDay();
            }
        }

        @Override
        public void onDay() {
            getActor().setAggroRange(0);
        }

        @Override
        public void onNight() {
            getActor().setAggroRange(-1);
        }
    }
}
