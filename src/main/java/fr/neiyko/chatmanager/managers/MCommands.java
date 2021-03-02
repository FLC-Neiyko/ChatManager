package fr.neiyko.chatmanager.managers;

import fr.neiyko.chatmanager.ChatManager;
import fr.neiyko.chatmanager.commands.*;

public class MCommands {

    private ChatManager chatManager = ChatManager.getInstance();

    public void initCommands() {
        chatManager.getCommand("clearchat").setExecutor(new ClearChat());
        chatManager.getCommand("lockchat").setExecutor(new LockChat());
        chatManager.getCommand("localclear").setExecutor(new LocalClear());
        chatManager.getCommand("staffchat").setExecutor(new StaffChat());
        chatManager.getCommand("mute").setExecutor(new Mute());
    }
}
