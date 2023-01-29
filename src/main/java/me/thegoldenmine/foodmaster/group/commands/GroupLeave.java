package me.thegoldenmine.foodmaster.group.commands;

import me.thegoldenmine.foodmaster.FoodMaster;
import me.thegoldenmine.foodmaster.group.GroupManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;


public class GroupLeave {
    private final FoodMaster plugin;
    private final GroupManager groupManager;

    public GroupLeave(FoodMaster main) {
        plugin = main;
        groupManager = new GroupManager(plugin);
    }

    public void leaveFromGroup(Player player) {
        if (plugin.game.isPlayerInGame(player)) {
            player.sendMessage("You cannot leave the group while in-game.");
            return;
        }

        if (plugin.waitingLobby.isPlayerInWaitingLobby(player)) {
            Location endLoc = plugin.mainConfig.getLocationMain("end_location");
            if (endLoc == null) {
                player.sendMessage("End location is not set. Unable to leave group.");
            } else {
                player.teleport(endLoc);
                groupManager.leaveGroup(player);
                // TODO clear the player's choose
            }
        } else {
            groupManager.leaveGroup(player);
        }
    }
}
