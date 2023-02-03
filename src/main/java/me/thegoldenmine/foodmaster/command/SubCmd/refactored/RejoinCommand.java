package me.thegoldenmine.foodmaster.command.SubCmd.refactored;

import me.thegoldenmine.foodmaster.FoodMaster;
import me.thegoldenmine.foodmaster.GroupManager;
import me.thegoldenmine.foodmaster.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public class RejoinCommand {
    private final FoodMaster plugin;
    private final Messenger messenger;
    private final GroupManager groupManager;

    public RejoinCommand(FoodMaster main) {
        plugin = main;
        messenger = new Messenger(plugin);
        groupManager = new GroupManager(plugin);
    }

    // TODO finish this command

    private boolean can_rejoin(Player player) {
        UUID uuid = player.getUniqueId();
        if (!plugin.kickedPlayers.contains(uuid)) {
            messenger.error(player, "You have to be kicked to rejoin a game.");
            return false;
        }
        if (!groupManager.isPlayerInGroup(player)) {
            plugin.kickedPlayers.remove(uuid);
            messenger.error(player, "You are not in a group");
            return false;
        }
        return true;
    }

    private boolean is_group_in_waiting_lobby(Player player) {
        return false;
    }

    private boolean is_group_in_game(Player player) {
        return false;
    }

    private boolean is_group_in_pve(Player player) {
        return false;
    }

    private void join_waiting_lobby(Player player) {}

    private void join_game(Player player) {}

    private void join_pve(Player player) {}

    public void rejoin(Player player) {
        UUID uuid = player.getUniqueId();
        if (!can_rejoin(player)) { return; }
        if (is_group_in_game(player)) {
            join_game(player);
        } else if (is_group_in_waiting_lobby(player)) {
            join_waiting_lobby(player);
        } else if (is_group_in_pve(player)) {
            join_pve(player);
        } else {
            messenger.info(player, "Your group is not playing anything.");
            plugin.kickedPlayers.remove(uuid);
        }
    }
}
