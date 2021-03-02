package fr.neiyko.chatmanager.events;

import fr.neiyko.chatmanager.ChatManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class EAntiSpam implements Listener {

    private ChatManager chatManager = ChatManager.getInstance();

    @EventHandler
    public void AntiSpam(AsyncPlayerChatEvent e) {
        if (e.isCancelled()) return;

        PlayerInteractEvent event;
        Player p = e.getPlayer();

        if (chatManager.getConfig().getBoolean("antispeam.enable")) {

            if (p.hasPermission(chatManager.getPermission("antispam.bypass-permission"))) {
                return;
            }
            long time = System.currentTimeMillis();
            Long lastUse = chatManager.chatCooldown.get(p.getName());
            Long config = chatManager.getConfig().getLong("antispam.delay");
            if (lastUse == null) {
                lastUse = 0L;
            }
            if (lastUse + config > time) {
                e.setCancelled(true);
                p.sendMessage(chatManager.getMessage("antispam.spam").replace("&", "§"));
            }
            chatManager.chatCooldown.remove(p.getName());
            chatManager.chatCooldown.put(p.getName(), time);
        }
    }
}
