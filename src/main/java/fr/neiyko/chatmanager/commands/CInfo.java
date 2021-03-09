package fr.neiyko.chatmanager.commands;

import fr.neiyko.chatmanager.ChatManager;
import fr.neiyko.chatmanager.Utils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class CInfo implements CommandExecutor {

    ChatManager chatManager = ChatManager.getInstance();
    Utils utils = chatManager.getUtils();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        if (sender instanceof Player) {

            Player p = (Player) sender;
            Player target = chatManager.getServer().getPlayer(args[0]);

            //TextHover

            //NextPage
            TextComponent nextPage = new TextComponent(chatManager.getMessage("player-info.next-page").replace("&", "§"));
            nextPage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(chatManager.getMessage("player-info.next-page-hover").replace("&", "§")).create()));
            //PreviousPage
            TextComponent previousPage = new TextComponent(chatManager.getMessage("player-info.previous-page").replace("&", "§"));
            previousPage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(chatManager.getMessage("player-info.previous-page-hover").replace("&", "§")).create()));

                if (chatManager.getConfig().getBoolean("player-info.permission-enable")) {
                    if (chatManager.hasPermission(p, "player-info.permission")) {
                        if (target != null) {
                            if (args.length == 1 || args.length == 2 && args[1].equalsIgnoreCase("1")) {
                                for (String message : chatManager.getMessageStringList("player-info.page-1")) {
                                    p.sendMessage(message.replace("%player%", target.getName())
                                            .replace("%displayname%", utils.targetRank(target) + " " + target.getDisplayName())
                                            .replace("%isop%", utils.targetIsOp(target))
                                            .replace("%rank%", utils.targetRank(target))
                                            .replace("%isonline%", utils.targetIsOnline(target))
                                            .replace("&", "§"));
                                }
                                p.sendMessage("§2✦ §6Page 1/" + chatManager.infoPage + " §2✦");
                                nextPage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/info " + target.getName() + " 2"));
                                p.spigot().sendMessage(nextPage);
                            }
                            if (args.length == 2 && args[1].equalsIgnoreCase("2")) {
                                for (String message : chatManager.getMessageStringList("player-info.page-2")) {
                                    p.sendMessage(message.replace("%player%", target.getName())
                                            .replace("%locx%", target.getLocation().getX() + "")
                                            .replace("%locy%", target.getLocation().getY() + "")
                                            .replace("%locz%", target.getLocation().getZ() + "")
                                            .replace("%health%", target.getHealth() + " / " + target.getMaxHealth())
                                            .replace("%food%", target.getFoodLevel() + " / 20")
                                            .replace("%xplevel%", target.getLevel() + "")
                                            .replace("&", "§"));
                                }
                                p.sendMessage("§2✦ §6Page 2/" + chatManager.infoPage + " §2✦");
                                previousPage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/info " + target.getName()));
                                p.spigot().sendMessage(previousPage);
                            }
                        } else {
                            p.sendMessage(chatManager.getMessage("player-info.not-online").replace("%player%", args[0]).replace("&", "§"));
                            return false;
                        }
                    } else {
                        p.sendMessage(chatManager.getMessage("player-info.no-permission").replace("&", "§"));
                        return false;
                    }
                }

        } else {
            sender.sendMessage(chatManager.getMessage("not-player-instance").replace("&", "§"));
            return false;
        }

        return false;
    }
}
