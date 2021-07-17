package handler.usercommands;

import l2ar.gameserver.handler.usercommands.IUserCommandHandler;
import l2ar.gameserver.handler.usercommands.UserCommandHandler;
import l2ar.gameserver.listener.script.OnInitScriptListener;

public abstract class ScriptUserCommand implements IUserCommandHandler, OnInitScriptListener {
    @Override
    public void onInit() {
        UserCommandHandler.getInstance().registerUserCommandHandler(this);
    }

}
