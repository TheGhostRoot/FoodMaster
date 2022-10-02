package me.thegoldenmine.foodmaster.Commands.SubCmd;

import me.thegoldenmine.foodmaster.FoodMaster;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.*;

public class ResetPlayer {
    private final FoodMaster plugin;

    public ResetPlayer(FoodMaster plugin) {
        this.plugin = plugin;
    }

    public void resetPlayer(Player player) {
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor red = ChatColor.RED;
        String s;
        if (plugin.mainConfig.getStrMain("name") != null) {
            s = " "+plugin.mainConfig.getStrMain("name")+" ";
        } else {
            s = " FoodMaster ";
        }
        String NORMAL = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + darkGray + "" + strikethrough + "-" + green + "" + italic + " ";
        // /fw reset
        //      0    index
        //      1        num
        if (player != null) {
            UUID uuid = player.getUniqueId();
            if (plugin.game.isPlayerInGame(player)) {
                String name = plugin.game.getGameName(player);
                if (name != null && plugin.stillAlive.containsKey(name)) {
                    plugin.stillAlive.get(name).remove(uuid);
                }
                plugin.game.removePlayerFromGame(player);
            }
            if (plugin.waitingLobby.isPlayerInWaitingLobby(player)) {
                plugin.waitingLobby.removePlayerFromWaitedLobby(player);
            }
            // kits
            plugin.playersInPotatoKit.remove(uuid);
            plugin.playersInBreadKit.remove(uuid);
            plugin.playersInMelonKit.remove(uuid);
            plugin.playersInCookieKit.remove(uuid);
            plugin.playersInFishKit.remove(uuid);
            plugin.playersRandomKit.remove(uuid);
            // pve
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
            // teams
            plugin.playersInBlueTeams.remove(uuid);
            plugin.playersInCyanTeams.remove(uuid);
            plugin.playersInRedTeams.remove(uuid);
            plugin.playersInGreenTeams.remove(uuid);
            plugin.playersInYellowTeams.remove(uuid);
            plugin.playersRandomTeam.remove(uuid);
            plugin.players2Teams.remove(uuid);
            plugin.players3Teams.remove(uuid);
            plugin.winners.remove(uuid);
            plugin.losses.remove(uuid);
            plugin.players4Teams.remove(uuid);
            plugin.players5Teams.remove(uuid);
            // others
            plugin.playersThatChoice5Teams.remove(uuid);
            plugin.playersThatChoice4Teams.remove(uuid);
            plugin.playersThatChoice2Teams.remove(uuid);
            plugin.playersThatChoice3Teams.remove(uuid);
            plugin.ReadySystem.remove(uuid);
            plugin.playersInFoodGame.remove(uuid);
            plugin.playersChoiceFoodGame.remove(uuid);
            plugin.FoodGameWinner.remove(uuid);
            plugin.playAgain.remove(uuid);
            plugin.ReadyPlayers.remove(uuid);
            plugin.kickedPlayers.remove(uuid);
            plugin.lives.remove(uuid);
            plugin.playersInFreeForAll.remove(uuid);
            plugin.inGameDeaths.remove(uuid);
            plugin.inGameKills.remove(uuid);
            plugin.playersChoiceFreeForAll.remove(uuid);
            plugin.mainConfig.setIntLose(String.valueOf(uuid), 0);
            plugin.mainConfig.setIntWin(String.valueOf(uuid), 0);
            plugin.mainConfig.setIntKill(String.valueOf(uuid), 0);
            plugin.mainConfig.setIntDeath(String.valueOf(uuid), 0);
            if (plugin.playerGroup.isPlayerInGroup(player)) {
                Set<UUID> playersInGroupOfPlayer = plugin.playerGroup.getPlayersInGroupOfPlayer(player);
                assert playersInGroupOfPlayer != null;
                plugin.locOfPlayersInWaitingLobby.remove(new HashSet<>(playersInGroupOfPlayer));
                plugin.allGroups.remove(new HashSet<>(playersInGroupOfPlayer));
            }
            plugin.allGroups.removeIf(Set::isEmpty);
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
            player.setGameMode(GameMode.SURVIVAL);
            player.sendMessage(NORMAL + "" + red + "You have been reset and the stats have been reset too.");
        }
    }

    public void resetPlayer(Player player, String[] args) {
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor red = ChatColor.RED;
        String s;
        if (plugin.mainConfig.getStrMain("name") != null) {
            s = " "+plugin.mainConfig.getStrMain("name")+" ";
        } else {
            s = " FoodMaster ";
        }
        String NORMAL = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + darkGray + "" + strikethrough + "-" + green + "" + italic + " ";
        String ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
        // /fw reset player
        //      0     1     index
        //      1      2    num
        if (player != null && args[1] != null) {
            Player resetPlayer = Bukkit.getPlayer(args[1]);
            if (resetPlayer != null) {
                UUID uuid = resetPlayer.getUniqueId();
                if (plugin.game.isPlayerInGame(resetPlayer)) {
                    String name = plugin.game.getGameName(resetPlayer);
                    if (name != null && plugin.stillAlive.containsKey(name)) {
                        plugin.stillAlive.get(name).remove(uuid);
                    }
                    plugin.game.removePlayerFromGame(resetPlayer);
                }
                if (plugin.waitingLobby.isPlayerInWaitingLobby(resetPlayer)) {
                    plugin.waitingLobby.removePlayerFromWaitedLobby(resetPlayer);
                }
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
                plugin.mainConfig.setIntLose(String.valueOf(uuid), 0);
                plugin.mainConfig.setIntWin(String.valueOf(uuid), 0);
                plugin.mainConfig.setIntKill(String.valueOf(uuid), 0);
                plugin.mainConfig.setIntDeath(String.valueOf(uuid), 0);
                if (plugin.playerGroup.isPlayerInGroup(resetPlayer)) {
                    Set<UUID> playersInGroupOfPlayer = plugin.playerGroup.getPlayersInGroupOfPlayer(resetPlayer);
                    assert playersInGroupOfPlayer != null;
                    plugin.locOfPlayersInWaitingLobby.remove(new HashSet<>(playersInGroupOfPlayer));
                    plugin.allGroups.remove(new HashSet<>(playersInGroupOfPlayer));
                }
                plugin.allGroups.removeIf(Set::isEmpty);
                PlayerInventory inventory = resetPlayer.getInventory();
                List<ItemStack> itemsToDelete = new ArrayList<>();
                for (ItemStack item : inventory) {
                    if (item != null && plugin.items.contains(item)) {
                        itemsToDelete.add(item);
                    }
                }
                for (ItemStack item : itemsToDelete) {
                    inventory.remove(item);
                }
                resetPlayer.setGameMode(GameMode.SURVIVAL);
                resetPlayer.sendMessage(NORMAL + "" + red + "" + italic + "You have been reset by " + gold + "" + italic + "" + player.getName() + "" + red + "" + italic + " and the stats are reset too.");
                player.sendMessage(NORMAL + "" + gold + "" + italic + "" + resetPlayer.getName() + "" + green + "" + italic + " the player has been reset. The stats have been reset too.");
            } else {
                player.sendMessage(ERROR + "This player is not found.");
            }
        }
    }
}
