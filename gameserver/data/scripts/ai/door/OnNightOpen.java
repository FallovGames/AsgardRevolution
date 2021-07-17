package ai.door;

import l2ar.commons.lang.reference.HardReference;
import l2ar.gameserver.GameTimeController;
import l2ar.gameserver.ai.DoorAI;
import l2ar.gameserver.listener.game.OnDayNightChangeListener;
import l2ar.gameserver.manager.ReflectionManager;
import l2ar.gameserver.model.instances.DoorInstance;

public class OnNightOpen extends DoorAI {
    public OnNightOpen(final DoorInstance actor) {
        super(actor);
        GameTimeController.getInstance().addListener(new NightDoorOpenController(actor));
    }

    @Override
    public boolean isGlobalAI() {
        return true;
    }

    private static class NightDoorOpenController implements OnDayNightChangeListener {
        private final HardReference<? extends DoorInstance> _actRef;

        NightDoorOpenController(final DoorInstance actor) {
            _actRef = actor.getRef();
        }

        @Override
        public void onDay() {
        }

        @Override
        public void onNight() {
            final DoorInstance door = _actRef.get();
            if (door != null && door.getReflection() == ReflectionManager.DEFAULT) {
                door.openMe();
                LOGGER.info("Zaken door (Location : " + door.getLoc() + ") is opened for 5 min.");
            } else {
                LOGGER.warn("Zaken door is null");
            }
        }
    }
}
