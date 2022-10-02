package me.thegoldenmine.foodmaster.Commands.SubCmd.Start;

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

public class StartPvESpider {
    private final FoodMaster plugin;

    public StartPvESpider(FoodMaster main) {
        plugin = main;
    }

    public Location findFreeWaitLobby(Player player, String cmdArgs) {
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

    public void startPvESpider(Player player, String args) {
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
        // player is one of the group team
        Location loc = findFreeWaitLobby(player, args);
        UUID uuid = player.getUniqueId();
        if (plugin.game.isPlayerInGame(player)) {
            player.sendMessage(ERROR + "You have to finish this game before you can start another one.");
            return;
        }
        if (plugin.playerPvE.isPlayerChooseToPlayPvE(player)) {
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
        String waitingLobbyName = plugin.waitingLobby.getWaitingLobbyNameByLoc(loc);
        String message = NORMAL + "You have been teleported to " + gold + "" + italic + "" + waitingLobbyName + "" + green + "" + italic + " waiting lobby. Choose your kit and get ready to fight.";
        if (plugin.playerGroup.isPlayerInGroup(player)) {
            // the player is in group
            Set<UUID> group = new HashSet<>(plugin.playerGroup.getPlayersInGroupOfPlayer(player));
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
                    plugin.playerGroup.removeAllPlayerInvites(players);
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
            plugin.playerGroup.removeAllPlayerInvites(player);
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
