package fr.neiyko.chatmanager;

import fr.neiyko.chatmanager.managers.MCommands;
import fr.neiyko.chatmanager.managers.MEvents;
import fr.neiyko.chatmanager.managers.MFiles;
import fr.neiyko.chatmanager.managers.MLoad;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public final class ChatManager extends JavaPlugin {

    private static ChatManager instance;

    private static HashMap<Player, Integer> mute = new HashMap<>();
    public HashMap<String, Long> chatCooldown = new HashMap<>();
    private MLoad managerLoad;
    private MEvents eventsManager;
    private MCommands commandsManager;
    private MFiles fileManager;
    private boolean error;

    public static ChatManager getInstance() {
        return instance;
    }

    public File configFile = new File(getDataFolder().getPath() + "/config.yml");
    public FileConfiguration fileConfigConfiguration;

    public File messagesFile = new File(getDataFolder().getPath() + "/messages.yml");
    public FileConfiguration fileConfigMessages;

    public File dataFile = new File(getDataFolder().getPath() + "/data.yml");
    public FileConfiguration fileConfigData;

    public File wordsFile = new File(getDataFolder().getPath() + "/blockedwords.yml");
    public FileConfiguration fileConfigBlockedWords;

    @Override
    public void onEnable() {
        instance = this;
        managerLoad = new MLoad();
        eventsManager = new MEvents();
        fileManager = new MFiles();
        commandsManager = new MCommands();
        managerLoad.pluginLoad();
    }

    @Override
    public void onDisable() {
        managerLoad.pluginDisable();
    }

    public MLoad getManagerLoad() {
        return managerLoad;
    }

    public MEvents getEventsManager() {
        return eventsManager;
    }

    public void logConsole(Level level, String msg) {
        getLogger().log(level, msg);
    }

    public MCommands getCommandsManager() {
        return commandsManager;
    }

    public MFiles getFileManager() {
        return fileManager;
    }

    public String getPermission(String perm) {
        return fileConfigConfiguration.getString(perm);
    }

    public String getMessage(String msg) {
        return fileConfigMessages.getString(msg);
    }

    public boolean hasPermission(Player p, String action) {
        if (p.hasPermission(getPermission(action))) {
            return true;
        }
        return false;
    }

    public void setError(boolean status) {
        error = status;
    }

    public boolean getError() {
        return error;
    }

    public boolean isPlayer(String player) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getName().equalsIgnoreCase(player)) {
                return true;
            }
        }
        return false;
    }

    public HashMap<Player, Integer> getMute() {
        return mute;
    }

    public List<String> getMessageStringList(String msg) {
        return fileConfigMessages.getStringList(msg);
    }

    public List<String> getBlockedWordsList(String msg) {
        return fileConfigBlockedWords.getStringList(msg);
    }
}
