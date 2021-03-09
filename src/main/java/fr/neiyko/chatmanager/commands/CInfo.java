package fr.neiyko.chatmanager.commands;

import fr.neiyko.chatmanager.ChatManager;
import fr.neiyko.chatmanager.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CInfo implements CommandExecutor {

    ChatManager chatManager = ChatManager.getInstance();
    Utils utils = Utils.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        if (sender instanceof Player) {

            Player p = (Player) sender;
            Player target = (Player) chatManager.getServer().getOfflinePlayer(args[0]);
            //Vault API

            if (chatManager.getConfig().getBoolean("player-info.permission-enable")) {
                if (chatManager.hasPermission(p, "player-info.permission")) {
                    for (String message : chatManager.getMessageStringList("player-info.page-1")) {
                        p.sendMessage(message.replace("%player%", target.getName()).replace("%displayname%", utils.targetRank((Player) target) + target.getName())
                                .replace("%isop%", utils.targetIsOp(target)));
                        //.replace("%rank%", utils.targetRank(target))
                        //.replace("%isonline%", utils.targetIsOnline(target)));
                    }
                } else {
                    p.sendMessage(chatManager.getMessage("player-info.no-permission").replace("&", "§"));
                    return false;
                }
            }
            System.out.println(p.getName());
            System.out.println(target.getName());
        } else {
            sender.sendMessage(chatManager.getMessage("not-player-instance").replace("&", "§"));
            return false;
        }

        return false;
    }
}
