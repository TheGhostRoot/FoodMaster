package me.thegoldenmine.foodmaster.group;

import me.thegoldenmine.foodmaster.FoodMaster;
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
                    groupManager.listCommand(player);
                } else if (args[1].equalsIgnoreCase("kick")) {
                    groupManager.kickCommand(player, args);
                } else if (args[1].equalsIgnoreCase("chat")) {
                    groupManager.chatCommand(player, args);
                } else if (args[1].equalsIgnoreCase("help")) {
                    plugin.helpMenu.helpGroupMenu(player);
                }
            } else {
                plugin.helpMenu.helpGroupMenu(player);
            }
        }
    }
}
