package me.thegoldenmine.foodmaster.Commands.SubCmd;

import me.thegoldenmine.foodmaster.FoodMaster;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public class Rejoin {
    private final FoodMaster plugin;

    public Rejoin(FoodMaster main) {
        plugin = main;
    }

    public void rejoinCommand(Player player) {
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor yellow = ChatColor.YELLOW;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor red = ChatColor.RED;
        String s;
        if (plugin.mainConfig.getStrMain("name") != null) {
            s = " " + plugin.mainConfig.getStrMain("name") + " ";
        } else {
            s = " FoodMaster ";
        }
        String WARN = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + yellow + "" + bold + "WARN " + darkGray + "" + strikethrough + "-" + yellow + "" + italic + " ";
        String NORMAL = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + darkGray + "" + strikethrough + "-" + green + "" + italic + " ";
        String ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
        // player is the sender of the command
        if (!player.isOnline()) {
            return;
        }
        UUID uuid = player.getUniqueId();
        if (plugin.kickedPlayers.contains(uuid) && plugin.playerGroup.isPlayerInGroup(player)) {
            if (plugin.playerGroup.isPlayerInGroup(player)) {
                for (UUID uuids : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
                    if (uuids != null) {
                        Player players = Bukkit.getPlayer(uuids);
                        if (players != null) {
                            UUID uniqueId = players.getUniqueId();
                            if (uniqueId != uuid) {
                                if (plugin.waitingLobby.isPlayerInWaitingLobby(players)) {
                                    // waiting lobby
                                    // playersInWaitingLobby
                                    // name - key
                                    // group - value
                                    for (String name : plugin.playersInWaitingLobby.keySet()) {
                                        if (name != null && plugin.playersInWaitingLobby.get(name).contains(uniqueId)) {
                                            Location loc = plugin.mainConfig.getLocationWaitLobby(name + "->wait-location");
                                            if (loc != null) {
                                                player.teleport(loc);
                                            } else {
                                                if (player.hasPermission("foodm.staff")) {
                                                    player.sendMessage(ERROR + "The end location is not set. Use " + ChatColor.DARK_PURPLE + "" + italic + "/fm set end");
                                                } else {
                                                    player.sendMessage(ERROR + "One of the locations isn't set.");
                                                }
                                            }
                                            player.getInventory().clear();
                                            plugin.playerGroup.removeAllPlayerInvites(player);
                                            plugin.playersInWaitingLobby.get(name).add(uuid);
                                            if (plugin.playersThatChoice2Teams.contains(uniqueId)) {
                                                plugin.playersThatChoice2Teams.add(uuid);
                                            } else if (plugin.playersThatChoice3Teams.contains(uniqueId)) {
                                                plugin.playersThatChoice3Teams.add(uuid);
                                            } else if (plugin.playersThatChoice4Teams.contains(uniqueId)) {
                                                plugin.playersThatChoice4Teams.add(uuid);
                                            } else if (plugin.playersThatChoice5Teams.contains(uniqueId)) {
                                                plugin.playersThatChoice5Teams.add(uuid);
                                            } else if (plugin.playersChoiceFreeForAll.contains(uniqueId)) {
                                                plugin.playersChoiceFreeForAll.add(uuid);
                                            } else if (plugin.playersChoicePvEZombie.contains(uniqueId)) {
                                                plugin.playersChoicePvEZombie.add(uuid);
                                            } else if (plugin.playersChoicePvESkeleton.contains(uniqueId)) {
                                                plugin.playersChoicePvESkeleton.add(uuid);
                                            } else if (plugin.playersChoicePvESlime.contains(uniqueId)) {
                                                plugin.playersChoicePvESlime.add(uuid);
                                            } else if (plugin.playersChoicePvESpider.contains(uniqueId)) {
                                                plugin.playersChoicePvESpider.add(uuid);
                                            } else if (plugin.playersChoicePvEEnderman.contains(uniqueId)) {
                                                plugin.playersChoicePvEEnderman.add(uuid);
                                            }
                                            plugin.kickedPlayers.remove(uuid);
                                            break;
                                        }
                                    }
                                } else if (plugin.game.isPlayerInGame(players)) {
                                    // game
                                    String GameName = plugin.game.getGameName(players);
                                    if (GameName != null) {
                                        Set<UUID> groupUUIDs = new HashSet<>(plugin.playersInGame.get(GameName));
                                        Set<String> namesOfGameLoc = plugin.GameSpawnPoints.get(GameName);
                                        if (namesOfGameLoc.isEmpty() || namesOfGameLoc.contains(null)) {
                                            player.sendMessage(ERROR + "" + gold + "" + italic + "" + GameName + "" + red + "" + italic + " doesn't have any spawn points.");
                                            plugin.kickedPlayers.remove(uuid);
                                            break;
                                        }
                                        int max = namesOfGameLoc.size() - 1;
                                        int theIndex = new Random().nextInt(max + 1);
                                        List<String> list1 = new ArrayList<>(namesOfGameLoc);
                                        String theNameOfLoc = list1.get(theIndex);
                                        if (plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uuid)) {
                                            Location theLocation = plugin.mainConfig.getLocationGame(GameName + "->" + theNameOfLoc + "-spawn-point");
                                            // free for all
                                            if (plugin.playersInFreeForAll.contains(uniqueId)) {
                                                plugin.playersInFreeForAll.add(uuid);
                                            }
                                            // Teams
                                            if (plugin.players2Teams.contains(uniqueId)) {
                                                int random = new Random().nextInt(2);
                                                if (random == 1) {
                                                    plugin.playersInBlueTeams.add(uuid);
                                                } else {
                                                    plugin.playersInRedTeams.add(uuid);
                                                }
                                            } else if (plugin.players3Teams.contains(uniqueId)) {
                                                int random = new Random().nextInt(3);
                                                if (random == 1) {
                                                    plugin.playersInBlueTeams.add(uuid);
                                                } else if (random == 2) {
                                                    plugin.playersInRedTeams.add(uuid);
                                                } else {
                                                    plugin.playersInGreenTeams.add(uuid);
                                                }
                                            } else if (plugin.players4Teams.contains(uniqueId)) {
                                                int random = new Random().nextInt(4);
                                                if (random == 1) {
                                                    plugin.playersInBlueTeams.add(uuid);
                                                } else if (random == 2) {
                                                    plugin.playersInRedTeams.add(uuid);
                                                } else if (random == 3) {
                                                    plugin.playersInGreenTeams.add(uuid);
                                                } else {
                                                    plugin.playersInCyanTeams.add(uuid);
                                                }
                                            } else if (plugin.players5Teams.contains(uniqueId)) {
                                                int random = new Random().nextInt(5);
                                                if (random == 1) {
                                                    plugin.playersInBlueTeams.add(uuid);
                                                } else if (random == 2) {
                                                    plugin.playersInRedTeams.add(uuid);
                                                } else if (random == 3) {
                                                    plugin.playersInGreenTeams.add(uuid);
                                                } else if (random == 4) {
                                                    plugin.playersInCyanTeams.add(uuid);
                                                } else {
                                                    plugin.playersInYellowTeams.add(uuid);
                                                }
                                            }
                                            // PvE
                                            if (plugin.playersInFoodGame.contains(uniqueId)) {
                                                plugin.playersInFoodGame.add(uuid);
                                            }
                                            if (!plugin.tpPlayersInGameNameLoc.containsValue(theNameOfLoc)) {
                                                player.getInventory().clear();
                                                player.teleport(theLocation);
                                                plugin.playersRandomKit.add(uuid);
                                                plugin.game.randomKitGiver(player);
                                                plugin.kickedPlayers.remove(uuid);
                                                plugin.tpPlayersInGameNameLoc.put(uuid, theNameOfLoc);
                                                plugin.gameSpawnCoolDown.addPlayerToCoolMap(uuid, 5);
                                                groupUUIDs.add(uuid);
                                                plugin.playersInGame.put(GameName, groupUUIDs);
                                                player.sendMessage(NORMAL + "You have been teleported to " + gold + "" + italic + "" + GameName + "" + green + "" + italic + " game on " + gold + "" + italic + "" + theNameOfLoc + "" + green + "" + italic + " location.");
                                                break;
                                            } else {
                                                player.sendMessage(ERROR + "There aren't any spawn points right now. Try again later with " + gold + "" + italic + "/fm rejoin " + red + "" + italic + ".");
                                            }
                                        }
                                    } else {
                                        player.sendMessage(WARN + "Your group is playing a game that does not exist.");
                                        plugin.kickedPlayers.remove(uuid);
                                    }
                                } else {
                                    player.sendMessage(NORMAL + "Your group is not playing anything.");
                                    plugin.kickedPlayers.remove(uuid);
                                }
                            }
                        }
                    }
                }
            } else {
                player.sendMessage(ERROR + "You are not in a group");
                plugin.kickedPlayers.remove(uuid);
            }
        } else {
            player.sendMessage(ERROR + "You have to be kicked to rejoin a game.");
        }
    }
}
