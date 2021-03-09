package fr.neiyko.chatmanager;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class Utils {

    private static Utils utils;

    public static Utils getInstance(){
        return utils;
    }

    public String targetIsOp(OfflinePlayer target) {
        if (target.isOp()) {
            return "Yes";
        } else {
            return "No";
        }
    }

    public String targetIsOnline(Player target) {
        if (target.isOnline()) {
            return "Yes";
        } else {
            return "No";
        }
    }

    public String targetRank(Player target) {
        //Vault API
        String primaryGroup = ChatManager.getPerms().getPrimaryGroup(target.getLocation().getWorld(), target.getName());

        return ChatManager.getChat().getGroupPrefix(target.getLocation().getWorld(), primaryGroup);
    }

}
