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

public class Mute implements CommandExecutor, Listener {

    private ChatManager chatManager = ChatManager.getInstance();
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (chatManager.getConfig().getBoolean("mute.enable") && cmd.getName().equalsIgnoreCase("mute")) {
                if (p.hasPermission(chatManager.getPermission("mute.permission"))) {
                    if (args.length == 0) {
                        p.sendMessage(chatManager.getMessage("mute.no-arguments").replace("&", "§"));
                    }
                    if (chatManager.isPlayer(args[0])) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (!chatManager.getMute().containsKey(target)) {
                            target.sendMessage(chatManager.getMessage("mute.mute").replace("%prefix%", chatManager.getMessage("prefix")).replace("&", "§"));
                            p.sendMessage(chatManager.getMessage("mute.author-mute").replace("%target%", target.getName()).replace("&", "§"));
                            if (chatManager.getConfig().getBoolean("mute.broadcast")) {
                                Bukkit.broadcastMessage(chatManager.getMessage("mute.broadcast").replace("%target%", target.getName())
                                        .replace("%author%", p.getName()).replace("%prefix%", chatManager.getMessage("prefix")).replace("&", "§"));
                            }
                            chatManager.getMute().put(target, -1);
                        } else {
                            target.sendMessage(chatManager.getMessage("mute.unmute").replace("%prefix%", chatManager.getMessage("prefix")).replace("&", "§"));
                            p.sendMessage(chatManager.getMessage("mute.author-unmute").replace("%target%", target.getName()).replace("&", "§"));
                            chatManager.getMute().remove(target);
                        }
                    } else {
                        p.sendMessage(chatManager.getMessage("mute.not-a-player").replace("&", "§"));
                    }
                } else {
                    p.sendMessage(chatManager.getMessage("mute.no-permission").replace("&", "§"));
                }
            }
        } else {
            sender.sendMessage(chatManager.getMessage("not-player-instance").replace("&", "§"));
            return false;
        }
        return false;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();

        if (chatManager.getMute().containsKey(p)) {
            if (!p.hasPermission(chatManager.getPermission("mute.bypass-permission"))) {
                e.setCancelled(true);
                p.sendMessage(chatManager.getMessage("mute.text-on-mute").replace("&", "§"));
            }
        }
    }
}