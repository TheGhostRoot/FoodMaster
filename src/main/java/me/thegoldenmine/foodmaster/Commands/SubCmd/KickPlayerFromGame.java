package me.thegoldenmine.foodmaster.Commands.SubCmd;

import me.thegoldenmine.foodmaster.FoodMaster;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class KickPlayerFromGame {
    private final FoodMaster plugin;

    public KickPlayerFromGame(FoodMaster plugin) {
        this.plugin = plugin;
    }

    public void kickPlayerFromGame(String[] args, Player player) {
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
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
        String INFO = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + aqua + "" + bold + "INFO " + darkGray + "" + strikethrough + "-" + aqua + "" + italic + " ";
        String NORMAL = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + darkGray + "" + strikethrough + "-" + green + "" + italic + " ";
        String ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
        // the player is the sender of the command
        // args are for the player to be kicked
        // /fm kick [player] [reason]
        // index   0    1        2
        // num     1    2        3
        StringBuilder message = new StringBuilder();
        if (player != null) {
            String playerKickStr = args[1];
            Player playerKick = Bukkit.getPlayer(playerKickStr);
            String reason = null;
            if (args.length >= 3) {
                int big = args.length - 1;
                for (int i = 2; i <= big; i++) {
                    message.append(" ").append(args[i]);
                }
                reason = String.valueOf(message);
            }
            if (playerKick != null) {
                if (!playerKick.isOnline()) {
                    player.sendMessage(ERROR+""+gold+""+italic+""+playerKick.getName()+""+red+""+italic+" is not Online!");
                    return;
                }
                if (player.equals(playerKick)) {
                    player.sendMessage(ERROR + "You can't kick yourself.");
                    return;
                }
                if (plugin.kickedPlayers.contains(playerKick.getUniqueId())) {
                    player.sendMessage(ERROR + "" + red + "" + italic + playerKick.getName() + red + "" + italic + " is already kicked.");
                } else {
                    if (plugin.playerGroup.isPlayerInGroup(playerKick)) {
                        Set<UUID> groupClone = new HashSet<>(plugin.playerGroup.getPlayersInGroupOfPlayer(playerKick));
                        boolean end = false;
                        for (UUID uuid : groupClone) {
                            Player playerInGroup = Bukkit.getPlayer(uuid);
                            if (playerInGroup != null) {
                                if (groupClone.size() == 2) {
                                    end = true;
                                } else {
                                    UUID kickUUID = playerKick.getUniqueId();
                                    if (plugin.timerTik.bossBar.getPlayers().contains(playerKick)) {
                                        plugin.timerTik.bossBar.removePlayer(playerKick);
                                    }
                                    if (plugin.game.isPlayerInGame(playerKick)) {
                                        plugin.game.removePlayerFromGame(playerKick);
                                    }
                                    if (plugin.waitingLobby.isPlayerInWaitingLobby(playerKick)) {
                                        plugin.waitingLobby.removePlayerFromWaitedLobby(playerKick);
                                    }
                                    // kits
                                    plugin.playersInPotatoKit.remove(kickUUID);
                                    plugin.playersInBreadKit.remove(kickUUID);
                                    plugin.playersInMelonKit.remove(kickUUID);
                                    plugin.playersInCookieKit.remove(kickUUID);
                                    plugin.playersInFishKit.remove(kickUUID);
                                    plugin.playersRandomKit.remove(kickUUID);
                                    // teams
                                    plugin.playersInBlueTeams.remove(kickUUID);
                                    plugin.playersInCyanTeams.remove(kickUUID);
                                    plugin.playersInRedTeams.remove(kickUUID);
                                    plugin.playersInGreenTeams.remove(kickUUID);
                                    plugin.playersInYellowTeams.remove(kickUUID);
                                    plugin.playersInFoodGame.remove(kickUUID);
                                    plugin.playersChoiceFoodGame.remove(kickUUID);
                                    plugin.FoodGameWinner.remove(kickUUID);
                                    plugin.playersRandomTeam.remove(kickUUID);
                                    plugin.players2Teams.remove(kickUUID);
                                    plugin.players3Teams.remove(kickUUID);
                                    plugin.players4Teams.remove(kickUUID);
                                    plugin.players5Teams.remove(kickUUID);
                                    // others
                                    plugin.playersThatChoice5Teams.remove(kickUUID);
                                    plugin.playersThatChoice4Teams.remove(kickUUID);
                                    plugin.playersThatChoice2Teams.remove(kickUUID);
                                    plugin.kickedPlayers.remove(kickUUID);
                                    plugin.playersThatChoice3Teams.remove(kickUUID);
                                    plugin.ReadySystem.remove(kickUUID);
                                    plugin.ReadyPlayers.remove(kickUUID);
                                    plugin.playersInFreeForAll.remove(kickUUID);
                                    plugin.playersChoiceFreeForAll.remove(kickUUID);
                                    plugin.playersChoiceFoodGame.remove(kickUUID);
                                    plugin.playersInFoodGame.remove(kickUUID);
                                    plugin.FoodGameWinner.remove(kickUUID);
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
                                    plugin.kickedPlayers.add(kickUUID);
                                    // Health
                                    AttributeInstance attributeInstance = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                                    assert attributeInstance != null;
                                    attributeInstance.setBaseValue(20);
                                    player.setHealth(20);
                                    player.removePotionEffect(PotionEffectType.SPEED);
                                    player.getInventory().clear();
                                    player.setGameMode(GameMode.SURVIVAL);
                                    if (reason != null) {
                                        playerInGroup.sendMessage(NORMAL + "" + gold + "" + italic + "" + playerKick.getName() + "" + green + "" + italic + " was kicked by " + gold + "" + italic + "" + player.getName() + green + "" + italic + "" + " due to " + gold + "" + italic + "" + reason);
                                        playerKick.sendMessage(NORMAL + "You were kicked by " + gold + "" + italic + "" + player.getName() + green + "" + italic + "" + " due to " + gold + "" + italic + reason);
                                    } else {
                                        playerInGroup.sendMessage(NORMAL + "" + gold + "" + italic + "" + playerKick.getName() + "" + green + "" + italic + " was kicked by " + gold + "" + italic + player.getName() + "" + green + "" + italic + " .");
                                        playerKick.sendMessage(NORMAL + "You were kicked by " + gold + "" + italic + player.getName() + green + "" + italic + " .");
                                    }
                                }
                            }
                        }
                        if (end) {
                            player.sendMessage(INFO + "You are alone in the game.");
                            for (UUID uuid : groupClone) {
                                Player playerInGroup = Bukkit.getPlayer(uuid);
                                if (playerInGroup != null) {
                                    plugin.endTheGame.endTheGameWithStatus(playerInGroup);
                                }
                            }
                            for (UUID uuid : groupClone) {
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
                        Location endLoc = plugin.mainConfig.getLocationMain("end_location");
                        if (endLoc == null) {
                            if (player.hasPermission("foodm.staff")) {
                                player.sendMessage(ERROR + "The end location is not set. Use " + ChatColor.DARK_PURPLE + "" + italic + "/fm set end");
                            } else {
                                player.sendMessage(ERROR + "You can't be teleported.");
                            }
                        } else {
                            player.teleport(endLoc);
                        }
                        playerKick.sendTitle(gold + "" + italic + "You were kicked by", aqua + "" + italic + "" + player.getName(), 2, 80, 2);
                    } else {
                        if (plugin.playerPvE.isPlayerPlayingPvE(playerKick)) {
                            // solo pve
                            playerKick.sendTitle(gold + "" + italic + "You were kicked by", aqua + "" + italic + "" + player.getName(), 2, 80, 2);
                            plugin.endTheGame.endThePvE(playerKick);
                        } else {
                            playerKick.sendMessage(ERROR + "" + gold + "" + italic + "" + playerKick.getName() + "" + red + "" + italic + "" + " is not in group and is not playing PvE.");
                        }
                    }
                }
            }
        }
    }
}
