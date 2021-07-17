package npc.model.residences.clanhall;

import npc.model.residences.ResidenceManager;
import l2ar.gameserver.model.entity.residence.ClanHall;
import l2ar.gameserver.model.entity.residence.Residence;
import l2ar.gameserver.network.lineage2.serverpackets.AgitDecoInfo;
import l2ar.gameserver.network.lineage2.serverpackets.L2GameServerPacket;
import l2ar.gameserver.templates.npc.NpcTemplate;

public class ManagerInstance extends ResidenceManager {
    public ManagerInstance(final int objectId, final NpcTemplate template) {
        super(objectId, template);
    }

    @Override
    protected Residence getResidence() {
        return getClanHall();
    }

    @Override
    public L2GameServerPacket decoPacket() {
        final ClanHall clanHall = getClanHall();
        if (clanHall != null) {
            return new AgitDecoInfo(clanHall);
        }
        return null;
    }

    @Override
    protected int getPrivUseFunctions() {
        return 2048;
    }

    @Override
    protected int getPrivSetFunctions() {
        return 16384;
    }

    @Override
    protected int getPrivDismiss() {
        return 8192;
    }

    @Override
    protected int getPrivDoors() {
        return 1024;
    }
}
