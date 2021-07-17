package ai.door;

import l2ar.gameserver.ai.DoorAI;
import l2ar.gameserver.data.xml.holder.ResidenceHolder;
import l2ar.gameserver.listener.actor.player.OnAnswerListener;
import l2ar.gameserver.model.Player;
import l2ar.gameserver.model.entity.residence.Residence;
import l2ar.gameserver.model.instances.DoorInstance;
import l2ar.gameserver.network.lineage2.components.SystemMsg;
import l2ar.gameserver.network.lineage2.serverpackets.ConfirmDlg;

public class ResidenceDoor extends DoorAI {
    public ResidenceDoor(final DoorInstance actor) {
        super(actor);
    }

    @Override
    public void onEvtTwiceClick(final Player player) {
        final DoorInstance door = getActor();
        final Residence residence = ResidenceHolder.getInstance().getResidence(door.getTemplate().getAIParams().getInteger("residence_id"));
        if (residence.getOwner() != null && player.getClan() != null && player.getClan() == residence.getOwner() && (player.getClanPrivileges() & 0x8000) == 0x8000) {
            final SystemMsg msg = door.isOpen() ? SystemMsg.WOULD_YOU_LIKE_TO_CLOSE_THE_GATE : SystemMsg.WOULD_YOU_LIKE_TO_OPEN_THE_GATE;
            player.ask(new ConfirmDlg(msg, 0), new OnAnswerListener() {
                @Override
                public void sayYes() {
                    if (door.isOpen()) {
                        door.closeMe(player, true);
                    } else {
                        door.openMe(player, true);
                    }
                }

                @Override
                public void sayNo() {
                }
            });
        }
    }
}
