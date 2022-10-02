package me.thegoldenmine.foodmaster.Commands.SubCmd;

import me.thegoldenmine.foodmaster.FoodMaster;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class EndTheGame {
    private final FoodMaster plugin;

    public EndTheGame(FoodMaster main) {
        plugin = main;
    }

    private void giveGameStats(UUID uuid1) {
        if (!plugin.inGameDeaths.containsKey(uuid1)) {
            plugin.inGameDeaths.put(uuid1, 0);
        }
        if (!plugin.inGameKills.containsKey(uuid1)) {
            plugin.inGameKills.put(uuid1, 0);
        }
    }

    public synchronized void endThePvE(Player player) {
        if (!player.isOnline()) {
            return;
        }
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor italic = ChatColor.ITALIC;
        String s = " " + plugin.mainConfig.getStrMain("name") + " ";
        String ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + ChatColor.RED + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + ChatColor.RED + "" + italic + " ";
        Location endLoc = plugin.mainConfig.getLocationMain("end_location");
        if (plugin.playerPvE.isPlayerPlayingPvE(player)) {
            NamespacedKey name = new NamespacedKey(plugin, "boss");
            NamespacedKey namePlayer = new NamespacedKey(plugin, "player");
            for (World world : Bukkit.getWorlds()) {
                for (LivingEntity entity : world.getLivingEntities()) {
                    PersistentDataContainer data = entity.getPersistentDataContainer();
                    if (data.has(name, PersistentDataType.STRING) && data.has(namePlayer, PersistentDataType.STRING)) {
                        if (plugin.playerGroup.isPlayerInGroup(player)) {
                            String playerName = data.get(namePlayer, PersistentDataType.STRING);
                            if (playerName != null) {
                                Player player1 = Bukkit.getPlayer(playerName);
                                if (player1 != null) {
                                    UUID uuid = player1.getUniqueId();
                                    if (plugin.playerGroup.getPlayersInGroupOfPlayer(player).contains(uuid)) {
                                        entity.setHealth(0);
                                    }
                                }
                            }
                        } else if (data.get(namePlayer, PersistentDataType.STRING).equals(player.getName())) {
                            entity.setHealth(0);
                        }
                    }
                }
            }
            String name1 = plugin.game.getGameName(player);
            plugin.stillAlive.remove(name1);
            if (plugin.playerGroup.isPlayerInGroup(player)) {
                for (UUID uuid1 : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
                    if (uuid1 != null) {
                        Player player1 = Bukkit.getPlayer(uuid1);
                        if (player1 != null) {
                            if (plugin.playersPlayingPvEZombie.contains(uuid1)) {
                                plugin.playAgain.put(uuid1, "pve-zombie");
                            } else if (plugin.playersPlayingPvESkeleton.contains(uuid1)) {
                                plugin.playAgain.put(uuid1, "pve-skeleton");
                            } else if (plugin.playersPlayingPvESlime.contains(uuid1)) {
                                plugin.playAgain.put(uuid1, "pve-slime");
                            } else if (plugin.playersPlayingPvESpider.contains(uuid1)) {
                                plugin.playAgain.put(uuid1, "pve-spider");
                            } else if (plugin.playersPlayingPvEEnderman.contains(uuid1)) {
                                plugin.playAgain.put(uuid1, "pve-enderman");
                            }
                            plugin.playersPlayingPvESpider.remove(uuid1);
                            plugin.playersPlayingPvESkeleton.remove(uuid1);
                            plugin.playersPlayingPvEZombie.remove(uuid1);
                            plugin.playersPlayingPvESlime.remove(uuid1);
                            plugin.playersPlayingPvEEnderman.remove(uuid1);
                            plugin.playersChoicePvESpider.remove(uuid1);
                            plugin.playersChoicePvESkeleton.remove(uuid1);
                            plugin.playersChoicePvEZombie.remove(uuid1);
                            plugin.playersChoicePvEEnderman.remove(uuid1);
                            plugin.playersChoicePvESlime.remove(uuid1);
                            plugin.game.removePlayerFromGame(player1);
                            player1.getInventory().clear();
                            AttributeInstance attributeInstance = player1.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                            assert attributeInstance != null;
                            attributeInstance.setBaseValue(20);
                            player1.setHealth(20);
                            player1.removePotionEffect(PotionEffectType.SPEED);
                            player1.getInventory().clear();
                            player1.setGameMode(GameMode.SURVIVAL);
                            if (endLoc != null) {
                                player1.teleport(endLoc);
                            } else {
                                if (!player1.hasPermission("foodm.staff")) {
                                    player1.sendMessage(ERROR + "One of the locations is not set.");
                                } else {
                                    player1.sendMessage(ERROR + "The end location is not set.");
                                }
                            }
                        }
                    }
                }
            } else {
                // the player is solo
                UUID uuid2 = player.getUniqueId();
                if (plugin.playersPlayingPvEZombie.contains(uuid2)) {
                    plugin.playAgain.put(uuid2, "pve-zombie");
                } else if (plugin.playersPlayingPvESkeleton.contains(uuid2)) {
                    plugin.playAgain.put(uuid2, "pve-skeleton");
                } else if (plugin.playersPlayingPvESlime.contains(uuid2)) {
                    plugin.playAgain.put(uuid2, "pve-slime");
                } else if (plugin.playersPlayingPvESpider.contains(uuid2)) {
                    plugin.playAgain.put(uuid2, "pve-spider");
                } else if (plugin.playersPlayingPvEEnderman.contains(uuid2)) {
                    plugin.playAgain.put(uuid2, "pve-enderman");
                }
                plugin.game.removePlayerFromGame(player);
                plugin.playersPlayingPvESpider.remove(uuid2);
                plugin.playersPlayingPvESkeleton.remove(uuid2);
                plugin.playersPlayingPvEZombie.remove(uuid2);
                plugin.playersPlayingPvESlime.remove(uuid2);
                plugin.playersPlayingPvEEnderman.remove(uuid2);
                plugin.playersChoicePvESpider.remove(uuid2);
                plugin.playersChoicePvESkeleton.remove(uuid2);
                plugin.playersChoicePvEZombie.remove(uuid2);
                plugin.playersChoicePvEEnderman.remove(uuid2);
                plugin.playersChoicePvESlime.remove(uuid2);
                player.getInventory().clear();
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
                    if (!player.hasPermission("foodm.staff")) {
                        player.sendMessage(ERROR + "One of the locations is not set.");
                    } else {
                        player.sendMessage(ERROR + "The end location is not set.");
                    }
                }
            }
        }
    }

    public synchronized void endTheGameWithStatus(Player player) {
        if (!player.isOnline()) {
            return;
        }
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        //ChatColor yellow = ChatColor.YELLOW;
        //ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        //ChatColor aqua = ChatColor.AQUA;
        ChatColor red = ChatColor.RED;
        String ERROR;
        if (plugin.mainConfig.getStrMain("name") != null) {
            ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + " " + plugin.mainConfig.getStrMain("name") + " " + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
        } else {
            ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + " FoodMaster " + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
        }
        //String playerStats = aqua + "" + italic + "<--=={ " + gold + "" + gold + "Player stats" + aqua + "" + italic + " }==-->";
        //String gameStats = aqua + "" + italic + "<--=={ " + gold + "" + gold + "Game stats" + aqua + "" + italic + " }==-->";
        UUID uuid = player.getUniqueId();
        Location endLoc = plugin.mainConfig.getLocationMain("end_location");
        if (endLoc == null) {
            if (player.hasPermission("foodm.staff")) {
                player.sendMessage(ERROR + "The end location is not set. Use " + ChatColor.DARK_PURPLE + "" + italic + "/fm set end");
            } else {
                player.sendMessage(ERROR + "One of the locations isn't set.");
            }
        } else {
            // play again -- start
            if (!plugin.playerGroup.isPlayerInGroup(player)) {
                player.sendMessage(ERROR + "You need to be in a group.");
            } else {
                if (plugin.playersInFreeForAll.contains(uuid) || plugin.playersChoiceFreeForAll.contains(uuid)) {
                    plugin.playAgain.put(uuid, "free-for-all");
                }
                if (plugin.players5Teams.contains(uuid) || plugin.playersThatChoice5Teams.contains(uuid)) {
                    plugin.playAgain.put(uuid, "team-deathmatch5");
                } else if (plugin.players4Teams.contains(uuid) || plugin.playersThatChoice4Teams.contains(uuid)) {
                    plugin.playAgain.put(uuid, "team-deathmatch4");
                } else if (plugin.players3Teams.contains(uuid) || plugin.playersThatChoice3Teams.contains(uuid)) {
                    plugin.playAgain.put(uuid, "team-deathmatch3");
                } else if (plugin.players2Teams.contains(uuid) || plugin.playersThatChoice2Teams.contains(uuid)) {
                    plugin.playAgain.put(uuid, "team-deathmatch2");
                }
                if (plugin.playersInFoodGame.contains(uuid) || plugin.playersChoiceFoodGame.contains(uuid)) {
                    plugin.playAgain.put(uuid, "food-game");
                }
                // play again -- end
                if (plugin.game.isPlayerInGame(player)) {
                    String name = plugin.game.getGameName(player);
                    if (name != null) {
                        plugin.timer.remove(name);
                        if (plugin.timerTik.bossBar.getPlayers().contains(player)) {
                            plugin.timerTik.bossBar.removePlayer(player);
                        }
                        for (UUID uuid1 : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
                            if (uuid1 != null) {
                                Player player1 = Bukkit.getPlayer(uuid1);
                                if (player1 != null) {
                                    giveGameStats(uuid1);
                                }
                            }
                        }
                        // MVP Board
                        if (plugin.isPlayerPlayingFoodGame(player)) {
                            for (UUID uuids : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
                                if (plugin.FoodGameWinner.contains(uuids)) {
                                    Player playerWinner = Bukkit.getPlayer(uuids);
                                    if (playerWinner != null) {
                                        plugin.endPvP.foodGameWinner(player, playerWinner);
                                    }
                                    break;
                                } else {
                                    plugin.endPvP.foodGame(player);
                                }
                            }
                        } else if (plugin.deathmatch.isPlayerPlayingFoodWars(player)) {
                            plugin.endPvP.deathMatch(player);
                        } else if (plugin.isPlayerPlayingFreeForAll(player)) {
                            plugin.endPvP.freeForAll(player);
                        }
                    }
                }
            }
            plugin.clearPlayer.clearThePlayer(player);
        }
    }
}
