package fr.neiyko.chatmanager.managers;

import fr.neiyko.chatmanager.ChatManager;
import fr.neiyko.chatmanager.commands.LockChat;
import fr.neiyko.chatmanager.commands.Mute;
import fr.neiyko.chatmanager.commands.StaffChat;
import fr.neiyko.chatmanager.events.EAntiInsults;
import fr.neiyko.chatmanager.events.EAntiLink;
import fr.neiyko.chatmanager.events.EAntiSpam;
import org.bukkit.plugin.PluginManager;

public class MEvents {

    private ChatManager chatManager = ChatManager.getInstance();

    public void initEvents() {

        PluginManager pm = chatManager.getServer().getPluginManager();
        pm.registerEvents(new LockChat(), chatManager);
        pm.registerEvents(new StaffChat(), chatManager);
        pm.registerEvents(new Mute(), chatManager);
        pm.registerEvents(new EAntiSpam(), chatManager);
        pm.registerEvents(new EAntiInsults(), chatManager);
        pm.registerEvents(new EAntiLink(), chatManager);
    }
}
