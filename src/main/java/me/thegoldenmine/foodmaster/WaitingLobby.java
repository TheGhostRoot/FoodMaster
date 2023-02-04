package me.thegoldenmine.foodmaster;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class WaitingLobby {
    private final FoodMaster plugin;
    private final Messenger messenger;
    private final GroupManager groupManager;
    // The name of the waiting lobby / All the players in the waiting lobby
    public HashMap<String, Set<UUID>> playersInWaitingLobby = new HashMap<>();

    public WaitingLobby(FoodMaster main) {
        plugin = main;
        messenger = new Messenger(plugin);
        groupManager = new GroupManager(plugin);
    }


    public Set<String> getWaitLobbyNames() {
        return playersInWaitingLobby.keySet();
    }

    public synchronized void removePlayerFromWaitedLobby(Player player) {
        UUID uuid = player.getUniqueId();
        if (getWaitLobbyNames().isEmpty()) {
            messenger.error(player, "There are no lobbies set.");
            return;
        }
        for (String lobbyName : getWaitLobbyNames()) {
            Set<UUID> uuids = playersInWaitingLobby.get(lobbyName);
            if (uuids.contains(uuid)) {
                uuids.remove(uuid);
                int playersLeft = playersInWaitingLobby.get(lobbyName).size();
                int maxPlayers = plugin.mainConfig.getIntWaitLobby("max-players_in_waiting_lobby");
                for (UUID uuid1 : uuids) {
                    Player player1 = Bukkit.getPlayer(uuid1);
                    if (player1 != null) {
                        messenger.info(player1, playersLeft + Messenger.INFO_GENERAL + " out of " + Messenger.MAIN_GENERAL + maxPlayers + Messenger.INFO_GENERAL + " players.");
                    }
                }
                break;
            }
        }
    }

    public String getWaitingLobbyNameByLoc(Location location) {
        for (String name : getWaitLobbyNames()) {
            try {
                Location loc = plugin.mainConfig.getLocationWaitLobby(name + "->wait-location");
                if (loc.equals(location)) {
                    return name;
                }
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public boolean isPlayerInWaitingLobby(Player player) {
        for (String name : getWaitLobbyNames()) {
            if (name != null && playersInWaitingLobby.get(name).contains(player.getUniqueId())) {
                return true;
            }
        }
        return false;
    }


    public boolean areGroupPlayersInWaitingLobby(Player player) {
        UUID playerUUID = player.getUniqueId();
        for (UUID uuid : groupManager.getPlayersInGroupOfPlayer(player)) {
            if (uuid != null && uuid != playerUUID) {
                Player groupPlayer = Bukkit.getPlayer(uuid);
                if (groupPlayer != null && isPlayerInWaitingLobby(groupPlayer)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getWaitingLobbyNameByPlayer(Player player) {
        for (String name : getWaitLobbyNames()) {
            if (playersInWaitingLobby.get(name).contains(player.getUniqueId())) {
                return name;
            }
        }
        return null;
    }

    public Location getWaitingLobbyLocationByName(String name) {
        try {
            return plugin.mainConfig.getLocationWaitLobby(name + "->wait-location");
        } catch (Exception e) {
            return null;
        }
    }

    public void joinYourGroupInWaitingLobby(Player player) {
        if (!areGroupPlayersInWaitingLobby(player)) {
            messenger.error(player, "Your group is not in waiting lobby");
            return;
        }
        UUID playerUUID = player.getUniqueId();
        for (UUID uuid : groupManager.getPlayersInGroupOfPlayer(player)) {
            if (uuid != null && uuid != playerUUID) {
                Player groupPlayer = Bukkit.getPlayer(uuid);
                String waitingLobbyName = getWaitingLobbyNameByPlayer(groupPlayer);
                if (groupPlayer != null && isPlayerInWaitingLobby(groupPlayer) && waitingLobbyName != null) {
                    player.teleport(getWaitingLobbyLocationByName(waitingLobbyName));
                    playersInWaitingLobby.get(waitingLobbyName).add(playerUUID);
                    break;
                }
            }
        }
    }
}
