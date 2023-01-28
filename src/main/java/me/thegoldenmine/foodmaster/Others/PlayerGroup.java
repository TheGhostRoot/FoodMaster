package me.thegoldenmine.foodmaster.Others;

import me.thegoldenmine.foodmaster.FoodMaster;
import me.thegoldenmine.foodmaster.Items.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.util.*;

public class PlayerGroup {
    private final FoodMaster plugin;

    public PlayerGroup(FoodMaster main) {
        plugin = main;
    }

    public boolean isPlayerInGroup(Player player) {
        for (Set<UUID> group : plugin.allGroups) {
            if (group.contains(player.getUniqueId())) {
                return true;
            }
        }
        return false;
    }

    public Set<UUID> getPlayersInGroupOfPlayer(Player player) {
        for (Set<UUID> group : plugin.allGroups) {
            if (group.contains(player.getUniqueId())) {
                return group;
            }
        }
        return null;
    }

    public String getPlayerNamesFromGroupString(Player player) {
        Set<String> names = new HashSet<>();
        if (player != null && plugin.playerGroup.isPlayerInGroup(player)) {
            for (UUID uuid : Objects.requireNonNull(plugin.playerGroup.getPlayersInGroupOfPlayer(player))) {
                Player players = Bukkit.getPlayer(uuid);
                if (players != null) {
                    names.add(players.getName());
                }
            }
        }
        return names.toString().replace("[", "").replace("]", "").replace("\"\"", "");
    }

