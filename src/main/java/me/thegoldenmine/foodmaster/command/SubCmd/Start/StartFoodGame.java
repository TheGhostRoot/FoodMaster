package me.thegoldenmine.foodmaster.command.SubCmd.Start;

import me.thegoldenmine.foodmaster.FoodMaster;
import me.thegoldenmine.foodmaster.Items.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class StartFoodGame {
    private final FoodMaster plugin;

    public StartFoodGame(FoodMaster main) {
        plugin = main;
    }

    public Location findFreeWaitLobby(Player player, String cmdArgs) {
        // waiting lobby location path in config name+"->wait-location"
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

    public void startFoodGame(Player player, String args) {
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
        String INFO = darkGray + "" + strikethrough + "-" + gold + "" + bold + Name + aqua + "" + bold + "INFO " + darkGray + "" + strikethrough + "-" + aqua + "" + italic + " ";
        String NORMAL = darkGray + "" + strikethrough + "-" + gold + "" + bold + Name + darkGray + "" + strikethrough + "-" + green + "" + italic + " ";
        String ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + Name + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
        UUID uuid = player.getUniqueId();
        Location loc = findFreeWaitLobby(player, args);
        if (plugin.playerGroup.isPlayerInGroup(player)) {
            // the player is in group
            if (plugin.playerPvE.isPlayerChooseToPlayPvE(player)) {
                player.sendMessage(ERROR + "You have already started " + gold + "" + italic + "PvE" + yellow + "" + italic + " .");
                return;
            }
            if (!plugin.playersChoiceFoodGame.isEmpty() && plugin.playersChoiceFoodGame.contains(uuid)) {
                player.sendMessage(ERROR + "You have already started FoodGame");
                return;
            }
            if (!plugin.playersChoiceFreeForAll.isEmpty() && plugin.playersChoiceFreeForAll.contains(uuid)) {
                player.sendMessage(ERROR + "You have already started FreeForAll");
                return;
            }
            if (plugin.deathmatch.isPlayerChooseToPlayFoodWars(player)) {
                player.sendMessage(ERROR + "You have already started TeamDeathmatch");
                return;
            }
            if (loc == null) {
                player.sendMessage(ERROR + "There are no available waiting lobbies right now.");
                return;
            }
            Set<UUID> group = plugin.playerGroup.getPlayersInGroupOfPlayer(player);
            String waitingLobbyName = plugin.waitingLobby.getWaitingLobbyNameByLoc(loc);
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
                    } else {
                        players.sendMessage(INFO + " You started " + gold + "" + italic + "Food Game" + aqua + "" + italic + ".");
                    }
                    players.setGameMode(GameMode.SURVIVAL);
                    players.getInventory().clear();
                    players.teleport(loc);
                    players.sendMessage(NORMAL + "You have been teleported to " + gold + "" + italic + "" + waitingLobbyName + "" + green + "" + italic + " waiting lobby. Get ready to fight.");
                    plugin.locOfPlayersInWaitingLobby.put(new HashSet<>(group), loc);
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
        } else {
            player.sendMessage(ERROR + "You can't play alone.");
        }
    }
}
