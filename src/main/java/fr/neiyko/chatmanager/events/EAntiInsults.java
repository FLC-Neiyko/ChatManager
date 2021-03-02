package fr.neiyko.chatmanager.events;

import fr.neiyko.chatmanager.ChatManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

public class EAntiInsults implements Listener {

    private ChatManager chatManager = ChatManager.getInstance();

    List<String> blackList = chatManager.getConfig().getStringList("antiinsults.blacklist-words");
    List<String> test = chatManager.getBlockedWordsList("blacklist-words");

    @EventHandler
    public void antiInsult(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();

        if (chatManager.getConfig().getBoolean("antiinsults.enable")) {
                for (String Blacklist : test) {
                    if (e.getMessage().toLowerCase().contains(Blacklist.toLowerCase())) {
                        e.setCancelled(true);
                        p.sendMessage(chatManager.getMessage("antiinsults.on-insult").replace("&" , "§"));
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            if (chatManager.hasPermission(player, "antiinsults.staff-receive-permission") || player.isOp()) {
                                for (String message : chatManager.getStringList("antiinsults.staff-message")) {
                                    player.sendMessage(message.replace("%player%", p.getName()).replace("%message%", e.getMessage())
                                            .replace("&", "§"));
                                }
                            }
                        }
                    }
                }
        }
    }
}
