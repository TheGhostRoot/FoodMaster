package me.thegoldenmine.foodmaster.Commands.SubCmd;

import me.thegoldenmine.foodmaster.FoodMaster;
import me.thegoldenmine.foodmaster.Items.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public class StartCommand {
    private final FoodMaster plugin;
    private String cmdArgs;

    public StartCommand(FoodMaster plugin) {
        this.plugin = plugin;
    }

    // Finished!
    public Location findFreeWaitLobby(Player player) {
        // "->wait-location"
        boolean isNull = false;
        for (String name : plugin.playersInWaitingLobby.keySet()) {
            // key = name | value = amount of players in that waiting lobby
            if (name != null) {
                if (plugin.mainConfig.getIntWaitLobby("max-players_in_waiting_lobby") == 0) {
                    return null;
                } else {
                    if (plugin.playersInWaitingLobby.get(name) == null) {
                        // new list
                        Set<UUID> empty = new HashSet<>();
                        plugin.playersInWaitingLobby.put(name, empty);
                    } else {
                        if (plugin.playersInWaitingLobby.get(name).size() <= plugin.mainConfig.getIntWaitLobby("max-players_in_waiting_lobby")) {
                            int freeSpace = plugin.mainConfig.getIntWaitLobby("max-players_in_waiting_lobby") - plugin.playersInWaitingLobby.get(name).size();
                            Location location = plugin.mainConfig.getLocationWaitLobby(name + "->wait-location");
                            if (cmdArgs.contains("pve")) {
                                if (plugin.playerGroup.isPlayerInGroup(player) && freeSpace >= Objects.requireNonNull(plugin.playerGroup.getPlayersInGroupOfPlayer(player)).size()) {
                                    return location;
                                } else {
                                    if (freeSpace >= 1) {
                                        return location;
                                    }
                                }
                            } else if (plugin.playerGroup.isPlayerInGroup(player) && freeSpace >= Objects.requireNonNull(plugin.playerGroup.getPlayersInGroupOfPlayer(player)).size()) {
                                return location;
                            }
                        }
                    }
                }
            } else {
                isNull = true;
            }
        }
        if (isNull) {
            plugin.playersInWaitingLobby.remove(null);
        }
        return null;
    }

    public void mainStart(Player player, String[] args) {
        if (!player.isOnline()) {
            return;
        }
        cmdArgs = Arrays.toString(args);
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
        String ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
        // Finished!
        if (args.length <= 2) {
            String message = INFO + "" + ChatColor.DARK_PURPLE + "" + italic + "/fm start " + gold + "" + italic + "[free-for-all/team-deathmatch] " + red + "" + bold + ">- " + darkGray + "" + italic + "You must choose what game you want.";
            if (args.length == 2) {
                if (args[1].equalsIgnoreCase("free-for-all")) {
                    // /fw start free-for-all-battle
                    //      0               1     index
                    plugin.startFreeForAll.startFreeForAll(player, cmdArgs);
                } else if (args[1].equalsIgnoreCase("food-game")) {
                    plugin.startFoodGame.startFoodGame(player, cmdArgs);
                } else {
                    player.sendMessage(message);
                }
            } else {
                player.sendMessage(message);
            }
        } else if (args[1].equalsIgnoreCase("team-deathmatch")) {
            // /fw start food-war [teams]
            //      1      2        3
            if (args.length == 3) {
                try {
                    int teams = Integer.parseInt(args[2]);
                    if (teams == 0) {
                        player.sendMessage(WARN + "You need enter the amount of teams to play this game. You need at least " + gold + "" + italic + "2" + yellow + "" + italic + "!");
                    } else {
                        if (plugin.playerGroup.isPlayerInGroup(player)) {
                            if (teams >= 2 && teams <= 5) {
                                // Food War
                                boolean b = teams == 3 || teams == 4 || teams == 5;
                                boolean b1 = teams == 4 || teams == 5;
                                int size = plugin.playerGroup.getPlayersInGroupOfPlayer(player).size();
                                boolean warn = b && size == 2 || b1 && size == 3 || teams == 5 && size == 4;
                                if (warn) {
                                    player.sendMessage(WARN + "You need more players.");
                                    return;
                                }
                                startFoodWar(player, teams);
                            } else {
                                player.sendMessage(ERROR + "You need at least " + gold + "" + italic + "2" + yellow + "" + italic + " teams! " + gold + "" + italic + "5" + yellow + "" + italic + " are max.");
                            }
                        } else {
                            player.sendMessage(ERROR + "You have to be in a group to start this game.");
                        }
                    }
                } catch (Exception e) {
                    player.sendMessage(ERROR + "You must provide a number. No letters are allowed!");
                }
            } else {
                player.sendMessage(INFO + "" + ChatColor.DARK_PURPLE + "" + italic + "/fm start team-deathmatch " + gold + "" + italic + "[Amount of teams] " + red + "" + bold + ">- " + green + "" + italic + "You need to enter the amount of teams ( 2 - 5 ).");
            }
        } else if (args[1].equalsIgnoreCase("pve")) {
            // /fm start pve boss
            //       0    1   2    index
            //       1    2   3      num
            String boss = args[2];
            if (!player.hasPermission("foodm.pve")) {
                player.sendMessage(INFO + "You don't have " + gold + "" + italic + "foodm.pve" + aqua + "" + italic + " permission.");
            } else {
                if (boss != null) {
                    if (boss.equalsIgnoreCase("Zombie")) {
                        plugin.startPvEZombie.startPvEZombie(player, cmdArgs);
                    } else if (boss.equalsIgnoreCase("Skeleton")) {
                        plugin.startPvESkeleton.startPvESkeleton(player, cmdArgs);
                    } else if (boss.equalsIgnoreCase("Spider")) {
                        plugin.startPvESpider.startPvESpider(player, cmdArgs);
                    } else if (boss.equalsIgnoreCase("Enderman")) {
                        plugin.startPvEEnderman.startPvEEnderman(player, cmdArgs);
                    } else if (boss.equalsIgnoreCase("Slime")) {
                        plugin.startPvESlime.startPvESlime(player, cmdArgs);
                    }
                } else {
                    player.sendMessage(INFO + "" + ChatColor.DARK_PURPLE + "" + italic + "/fm start pve " + gold + "" + italic + "[boss] " + red + "" + bold + ">- " + darkGray + "" + italic + "You need to enter the BOSS.");
                }
            }
        } else {
            player.sendMessage(INFO + "" + ChatColor.DARK_PURPLE + "" + italic + "/fm start [team-deathmatch/pve] " + gold + "" + italic + "[teams/boss] " + red + "" + bold + ">- " + darkGray + "" + italic + "You need to enter the amount of teams ( 2 - 5 ) or a BOSS.");
        }
    }

    public void startFoodWar(Player player, int teams) {
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor yellow = ChatColor.YELLOW;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor aqua = ChatColor.AQUA;
        ChatColor red = ChatColor.RED;
        String s2;
        if (plugin.mainConfig.getStrMain("name") != null) {
            s2 = " " + plugin.mainConfig.getStrMain("name") + " ";
        } else {
            s2 = " FoodMaster ";
        }
        String INFO = darkGray + "" + strikethrough + "-" + gold + "" + bold + s2 + aqua + "" + bold + "INFO " + darkGray + "" + strikethrough + "-" + aqua + "" + italic + " ";
        String NORMAL = darkGray + "" + strikethrough + "-" + gold + "" + bold + s2 + darkGray + "" + strikethrough + "-" + green + "" + italic + " ";
        String ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + s2 + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
        UUID uuid = player.getUniqueId();
        String s = "You are in a game. You can't start another one.";
        String s1 = "You have been teleported to ";
        if (!plugin.playerGroup.isPlayerInGroup(player)) {
            player.sendMessage(ERROR + "You are not in group.");
            return;
        }
        Set<UUID> group = plugin.playerGroup.getPlayersInGroupOfPlayer(player);
        if (plugin.playersChoiceFoodGame.contains(uuid)) {
            player.sendMessage(ERROR + "You have already started " + gold + "" + italic + "Food Game" + yellow + "" + italic + ".");
            return;
        }
        if (plugin.playersChoiceFreeForAll.contains(uuid)) {
            player.sendMessage(ERROR + "You have already started " + gold + "" + italic + "Free For All" + yellow + "" + italic + ".");
            return;
        }
        if (plugin.playerPvE.isPlayerChooseToPlayPvE(player)) {
            player.sendMessage(ERROR + "You have already started " + gold + "" + italic + "PvE" + yellow + "" + italic + " .");
            return;
        }
        if (plugin.playersThatChoice2Teams.contains(uuid) || plugin.playersThatChoice3Teams.contains(uuid) || plugin.playersThatChoice4Teams.contains(uuid) || plugin.playersThatChoice5Teams.contains(uuid)) {
            player.sendMessage(ERROR + "You have already started " + gold + "" + italic + "Team Deathmatch" + yellow + "" + italic + ".");
            return;
        }
        if (plugin.game.isPlayerInGame(player)) {
            player.sendMessage(ERROR + "You are in game.");
            return;
        }
        Location loc = findFreeWaitLobby(player);
        if (loc == null) {
            player.sendMessage(ERROR + "There are no available waiting lobbies right now.");
            return;
        }
        if (teams == 2) {
            if (plugin.players2Teams.contains(uuid)) {
                player.sendMessage(ERROR + "You are in a game. You can't start another one.");
                return;
            }
            String waitingLobbyName = plugin.waitingLobby.getWaitingLobbyNameByLoc(loc);
            for (UUID uuids : group) {
                Player players = Bukkit.getPlayer(uuids);
                if (players != null) {
                    plugin.playersThatChoice2Teams.add(uuids);
                    if (!players.equals(player)) {
                        players.sendMessage(INFO + "" + gold + "" + italic + "" + player.getName() + "" + green + "" + italic + " has started "+gold+""+italic+"Team Deathmatch"+green+""+italic+" with " + gold + "" + italic + "2" + green + "" + italic + " teams.");
                    }
                    players.setGameMode(GameMode.SURVIVAL);
                    players.getInventory().clear();
                    if (plugin.playersInWaitingLobby.get(waitingLobbyName) == null || plugin.playersInWaitingLobby.get(waitingLobbyName).isEmpty()) {
                        plugin.playersInWaitingLobby.put(waitingLobbyName, new HashSet<>(group));
                    } else {
                        Set<UUID> NewPlayers = new HashSet<>(plugin.playersInWaitingLobby.get(waitingLobbyName));
                        NewPlayers.addAll(new HashSet<>(group));
                        plugin.playersInWaitingLobby.put(waitingLobbyName, NewPlayers);
                    }
                    players.teleport(loc);
                    players.sendMessage(NORMAL + s1 + gold + "" + italic + "" + waitingLobbyName + "" + green + "" + italic + " waiting lobby. Choose your kit and get ready to fight.");
                    plugin.locOfPlayersInWaitingLobby.put(new HashSet<>(group), loc);
                    players.getInventory().addItem(ItemManager.kitChooser);
                    players.getInventory().addItem(ItemManager.teams);
                    if (players.getInventory().contains(ItemManager.groupLeave)) {
                        players.getInventory().remove(ItemManager.groupLeave);
                    }
                    plugin.playerGroup.removeAllPlayerInvites(players);
                    players.getInventory().addItem(ItemManager.isReady);
                }
            }
            for (UUID uuids : plugin.playersInWaitingLobby.get(waitingLobbyName)) {
                if (uuids != null) {
                    Player playersInWL = Bukkit.getPlayer(uuids);
                    if (playersInWL != null) {
                        playersInWL.sendMessage(INFO + "" + plugin.playersInWaitingLobby.get(waitingLobbyName).size() + "" + green + "" + italic + " out of " + gold + "" + italic + "" + plugin.mainConfig.getIntWaitLobby("max-players_in_waiting_lobby") + "" + green + "" + italic + "" + " players.");
                    }
                }
            }
        } else if (teams == 3) {
            if (plugin.players3Teams.contains(uuid)) {
                player.sendMessage(ERROR + s);
            } else {
                String waitingLobbyName = plugin.waitingLobby.getWaitingLobbyNameByLoc(loc);
                for (UUID uuids : group) {
                    Player players = Bukkit.getPlayer(uuids);
                    if (players != null) {
                        plugin.playersThatChoice3Teams.add(uuids);
                        if (!players.equals(player)) {
                            players.sendMessage(INFO + "" + gold + "" + italic + "" + player.getName() + "" + green + "" + italic + " has started "+gold+""+italic+"Team Deathmatch"+green+""+italic+" with " + gold + "" + italic + "3" + green + "" + italic + " teams.");
                        }
                        players.setGameMode(GameMode.SURVIVAL);
                        players.getInventory().clear();
                        if (plugin.playersInWaitingLobby.get(waitingLobbyName) == null || plugin.playersInWaitingLobby.get(waitingLobbyName).isEmpty()) {
                            plugin.playersInWaitingLobby.put(waitingLobbyName, new HashSet<>(group));
                        } else {
                            Set<UUID> NewPlayers = new HashSet<>(plugin.playersInWaitingLobby.get(waitingLobbyName));
                            NewPlayers.addAll(new HashSet<>(group));
                            plugin.playersInWaitingLobby.put(waitingLobbyName, NewPlayers);
                        }
                        players.teleport(loc);
                        players.sendMessage(NORMAL + s1 + gold + "" + italic + "" + waitingLobbyName + "" + green + "" + italic + " waiting lobby. Choose your kit and get ready to fight.");
                        plugin.locOfPlayersInWaitingLobby.put(new HashSet<>(group), loc);
                        players.getInventory().addItem(ItemManager.kitChooser);
                        players.getInventory().addItem(ItemManager.teams);
                        if (players.getInventory().contains(ItemManager.groupLeave)) {
                            players.getInventory().remove(ItemManager.groupLeave);
                        }
                        plugin.playerGroup.removeAllPlayerInvites(players);
                        players.getInventory().addItem(ItemManager.isReady);
                    }
                }
                for (UUID uuids : plugin.playersInWaitingLobby.get(waitingLobbyName)) {
                    if (uuids != null) {
                        Player playersInWL = Bukkit.getPlayer(uuids);
                        if (playersInWL != null) {
                            playersInWL.sendMessage(INFO + "" + plugin.playersInWaitingLobby.get(waitingLobbyName).size() + "" + green + "" + italic + " out of " + gold + "" + italic + "" + plugin.mainConfig.getIntWaitLobby("max-players_in_waiting_lobby") + "" + green + "" + italic + "" + " players.");
                        }
                    }
                }
            }
        } else if (teams == 4) {
            if (plugin.players4Teams.contains(uuid)) {
                player.sendMessage(ERROR + s);
            } else {
                String waitingLobbyName = plugin.waitingLobby.getWaitingLobbyNameByLoc(loc);
                for (UUID uuids : group) {
                    Player players = Bukkit.getPlayer(uuids);
                    if (players != null) {
                        plugin.playersThatChoice4Teams.add(uuids);
                        if (!players.equals(player)) {
                            players.sendMessage(INFO + "" + gold + "" + italic + "" + player.getName() + "" + green + "" + italic + " has started "+gold+""+italic+"Team Deathmatch"+green+""+italic+" with " + gold + "" + italic + "4" + green + "" + italic + " teams.");
                        }
                        players.setGameMode(GameMode.SURVIVAL);
                        players.getInventory().clear();
                        if (plugin.playersInWaitingLobby.get(waitingLobbyName) == null || plugin.playersInWaitingLobby.get(waitingLobbyName).isEmpty()) {
                            plugin.playersInWaitingLobby.put(waitingLobbyName, new HashSet<>(group));
                        } else {
                            Set<UUID> NewPlayers = new HashSet<>(plugin.playersInWaitingLobby.get(waitingLobbyName));
                            NewPlayers.addAll(new HashSet<>(group));
                            plugin.playersInWaitingLobby.put(waitingLobbyName, NewPlayers);
                        }
                        players.teleport(loc);
                        players.sendMessage(NORMAL + s1 + gold + "" + italic + "" + waitingLobbyName + "" + green + "" + italic + " waiting lobby. Choose your kit and get ready to fight.");
                        plugin.locOfPlayersInWaitingLobby.put(new HashSet<>(group), loc);
                        players.getInventory().addItem(ItemManager.kitChooser);
                        players.getInventory().addItem(ItemManager.teams);
                        if (players.getInventory().contains(ItemManager.groupLeave)) {
                            players.getInventory().remove(ItemManager.groupLeave);
                        }
                        plugin.playerGroup.removeAllPlayerInvites(players);
                        players.getInventory().addItem(ItemManager.isReady);
                    }
                }
                for (UUID uuids : plugin.playersInWaitingLobby.get(waitingLobbyName)) {
                    if (uuids != null) {
                        Player playersInWL = Bukkit.getPlayer(uuids);
                        if (playersInWL != null) {
                            playersInWL.sendMessage(INFO + "" + plugin.playersInWaitingLobby.get(waitingLobbyName).size() + "" + green + "" + italic + " out of " + gold + "" + italic + "" + plugin.mainConfig.getIntWaitLobby("max-players_in_waiting_lobby") + "" + green + "" + italic + "" + " players.");
                        }
                    }
                }
            }
        } else if (teams == 5) {
            if (plugin.players5Teams.contains(uuid)) {
                player.sendMessage(ERROR + s);
            } else {
                String waitingLobbyName = plugin.waitingLobby.getWaitingLobbyNameByLoc(loc);
                for (UUID uuids : group) {
                    Player players = Bukkit.getPlayer(uuids);
                    if (players != null) {
                        plugin.playersThatChoice5Teams.add(uuids);
                        if (!players.equals(player)) {
                            players.sendMessage(INFO + "" + gold + "" + italic + "" + player.getName() + "" + green + "" + italic + " have started "+gold+""+italic+"Team Deathmatch"+green+""+italic+" with " + gold + "" + italic + "5" + green + "" + italic + " teams.");
                        }
                        players.setGameMode(GameMode.SURVIVAL);
                        players.getInventory().clear();
                        if (plugin.playersInWaitingLobby.get(waitingLobbyName) == null || plugin.playersInWaitingLobby.get(waitingLobbyName).isEmpty()) {
                            plugin.playersInWaitingLobby.put(waitingLobbyName, new HashSet<>(group));
                        } else {
                            Set<UUID> NewPlayers = new HashSet<>(plugin.playersInWaitingLobby.get(waitingLobbyName));
                            NewPlayers.addAll(new HashSet<>(group));
                            plugin.playersInWaitingLobby.put(waitingLobbyName, NewPlayers);
                        }
                        players.teleport(loc);
                        players.sendMessage(NORMAL + s1 + gold + "" + italic + "" + waitingLobbyName + "" + green + "" + italic + " waiting lobby. Choose your kit and get ready to fight.");
                        plugin.locOfPlayersInWaitingLobby.put(new HashSet<>(group), loc);
                        players.getInventory().addItem(ItemManager.kitChooser);
                        players.getInventory().addItem(ItemManager.teams);
                        if (players.getInventory().contains(ItemManager.groupLeave)) {
                            players.getInventory().remove(ItemManager.groupLeave);
                        }
                        plugin.playerGroup.removeAllPlayerInvites(players);
                        players.getInventory().addItem(ItemManager.isReady);
                    }
                }
                for (UUID uuids : plugin.playersInWaitingLobby.get(waitingLobbyName)) {
                    if (uuids != null) {
                        Player playersInWL = Bukkit.getPlayer(uuids);
                        if (playersInWL != null) {
                            playersInWL.sendMessage(INFO + "" + plugin.playersInWaitingLobby.get(waitingLobbyName).size() + "" + green + "" + italic + " out of " + gold + "" + italic + "" + plugin.mainConfig.getIntWaitLobby("max-players_in_waiting_lobby") + "" + green + "" + italic + "" + " players.");
                        }
                    }
                }
            }
        }
    }
}
