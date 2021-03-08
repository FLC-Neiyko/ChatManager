package fr.neiyko.chatmanager.events;

import fr.neiyko.chatmanager.ChatManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

public class EAntiLink implements Listener {

    private ChatManager chatManager = ChatManager.getInstance();

    List<String> linksList = chatManager.getLinksList("blocked-links");

    @EventHandler
    public void antiLink(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String msg = e.getMessage();

        if (e.isCancelled()) return;

        if (chatManager.getConfig().getBoolean("antilink.enable")) {
            if (!p.hasPermission(chatManager.getPermission("antilink.bypass-permission"))) {
                for (String list : linksList) {
                    if (msg.toLowerCase().contains(list.toLowerCase())) {
                        e.setCancelled(true);
                        p.sendMessage(chatManager.getMessage("anti-link.on-link").replace("&", "§")
                        .replace("%message%", msg));
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            if (chatManager.hasPermission(player, "antilink.staff-receive-permission")) {
                                for (String string : chatManager.getMessageStringList("anti-link.staff-message")) {
                                    player.sendMessage(string.replace("%player%", p.getName())
                                            .replace("%message%", msg).replace("&", "§"));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
