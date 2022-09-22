package me.thegoldenmine.foodmaster.Others;

import me.thegoldenmine.foodmaster.FoodMaster;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

public class RemovePlayerFromWaitLobby {
    private final FoodMaster plugin;

    public RemovePlayerFromWaitLobby(FoodMaster main) {
        plugin = main;
    }

    public synchronized void removePlayerFromWaitedLobby(Player player) {
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor red = ChatColor.RED;
        String s;
        if (plugin.mainConfig.getStrMain("name") != null) {
            s = " "+plugin.mainConfig.getStrMain("name")+" ";
        } else {
            s = " FoodMaster ";
        }
        String ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + red + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + red + "" + italic + " ";
        String INFO = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + ChatColor.AQUA + "" + bold + "INFO " + darkGray + "" + strikethrough + "-" + ChatColor.AQUA + "" + italic + " ";
        UUID uuid = player.getUniqueId();
        if (plugin.getWaitLobbyNames().isEmpty()) {
            player.sendMessage(ERROR + "There are no lobbies set.");
        } else {
            if (plugin.isPlayerInWaitingLobby(player)) {
                for (String lobbyName : plugin.getWaitLobbyNames()) {
                    Set<UUID> uuids = plugin.playersInWaitingLobby.get(lobbyName);
                    if (uuids.contains(uuid)) {
                        uuids.remove(uuid);
                        for (UUID uuid1 : plugin.playersInWaitingLobby.get(lobbyName)) {
                            if (uuid1 != null) {
                                Player player1 = Bukkit.getPlayer(uuid1);
                                if (player1 != null) {
                                    player1.sendMessage(INFO + "" + plugin.playersInWaitingLobby.get(lobbyName).size() + "" + ChatColor.GREEN + "" + italic + " out of " + gold + "" + italic + "" + plugin.mainConfig.getIntWaitLobby("max-players_in_waiting_lobby") + "" + ChatColor.GREEN + "" + italic + "" + " players.");
                                }
                            }
                        }
                        break;
                    }
                }
            }
        }
    }
}
