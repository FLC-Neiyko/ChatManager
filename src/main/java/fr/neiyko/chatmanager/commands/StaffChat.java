package fr.neiyko.chatmanager.commands;

import com.google.common.base.Joiner;
import fr.neiyko.chatmanager.ChatManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;

public class StaffChat implements CommandExecutor, Listener {

    private ChatManager chatManager = ChatManager.getInstance();
    private static HashMap<Player, Boolean> staffchat = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("staffchat")) {
                if (args.length == 0) {
                    if (p.hasPermission(chatManager.getPermission("staffchat.permission"))) {
                        if (chatManager.getConfig().getBoolean("staffchat.enable")) {
                            if (!staffchat.containsKey(p)) {
                                p.sendMessage(chatManager.getMessage("staffchat.enable").replace("%prefix%", chatManager.getMessage("staffchat.prefix"))
                                        .replace("&", "§"));
                                staffchat.put(p, true);
                            } else {
                                p.sendMessage(chatManager.getMessage("staffchat.disable").replace("%prefix%", chatManager.getMessage("staffchat.prefix"))
                                        .replace("&", "§"));
                                staffchat.remove(p);
                            }
                        }
                    } else {
                        p.sendMessage(chatManager.getMessage("staffchat.no-permission").replace("%prefix%", chatManager.getMessage("staffchat.prefix"))
                                .replace("&", "§"));
                    }
                }
                if (args.length >= 1) {
                    if (p.hasPermission(chatManager.getPermission("staffchat.permission"))) {
                        if (chatManager.getConfig().getBoolean("staffchat.enable")) {
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                if (player.hasPermission(chatManager.getPermission("staffchat.permission"))) {
                                    String msg = Joiner.on(" ").join(args);
                                    player.sendMessage(chatManager.getMessage("staffchat.prefix").replace("&", "§") + " " + p.getName() + ": " + msg);
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }



    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();

        if (staffchat.containsKey(p)){
            if (staffchat.get(p) && p.hasPermission(chatManager.getPermission("staffchat.permission"))) {
                e.setCancelled(true);
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.hasPermission(chatManager.getPermission("staffchat.permission"))) {
                        player.sendMessage(chatManager.getMessage("staffchat.prefix").replace("&", "§") + " " + p.getName() + ": " + e.getMessage());
                    }
                }
            }
        }
    }
}
