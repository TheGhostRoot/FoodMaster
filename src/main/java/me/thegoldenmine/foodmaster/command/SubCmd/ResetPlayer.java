package me.thegoldenmine.foodmaster.command.SubCmd;

import me.thegoldenmine.foodmaster.FoodMaster;
import me.thegoldenmine.foodmaster.GroupManager;
import me.thegoldenmine.foodmaster.Messenger;
import me.thegoldenmine.foodmaster.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.*;

public class ResetPlayer {
    private final FoodMaster plugin;
    private final GroupManager groupManager;
    private final Messenger messenger;
    private final Utils utils;

    public ResetPlayer(FoodMaster main) {
        plugin = main;
        groupManager = new GroupManager(plugin);
        messenger = new Messenger(plugin);
        utils = new Utils(plugin);
    }

    private void resetStats(UUID uuid) {
        plugin.mainConfig.setIntLose(String.valueOf(uuid), 0);
        plugin.mainConfig.setIntWin(String.valueOf(uuid), 0);
        plugin.mainConfig.setIntKill(String.valueOf(uuid), 0);
        plugin.mainConfig.setIntDeath(String.valueOf(uuid), 0);
    }

    private void remove_from_maps(UUID uuid) {
        // TODO refactor this when the maps are refactored
        // kits
        plugin.playersInPotatoKit.remove(uuid);
        plugin.playersInBreadKit.remove(uuid);
        plugin.playersInMelonKit.remove(uuid);
        plugin.playersInCookieKit.remove(uuid);
        plugin.playersInFishKit.remove(uuid);
        plugin.playersRandomKit.remove(uuid);
        // teams
        plugin.playersInBlueTeams.remove(uuid);
        plugin.playersInCyanTeams.remove(uuid);
        plugin.playersInRedTeams.remove(uuid);
        plugin.FoodGameWinner.remove(uuid);
        plugin.playersInGreenTeams.remove(uuid);
        plugin.playersInYellowTeams.remove(uuid);
        plugin.playersRandomTeam.remove(uuid);
        plugin.players2Teams.remove(uuid);
        plugin.players3Teams.remove(uuid);
        plugin.players4Teams.remove(uuid);
        plugin.players5Teams.remove(uuid);
        // PVE
        plugin.playersPlayingPvESpider.remove(uuid);
        plugin.playersPlayingPvESkeleton.remove(uuid);
        plugin.playersPlayingPvEZombie.remove(uuid);
        plugin.playersPlayingPvESlime.remove(uuid);
        plugin.playersPlayingPvEEnderman.remove(uuid);
        plugin.playersChoicePvESpider.remove(uuid);
        plugin.playersChoicePvESkeleton.remove(uuid);
        plugin.playersChoicePvEZombie.remove(uuid);
        plugin.playersChoicePvEEnderman.remove(uuid);
        plugin.playersChoicePvESlime.remove(uuid);
        // others
        plugin.playersThatChoice5Teams.remove(uuid);
        plugin.playersThatChoice4Teams.remove(uuid);
        plugin.playersThatChoice2Teams.remove(uuid);
        plugin.playersThatChoice3Teams.remove(uuid);
        plugin.ReadySystem.remove(uuid);
        plugin.playAgain.remove(uuid);
        plugin.winners.remove(uuid);
        plugin.losses.remove(uuid);
        plugin.ReadyPlayers.remove(uuid);
        plugin.kickedPlayers.remove(uuid);
        plugin.lives.remove(uuid);
        plugin.playersInFreeForAll.remove(uuid);
        plugin.playersChoiceFreeForAll.remove(uuid);
    }

    private void remove_from_game_or_waiting_lobby(Player player) {
        if (plugin.game.isPlayerInGame(player)) {
            String name = plugin.game.getGameName(player);
            if (name != null && plugin.stillAlive.containsKey(name)) {
                plugin.stillAlive.get(name).remove(player.getUniqueId());
            }
            plugin.game.removePlayerFromGame(player);
        }
        if (plugin.waitingLobby.isPlayerInWaitingLobby(player)) {
            plugin.waitingLobby.removePlayerFromWaitedLobby(player);
        }
    }

    private void remove_from_group(Player player) {
        if (groupManager.isPlayerInGroup(player)) {
            Set<UUID> playersInGroupOfPlayer = groupManager.getPlayersInGroupOfPlayer(player);
            assert playersInGroupOfPlayer != null;
            plugin.locOfPlayersInWaitingLobby.remove(new HashSet<>(playersInGroupOfPlayer));
            plugin.allGroups.remove(new HashSet<>(playersInGroupOfPlayer));
        }
        plugin.allGroups.removeIf(Set::isEmpty);
    }

    private void remove_all_foodmaster_items(Player player) {
        utils.clearPlayerChose(player);
        PlayerInventory inventory = player.getInventory();
        List<ItemStack> itemsToDelete = new ArrayList<>();
        for (ItemStack item : inventory) {
            if (item != null && plugin.items.contains(item)) {
                itemsToDelete.add(item);
            }
        }
        for (ItemStack item : itemsToDelete) {
            inventory.remove(item);
        }
    }

    public void reset_player_command(Player player, String[] args) {
        Player resetPlayer = args.length > 1 ? Bukkit.getPlayer(args[1]) : player;
        if (player != null) {
            if (resetPlayer == null) {
                messenger.error(player, "Player is invalid.");
                return;
            }
            remove_from_game_or_waiting_lobby(player);
            remove_from_group(player);
            remove_all_foodmaster_items(player);
            UUID uuid = player.getUniqueId();
            remove_from_maps(uuid);
            resetStats(uuid);
            resetPlayer.setGameMode(GameMode.SURVIVAL);
            if (resetPlayer == player) {
                messenger.warn(resetPlayer, "Your stats are reset.");
            } else {
                messenger.warn(resetPlayer, "Your stats are reset by "+ Messenger.MAIN_GENERAL + player.getName());
            }
        }
    }
}
