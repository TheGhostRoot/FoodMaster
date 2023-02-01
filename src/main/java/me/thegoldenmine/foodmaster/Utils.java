package me.thegoldenmine.foodmaster;

import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashSet;
import java.util.UUID;

public class Utils {
    private final FoodMaster plugin;
    private final Messenger messenger;
    private final GroupManager groupManager;

    public Utils(FoodMaster main) {
        plugin = main;
        messenger = new Messenger(plugin);
        groupManager = new GroupManager(plugin);
    }

    public float getKD(Player player) {
        UUID uuid1 = player.getUniqueId();
        String theUUID = uuid1.toString();
        int deaths = plugin.mainConfig.getIntDeath(theUUID);
        int kills = plugin.mainConfig.getIntKill(theUUID);
        float KD = 0;
        if (deaths != 0) {
            KD = (float) kills / deaths;
        }
        return KD;
    }

    public float getWL(Player player) {
        UUID uuid1 = player.getUniqueId();
        String theUUID = String.valueOf(uuid1);
        int win1 = plugin.mainConfig.getIntWin(theUUID);
        int lose1 = plugin.mainConfig.getIntLose(theUUID);
        float WL = 0;
        if (lose1 != 0) {
            WL = (float) win1 / lose1;
        }
        return WL;
    }

    public void clearPlayerChose(Player player) {
        // TODO refactor this when the maps are refactored
        plugin.waitingLobby.removePlayerFromWaitedLobby(player);
        PlayerInventory inventory = player.getInventory();
        if (inventory.getHelmet() != null) {
            inventory.setHelmet(null);
        }
        if (inventory.getChestplate() != null) {
            inventory.setChestplate(null);
        }
        if (inventory.getLeggings() != null) {
            inventory.setLeggings(null);
        }
        if (inventory.getBoots() != null) {
            inventory.setBoots(null);
        }
        UUID uuid = player.getUniqueId();
        plugin.playerFood.remove(uuid);
        plugin.playerTeams.remove(uuid);
        if (plugin.playerGroup.isPlayerInGroup(player)) {
            plugin.locOfPlayersInWaitingLobby.remove(new HashSet<>(groupManager.getPlayersInGroupOfPlayer(player)));
        }
        plugin.kickedPlayers.remove(uuid);
        plugin.playersThatChoice3Teams.remove(uuid);
        String name1 = plugin.game.getGameName(player);
        plugin.stillAlive.remove(name1);
        plugin.ReadySystem.remove(uuid);
        plugin.ReadyPlayers.remove(uuid);
        plugin.playersInFreeForAll.remove(uuid);
        plugin.lives.remove(uuid);
        plugin.playersChoiceFreeForAll.remove(uuid);
    }
}
