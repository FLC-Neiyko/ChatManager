package fr.neiyko.chatmanager.managers;

import fr.neiyko.chatmanager.ChatManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class MLoad {

    private ChatManager chatManager = ChatManager.getInstance();

    public void pluginLoad() {
        long start_timer = System.currentTimeMillis();

        chatManager.logConsole(Level.INFO, "=== Beginning of loading ===");
        chatManager.logConsole(Level.INFO, "Loading the plugin...");
        chatManager.logConsole(Level.INFO, "----");
        
        chatManager.getFileManager().initFile();
        chatManager.getCommandsManager().initCommands();
        chatManager.getEventsManager().initEvents();
        
        dataLoad();

        if (chatManager.getError()) errorMSG();

        long end_timer = System.currentTimeMillis();
        chatManager.logConsole(Level.INFO, "Loading completed in " + (end_timer - start_timer) + " ms");
        pluginEnable();
    }

    public void dataLoad(){
        //Manages how to retrieve data.
        for (OfflinePlayer p : Bukkit.getOfflinePlayers()){
            if (chatManager.fileConfigData.contains(p.getName())){
                chatManager.getMute().put(p.getPlayer(), chatManager.fileConfigData.getInt(p.getName()));
            }
        }
    }


    public void dataUnload(){
        //Manages how to save data.
        for (Player p : chatManager.getMute().keySet()) {
            chatManager.fileConfigData.set(p.getName(), chatManager.getMute().get(p));
        }
        chatManager.getFileManager().saveResource(chatManager.dataFile);
        chatManager.getMute().clear();
    }

    public void pluginEnable() {
        chatManager.logConsole(Level.INFO, "----");
        chatManager.logConsole(Level.INFO, "Plugin ChatManager");
        chatManager.logConsole(Level.INFO, "By Neiyko");
        chatManager.logConsole(Level.INFO, "Status: Enabled");
        chatManager.logConsole(Level.INFO, "----");
    }

    public void pluginDisable() {
        dataUnload();
        chatManager.logConsole(Level.INFO, "----");
        chatManager.logConsole(Level.INFO, "Plugin ChatManager");
        chatManager.logConsole(Level.INFO, "By Neiyko");
        chatManager.logConsole(Level.INFO, "Status: Disabled");
        chatManager.logConsole(Level.INFO, "----");
    }

    public void errorMSG() {
        chatManager.logConsole(Level.INFO, "----");
        chatManager.logConsole(Level.INFO, "Plugin ChatManager");
        chatManager.logConsole(Level.INFO, "By Neiyko");
        chatManager.logConsole(Level.INFO, "Status: Error");
    }
}
