package me.thegoldenmine.foodmaster.Commands.SubCmd.EndHelpers;

import me.thegoldenmine.foodmaster.FoodMaster;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ClearPlayer {
    private final FoodMaster plugin;

    public ClearPlayer(FoodMaster main) {
        plugin = main;
    }

    public void clearThePlayer(Player player) {
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor italic = ChatColor.ITALIC;;
        ChatColor red = ChatColor.RED;
        String ERROR;
        if (plugin.mainConfig.getStrMain("name") != null) {
            ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + " " + plugin.mainConfig.getStrMain("name") + " " + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
        } else {
            ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + " FoodMaster " + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
        }
        Location endLoc = plugin.mainConfig.getLocationMain("end_location");
        UUID uuid = player.getUniqueId();
        if (plugin.game.isPlayerInGame(player)) {
            plugin.game.removePlayerFromGame(player);
        }
        if (plugin.waitingLobby.isPlayerInWaitingLobby(player)) {
            plugin.waitingLobby.removePlayerFromWaitedLobby(player);
        }
        if (plugin.playerGroup.isPlayerInGroup(player)) {
            Set<UUID> playerGroupClone = new HashSet<>(plugin.playerGroup.getPlayersInGroupOfPlayer(player));
            if (!playerGroupClone.isEmpty()) {
                plugin.locOfPlayersInWaitingLobby.remove(playerGroupClone);
            }
        }
        PlayerInventory inventory = player.getInventory();
        if (inventory.getHelmet() != null) {
            inventory.setHelmet(null);
        }
        if (inventory.getChestplate() != null) {
            inventory.setChestplate(null);
        }
        if (inventory.getLeggings() != null) {
            inventory.setLeggings(null);
        }
        if (inventory.getBoots() != null) {
            inventory.setBoots(null);
        }
        // kits
        plugin.playersInPotatoKit.remove(uuid);
        plugin.playersInBreadKit.remove(uuid);
        plugin.playersInMelonKit.remove(uuid);
        plugin.playersInCookieKit.remove(uuid);
        plugin.playersInFishKit.remove(uuid);
        plugin.playersRandomKit.remove(uuid);
        // teams
        plugin.players2Teams.remove(uuid);
        plugin.players3Teams.remove(uuid);
        plugin.players4Teams.remove(uuid);
        plugin.players5Teams.remove(uuid);
        // others
        plugin.playersThatChoice5Teams.remove(uuid);
        plugin.playersThatChoice4Teams.remove(uuid);
        plugin.playersThatChoice2Teams.remove(uuid);
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
        if (plugin.playerGroup.isPlayerInGroup(player)) {
            plugin.locOfPlayersInWaitingLobby.remove(new HashSet<>(plugin.playerGroup.getPlayersInGroupOfPlayer(player)));
        }
        plugin.kickedPlayers.remove(uuid);
        plugin.playersThatChoice3Teams.remove(uuid);
        String name1 = plugin.game.getGameName(player);
        plugin.stillAlive.remove(name1);
        plugin.ReadySystem.remove(uuid);
        plugin.ReadyPlayers.remove(uuid);
        plugin.playersInFreeForAll.remove(uuid);
        plugin.lives.remove(uuid);
        plugin.playersChoiceFreeForAll.remove(uuid);
        // Health
        AttributeInstance attributeInstance = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        assert attributeInstance != null;
        attributeInstance.setBaseValue(20);
        player.setHealth(20);
        player.removePotionEffect(PotionEffectType.SPEED);
        player.getInventory().clear();
        player.setGameMode(GameMode.SURVIVAL);
        if (endLoc != null) {
            player.teleport(endLoc);
        } else {
            if (player.hasPermission("foodm.staff")) {
                player.sendMessage(ERROR + "The end location is not set. Use " + ChatColor.DARK_PURPLE + "" + italic + "/fm set end");
            } else {
                player.sendMessage(ERROR + "One of the locations isn't set.");
            }
        }
    }
}
