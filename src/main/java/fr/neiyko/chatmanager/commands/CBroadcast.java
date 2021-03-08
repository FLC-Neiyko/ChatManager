package fr.neiyko.chatmanager.commands;

import fr.neiyko.chatmanager.ChatManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CBroadcast implements CommandExecutor {

    ChatManager chatManager = ChatManager.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;

            StringBuilder str = new StringBuilder();
            for (String arg : args) {
                str.append(arg).append(" ");
            }
            String broadcast = str.toString();

            if (chatManager.getConfig().getBoolean("broadcast.enable")) {
                if (chatManager.hasPermission(p, "broadcast.permission") || p.isOp()) {
                    Bukkit.broadcastMessage("");
                    Bukkit.broadcastMessage(chatManager.getMessage("broadcast.on-broadcast").replace("%bprefix%", chatManager.getMessage("broadcast.prefix")
                    ).replace("%broadcast%", broadcast).replace("&", "§"));
                    Bukkit.broadcastMessage("");
                } else {
                    p.sendMessage(chatManager.getMessage("broadcast.no-permission").replace("&", "§"));
                }
            }
        } else {
            sender.sendMessage(chatManager.getMessage("not-player-instance").replace("&", "§"));
            return false;
        }
        return false;
    }
}
