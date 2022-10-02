package me.thegoldenmine.foodmaster.Commands.GroupCmd;

import me.thegoldenmine.foodmaster.FoodMaster;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public class GroupKick {
    private final FoodMaster plugin;

    public GroupKick(FoodMaster plugin) {
        this.plugin = plugin;
    }

    public void PlayerKickGroup(Player player, String[] args) {
        // the player is the one that wants to kick
        // /fw group kick [player name] [reason]
        //      0     1      2            3    index
        //      1     2      3            4   num
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor yellow = ChatColor.YELLOW;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor aqua = ChatColor.AQUA;
        ChatColor red = ChatColor.RED;
        String s;
        if (plugin.mainConfig.getStrMain("name") != null) {
            s = " "+plugin.mainConfig.getStrMain("name")+" ";
        } else {
            s = " FoodMaster ";
        }
        String WARN = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + yellow + "" + bold + "WARN " + darkGray + "" + strikethrough + "-" + yellow + "" + italic + " ";
        String INFO = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + aqua + "" + bold + "INFO " + darkGray + "" + strikethrough + "-" + aqua + "" + italic + " ";
        String NORMAL = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + darkGray + "" + strikethrough + "-" + green + "" + italic + " ";
        String ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
        StringBuilder message = new StringBuilder();
        if (args.length >= 3) {
            String playerName = args[2];
            String reason = null;
            if (args.length >= 4) {
                // null
                int big = args.length - 1;
                for (int i = 3; i <= big; i++) {
                    message.append(" ").append(args[i]);
                }
                reason = String.valueOf(message);
            }
            if (playerName != null) {
                Player playerKick = Bukkit.getPlayer(playerName);
                if (playerKick != null) {
                    if (playerKick.getUniqueId() == player.getUniqueId()) {
                        player.sendMessage(ERROR + "You can't kick yourself.");
                        return;
                    }
                    if (!plugin.playerGroup.isPlayerInGroup(player)) {
                        player.sendMessage(ERROR + "You have to be in a group.");
                        return;
                    }
                    if (!playerKick.isOnline()) {
                        player.sendMessage(ERROR + "" + gold + "" + italic + "" + playerKick.getName() + "" + red + "" + italic + " is not Online!");
                        return;
                    }
                    Set<UUID> playersInGroupOfPlayer = plugin.playerGroup.getPlayersInGroupOfPlayer(playerKick);
                    if (!plugin.playerGroup.isPlayerInGroup(playerKick)) {
                        player.sendMessage(ERROR + "" + playerKick.getName() + "" + red + "" + italic + " is not in group.");
                        return;
                    } else if (plugin.playerGroup.isPlayerInGroup(playerKick) && !playersInGroupOfPlayer.contains(player.getUniqueId())) {
                        player.sendMessage(ERROR + "" + playerKick.getName() + "" + red + "" + italic + " is not in your group.");
                        return;
                    }
                    String s1 = " . Reason: ";
                    Set<UUID> playersInG = new HashSet<>(playersInGroupOfPlayer);
                    if (playersInG.size() - 1 == 1) {
                        if (plugin.playerPvE.isPlayerPlayingPvE(playerKick)) {
                            plugin.endTheGame.endThePvE(playerKick);
                        } else {
                            for (UUID uuids : playersInG) {
                                if (uuids != null) {
                                    Player playersInGroup = Bukkit.getPlayer(uuids);
                                    if (playersInGroup != null) {
                                        plugin.endTheGame.endTheGameWithStatus(playersInGroup);
                                    }
                                }
                            }
                            for (UUID uuid : playersInG) {
                                if (uuid != null) {
                                    plugin.inGameKills.remove(uuid);
                                    plugin.inGameDeaths.remove(uuid);
                                    plugin.playersInBlueTeams.remove(uuid);
                                    plugin.playersInCyanTeams.remove(uuid);
                                    plugin.playersInRedTeams.remove(uuid);
                                    plugin.playersInGreenTeams.remove(uuid);
                                    plugin.playersInYellowTeams.remove(uuid);
                                    plugin.playersRandomTeam.remove(uuid);
                                    plugin.playersChoiceFoodGame.remove(uuid);
                                    plugin.playersInFoodGame.remove(uuid);
                                    plugin.FoodGameWinner.remove(uuid);
                                }
                            }
                            for (UUID uuid : plugin.winners) {
                                if (uuid != null) {
                                    Player player11 = Bukkit.getPlayer(uuid);
                                    if (player11 != null) {
                                        plugin.giveOneWinToPlayer.givePlayerWin(player11);
                                    }
                                }
                            }
                            for (UUID uuid : plugin.losses) {
                                if (uuid != null) {
                                    Player player11 = Bukkit.getPlayer(uuid);
                                    if (player11 != null) {
                                        plugin.giveOneLoseToPlayer.givePlayerLose(player11);
                                    }
                                }
                            }
                            plugin.winners.clear();
                            plugin.losses.clear();
                        }
                    }
                    String playersList = plugin.playerGroup.getPlayerNamesFromGroupString(playerKick).replace("[", "").replace("]", "").replace("\"\"", "");
                    playersInGroupOfPlayer.remove(playerKick.getUniqueId());
                    plugin.allGroups.removeIf(Set::isEmpty);
                    // remove very small groups
                    List<Set<UUID>> removeListG = new ArrayList<>();
                    for (Set<UUID> group : plugin.allGroups) {
                        if (group.size() <= 1) {
                            removeListG.add(group);
                        }
                        if (group.size() == 1) {
                            UUID uuid = group.stream().findFirst().get();
                            Player players = Bukkit.getPlayer(uuid);
                            if (players != null) {
                                group.remove(players.getUniqueId());
                                plugin.allGroups.removeIf(Set::isEmpty);
                                players.sendMessage(INFO + "You have been removed from the group, because there is no one else in the group.");
                            }
                        }
                    }
                    removeListG.forEach(plugin.allGroups::remove);
                    Location loc = plugin.mainConfig.getLocationMain("end_location");
                    String s2 = " players by ";
                    for (UUID uuid : playersInGroupOfPlayer) {
                        Player players = Bukkit.getPlayer(uuid);
                        if (players != null && !players.equals(playerKick) && !players.equals(player)) {
                            if (reason != null) {
                                players.sendMessage(NORMAL + "" + gold + "" + italic + "" + playerKick.getName() + "" + green + "" + italic + " has been kicked from the group by " + gold + "" + italic + "" + player.getName() + "" + green + "" + italic + s1 + yellow + "" + italic + reason);
                            } else {
                                players.sendMessage(NORMAL + "" + gold + "" + italic + "" + playerKick.getName() + "" + green + "" + italic + " has been kicked from the group by " + gold + "" + italic + "" + player.getName() + "" + green + "" + italic + " .");
                            }
                        }
                    }
                    if (plugin.game.isPlayerInGame(playerKick) || plugin.waitingLobby.isPlayerInWaitingLobby(playerKick)) {
                        if (loc != null) {
                            UUID uuid = playerKick.getUniqueId();
                            String name = plugin.game.getGameName(playerKick);
                            plugin.stillAlive.get(name).remove(playerKick.getUniqueId());
                            if (plugin.stillAlive.get(name).isEmpty()) {
                                plugin.stillAlive.remove(name);
                            }
                            plugin.game.removePlayerFromGame(playerKick);
                            plugin.waitingLobby.removePlayerFromWaitedLobby(playerKick);
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
                            plugin.playersInGreenTeams.remove(uuid);
                            plugin.playersInYellowTeams.remove(uuid);
                            plugin.playersRandomTeam.remove(uuid);
                            // others
                            plugin.playersThatChoice5Teams.remove(uuid);
                            plugin.playersThatChoice4Teams.remove(uuid);
                            plugin.playersThatChoice2Teams.remove(uuid);
                            plugin.playersThatChoice3Teams.remove(uuid);
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
                            playerKick.getInventory().clear();
                            playerKick.teleport(loc);
                            if (reason != null) {
                                playerKick.sendMessage(NORMAL + "You are kick from the group of " + gold + "" + italic + "" + playersList + "" + green + "" + italic + s2 + gold + "" + italic + "" + player.getName() + "" + green + "" + italic + "" + s1 + yellow + "" + italic + "" + reason);
                            } else {
                                playerKick.sendMessage(NORMAL + "You are kick from the group of " + gold + "" + italic + "" + playersList + "" + green + "" + italic + s2 + gold + "" + italic + "" + player.getName() + "" + green + "" + italic + "" + " .");
                            }
                        } else {
                            if (player.hasPermission("foodm.staff")) {
                                player.sendMessage(WARN + "The end location has not been set. Use this command to set the end location " + red + "" + bold + "-> " + darkGray + "" + italic + "/fm set end");
                            } else {
                                player.sendMessage(WARN + "One of the locations has not been set.");
                            }
                        }
                    } else {
                        playerKick.getInventory().clear();
                        if (loc != null) {
                            playerKick.teleport(loc);
                        } else {
                            if (player.hasPermission("foodm.staff")) {
                                player.sendMessage(WARN + "The end location has not been set. Use this command to set the end location " + red + "" + bold + "-> " + darkGray + "" + italic + "/fm set end");
                            } else {
                                player.sendMessage(WARN + "One of the locations has not been set.");
                            }
                            return;
                        }
                        if (reason != null) {
                            player.sendMessage(NORMAL + "You have kicked " + gold + "" + italic + "" + playerKick.getName() + "" + green + "" + italic + " from your group. Reason: " + yellow + "" + italic + "" + reason);
                            playerKick.sendMessage(NORMAL + "You have been kicked from the group of " + gold + "" + italic + "" + playersList + "" + green + "" + italic + s2 + gold + "" + italic + "" + player.getName() + "" + green + "" + italic + "" + s1 + yellow + "" + italic + "" + reason);
                        } else {
                            player.sendMessage(NORMAL + "You have kicked " + gold + "" + italic + "" + playerKick.getName() + "" + green + "" + italic + " from your group.");
                            playerKick.sendMessage(NORMAL + "You have been kicked from the group of " + gold + "" + italic + "" + playersList + "" + green + "" + italic + s2 + gold + "" + italic + "" + player.getName() + "" + green + "" + italic + " .");
                        }
                    }
                    playerKick.sendTitle(gold + "" + italic + "You were kicked from your group by", aqua + "" + italic + "" + player.getName(), 2, 80, 2);
                    player.sendTitle(gold + "" + italic + "You just kicked", aqua + "" + italic + "" + playerKick.getName(), 2, 80, 2);
                } else {
                    player.sendMessage(ERROR + "" + gold + "" + italic + "" + playerName + "" + red + "" + italic + " can't be found.");
                }
            }
        }
    }
}
