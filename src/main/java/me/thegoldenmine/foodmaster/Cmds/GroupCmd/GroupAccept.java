package me.thegoldenmine.foodmaster.Cmds.GroupCmd;

import me.thegoldenmine.foodmaster.FoodMaster;
import me.thegoldenmine.foodmaster.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class GroupAccept {
    private final FoodMaster plugin;

    public GroupAccept(FoodMaster plugin) {
        this.plugin = plugin;
    }

    public void acceptGroupInvite(Player invited, String[] args) {
        // the invited is the sender
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
        if (args.length > 2) {
            Player inviter = Bukkit.getPlayer(args[2]);
            if (!invited.equals(inviter)) {
                if (inviter != null) {
                    UUID uuid = invited.getUniqueId();
                    if (!inviter.isOnline()) {
                        invited.sendMessage(ERROR+""+gold+""+italic+""+ inviter.getName()+""+red+""+italic+" is not online.");
                        return;
                    }
                    if (plugin.isPlayerInGame(inviter)) {
                        invited.sendMessage(ERROR + "" + gold + "" + italic + "" + inviter.getName() + "" + red + "" + italic + " is in-game so you can't join the group.");
                        return;
                    }
                    if (plugin.isPlayerInWaitingLobby(inviter)) {
                        invited.sendMessage(ERROR + "" + gold + "" + italic + "" + inviter.getName() + "" + red + "" + italic + " is in the waiting lobby so you can't join the group.");
                        return;
                    }
                    String s1 = "'s group.";
                    if (plugin.isPlayerInGame(invited)) {
                        invited.sendMessage(ERROR + "You are in-game so you can't join " + gold + "" + italic + "" + inviter.getName() + "" + red + "" + italic + s1);
                        return;
                    }
                    if (plugin.isPlayerInWaitingLobby(invited)) {
                        invited.sendMessage(ERROR + "You are in the waiting lobby so you can't join " + gold + "" + italic + "" + inviter.getName() + "" + red + "" + italic + s1);
                        return;
                    }
                    if (plugin.invites.get(uuid) == null) {
                        return;
                    }
                    String s2 = "You are already in ";
                    if (plugin.isPlayerInGroup(inviter) && plugin.isPlayerInGroup(invited)) {
                        // THEY BOTH ARE IN GROUP
                        // invited will join
                        if (plugin.getPlayersInGroupOfPlayer(invited).contains(inviter.getUniqueId())) {
                            invited.sendMessage(INFO + s2 + gold + "" + italic + "" + inviter.getName() + "" + aqua + "" + italic + s1);
                        } else {
                            plugin.PlayerLeaveFromGroup(invited);
                            // key - invited
                            // list - inviters
                            plugin.PlayerJoinInGroup(invited, inviter);
                            plugin.groupInviteManager.playerCoolDownMap.remove(uuid);
                            Set<UUID> uuids = plugin.invites.get(uuid);
                            if (!uuids.isEmpty()) {
                                uuids.remove(inviter.getUniqueId());
                                plugin.invites.put(uuid, uuids);
                            }
                            if (!invited.getInventory().contains(ItemManager.groupLeave) && !inviter.getInventory().contains(ItemManager.groupLeave)) {
                                invited.getInventory().setItem(8, ItemManager.groupLeave);
                                inviter.getInventory().setItem(8, ItemManager.groupLeave);
                            } else if (!inviter.getInventory().contains(ItemManager.groupLeave) && inviter.getInventory().contains(ItemManager.groupLeave)) {
                                inviter.getInventory().setItem(8, ItemManager.groupLeave);
                            } else if (inviter.getInventory().contains(ItemManager.groupLeave) && !inviter.getInventory().contains(ItemManager.groupLeave)) {
                                invited.getInventory().setItem(8, ItemManager.groupLeave);
                            }
                        }
                    } else if (plugin.isPlayerInGroup(invited) && !plugin.isPlayerInGroup(inviter)) {
                        // The invited have grouped
                        // The inviter is alone
                        // invited go to inviter
                        if (plugin.getPlayersInGroupOfPlayer(invited).contains(inviter.getUniqueId())) {
                            invited.sendMessage(INFO + s2 + gold + "" + italic + "" + inviter.getName() + "'s" + aqua + "" + italic + " group.");
                        } else {
                            Set<UUID> Group = new HashSet<>();
                            plugin.PlayerLeaveFromGroup(invited);
                            Group.add(uuid);
                            Group.add(inviter.getUniqueId());
                            plugin.allGroups.add(Group);
                            inviter.sendMessage(NORMAL + "You're now in a group with " + gold + "" + italic + "" + invited.getName() + "" + green + "" + italic + " .");
                            plugin.groupInviteManager.playerCoolDownMap.remove(uuid);
                            Set<UUID> uuids = plugin.invites.get(uuid);
                            if (!uuids.isEmpty()) {
                                uuids.remove(inviter.getUniqueId());
                                plugin.invites.put(uuid, uuids);
                            }
                            if (!invited.getInventory().contains(ItemManager.groupLeave) && !inviter.getInventory().contains(ItemManager.groupLeave)) {
                                invited.getInventory().setItem(8, ItemManager.groupLeave);
                                inviter.getInventory().setItem(8, ItemManager.groupLeave);
                            } else if (!inviter.getInventory().contains(ItemManager.groupLeave) && inviter.getInventory().contains(ItemManager.groupLeave)) {
                                inviter.getInventory().setItem(8, ItemManager.groupLeave);
                            } else if (inviter.getInventory().contains(ItemManager.groupLeave) && !inviter.getInventory().contains(ItemManager.groupLeave)) {
                                invited.getInventory().setItem(8, ItemManager.groupLeave);
                            }
                            invited.sendTitle(gold + "" + italic + "You've joined a group with", aqua + "" + italic + "" + plugin.getPlayerNamesFromGroupString(inviter).replace("\"\"", "").replace("[", "").replace("]", ""), 2, 80, 2);
                        }
                    } else if (plugin.isPlayerInGroup(inviter) && !plugin.isPlayerInGroup(invited)) {
                        // THIS IS THE ONE
                        // inviter have group
                        // invited joins / alone
                        if (plugin.getPlayersInGroupOfPlayer(inviter).contains(uuid)) {
                            invited.sendMessage(INFO + s2 + gold + "" + italic + "" + inviter.getName() + "'s" + aqua + "" + italic + " group.");
                        } else {
                            if (plugin.getPlayersInGroupOfPlayer(inviter).size() >= plugin.mainConfig.getIntMain("max-players_in_group")) {
                                inviter.sendMessage(WARN + "Your group has reached the player limit.");
                                return;
                            }
                            if (!plugin.invites.containsKey(uuid)) {
                                Set<UUID> justASet = new HashSet<>();
                                justASet.add(inviter.getUniqueId());
                                plugin.invites.put(uuid, justASet);
                            }
                            plugin.PlayerJoinInGroup(invited, inviter);
                            // remove the invited
                            plugin.groupInviteManager.playerCoolDownMap.remove(uuid);
                            Set<UUID> uuids = plugin.invites.get(uuid);
                            if (!uuids.isEmpty()) {
                                uuids.remove(inviter.getUniqueId());
                            }
                            if (!invited.getInventory().contains(ItemManager.groupLeave) && !inviter.getInventory().contains(ItemManager.groupLeave)) {
                                invited.getInventory().setItem(8, ItemManager.groupLeave);
                                inviter.getInventory().setItem(8, ItemManager.groupLeave);
                            } else if (!inviter.getInventory().contains(ItemManager.groupLeave) && inviter.getInventory().contains(ItemManager.groupLeave)) {
                                inviter.getInventory().setItem(8, ItemManager.groupLeave);
                            } else if (inviter.getInventory().contains(ItemManager.groupLeave) && !inviter.getInventory().contains(ItemManager.groupLeave)) {
                                invited.getInventory().setItem(8, ItemManager.groupLeave);
                            }
                        }
                    } else {
                        // NEW GROUP
                        Set<UUID> group2 = new HashSet<>();
                        group2.add(uuid);
                        group2.add(inviter.getUniqueId());
                        invited.sendMessage(NORMAL + "You are now in a group with " + gold + "" + italic + "" + inviter.getName() + "" + green + "" + italic + " .");
                        inviter.sendMessage(NORMAL + "You are now in a group with " + gold + "" + italic + "" + invited.getName() + "" + green + "" + italic + " .");
                        plugin.allGroups.add(group2);
                        if (plugin.getPlayersInGroupOfPlayer(inviter).size() >= plugin.mainConfig.getIntMain("max-players_in_group")) {
                            inviter.sendMessage(WARN + "Your group has reached the player limit.");
                        }
                        plugin.groupInviteManager.playerCoolDownMap.remove(uuid);
                        Set<UUID> uuids = plugin.invites.get(uuid);
                        if (!uuids.isEmpty()) {
                            uuids.remove(inviter.getUniqueId());
                            plugin.invites.put(uuid, uuids);
                        }
                        if (!invited.getInventory().contains(ItemManager.groupLeave) && !inviter.getInventory().contains(ItemManager.groupLeave)) {
                            invited.getInventory().setItem(8, ItemManager.groupLeave);
                            inviter.getInventory().setItem(8, ItemManager.groupLeave);
                        } else if (!inviter.getInventory().contains(ItemManager.groupLeave) && inviter.getInventory().contains(ItemManager.groupLeave)) {
                            inviter.getInventory().setItem(8, ItemManager.groupLeave);
                        } else if (inviter.getInventory().contains(ItemManager.groupLeave) && !inviter.getInventory().contains(ItemManager.groupLeave)) {
                            invited.getInventory().setItem(8, ItemManager.groupLeave);
                        }
                        inviter.sendTitle(gold + "" + italic + "You are now in a group with", aqua + "" + italic + "" + invited.getName(), 1, 80, 1);
                        invited.sendTitle(gold + "" + italic + "You are now in a group with", aqua + "" + italic + "" + inviter.getName(), 1, 80, 1);
                    }
                    // invited joins the inviter's group
                    Set<UUID> playersInGroupOfPlayer = new HashSet<>(plugin.getPlayersInGroupOfPlayer(invited));
                    for (UUID uuid1 : playersInGroupOfPlayer) {
                        if (uuid1 != null && plugin.playAgain.containsKey(uuid1)) {
                            String game = plugin.playAgain.get(uuid1);
                            for (UUID uuid3 : playersInGroupOfPlayer) {
                                if (uuid3 != null) {
                                    plugin.playAgain.put(uuid3, game);
                                }
                            }
                            break;
                        }
                    }
                } else {
                    invited.sendMessage(ERROR + "There are no players with the name " + gold + "" + italic + "" + args[2] + "" + red + "" + italic + " .");
                }
            } else {
                invited.sendMessage(ERROR + "You can't invite yourself.");
            }
        }
    }
}