    public synchronized void PlayerLeaveFromGroup(Player playerLeaver) {
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
            s = " " + plugin.mainConfig.getStrMain("name") + " ";
        } else {
            s = " FoodMaster ";
        }
        String WARN = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + yellow + "" + bold + "WARN " + darkGray + "" + strikethrough + "-" + yellow + "" + italic + " ";
        String INFO = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + aqua + "" + bold + "INFO " + darkGray + "" + strikethrough + "-" + aqua + "" + italic + " ";
        String NORMAL = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + darkGray + "" + strikethrough + "-" + green + "" + italic + " ";
        String ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
        Location endLoc = plugin.mainConfig.getLocationMain("end_location");
        if (playerLeaver == null) {
            plugin.getLogger().info(ERROR + "An unknown player wants to leave the group.");
            return;
        }
        if (!plugin.playerGroup.isPlayerInGroup(playerLeaver)) {
            playerLeaver.sendMessage(WARN + "You need to be in a group so you can leave.");
            return;
        }
        for (UUID uuid : plugin.playerGroup.getPlayersInGroupOfPlayer(playerLeaver)) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null && !player.equals(playerLeaver)) {
                player.sendMessage(NORMAL + "" + gold + "" + italic + "" + playerLeaver.getName() + "" + green + "" + italic + " has left the group.");
            }
        }
        if (endLoc == null) {
            if (playerLeaver.hasPermission("foodm.staff")) {
                playerLeaver.sendMessage(WARN + "The end location is not set. Use " + ChatColor.DARK_PURPLE + "" + italic + "/fm set end");
            } else {
                playerLeaver.sendMessage(WARN + "One of the locations are not set.");
            }
        }
        playerLeaver.sendTitle(gold + "" + italic + "You left the group", aqua + "" + italic + "" + plugin.playerGroup.getPlayerNamesFromGroupString(playerLeaver).replaceAll("\"\"", "").replace("[", "").replace("]", ""), 2, 80, 2);
        if (plugin.game.isPlayerInGame(playerLeaver) && plugin.playerGroup.getPlayersInGroupOfPlayer(playerLeaver).size() == 2) {
            for (UUID uuid1 : plugin.playerGroup.getPlayersInGroupOfPlayer(playerLeaver)) {
                if (uuid1 != null) {
                    Player playerGroup = Bukkit.getPlayer(uuid1);
                    if (playerGroup != null) {
                        plugin.endTheGame.endTheGameWithStatus(playerGroup);
                    }
                }
            }
            for (UUID uuid1 : plugin.playerGroup.getPlayersInGroupOfPlayer(playerLeaver)) {
                if (uuid1 != null) {
                    plugin.inGameKills.remove(uuid1);
                    plugin.inGameDeaths.remove(uuid1);
                    plugin.playersInBlueTeams.remove(uuid1);
                    plugin.playersInCyanTeams.remove(uuid1);
                    plugin.playersInRedTeams.remove(uuid1);
                    plugin.playersInGreenTeams.remove(uuid1);
                    plugin.playersInYellowTeams.remove(uuid1);
                    plugin.playersRandomTeam.remove(uuid1);
                    plugin.playersChoiceFoodGame.remove(uuid1);
                    plugin.playersInFoodGame.remove(uuid1);
                    plugin.FoodGameWinner.remove(uuid1);
                }
            }
            for (UUID uuid : plugin.winners) {
                if (uuid != null) {
                    Player player = Bukkit.getPlayer(uuid);
                    if (player != null) {
                        plugin.giveOneWinToPlayer.givePlayerWin(player);
                    }
                }
            }
            for (UUID uuid : plugin.losses) {
                if (uuid != null) {
                    Player player = Bukkit.getPlayer(uuid);
                    if (player != null) {
                        plugin.giveOneLoseToPlayer.givePlayerLose(player);
                    }
                }
            }
            plugin.winners.clear();
            plugin.losses.clear();
            plugin.playerGroup.getPlayersInGroupOfPlayer(playerLeaver).remove(playerLeaver.getUniqueId());
            if (playerLeaver.getInventory().contains(ItemManager.playAgain)) {
                playerLeaver.getInventory().remove(ItemManager.playAgain);
            }
            playerLeaver.sendMessage(NORMAL + "You left the group.");
            plugin.playAgain.remove(playerLeaver.getUniqueId());
            List<Set<UUID>> removeListG = new ArrayList<>();
            for (Set<UUID> group : plugin.allGroups) {
                if (group.size() <= 1) {
                    removeListG.add(group);
                }
                if (group.size() == 1) {
                    UUID uuid = group.stream().findFirst().get();
                    Player player = Bukkit.getPlayer(uuid);
                    if (player != null) {
                        if (plugin.waitingLobby.isPlayerInWaitingLobby(player)) {
                            plugin.locOfPlayersInWaitingLobby.remove(new HashSet<>(plugin.playerGroup.getPlayersInGroupOfPlayer(player)));
                            plugin.waitingLobby.removePlayerFromWaitedLobby(player);
                            if (endLoc != null) {
                                player.teleport(endLoc);
                            }
                        }
                        if (plugin.game.isPlayerInGame(player)) {
                            String name1 = plugin.game.getGameName(player);
                            plugin.stillAlive.remove(name1);
                            plugin.game.removePlayerFromGame(player);
                            if (endLoc != null) {
                                player.teleport(endLoc);
                            }
                        }
                        group.remove(player.getUniqueId());
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
                        plugin.playAgain.remove(uuid);
                        plugin.kickedPlayers.remove(uuid);
                        plugin.playersThatChoice3Teams.remove(uuid);
                        plugin.ReadySystem.remove(uuid);
                        plugin.ReadyPlayers.remove(uuid);
                        plugin.playersInFreeForAll.remove(uuid);
                        plugin.lives.remove(uuid);
                        plugin.playersChoiceFreeForAll.remove(uuid);
                        if (player.getInventory().contains(ItemManager.playAgain)) {
                            player.getInventory().remove(ItemManager.playAgain);
                        }
                        plugin.allGroups.removeIf(Set::isEmpty);
                        player.sendMessage(INFO + "It seems like you were alone in the group, so the group has been removed.");
                    }
                }
            }
            removeListG.forEach(plugin.allGroups::remove);
            return;
        }
        if (plugin.waitingLobby.isPlayerInWaitingLobby(playerLeaver)) {
            plugin.locOfPlayersInWaitingLobby.remove(new HashSet<>(plugin.playerGroup.getPlayersInGroupOfPlayer(playerLeaver)));
            plugin.waitingLobby.removePlayerFromWaitedLobby(playerLeaver);
            if (endLoc != null) {
                playerLeaver.teleport(endLoc);
            }
        }
        if (plugin.game.isPlayerInGame(playerLeaver)) {
            String name1 = plugin.game.getGameName(playerLeaver);
            plugin.stillAlive.remove(name1);
            plugin.game.removePlayerFromGame(playerLeaver);
            if (endLoc != null) {
                playerLeaver.teleport(endLoc);
            }
        }
        plugin.playerGroup.getPlayersInGroupOfPlayer(playerLeaver).remove(playerLeaver.getUniqueId());
        plugin.allGroups.removeIf(Set::isEmpty);
        playerLeaver.sendMessage(NORMAL + "You left the group.");
        // remove very small groups
        List<Set<UUID>> removeListG = new ArrayList<>();
        for (Set<UUID> group : plugin.allGroups) {
            if (group.size() <= 1) {
                removeListG.add(group);
            }
            if (group.size() == 1) {
                UUID uuid = group.stream().findFirst().get();
                Player player = Bukkit.getPlayer(uuid);
                if (player != null) {
                    if (plugin.waitingLobby.isPlayerInWaitingLobby(player)) {
                        plugin.locOfPlayersInWaitingLobby.remove(new HashSet<>(plugin.playerGroup.getPlayersInGroupOfPlayer(player)));
                        plugin.waitingLobby.removePlayerFromWaitedLobby(player);
                        if (endLoc != null) {
                            player.teleport(endLoc);
                        }
                    }
                    if (plugin.game.isPlayerInGame(player)) {
                        String name1 = plugin.game.getGameName(player);
                        plugin.stillAlive.remove(name1);
                        plugin.game.removePlayerFromGame(player);
                        if (endLoc != null) {
                            player.teleport(endLoc);
                        }
                    }
                    group.remove(player.getUniqueId());
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
                    plugin.kickedPlayers.remove(uuid);
                    plugin.playersThatChoice3Teams.remove(uuid);
                    plugin.ReadySystem.remove(uuid);
                    plugin.ReadyPlayers.remove(uuid);
                    plugin.playersInFreeForAll.remove(uuid);
                    plugin.lives.remove(uuid);
                    plugin.playersChoiceFreeForAll.remove(uuid);
                    if (player.getInventory().contains(ItemManager.playAgain)) {
                        player.getInventory().remove(ItemManager.playAgain);
                    }
                    plugin.allGroups.removeIf(Set::isEmpty);
                    player.sendMessage(INFO + "It seems like you were alone in the group, so the group has been removed.");
                }
            }
        }
        removeListG.forEach(plugin.allGroups::remove);
        UUID uuid = playerLeaver.getUniqueId();
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
        plugin.kickedPlayers.remove(uuid);
        plugin.playersThatChoice3Teams.remove(uuid);
        plugin.ReadySystem.remove(uuid);
        plugin.ReadyPlayers.remove(uuid);
        plugin.playersInFreeForAll.remove(uuid);
        plugin.lives.remove(uuid);
        plugin.playersChoiceFreeForAll.remove(uuid);
        if (playerLeaver.getInventory().contains(ItemManager.playAgain)) {
            playerLeaver.getInventory().remove(ItemManager.playAgain);
        }
    }

    public synchronized void PlayerJoinInGroup(Player playerJoiner, Player inviter) {
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
        UUID uuid = inviter.getUniqueId();
        UUID uniqueId = playerJoiner.getUniqueId();
        // playerJoiner = invited
        // key - invited
        // value - inviters
        Set<UUID> list = plugin.invites.get(uniqueId);
        if (list != null && list.contains(uuid)) {
            if (plugin.playerGroup.isPlayerInGroup(inviter) && plugin.playerGroup.getPlayersInGroupOfPlayer(inviter).size() >= plugin.mainConfig.getIntMain("max-players_in_group")) {
                inviter.sendMessage(WARN + "You have reached the max player limit in your group");
                playerJoiner.sendMessage(WARN + "" + red + "" + italic + "" + inviter.getName() + "" + yellow + "" + italic + " has reached the max player limit in the group so you can't join.");
                return;
            }
            if (plugin.playerGroup.isPlayerInGroup(playerJoiner) && plugin.playerGroup.getPlayersInGroupOfPlayer(playerJoiner).contains(uniqueId)) {
                plugin.playerGroup.getPlayersInGroupOfPlayer(playerJoiner).remove(playerJoiner.getUniqueId());
                String ok = plugin.playerGroup.getPlayerNamesFromGroupString(inviter).replace("[", "").replace("]", "").replace("\"\"", "");
                String okJ = plugin.playerGroup.getPlayerNamesFromGroupString(playerJoiner).replace("[", "").replace("]", "").replace("\"\"", "");
                playerJoiner.sendMessage(NORMAL + "You have left the group of " + gold + "" + italic + "" + okJ + "" + green + "" + italic + " players and joined the group of " + gold + "" + italic + "" + ok + "" + green + "" + italic + " players.");
            }
            if (plugin.playerGroup.isPlayerInGroup(playerJoiner) && plugin.playerGroup.isPlayerInGroup(inviter)) {
                plugin.playerGroup.PlayerLeaveFromGroup(playerJoiner);
            }
            if (!plugin.playerGroup.isPlayerInGroup(playerJoiner) && plugin.playerGroup.isPlayerInGroup(inviter)) {
                plugin.playerGroup.getPlayersInGroupOfPlayer(inviter).add(uniqueId);
            }
            for (UUID players : Objects.requireNonNull(plugin.playerGroup.getPlayersInGroupOfPlayer(inviter))) {
                Player playersInGroup = Bukkit.getPlayer(players);
                if (playersInGroup != null && playersInGroup != playerJoiner) {
                    playersInGroup.sendMessage(NORMAL + "" + gold + "" + italic + "" + playerJoiner.getName() + "" + green + "" + italic + " has joined the group.");
                }
            }
            if (plugin.playerGroup.isPlayerInGroup(inviter) && plugin.playerGroup.getPlayersInGroupOfPlayer(inviter).size() >= plugin.mainConfig.getIntMain("max-players_in_group")) {
                inviter.sendMessage(NORMAL + "You have reached the max player limit in your group.");
                playerJoiner.sendMessage(NORMAL + "" + gold + "" + italic + "" + inviter.getName() + "" + green + "" + italic + " have reached the max player limit in the group.");
            }
            if (plugin.playerGroup.isPlayerInGroup(playerJoiner) && plugin.playerGroup.getPlayersInGroupOfPlayer(playerJoiner).contains(uuid)) {
                String okJ = plugin.playerGroup.getPlayerNamesFromGroupString(playerJoiner).replace("[", "").replace("]", "").replace("\"\"", "");
                playerJoiner.sendMessage(NORMAL + "You are now in a group with " + gold + "" + italic + "" + okJ + "" + green + "" + italic + " players.");
                playerJoiner.sendTitle(gold + "" + italic + "You have joined a group with", ChatColor.AQUA + "" + ChatColor.ITALIC + "" + plugin.playerGroup.getPlayerNamesFromGroupString(inviter).replaceAll("\"\"", "").replace("[", "").replace("]", ""), 2, 80, 2);
            }
        }
        for (UUID uuid1 : plugin.playerGroup.getPlayersInGroupOfPlayer(playerJoiner)) {
            if (uuid1 != null) {
                plugin.playAgain.remove(uuid1);
            }
        }
    }

    public boolean checkIfTheGroupIsReady(Player player) {
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor yellow = ChatColor.YELLOW;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor aqua = ChatColor.AQUA;
        ChatColor red = ChatColor.RED;
        String s;
        if (plugin.mainConfig.getStrMain("name") != null) {
            s = " " + plugin.mainConfig.getStrMain("name") + " ";
        } else {
            s = " FoodMaster ";
        }
        String WARN = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + yellow + "" + bold + "WARN " + darkGray + "" + strikethrough + "-" + yellow + "" + italic + " ";
        if (player != null) {
            if (plugin.playerPvE.isPlayerChooseToPlayPvE(player) && !plugin.playerGroup.isPlayerInGroup(player)) {
                // playing alone
                return plugin.ReadyPlayers.contains(player.getUniqueId());
            } else if (plugin.playerPvE.isPlayerChooseToPlayPvE(player) && plugin.playerGroup.isPlayerInGroup(player)) {
                Set<UUID> ReadyUUID = new HashSet<>();
                for (UUID uuidOfGroup : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
                    if (uuidOfGroup != null) {
                        Player playerInGroup = Bukkit.getPlayer(uuidOfGroup);
                        if (playerInGroup != null && plugin.ReadyPlayers.contains(playerInGroup.getUniqueId())) {
                            ReadyUUID.add(playerInGroup.getUniqueId());
                        }
                    }
                }
                return ReadyUUID.size() == plugin.playerGroup.getPlayersInGroupOfPlayer(player).size();
            } else if (plugin.playerGroup.isPlayerInGroup(player)) {
                UUID uuid = player.getUniqueId();
                Set<UUID> ReadyUUID = new HashSet<>();
                for (UUID uuidOfGroup : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
                    if (uuidOfGroup != null) {
                        Player playerInGroup = Bukkit.getPlayer(uuidOfGroup);
                        if (playerInGroup != null && plugin.ReadyPlayers.contains(playerInGroup.getUniqueId())) {
                            ReadyUUID.add(playerInGroup.getUniqueId());
                        }
                    }
                }
                if (plugin.deathmatch.isThereEmptyTeam(player)) {
                    Set<UUID> blueTeam = plugin.deathmatch.getGroupPlayersInBlueTeam(player);
                    Set<UUID> redTeam = plugin.deathmatch.getGroupPlayersInRedTeam(player);
                    Set<UUID> greenTeam = plugin.deathmatch.getGroupPlayersInGreenTeam(player);
                    Set<UUID> yellowTeam = plugin.deathmatch.getGroupPlayersInYellowTeam(player);
                    Set<UUID> cyanTeam = plugin.deathmatch.getGroupPlayersInCyanTeam(player);
                    if (plugin.playersThatChoice2Teams.contains(uuid)) {
                        if (blueTeam.isEmpty()) {
                            player.sendMessage(WARN + "Someone has to join the " + ChatColor.BLUE + "" + italic + "Blue team" + yellow + "" + italic + ".");
                            return false;
                        }
                        if (redTeam.isEmpty()) {
                            player.sendMessage(WARN + "Someone has to join the " + red + "" + italic + "Red team" + yellow + "" + italic + ".");
                            return false;
                        }
                    } else if (plugin.playersThatChoice3Teams.contains(uuid)) {
                        if (blueTeam.isEmpty()) {
                            player.sendMessage(WARN + "Someone has to join the " + ChatColor.BLUE + "" + italic + "Blue team" + yellow + "" + italic + ".");
                            return false;
                        }
                        if (redTeam.isEmpty()) {
                            player.sendMessage(WARN + "Someone has to join the " + red + "" + italic + "Red team" + yellow + "" + italic + ".");
                            return false;
                        }
                        if (greenTeam.isEmpty()) {
                            player.sendMessage(WARN + "Someone has to join the " + ChatColor.DARK_GREEN + "" + italic + "Green team" + yellow + "" + italic + ".");
                            return false;
                        }
                    } else if (plugin.playersThatChoice4Teams.contains(uuid)) {
                        if (blueTeam.isEmpty()) {
                            player.sendMessage(WARN + "Someone has to join the " + ChatColor.BLUE + "" + italic + "Blue team" + yellow + "" + italic + ".");
                            return false;
                        }
                        if (redTeam.isEmpty()) {
                            player.sendMessage(WARN + "Someone has to join the " + red + "" + italic + "Red team" + yellow + "" + italic + ".");
                            return false;
                        }
                        if (greenTeam.isEmpty()) {
                            player.sendMessage(WARN + "Someone has to join the " + ChatColor.DARK_GREEN + "" + italic + "Green team" + yellow + "" + italic + ".");
                            return false;
                        }
                        if (cyanTeam.isEmpty()) {
                            player.sendMessage(WARN + "Someone has to join the " + aqua + "" + italic + "Cyan team" + yellow + "" + italic + ".");
                            return false;
                        }
                    } else if (plugin.playersThatChoice5Teams.contains(uuid)) {
                        if (yellowTeam.isEmpty()) {
                            player.sendMessage(WARN + "Someone has to join the " + gold + "" + italic + "Yellow team" + yellow + "" + italic + ".");
                            return false;
                        }
                        if (blueTeam.isEmpty()) {
                            player.sendMessage(WARN + "Someone has to join the " + ChatColor.BLUE + "" + italic + "Blue team" + yellow + "" + italic + ".");
                            return false;
                        }
                        if (redTeam.isEmpty()) {
                            player.sendMessage(WARN + "Someone has to join the " + red + "" + italic + "Red team" + yellow + "" + italic + ".");
                            return false;
                        }
                        if (greenTeam.isEmpty()) {
                            player.sendMessage(WARN + "Someone has to join the " + ChatColor.DARK_GREEN + "" + italic + "Green team" + yellow + "" + italic + ".");
                            return false;
                        }
                        if (cyanTeam.isEmpty()) {
                            player.sendMessage(WARN + "Someone has to join the " + aqua + "" + italic + "Cyan team" + yellow + "" + italic + ".");
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return ReadyUUID.size() == plugin.playerGroup.getPlayersInGroupOfPlayer(player).size();
                }
            }
        }
        return false;
    }

    public synchronized void removeAllPlayerInvites(Player player) {
        if (player != null) {
            UUID uuid = player.getUniqueId();
            Set<UUID> inviters = plugin.invites.get(uuid);
            if (inviters != null && !inviters.isEmpty()) {
                inviters.clear();
                plugin.invites.put(uuid, inviters);
            }
            for (UUID uuidKey : plugin.invites.keySet()) {
                if (uuidKey != null) {
                    Set<UUID> uuidList = plugin.invites.get(uuidKey);
                    if (uuidList != null && !uuidList.isEmpty()) {
                        uuidList.remove(uuid);
                        plugin.invites.put(uuidKey, uuidList);
                    }
                }
            }
        }
    }

    public void giveThePlayerArmor(Player player) {
        if (player == null) {
            return;
        }
        for (UUID uuid : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
            if (uuid != null) {
                Player player1 = Bukkit.getPlayer(uuid);
                if (player1 != null) {
                    PlayerInventory inventory = player1.getInventory();
                    // Hat
                    if (!inventory.contains(ItemManager.BlueHat) || !inventory.contains(ItemManager.RedHat) || !inventory.contains(ItemManager.YellowHat) || !inventory.contains(ItemManager.CyanHat) || !inventory.contains(ItemManager.GreenHat)) {
                        if (plugin.playersInBlueTeams.contains(uuid)) {
                            inventory.setHelmet(ItemManager.BlueHat);
                        } else if (plugin.playersInRedTeams.contains(uuid)) {
                            inventory.setHelmet(ItemManager.RedHat);
                        } else if (plugin.playersInYellowTeams.contains(uuid)) {
                            inventory.setHelmet(ItemManager.YellowHat);
                        } else if (plugin.playersInCyanTeams.contains(uuid)) {
                            inventory.setHelmet(ItemManager.CyanHat);
                        } else if (plugin.playersInGreenTeams.contains(uuid)) {
                            inventory.setHelmet(ItemManager.GreenHat);
                        }
                    }
                    // Chestplate
                    if (!inventory.contains(ItemManager.BlueChest) || !inventory.contains(ItemManager.RedChest) || !inventory.contains(ItemManager.YellowChest) || !inventory.contains(ItemManager.CyanChest) || !inventory.contains(ItemManager.GreenChest)) {
                        if (plugin.playersInBlueTeams.contains(uuid)) {
                            inventory.setChestplate(ItemManager.BlueChest);
                        } else if (plugin.playersInRedTeams.contains(uuid)) {
                            inventory.setChestplate(ItemManager.RedChest);
                        } else if (plugin.playersInYellowTeams.contains(uuid)) {
                            inventory.setChestplate(ItemManager.YellowChest);
                        } else if (plugin.playersInCyanTeams.contains(uuid)) {
                            inventory.setChestplate(ItemManager.CyanChest);
                        } else if (plugin.playersInGreenTeams.contains(uuid)) {
                            inventory.setChestplate(ItemManager.GreenChest);
                        }
                    }
                    // Legs
                    if (!inventory.contains(ItemManager.BlueLeg) || !inventory.contains(ItemManager.RedLeg) || !inventory.contains(ItemManager.YellowLeg) || !inventory.contains(ItemManager.CyanLeg) || !inventory.contains(ItemManager.GreenLeg)) {
                        if (plugin.playersInBlueTeams.contains(uuid)) {
                            inventory.setLeggings(ItemManager.BlueLeg);
                        } else if (plugin.playersInRedTeams.contains(uuid)) {
                            inventory.setLeggings(ItemManager.RedLeg);
                        } else if (plugin.playersInYellowTeams.contains(uuid)) {
                            inventory.setLeggings(ItemManager.YellowLeg);
                        } else if (plugin.playersInCyanTeams.contains(uuid)) {
                            inventory.setLeggings(ItemManager.CyanLeg);
                        } else if (plugin.playersInGreenTeams.contains(uuid)) {
                            inventory.setLeggings(ItemManager.GreenLeg);
                        }
                    }
                    // Boots
                    if (!inventory.contains(ItemManager.BlueBoots) || !inventory.contains(ItemManager.RedBoots) || !inventory.contains(ItemManager.YellowBoots) || !inventory.contains(ItemManager.CyanBoots) || !inventory.contains(ItemManager.GreenBoots)) {
                        if (plugin.playersInBlueTeams.contains(uuid)) {
                            inventory.setBoots(ItemManager.BlueBoots);
                        } else if (plugin.playersInRedTeams.contains(uuid)) {
                            inventory.setBoots(ItemManager.RedBoots);
                        } else if (plugin.playersInYellowTeams.contains(uuid)) {
                            inventory.setBoots(ItemManager.YellowBoots);
                        } else if (plugin.playersInCyanTeams.contains(uuid)) {
                            inventory.setBoots(ItemManager.CyanBoots);
                        } else if (plugin.playersInGreenTeams.contains(uuid)) {
                            inventory.setBoots(ItemManager.GreenBoots);
                        }
                    }
                }
            }
        }
    }

    public boolean isPlayerChosenKit(Player player) {
        UUID uuid = player.getUniqueId();
        if (plugin.playersInPotatoKit.contains(uuid)) {
            return true;
        } else if (plugin.playersInBreadKit.contains(uuid)) {
            return true;
        } else if (plugin.playersInCookieKit.contains(uuid)) {
            return true;
        } else if (plugin.playersInMelonKit.contains(uuid)) {
            return true;
        } else if (plugin.playersInFishKit.contains(uuid)) {
            return true;
        } else return plugin.playersInBeefKit.contains(uuid);
    }
}
