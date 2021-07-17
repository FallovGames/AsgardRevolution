package handler.admincommands;

import l2ar.gameserver.handler.admincommands.AdminCommandHandler;
import l2ar.gameserver.handler.admincommands.IAdminCommandHandler;
import l2ar.gameserver.listener.script.OnInitScriptListener;

public abstract class ScriptAdminCommand implements IAdminCommandHandler, OnInitScriptListener {
    @Override
    public void onInit() {
        AdminCommandHandler.getInstance().registerAdminCommandHandler(this);
    }

}
