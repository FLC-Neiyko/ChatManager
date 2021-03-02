package fr.neiyko.chatmanager.commands;

import fr.neiyko.chatmanager.ChatManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class LockChat implements CommandExecutor, Listener {

    private ChatManager chatManager = ChatManager.getInstance();
    private static boolean chatlock = false;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (chatManager.getConfig().getBoolean("lockchat.enable") && cmd.getName().equalsIgnoreCase("lockchat")) {
                if (args.length == 0) {
                    if (p.hasPermission(chatManager.getPermission("lockchat.permission")) || p.isOp()) {
                        if (!chatlock) {
                            Bukkit.broadcastMessage(chatManager.getMessage("lockchat.lock").replace("%prefix%", chatManager.getMessage("prefix"))
                            .replace("&", "§"));
                            chatlock = true;
                        } else {
                            Bukkit.broadcastMessage(chatManager.getMessage("lockchat.unlock").replace("%prefix%", chatManager.getMessage("prefix"))
                            .replace("&", "§"));
                            chatlock = false;
                        }
                    } else {
                        p.sendMessage(chatManager.getMessage("lockchat.no-permission").replace("&", "§"));
                    }
                }
            }
        }

        return false;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();

        if (chatlock && !p.hasPermission(chatManager.getPermission("lockchat.bypass-permission"))) {
            e.setCancelled(true);
            p.sendMessage(chatManager.getMessage("lockchat.text-on-lock").replace("%prefix%", chatManager.getMessage("prefix"))
                    .replace("&", "§"));
        }
    }
}
