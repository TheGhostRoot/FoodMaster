package me.thegoldenmine.foodmaster.group.commands;

import me.thegoldenmine.foodmaster.FoodMaster;
import me.thegoldenmine.foodmaster.group.GroupManager;
import org.bukkit.entity.Player;

public class GroupMain {
    private final FoodMaster plugin;
    private final GroupManager groupManager;

    public GroupMain(FoodMaster main) {
        plugin = main;
        groupManager = new GroupManager(plugin);
    }

    public void GroupMain(Player player, String[] args) {
        // The player is the sender of the command
        if (player != null) {
            if (args.length >= 2) {
                if (args[1].equalsIgnoreCase("invite")) {
                    groupManager.inviteCommand(player, args);
                } else if (args[1].equalsIgnoreCase("accept")) {
                    groupManager.acceptCommand(player, args);
                } else if (args[1].equalsIgnoreCase("leave")) {
                    groupManager.leaveCommand(player);
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
