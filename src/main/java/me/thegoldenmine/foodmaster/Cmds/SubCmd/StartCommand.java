package me.thegoldenmine.foodmaster.Cmds.SubCmd;

import me.thegoldenmine.foodmaster.FoodMaster;
import me.thegoldenmine.foodmaster.ItemManager;
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
                                if (plugin.isPlayerInGroup(player) && freeSpace >= Objects.requireNonNull(plugin.getPlayersInGroupOfPlayer(player)).size()) {
                                    return location;
                                } else {
                                    if (freeSpace >= 1) {
                                        return location;
                                    }
                                }
                            } else if (plugin.isPlayerInGroup(player) && freeSpace >= Objects.requireNonNull(plugin.getPlayersInGroupOfPlayer(player)).size()) {
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
            s = " "+plugin.mainConfig.getStrMain("name")+" ";
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
                    startFreeForAll(player);
                } else if (args[1].equalsIgnoreCase("food-game")) {
                    startFoodGame(player);
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
                        if (plugin.isPlayerInGroup(player)) {
                            if (teams >= 2 && teams <= 5) {
                                // Food War
                                boolean b = teams == 3 || teams == 4 || teams == 5;
                                boolean b1 = teams == 4 || teams == 5;
                                int size = plugin.getPlayersInGroupOfPlayer(player).size();
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
                        startPvEZombie(player);
                    } else if (boss.equalsIgnoreCase("Skeleton")) {
                        startPvESkeleton(player);
                    } else if (boss.equalsIgnoreCase("Spider")) {
                        startPvESpider(player);
                    } else if (boss.equalsIgnoreCase("Enderman")) {
                        startPvEEnderman(player);
                    } else if (boss.equalsIgnoreCase("Slime")) {
                        startPvESlime(player);
                    }
                } else {
                    player.sendMessage(INFO + "" + ChatColor.DARK_PURPLE + "" + italic + "/fm start pve " + gold + "" + italic + "[boss] " + red + "" + bold + ">- " + darkGray + "" + italic + "You need to enter the BOSS.");
                }
            }
        } else {
            player.sendMessage(INFO + "" + ChatColor.DARK_PURPLE + "" + italic + "/fm start [team-deathmatch/pve] " + gold + "" + italic + "[teams/boss] " + red + "" + bold + ">- " + darkGray + "" + italic + "You need to enter the amount of teams ( 2 - 5 ) or a BOSS.");
        }
    }

    public void startFoodGame(Player player) {
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
       String INFO = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + aqua + "" + bold + "INFO " + darkGray + "" + strikethrough + "-" + aqua + "" + italic + " ";
        String NORMAL = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + darkGray + "" + strikethrough + "-" + green + "" + italic + " ";
        String ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
        UUID uuid = player.getUniqueId();
        Location loc = findFreeWaitLobby(player);
        if (plugin.isPlayerInGroup(player)) {
            // the player is in group
            if (plugin.isPlayerChooseToPlayPvE(player)) {
                player.sendMessage(ERROR + "You have already started " + gold + "" + italic + "PvE" + yellow + "" + italic + " .");
                return;
            }
            if (!plugin.playersChoiceFoodGame.isEmpty() && plugin.playersChoiceFoodGame.contains(uuid)) {
                player.sendMessage(ERROR+"You have already started FoodGame");
                return;
            }
            if (!plugin.playersChoiceFreeForAll.isEmpty() && plugin.playersChoiceFreeForAll.contains(uuid)) {
                player.sendMessage(ERROR+"You have already started FreeForAll");
                return;
            }
            if (plugin.isPlayerChooseToPlayFoodWars(player)) {
                player.sendMessage(ERROR+"You have already started TeamDeathmatch");
                return;
            }
            if (loc == null) {
                player.sendMessage(ERROR + "There are no available waiting lobbies right now.");
                return;
            }
            Set<UUID> group = plugin.getPlayersInGroupOfPlayer(player);
            String waitingLobbyName = plugin.getWaitingLobbyNameByLoc(loc);
            for (UUID uuids : group) {
                Player players = Bukkit.getPlayer(uuids);
                if (players != null) {
                    plugin.playersChoiceFoodGame.addAll(new HashSet<>(group));
                    if (plugin.playersInWaitingLobby.get(waitingLobbyName) == null || plugin.playersInWaitingLobby.get(waitingLobbyName).isEmpty()) {
                        plugin.playersInWaitingLobby.put(waitingLobbyName, new HashSet<>(group));
                    } else {
                        Set<UUID> NewPlayers = new HashSet<>(plugin.playersInWaitingLobby.get(waitingLobbyName));
                        NewPlayers.addAll(new HashSet<>(group));
                        plugin.playersInWaitingLobby.put(waitingLobbyName, NewPlayers);
                    }
                    if (!players.equals(player)) {
                        players.sendMessage(INFO + "" + gold + "" + italic + "" + player.getName() + "" + aqua + "" + italic + " has started " + gold + "" + italic + "Food Game" + aqua + "" + italic + ".");
                    }
                    players.setGameMode(GameMode.SURVIVAL);
                    players.getInventory().clear();
                    players.teleport(loc);
                    players.sendMessage(NORMAL + "You have been teleported to " + gold + "" + italic + "" + waitingLobbyName + "" + green + "" + italic + " waiting lobby. Get ready to fight.");
                    plugin.locOfPlayersInWaitingLobby.put(new HashSet<>(group), loc);
                    if (players.getInventory().contains(ItemManager.groupLeave)) {
                        players.getInventory().remove(ItemManager.groupLeave);
                    }
                    plugin.removeAllPlayerInvites(players);
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
        } else {
            player.sendMessage(ERROR + "You can't play alone.");
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
            s2 = " "+plugin.mainConfig.getStrMain("name")+" ";
        } else {
            s2 = " FoodMaster ";
        }
        String INFO = darkGray + "" + strikethrough + "-" + gold + "" + bold + s2 + aqua + "" + bold + "INFO " + darkGray + "" + strikethrough + "-" + aqua + "" + italic + " ";
        String NORMAL = darkGray + "" + strikethrough + "-" + gold + "" + bold + s2 + darkGray + "" + strikethrough + "-" + green + "" + italic + " ";
        String ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + s2 + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
        UUID uuid = player.getUniqueId();
        String s = "You are in a game. You can't start another one.";
        String s1 = "You have been teleported to ";
        if (!plugin.isPlayerInGroup(player)) {
            player.sendMessage(ERROR + "You are not in group.");
            return;
        }
        Set<UUID> group = plugin.getPlayersInGroupOfPlayer(player);
        if (plugin.playersChoiceFoodGame.contains(uuid)) {
            player.sendMessage(ERROR + "You have already started " + gold + "" + italic + "Food Game" + yellow + "" + italic + ".");
            return;
        }
        if (plugin.playersChoiceFreeForAll.contains(uuid)) {
            player.sendMessage(ERROR + "You have already started " + gold + "" + italic + "Free For All" + yellow + "" + italic + ".");
            return;
        }
        if (plugin.isPlayerChooseToPlayPvE(player)) {
            player.sendMessage(ERROR + "You have already started " + gold + "" + italic + "PvE" + yellow + "" + italic + " .");
            return;
        }
        if (plugin.playersThatChoice2Teams.contains(uuid) || plugin.playersThatChoice3Teams.contains(uuid) || plugin.playersThatChoice4Teams.contains(uuid) || plugin.playersThatChoice5Teams.contains(uuid)) {
            player.sendMessage(ERROR + "You have already started " + gold + "" + italic + "Team Deathmatch" + yellow + "" + italic + ".");
            return;
        }
        if (plugin.isPlayerInGame(player)) {
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
            String waitingLobbyName = plugin.getWaitingLobbyNameByLoc(loc);
            for (UUID uuids : group) {
                Player players = Bukkit.getPlayer(uuids);
                if (players != null) {
                    plugin.playersThatChoice2Teams.add(uuids);
                    if (!players.equals(player)) {
                        players.sendMessage(INFO + "" + gold + "" + italic + "" + player.getName() + "" + green + "" + italic + " has started Food Wars with " + gold + "" + italic + "2" + green + "" + italic + " teams.");
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
                    plugin.removeAllPlayerInvites(players);
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
                String waitingLobbyName = plugin.getWaitingLobbyNameByLoc(loc);
                for (UUID uuids : group) {
                    Player players = Bukkit.getPlayer(uuids);
                    if (players != null) {
                        plugin.playersThatChoice3Teams.add(uuids);
                        if (!players.equals(player)) {
                            players.sendMessage(INFO + "" + gold + "" + italic + "" + player.getName() + "" + green + "" + italic + " has started Food Wars with " + gold + "" + italic + "3" + green + "" + italic + " teams.");
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
                        plugin.removeAllPlayerInvites(players);
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
                String waitingLobbyName = plugin.getWaitingLobbyNameByLoc(loc);
                for (UUID uuids : group) {
                    Player players = Bukkit.getPlayer(uuids);
                    if (players != null) {
                        plugin.playersThatChoice4Teams.add(uuids);
                        if (!players.equals(player)) {
                            players.sendMessage(INFO + "" + gold + "" + italic + "" + player.getName() + "" + green + "" + italic + " has started Food Wars with " + gold + "" + italic + "4" + green + "" + italic + " teams.");
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
                        plugin.removeAllPlayerInvites(players);
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
                String waitingLobbyName = plugin.getWaitingLobbyNameByLoc(loc);
                for (UUID uuids : group) {
                    Player players = Bukkit.getPlayer(uuids);
                    if (players != null) {
                        plugin.playersThatChoice5Teams.add(uuids);
                        if (!players.equals(player)) {
                            players.sendMessage(INFO + "" + gold + "" + italic + "" + player.getName() + "" + green + "" + italic + " have started Food Wars with " + gold + "" + italic + "5" + green + "" + italic + " teams.");
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
                        plugin.removeAllPlayerInvites(players);
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

    public void startFreeForAll(Player player) {
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
        // player is one of the group team
        Location loc = findFreeWaitLobby(player);
        if (plugin.isPlayerInGroup(player)) {
            // the player is in group
            UUID uuid = player.getUniqueId();
            if (plugin.isPlayerInGame(player)) {
                player.sendMessage(ERROR + "You have to finish this game before you can start another one.");
                return;
            }
            if (plugin.isPlayerChooseToPlayPvE(player)) {
                player.sendMessage(WARN + "You have already started " + gold + "" + italic + "PvE" + yellow + "" + italic + " .");
                return;
            }
            if (plugin.playersChoiceFoodGame.contains(uuid)) {
                player.sendMessage(WARN + "You have already started " + gold + "" + italic + "Food Game" + yellow + "" + italic + ".");
                return;
            }
            if (plugin.playersThatChoice2Teams.contains(uuid) || plugin.playersThatChoice3Teams.contains(uuid) || plugin.playersThatChoice4Teams.contains(uuid) || plugin.playersThatChoice5Teams.contains(uuid)) {
                player.sendMessage(WARN + "You have already started " + gold + "" + italic + "Team Deathmatch" + yellow + "" + italic + ".");
                return;
            }
            if (plugin.playersChoiceFreeForAll.contains(uuid)) {
                player.sendMessage(WARN + "You have already started " + gold + "" + italic + "Free For All" + yellow + "" + italic + ".");
                return;
            }
            if (loc == null) {
                player.sendMessage(ERROR + "There are no available waiting lobbies right now.");
                return;
            }
            String waitingLobbyName = plugin.getWaitingLobbyNameByLoc(loc);
            Set<UUID> group = plugin.getPlayersInGroupOfPlayer(player);
            for (UUID uuids : group) {
                Player players = Bukkit.getPlayer(uuids);
                if (players != null) {
                    plugin.playersChoiceFreeForAll.addAll(new HashSet<>(group));
                    if (plugin.playersInWaitingLobby.get(waitingLobbyName) == null || plugin.playersInWaitingLobby.get(waitingLobbyName).isEmpty()) {
                        plugin.playersInWaitingLobby.put(waitingLobbyName, new HashSet<>(group));
                    } else {
                        Set<UUID> NewPlayers = new HashSet<>(plugin.playersInWaitingLobby.get(waitingLobbyName));
                        NewPlayers.addAll(new HashSet<>(group));
                        plugin.playersInWaitingLobby.put(waitingLobbyName, NewPlayers);
                    }
                    if (!players.equals(player)) {
                        players.sendMessage(INFO + "" + gold + "" + italic + "" + player.getName() + "" + aqua + "" + italic + " has started " + gold + "" + italic + "Free For All" + aqua + "" + italic + ".");
                    }
                    players.setGameMode(GameMode.SURVIVAL);
                    players.getInventory().clear();
                    players.teleport(loc);
                    players.sendMessage(NORMAL + "You have been teleported to " + gold + "" + italic + "" + waitingLobbyName + "" + green + "" + italic + " waiting lobby. Choose your kit and get ready to fight.");
                    plugin.locOfPlayersInWaitingLobby.put(new HashSet<>(group), loc);
                    players.getInventory().addItem(ItemManager.kitChooser);
                    if (players.getInventory().contains(ItemManager.groupLeave)) {
                        players.getInventory().remove(ItemManager.groupLeave);
                    }
                    plugin.removeAllPlayerInvites(players);
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
        } else {
            player.sendMessage(ERROR + "You can't play alone.");
        }
    }

    public void startPvEEnderman(Player player) {
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
        // player is one of the group team
        Location loc = findFreeWaitLobby(player);
        UUID uuid = player.getUniqueId();
        if (plugin.isPlayerInGame(player)) {
            player.sendMessage(ERROR + "You have to finish this game before you can start another one.");
            return;
        }
        if (plugin.isPlayerChooseToPlayPvE(player)) {
            player.sendMessage(WARN + "You have already started " + gold + "" + italic + "PvE" + yellow + "" + italic + " .");
            return;
        }
        if (plugin.playersChoiceFoodGame.contains(uuid)) {
            player.sendMessage(WARN + "You have already started " + gold + "" + italic + "Food Game" + yellow + "" + italic + ".");
            return;
        }
        if (plugin.playersThatChoice2Teams.contains(uuid) || plugin.playersThatChoice3Teams.contains(uuid) || plugin.playersThatChoice4Teams.contains(uuid) || plugin.playersThatChoice5Teams.contains(uuid)) {
            player.sendMessage(WARN + "You have already started " + gold + "" + italic + "Team Deathmatch" + yellow + "" + italic + ".");
            return;
        }
        if (plugin.playersChoiceFreeForAll.contains(uuid)) {
            player.sendMessage(WARN + "You have already started " + gold + "" + italic + "Free For All" + yellow + "" + italic + ".");
            return;
        }
        if (loc == null) {
            player.sendMessage(ERROR + "There are no available waiting lobbies right now.");
            return;
        }
        String waitingLobbyName = plugin.getWaitingLobbyNameByLoc(loc);
        String message = NORMAL + "You have been teleported to " + gold + "" + italic + "" + waitingLobbyName + "" + green + "" + italic + " waiting lobby. Choose your kit and get ready to fight.";
        if (plugin.isPlayerInGroup(player)) {
            // the player is in group
            Set<UUID> group = new HashSet<>(plugin.getPlayersInGroupOfPlayer(player));
            for (UUID uuids : group) {
                Player players = Bukkit.getPlayer(uuids);
                if (players != null) {
                    plugin.playersChoicePvEEnderman.addAll(new HashSet<>(group));
                    if (plugin.playersInWaitingLobby.get(waitingLobbyName) == null || plugin.playersInWaitingLobby.get(waitingLobbyName).isEmpty()) {
                        plugin.playersInWaitingLobby.put(waitingLobbyName, new HashSet<>(group));
                    } else {
                        Set<UUID> NewPlayers = new HashSet<>(plugin.playersInWaitingLobby.get(waitingLobbyName));
                        NewPlayers.addAll(new HashSet<>(group));
                        plugin.playersInWaitingLobby.put(waitingLobbyName, NewPlayers);
                    }
                    if (!players.equals(player)) {
                        players.sendMessage(INFO + "" + gold + "" + italic + "" + player.getName() + "" + aqua + "" + italic + " has started " + gold + "" + italic + "PvE Enderman boss" + aqua + "" + italic + ".");
                    }
                    players.setGameMode(GameMode.SURVIVAL);
                    players.getInventory().clear();
                    players.teleport(loc);
                    players.sendMessage(message);
                    plugin.locOfPlayersInWaitingLobby.put(new HashSet<>(group), loc);
                    players.getInventory().addItem(ItemManager.kitChooser);
                    if (players.getInventory().contains(ItemManager.groupLeave)) {
                        players.getInventory().remove(ItemManager.groupLeave);
                    }
                    plugin.removeAllPlayerInvites(players);
                    players.getInventory().addItem(ItemManager.isReady);
                }
            }
        } else {
            // the player is alone
            plugin.playersChoicePvEEnderman.add(uuid);
            Set<UUID> NewPlayers = new HashSet<>(plugin.playersInWaitingLobby.get(waitingLobbyName));
            NewPlayers.add(uuid);
            if (plugin.playersInWaitingLobby.get(waitingLobbyName) == null || plugin.playersInWaitingLobby.get(waitingLobbyName).isEmpty()) {
                plugin.playersInWaitingLobby.put(waitingLobbyName, NewPlayers);
            } else {
                Set<UUID> NewPlayersInWaiting = new HashSet<>(plugin.playersInWaitingLobby.get(waitingLobbyName));
                NewPlayersInWaiting.add(uuid);
                plugin.playersInWaitingLobby.put(waitingLobbyName, NewPlayersInWaiting);
            }
            player.sendMessage(INFO + "You started " + gold + "" + italic + "PvE Enderman boss" + aqua + "" + italic + ".");
            player.setGameMode(GameMode.SURVIVAL);
            player.getInventory().clear();
            player.teleport(loc);
            player.sendMessage(message);
            player.getInventory().addItem(ItemManager.kitChooser);
            if (player.getInventory().contains(ItemManager.groupLeave)) {
                player.getInventory().remove(ItemManager.groupLeave);
            }
            plugin.removeAllPlayerInvites(player);
            player.setGameMode(GameMode.SURVIVAL);
            player.getInventory().addItem(ItemManager.isReady);
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

    public void startPvESlime(Player player) {
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
        // player is one of the group team
        Location loc = findFreeWaitLobby(player);
        UUID uuid = player.getUniqueId();
        if (plugin.isPlayerInGame(player)) {
            player.sendMessage(ERROR + "You have to finish this game before you can start another one.");
            return;
        }
        if (plugin.isPlayerChooseToPlayPvE(player)) {
            player.sendMessage(WARN + "You have already started " + gold + "" + italic + "PvE" + yellow + "" + italic + " .");
            return;
        }
        if (plugin.playersChoiceFoodGame.contains(uuid)) {
            player.sendMessage(WARN + "You have already started " + gold + "" + italic + "Food Game" + yellow + "" + italic + ".");
            return;
        }
        if (plugin.playersThatChoice2Teams.contains(uuid) || plugin.playersThatChoice3Teams.contains(uuid) || plugin.playersThatChoice4Teams.contains(uuid) || plugin.playersThatChoice5Teams.contains(uuid)) {
            player.sendMessage(WARN + "You have already started " + gold + "" + italic + "Team Deathmatch" + yellow + "" + italic + ".");
            return;
        }
        if (plugin.playersChoiceFreeForAll.contains(uuid)) {
            player.sendMessage(WARN + "You have already started " + gold + "" + italic + "Free For All" + yellow + "" + italic + ".");
            return;
        }
        if (loc == null) {
            player.sendMessage(ERROR + "There are no available waiting lobbies right now.");
            return;
        }
        String waitingLobbyName = plugin.getWaitingLobbyNameByLoc(loc);
        String message = NORMAL + "You have been teleported to " + gold + "" + italic + "" + waitingLobbyName + "" + green + "" + italic + " waiting lobby. Choose your kit and get ready to fight.";
        if (plugin.isPlayerInGroup(player)) {
            // the player is in group
            Set<UUID> group = new HashSet<>(plugin.getPlayersInGroupOfPlayer(player));
            for (UUID uuids : group) {
                Player players = Bukkit.getPlayer(uuids);
                if (players != null) {
                    plugin.playersChoicePvESlime.addAll(new HashSet<>(group));
                    if (plugin.playersInWaitingLobby.get(waitingLobbyName) == null || plugin.playersInWaitingLobby.get(waitingLobbyName).isEmpty()) {
                        plugin.playersInWaitingLobby.put(waitingLobbyName, new HashSet<>(group));
                    } else {
                        Set<UUID> NewPlayers = new HashSet<>(plugin.playersInWaitingLobby.get(waitingLobbyName));
                        NewPlayers.addAll(new HashSet<>(group));
                        plugin.playersInWaitingLobby.put(waitingLobbyName, NewPlayers);
                    }
                    if (!players.equals(player)) {
                        players.sendMessage(INFO + "" + gold + "" + italic + "" + player.getName() + "" + aqua + "" + italic + " has started " + gold + "" + italic + "PvE Slime boss" + aqua + "" + italic + ".");
                    }
                    players.setGameMode(GameMode.SURVIVAL);
                    players.getInventory().clear();
                    players.teleport(loc);
                    players.sendMessage(message);
                    plugin.locOfPlayersInWaitingLobby.put(new HashSet<>(group), loc);
                    players.getInventory().addItem(ItemManager.kitChooser);
                    if (players.getInventory().contains(ItemManager.groupLeave)) {
                        players.getInventory().remove(ItemManager.groupLeave);
                    }
                    plugin.removeAllPlayerInvites(players);
                    players.getInventory().addItem(ItemManager.isReady);
                }
            }
        } else {
            // the player is alone
            plugin.playersChoicePvESlime.add(uuid);
            Set<UUID> NewPlayers = new HashSet<>(plugin.playersInWaitingLobby.get(waitingLobbyName));
            NewPlayers.add(uuid);
            if (plugin.playersInWaitingLobby.get(waitingLobbyName) == null || plugin.playersInWaitingLobby.get(waitingLobbyName).isEmpty()) {
                plugin.playersInWaitingLobby.put(waitingLobbyName, NewPlayers);
            } else {
                Set<UUID> NewPlayersInWaiting = new HashSet<>(plugin.playersInWaitingLobby.get(waitingLobbyName));
                NewPlayersInWaiting.add(uuid);
                plugin.playersInWaitingLobby.put(waitingLobbyName, NewPlayersInWaiting);
            }
            player.sendMessage(INFO + "You started " + gold + "" + italic + "PvE Slime boss" + aqua + "" + italic + ".");
            player.setGameMode(GameMode.SURVIVAL);
            player.getInventory().clear();
            player.teleport(loc);
            player.sendMessage(message);
            player.getInventory().addItem(ItemManager.kitChooser);
            if (player.getInventory().contains(ItemManager.groupLeave)) {
                player.getInventory().remove(ItemManager.groupLeave);
            }
            plugin.removeAllPlayerInvites(player);
            player.getInventory().addItem(ItemManager.isReady);
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

    public void startPvEZombie(Player player) {
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
        // player is one of the group team
        Location loc = findFreeWaitLobby(player);
        UUID uuid = player.getUniqueId();
        if (plugin.isPlayerInGame(player)) {
            player.sendMessage(ERROR + "You have to finish this game before you can start another one.");
            return;
        }
        if (plugin.isPlayerChooseToPlayPvE(player)) {
            player.sendMessage(WARN + "You have already started " + gold + "" + italic + "PvE" + yellow + "" + italic + " .");
            return;
        }
        if (plugin.playersChoiceFoodGame.contains(uuid)) {
            player.sendMessage(WARN + "You have already started " + gold + "" + italic + "Food Game" + yellow + "" + italic + ".");
            return;
        }
        if (plugin.playersThatChoice2Teams.contains(uuid) || plugin.playersThatChoice3Teams.contains(uuid) || plugin.playersThatChoice4Teams.contains(uuid) || plugin.playersThatChoice5Teams.contains(uuid)) {
            player.sendMessage(WARN + "You have already started " + gold + "" + italic + "Team Deathmatch" + yellow + "" + italic + ".");
            return;
        }
        if (plugin.playersChoiceFreeForAll.contains(uuid)) {
            player.sendMessage(WARN + "You have already started " + gold + "" + italic + "Free For All" + yellow + "" + italic + ".");
            return;
        }
        if (loc == null) {
            player.sendMessage(ERROR + "There are no available waiting lobbies right now.");
            return;
        }
        String waitingLobbyName = plugin.getWaitingLobbyNameByLoc(loc);
        String message = NORMAL + "You have been teleported to " + gold + "" + italic + "" + waitingLobbyName + "" + green + "" + italic + " waiting lobby. Choose your kit and get ready to fight.";
        if (plugin.isPlayerInGroup(player)) {
            // the player is in group
            Set<UUID> group = new HashSet<>(plugin.getPlayersInGroupOfPlayer(player));
            for (UUID uuids : group) {
                Player players = Bukkit.getPlayer(uuids);
                if (players != null) {
                    plugin.playersChoicePvEZombie.addAll(new HashSet<>(group));
                    if (plugin.playersInWaitingLobby.get(waitingLobbyName) == null || plugin.playersInWaitingLobby.get(waitingLobbyName).isEmpty()) {
                        plugin.playersInWaitingLobby.put(waitingLobbyName, new HashSet<>(group));
                    } else {
                        Set<UUID> NewPlayers = new HashSet<>(plugin.playersInWaitingLobby.get(waitingLobbyName));
                        NewPlayers.addAll(new HashSet<>(group));
                        plugin.playersInWaitingLobby.put(waitingLobbyName, NewPlayers);
                    }
                    if (!players.equals(player)) {
                        players.sendMessage(INFO + "" + gold + "" + italic + "" + player.getName() + "" + aqua + "" + italic + " has started " + gold + "" + italic + "PvE Zombie boss" + aqua + "" + italic + ".");
                    }
                    players.setGameMode(GameMode.SURVIVAL);
                    players.getInventory().clear();
                    players.teleport(loc);
                    players.sendMessage(message);
                    plugin.locOfPlayersInWaitingLobby.put(new HashSet<>(group), loc);
                    players.getInventory().addItem(ItemManager.kitChooser);
                    if (players.getInventory().contains(ItemManager.groupLeave)) {
                        players.getInventory().remove(ItemManager.groupLeave);
                    }
                    plugin.removeAllPlayerInvites(players);
                    players.getInventory().addItem(ItemManager.isReady);
                }
            }
        } else {
            // the player is alone
            plugin.playersChoicePvEZombie.add(uuid);
            Set<UUID> NewPlayers = new HashSet<>(plugin.playersInWaitingLobby.get(waitingLobbyName));
            NewPlayers.add(uuid);
            if (plugin.playersInWaitingLobby.get(waitingLobbyName) == null || plugin.playersInWaitingLobby.get(waitingLobbyName).isEmpty()) {
                plugin.playersInWaitingLobby.put(waitingLobbyName, NewPlayers);
            } else {
                Set<UUID> NewPlayersInWaiting = new HashSet<>(plugin.playersInWaitingLobby.get(waitingLobbyName));
                NewPlayersInWaiting.add(uuid);
                plugin.playersInWaitingLobby.put(waitingLobbyName, NewPlayersInWaiting);
            }
            player.setGameMode(GameMode.SURVIVAL);
            player.getInventory().clear();
            player.sendMessage(INFO + "You started " + gold + "" + italic + "PvE Zombie boss" + aqua + "" + italic + ".");
            player.teleport(loc);
            player.sendMessage(message);
            player.getInventory().addItem(ItemManager.kitChooser);
            if (player.getInventory().contains(ItemManager.groupLeave)) {
                player.getInventory().remove(ItemManager.groupLeave);
            }
            plugin.removeAllPlayerInvites(player);
            player.getInventory().addItem(ItemManager.isReady);
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

    public void startPvESkeleton(Player player) {
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
        // player is one of the group team
        Location loc = findFreeWaitLobby(player);
        UUID uuid = player.getUniqueId();
        if (plugin.isPlayerInGame(player)) {
            player.sendMessage(ERROR + "You have to finish this game before you can start another one.");
            return;
        }
        if (plugin.isPlayerChooseToPlayPvE(player)) {
            player.sendMessage(WARN + "You have already started " + gold + "" + italic + "PvE" + yellow + "" + italic + " .");
            return;
        }
        if (plugin.playersChoiceFoodGame.contains(uuid)) {
            player.sendMessage(WARN + "You have already started " + gold + "" + italic + "Food Game" + yellow + "" + italic + ".");
            return;
        }
        if (plugin.playersThatChoice2Teams.contains(uuid) || plugin.playersThatChoice3Teams.contains(uuid) || plugin.playersThatChoice4Teams.contains(uuid) || plugin.playersThatChoice5Teams.contains(uuid)) {
            player.sendMessage(WARN + "You have already started " + gold + "" + italic + "Team Deathmatch" + yellow + "" + italic + ".");
            return;
        }
        if (plugin.playersChoiceFreeForAll.contains(uuid)) {
            player.sendMessage(WARN + "You have already started " + gold + "" + italic + "Free For All" + yellow + "" + italic + ".");
            return;
        }
        if (loc == null) {
            player.sendMessage(ERROR + "There are no available waiting lobbies right now.");
            return;
        }
        String waitingLobbyName = plugin.getWaitingLobbyNameByLoc(loc);
        String message = NORMAL + "You have been teleported to " + gold + "" + italic + "" + waitingLobbyName + "" + green + "" + italic + " waiting lobby. Choose your kit and get ready to fight.";
        if (plugin.isPlayerInGroup(player)) {
            // the player is in group
            Set<UUID> group = new HashSet<>(plugin.getPlayersInGroupOfPlayer(player));
            for (UUID uuids : group) {
                Player players = Bukkit.getPlayer(uuids);
                if (players != null) {
                    plugin.playersChoicePvESkeleton.addAll(new HashSet<>(group));
                    if (plugin.playersInWaitingLobby.get(waitingLobbyName) == null || plugin.playersInWaitingLobby.get(waitingLobbyName).isEmpty()) {
                        plugin.playersInWaitingLobby.put(waitingLobbyName, new HashSet<>(group));
                    } else {
                        Set<UUID> NewPlayers = new HashSet<>(plugin.playersInWaitingLobby.get(waitingLobbyName));
                        NewPlayers.addAll(new HashSet<>(group));
                        plugin.playersInWaitingLobby.put(waitingLobbyName, NewPlayers);
                    }
                    if (!players.equals(player)) {
                        players.sendMessage(INFO + "" + gold + "" + italic + "" + player.getName() + "" + aqua + "" + italic + " has started " + gold + "" + italic + "PvE Skeleton boss" + aqua + "" + italic + ".");
                    }
                    players.setGameMode(GameMode.SURVIVAL);
                    players.getInventory().clear();
                    players.teleport(loc);
                    players.sendMessage(message);
                    plugin.locOfPlayersInWaitingLobby.put(new HashSet<>(group), loc);
                    players.getInventory().addItem(ItemManager.kitChooser);
                    if (players.getInventory().contains(ItemManager.groupLeave)) {
                        players.getInventory().remove(ItemManager.groupLeave);
                    }
                    plugin.removeAllPlayerInvites(players);
                    players.getInventory().addItem(ItemManager.isReady);
                }
            }
        } else {
            // the player is alone
            plugin.playersChoicePvESkeleton.add(uuid);
            Set<UUID> NewPlayers = new HashSet<>(plugin.playersInWaitingLobby.get(waitingLobbyName));
            NewPlayers.add(uuid);
            if (plugin.playersInWaitingLobby.get(waitingLobbyName) == null || plugin.playersInWaitingLobby.get(waitingLobbyName).isEmpty()) {
                plugin.playersInWaitingLobby.put(waitingLobbyName, NewPlayers);
            } else {
                Set<UUID> NewPlayersInWaiting = new HashSet<>(plugin.playersInWaitingLobby.get(waitingLobbyName));
                NewPlayersInWaiting.add(uuid);
                plugin.playersInWaitingLobby.put(waitingLobbyName, NewPlayersInWaiting);
            }
            player.setGameMode(GameMode.SURVIVAL);
            player.getInventory().clear();
            player.sendMessage(INFO + "You started " + gold + "" + italic + "PvE Skeleton boss" + aqua + "" + italic + ".");
            player.teleport(loc);
            player.sendMessage(message);
            player.getInventory().addItem(ItemManager.kitChooser);
            if (player.getInventory().contains(ItemManager.groupLeave)) {
                player.getInventory().remove(ItemManager.groupLeave);
            }
            plugin.removeAllPlayerInvites(player);
            player.getInventory().addItem(ItemManager.isReady);
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

    public void startPvESpider(Player player) {
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
        // player is one of the group team
        Location loc = findFreeWaitLobby(player);
        UUID uuid = player.getUniqueId();
        if (plugin.isPlayerInGame(player)) {
            player.sendMessage(ERROR + "You have to finish this game before you can start another one.");
            return;
        }
        if (plugin.isPlayerChooseToPlayPvE(player)) {
            player.sendMessage(WARN + "You have already started " + gold + "" + italic + "PvE" + yellow + "" + italic + " .");
            return;
        }
        if (plugin.playersChoiceFoodGame.contains(uuid)) {
            player.sendMessage(WARN + "You have already started " + gold + "" + italic + "Food Game" + yellow + "" + italic + ".");
            return;
        }
        if (plugin.playersThatChoice2Teams.contains(uuid) || plugin.playersThatChoice3Teams.contains(uuid) || plugin.playersThatChoice4Teams.contains(uuid) || plugin.playersThatChoice5Teams.contains(uuid)) {
            player.sendMessage(WARN + "You have already started " + gold + "" + italic + "Team Deathmatch" + yellow + "" + italic + ".");
            return;
        }
        if (plugin.playersChoiceFreeForAll.contains(uuid)) {
            player.sendMessage(WARN + "You have already started " + gold + "" + italic + "Free For All" + yellow + "" + italic + ".");
            return;
        }
        if (loc == null) {
            player.sendMessage(ERROR + "There are no available waiting lobbies right now.");
            return;
        }
        String waitingLobbyName = plugin.getWaitingLobbyNameByLoc(loc);
        String message = NORMAL + "You have been teleported to " + gold + "" + italic + "" + waitingLobbyName + "" + green + "" + italic + " waiting lobby. Choose your kit and get ready to fight.";
        if (plugin.isPlayerInGroup(player)) {
            // the player is in group
            Set<UUID> group = new HashSet<>(plugin.getPlayersInGroupOfPlayer(player));
            for (UUID uuids : group) {
                Player players = Bukkit.getPlayer(uuids);
                if (players != null) {
                    plugin.playersChoicePvESpider.addAll(new HashSet<>(group));
                    if (plugin.playersInWaitingLobby.get(waitingLobbyName) == null || plugin.playersInWaitingLobby.get(waitingLobbyName).isEmpty()) {
                        plugin.playersInWaitingLobby.put(waitingLobbyName, new HashSet<>(group));
                    } else {
                        Set<UUID> NewPlayers = new HashSet<>(plugin.playersInWaitingLobby.get(waitingLobbyName));
                        NewPlayers.addAll(new HashSet<>(group));
                        plugin.playersInWaitingLobby.put(waitingLobbyName, NewPlayers);
                    }
                    if (!players.equals(player)) {
                        players.sendMessage(INFO + "" + gold + "" + italic + "" + player.getName() + "" + aqua + "" + italic + " has started " + gold + "" + italic + "PvE Spider boss" + aqua + "" + italic + ".");
                    }
                    players.setGameMode(GameMode.SURVIVAL);
                    players.getInventory().clear();
                    players.teleport(loc);
                    players.sendMessage(message);
                    plugin.locOfPlayersInWaitingLobby.put(new HashSet<>(group), loc);
                    players.getInventory().addItem(ItemManager.kitChooser);
                    if (players.getInventory().contains(ItemManager.groupLeave)) {
                        players.getInventory().remove(ItemManager.groupLeave);
                    }
                    plugin.removeAllPlayerInvites(players);
                    players.getInventory().addItem(ItemManager.isReady);
                }
            }
        } else {
            // the player is alone
            plugin.playersChoicePvESpider.add(uuid);
            Set<UUID> NewPlayers = new HashSet<>(plugin.playersInWaitingLobby.get(waitingLobbyName));
            NewPlayers.add(uuid);
            if (plugin.playersInWaitingLobby.get(waitingLobbyName) == null || plugin.playersInWaitingLobby.get(waitingLobbyName).isEmpty()) {
                plugin.playersInWaitingLobby.put(waitingLobbyName, NewPlayers);
            } else {
                Set<UUID> NewPlayersInWaiting = new HashSet<>(plugin.playersInWaitingLobby.get(waitingLobbyName));
                NewPlayersInWaiting.add(uuid);
                plugin.playersInWaitingLobby.put(waitingLobbyName, NewPlayersInWaiting);
            }
            player.setGameMode(GameMode.SURVIVAL);
            player.getInventory().clear();
            player.sendMessage(INFO + "You started " + gold + "" + italic + "PvE Spider boss" + aqua + "" + italic + ".");
            player.teleport(loc);
            player.sendMessage(message);
            player.getInventory().addItem(ItemManager.kitChooser);
            if (player.getInventory().contains(ItemManager.groupLeave)) {
                player.getInventory().remove(ItemManager.groupLeave);
            }
            plugin.removeAllPlayerInvites(player);
            player.getInventory().addItem(ItemManager.isReady);
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
