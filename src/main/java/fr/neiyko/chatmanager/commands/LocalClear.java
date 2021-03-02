package fr.neiyko.chatmanager.commands;

import fr.neiyko.chatmanager.ChatManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LocalClear implements CommandExecutor {

    private ChatManager chatManager = ChatManager.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (chatManager.getConfig().getBoolean("localclear.enable") && cmd.getName().equalsIgnoreCase("localclear")) {
                if (args.length == 0) {
                    if (chatManager.getConfig().getBoolean("localclear.permission-enable")){
                        if (p.hasPermission(chatManager.getPermission("localclear.permission")) || p.isOp()) {
                            for (int i = 0; i < 100; i++) {
                                p.sendMessage("");
                            }
                            p.sendMessage(chatManager.getMessage("localclear.clear").replace("%prefix%", chatManager.getMessage("prefix"))
                                    .replace("&", "§"));
                        } else {
                            p.sendMessage(chatManager.getMessage("localclear.no-permission").replace("&", "§"));
                        }
                    } else {
                        for (int i = 0; i < 100; i++) {
                            p.sendMessage("");
                        }
                        p.sendMessage(chatManager.getMessage("localclear.clear").replace("%prefix%", chatManager.getMessage("prefix"))
                                .replace("&", "§"));
                    }
                }
            }
        }

        return false;
    }
}
