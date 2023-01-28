package me.thegoldenmine.foodmaster.group.commands;

import me.thegoldenmine.foodmaster.FoodMaster;
import me.thegoldenmine.foodmaster.Items.ItemManager;
import me.thegoldenmine.foodmaster.group.GroupManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class GroupAccept {
    private FoodMaster plugin;

    public GroupAccept(FoodMaster plugin) {
        this.plugin = plugin;
    }

    public void acceptGroupInvite(Player invited, String[] args) {
        // the invited player is the sender of the command/invitation

        GroupManager groupManager = new GroupManager(plugin);

        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor yellow = ChatColor.YELLOW;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor aqua = ChatColor.AQUA;
        ChatColor red = ChatColor.RED;
        String Name;
        if (plugin.mainConfig.getStrMain("name") != null) {
            Name = " " + plugin.mainConfig.getStrMain("name") + " ";
        } else {
            Name = " FoodMaster ";
        }
        String WARN = darkGray + "" + strikethrough + "-" + gold + "" + bold + Name + yellow + "" + bold + "WARN " + darkGray + "" + strikethrough + "-" + yellow + "" + italic + " ";
        String INFO = darkGray + "" + strikethrough + "-" + gold + "" + bold + Name + aqua + "" + bold + "INFO " + darkGray + "" + strikethrough + "-" + aqua + "" + italic + " ";
        String NORMAL = darkGray + "" + strikethrough + "-" + gold + "" + bold + Name + darkGray + "" + strikethrough + "-" + green + "" + italic + " ";
        String ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + Name + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
        if (args.length > 2) {
            Player inviter = Bukkit.getPlayer(args[2]);
            if (!invited.equals(inviter)) {
                if (inviter != null) {
                    UUID uuid = invited.getUniqueId();
                    if (!groupManager.canJoin(invited, inviter)) { return; }
                    groupManager.joinGroup(invited, inviter);
                    if (plugin.playerGroup.isPlayerInGroup(inviter) && plugin.playerGroup.isPlayerInGroup(invited)) {
                        // THEY BOTH ARE IN GROUP
                        // invited will join
                        if (plugin.playerGroup.getPlayersInGroupOfPlayer(invited).contains(inviter.getUniqueId())) {
                            invited.sendMessage(INFO + "You are already in " + gold + "" + italic + "" + inviter.getName() + "" + aqua + "" + italic + "'s group");
                        } else {
                            plugin.playerGroup.PlayerLeaveFromGroup(invited);
                            // key - invited
                            // list - inviters
                            plugin.playerGroup.PlayerJoinInGroup(invited, inviter);
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
                    } else if (plugin.playerGroup.isPlayerInGroup(invited) && !plugin.playerGroup.isPlayerInGroup(inviter)) {
                        // The invited have a group
                        // The inviter is alone
                        if (plugin.playerGroup.getPlayersInGroupOfPlayer(invited).contains(inviter.getUniqueId())) {
                            invited.sendMessage(INFO + "You are already in " + gold + "" + italic + "" + inviter.getName() + "" + aqua + "" + italic + "'s group.");
                        } else {
                            Set<UUID> Group = new HashSet<>();
                            plugin.playerGroup.PlayerLeaveFromGroup(invited);
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
                            invited.sendTitle(gold + "" + italic + "You've joined a group with", aqua + "" + italic + "" + plugin.playerGroup.getPlayerNamesFromGroupString(inviter).replace("\"\"", "").replace("[", "").replace("]", ""), 2, 80, 2);
                        }
                    } else if (plugin.playerGroup.isPlayerInGroup(inviter) && !plugin.playerGroup.isPlayerInGroup(invited)) {
                        // the inviter have a group
                        // the invited is alone
                        // invited joins the inviter's group
                        if (plugin.playerGroup.getPlayersInGroupOfPlayer(inviter).contains(uuid)) {
                            invited.sendMessage(INFO + "You are already in " + gold + "" + italic + "" + inviter.getName() + "" + aqua + "" + italic + "'s group.");
                        } else {
                            if (plugin.playerGroup.getPlayersInGroupOfPlayer(inviter).size() >= plugin.mainConfig.getIntMain("max-players_in_group")) {
                                inviter.sendMessage(WARN + "Your group has reached the player limit.");
                                return;
                            }
                            if (!plugin.invites.containsKey(uuid)) {
                                Set<UUID> justASet = new HashSet<>();
                                justASet.add(inviter.getUniqueId());
                                plugin.invites.put(uuid, justASet);
                            }
                            plugin.playerGroup.PlayerJoinInGroup(invited, inviter);
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
                        // CREATE NEW GROUP
                        // they are not in any group so create one
                        Set<UUID> group2 = new HashSet<>();
                        group2.add(uuid);
                        group2.add(inviter.getUniqueId());
                        invited.sendMessage(NORMAL + "You are now in a group with " + gold + "" + italic + "" + inviter.getName() + "" + green + "" + italic + " .");
                        inviter.sendMessage(NORMAL + "You are now in a group with " + gold + "" + italic + "" + invited.getName() + "" + green + "" + italic + " .");
                        plugin.allGroups.add(group2);
                        if (plugin.playerGroup.getPlayersInGroupOfPlayer(inviter).size() >= plugin.mainConfig.getIntMain("max-players_in_group")) {
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
                    // invited players joins the inviter's group
                    Set<UUID> playersInGroupOfPlayer = new HashSet<>(plugin.playerGroup.getPlayersInGroupOfPlayer(inviter));
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
