package me.thegoldenmine.foodmaster.Cmds.GroupCmd;

import me.thegoldenmine.foodmaster.FoodMaster;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.w3c.dom.Text;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class GroupInvite {
    private final FoodMaster plugin;

    public GroupInvite(FoodMaster plugin) {
        this.plugin = plugin;
    }

    public void PlayerGroupInvite(Player player, String[] args) {
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
        if (args.length >= 3) {
            // /fm group invite playerName
            //      0      1        2      index
            //      1      2        3       num
            Player playerInv = Bukkit.getPlayer(args[2]);
            // player - sender - inviter - value
            // playerInv - accept - invited - key
            if (playerInv != null) {
                if (player != null) {
                    UUID playerInvUniqueId = playerInv.getUniqueId();
                    if (plugin.isPlayerInGroup(player) && plugin.getPlayersInGroupOfPlayer(player).contains(playerInvUniqueId)) {
                        player.sendMessage(ERROR + "You are already in " + gold + "" + italic + "" + playerInv.getName() + "'s" + red + "" + italic + " group.");
                        return;
                    }
                    if (plugin.commandCoolDown.isPlayerInCoolDown(player)) {
                        player.sendMessage(WARN + "Slow down. You have " + gold + "" + italic + "" + plugin.commandCoolDown.getTime(player) + "" + yellow + "" + italic + " seconds left.");
                    } else {
                        if (plugin.groupInviteManager.isPlayerInvited(playerInv)) {
                            player.sendMessage(WARN + "You have already invited " + gold + "" + italic + "" + playerInv.getName() + "" + yellow + "" + italic + " .");
                        } else {
                            if (playerInv.equals(player)) {
                                player.sendMessage(ERROR + "You can't invite yourself.");
                            } else {
                                if (plugin.isPlayerInGame(player)) {
                                    player.sendMessage(ERROR + "You can't send invites during a game.");
                                    return;
                                }
                                if (plugin.isPlayerInWaitingLobby(player)) {
                                    player.sendMessage(ERROR + "You can't send invites while waiting in a waiting lobby.");
                                    return;
                                }
                                if (plugin.isPlayerInGame(playerInv)) {
                                    player.sendMessage(ERROR + "" + gold + "" + italic + "" + playerInv.getName() + "" + red + "" + italic + " is in-game. Players that are in the game can't be invited.");
                                    return;
                                }
                                if (plugin.isPlayerInWaitingLobby(playerInv)) {
                                    player.sendMessage(ERROR + "" + gold + "" + italic + "" + playerInv.getName() + "" + red + "" + italic + " is in a waiting lobby. Players that are in the waiting lobby can't be invited.");
                                    return;
                                }
                                if (plugin.mainConfig.getIntMain("max-players_in_group") < 2) {
                                    if (player.hasPermission("foodm.staff")) {
                                        player.sendMessage(ERROR + "You have to set the " + darkGray + "" + italic + "max-players-in-group" + red + "" + italic + " to more than " + gold + "" + italic + "1" + red + "" + italic + " to invite players.");
                                    } else {
                                        player.sendMessage(ERROR + "The max amount of players allowed in one group isn't set correctly.");
                                    }
                                    return;
                                }
                                UUID playerUniqueId = player.getUniqueId();
                                String s1 = " minutes to accept the invite.";
                                if (plugin.isPlayerInGroup(player)) {
                                    if (plugin.getPlayersInGroupOfPlayer(player).size() >= plugin.mainConfig.getIntMain("max-players_in_group")) {
                                        player.sendMessage(WARN + "Your group has reached the player limit.");
                                    } else {
                                        if (plugin.invites.get(playerInvUniqueId) == null) {
                                            Set<UUID> uuids = new HashSet<>();
                                            uuids.add(playerUniqueId);
                                            plugin.invites.put(playerInvUniqueId, uuids);
                                        } else {
                                            Set<UUID> alreadyPlayers = plugin.invites.get(playerInvUniqueId);
                                            alreadyPlayers.add(playerUniqueId);
                                            plugin.invites.put(playerInvUniqueId, alreadyPlayers);
                                        }
                                        plugin.groupInviteManager.addPlayerToCoolMap(playerInv, 5);
                                        plugin.commandCoolDown.addPlayerToCoolMap(player, 5);
                                        player.sendMessage(NORMAL + "You have successfully invited " + gold + "" + italic + "" + playerInv.getName() + "" + green + "" + italic + " to your group. " + gold + "" + italic + "" + playerInv.getName() + "" + green + "" + italic + " has " + gold + "" + italic + "" + plugin.groupInviteManager.getTime(playerInv) + "" + green + "" + italic + s1);
                                        playerInv.sendMessage(NORMAL + "You have been invited by " + gold + "" + italic + "" + player.getName() + "" + green + "" + italic + " in their group to play FoodMaster. You have " + gold + "" + italic + plugin.groupInviteManager.getTime(playerInv) + "" + green + "" + italic + s1+" Use this command to accept: "+gold+"/fm group accept "+player.getName());
                                    }
                                } else {
                                    if (plugin.invites.get(playerInvUniqueId) == null) {
                                        Set<UUID> uuids = new HashSet<>();
                                        uuids.add(playerUniqueId);
                                        plugin.invites.put(playerInvUniqueId, uuids);
                                    } else {
                                        Set<UUID> alreadyPlayers = plugin.invites.get(playerInvUniqueId);
                                        alreadyPlayers.add(playerUniqueId);
                                        plugin.invites.put(playerInvUniqueId, alreadyPlayers);
                                    }
                                    plugin.groupInviteManager.addPlayerToCoolMap(playerInv, 5);
                                    plugin.commandCoolDown.addPlayerToCoolMap(player, 5);
                                    player.sendMessage(NORMAL + "You have successfully invited " + gold + "" + italic + "" + playerInv.getName() + "" + green + "" + italic + " to your group. " + gold + "" + italic + "" + playerInv.getName() + "" + green + "" + italic + " has " + gold + "" + italic + "" + plugin.groupInviteManager.getTime(playerInv) + "" + green + "" + italic + s1);
                                    playerInv.sendMessage(NORMAL + "You have been invited by " + gold + "" + italic + "" + player.getName() + "" + green + "" + italic + " in their group to play FoodMaster. You have " + gold + "" + italic + "" + plugin.groupInviteManager.getTime(playerInv) + "" + green + "" + italic + s1+" Use this command to accept: "+gold+"/fm group accept "+player.getName());
                                }
                            }
                        }
                    }
                }
            } else {
                player.sendMessage(ERROR + "An unknown player was given for invitation by " + gold + "" + italic + "" + player.getName() + "" + red + "" + italic + " .");
            }
        } else {
            player.sendMessage(INFO + "" + ChatColor.DARK_PURPLE + "" + italic + "/fm group invite " + gold + "" + italic + "[player name] " + red + "" + bold + ">- " + darkGray + "" + italic + "Invites the player whose name you have entered. The player that you invite will have 5 minutes to accept the invite.");
        }
    }
}
