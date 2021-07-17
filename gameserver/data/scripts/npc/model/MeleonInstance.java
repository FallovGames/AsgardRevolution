package npc.model;

import l2ar.commons.lang.reference.HardReference;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.Player;
import l2ar.gameserver.model.Skill;
import l2ar.gameserver.model.instances.SpecialMonsterInstance;
import l2ar.gameserver.templates.npc.NpcTemplate;

public class MeleonInstance extends SpecialMonsterInstance {
    public static final int Young_Watermelon = 13271;
    public static final int Rain_Watermelon = 13273;
    public static final int Defective_Watermelon = 13272;
    public static final int Young_Honey_Watermelon = 13275;
    public static final int Rain_Honey_Watermelon = 13277;
    public static final int Defective_Honey_Watermelon = 13276;
    public static final int Large_Rain_Watermelon = 13274;
    public static final int Large_Rain_Honey_Watermelon = 13278;
    private HardReference<Player> _spawnerRef;

    public MeleonInstance(final int objectId, final NpcTemplate template) {
        super(objectId, template);
    }

    public Player getSpawner() {
        return _spawnerRef.get();
    }

    public void setSpawner(final Player spawner) {
        _spawnerRef = spawner.getRef();
    }

    @Override
    public void reduceCurrentHp(double i, final Creature attacker, final Skill skill, final boolean awake, final boolean standUp, final boolean directHp, final boolean canReflect, final boolean transferDamage, final boolean isDot, final boolean sendMessage) {
        if (attacker.getActiveWeaponInstance() == null) {
            return;
        }
        final int weaponId = attacker.getActiveWeaponInstance().getItemId();
        if (getNpcId() == Defective_Honey_Watermelon || getNpcId() == Rain_Honey_Watermelon || getNpcId() == Large_Rain_Honey_Watermelon) {
            if (weaponId != 4202 && weaponId != 5133 && weaponId != 5817 && weaponId != 7058 && weaponId != 8350) {
                return;
            }
            i = 1.0;
        } else {
            if (getNpcId() != Rain_Watermelon && getNpcId() != Defective_Watermelon && getNpcId() != Large_Rain_Watermelon) {
                return;
            }
            i = 5.0;
        }
        super.reduceCurrentHp(i, attacker, skill, awake, standUp, directHp, canReflect, transferDamage, isDot, sendMessage);
    }

    @Override
    public long getRegenTick() {
        return 0L;
    }

    @Override
    public boolean canChampion() {
        return false;
    }
}
