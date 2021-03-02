package fr.neiyko.chatmanager.commands;

import fr.neiyko.chatmanager.ChatManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearChat implements CommandExecutor {

    private ChatManager chatManager = ChatManager.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (chatManager.getConfig().getBoolean("clearchat.enable") && cmd.getName().equalsIgnoreCase("clearchat")) {
                if (args.length == 0) {
                    if (p.hasPermission(chatManager.getPermission("clearchat.permission")) || p.isOp()) {
                        for (int i = 0; i < 100; i++) {
                            Bukkit.getOnlinePlayers().forEach(players -> players.sendMessage(""));
                        }
                        Bukkit.broadcastMessage(chatManager.getMessage("clearchat.clear").replace("%prefix%", chatManager.getMessage("prefix")).replace("&", "§"));
                    } else {
                        p.sendMessage(chatManager.getMessage("clearchat.no-permission").replace("&", "§"));
                    }
                }
            }
        }
        return false;
    }
}
