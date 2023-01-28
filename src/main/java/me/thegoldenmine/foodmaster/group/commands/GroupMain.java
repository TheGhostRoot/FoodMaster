package me.thegoldenmine.foodmaster.group.commands;

import me.thegoldenmine.foodmaster.FoodMaster;
import org.bukkit.entity.Player;

public class GroupMain {
    public FoodMaster plugin;

    public GroupMain(FoodMaster plugin) {
        this.plugin = plugin;
    }

    public void GroupMain(Player player, String[] args) {
        // The player is the sender of the command
        if (player != null) {
            if (args.length >= 2) {
                if (args[1].equalsIgnoreCase("invite")) {
                    plugin.groupInvite.PlayerGroupInvite(player, args);
                } else if (args[1].equalsIgnoreCase("accept")) {
                    plugin.groupAccept.acceptGroupInvite(player, args);
                } else if (args[1].equalsIgnoreCase("leave")) {
                    plugin.playerGroup.PlayerLeaveFromGroup(player);
                } else if (args[1].equalsIgnoreCase("list")) {
                    plugin.groupList.getGroupPlayers(player);
                } else if (args[1].equalsIgnoreCase("kick")) {
                    plugin.groupKick.PlayerKickGroup(player, args);
                } else if (args[1].equalsIgnoreCase("chat")) {
                    plugin.groupChat.groupChat(player, args);
                } else if (args[1].equalsIgnoreCase("help")) {
                    plugin.helpMenu.helpGroupMenu(player);
                }
            } else {
                plugin.helpMenu.helpGroupMenu(player);
            }
        }
    }
}
