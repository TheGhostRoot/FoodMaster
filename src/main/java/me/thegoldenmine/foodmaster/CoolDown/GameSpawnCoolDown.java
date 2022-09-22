package me.thegoldenmine.foodmaster.CoolDown;

import me.thegoldenmine.foodmaster.FoodMaster;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class GameSpawnCoolDown {
    public Map<UUID, Integer> playerCoolDownMap = new HashMap<>();

    public GameSpawnCoolDown(FoodMaster plugin) {
        new BukkitRunnable() {
            @Override
            public void run() {
                List<UUID> delList = new ArrayList<>();
                for (UUID uuid : playerCoolDownMap.keySet()) {
                    if (playerCoolDownMap.get(uuid) == 1) {
                        delList.add(uuid);
                        // name = name of game location
                        plugin.tpPlayersInGameNameLoc.put(uuid, null);
                        continue;
                    }
                    playerCoolDownMap.put(uuid, playerCoolDownMap.get(uuid) - 1);
                }
                for (UUID uuids : delList) {
                    if (uuids != null) {
                        playerCoolDownMap.remove(uuids);
                    }
                }
                delList.clear();
            }
        }.runTaskTimer(plugin, 0, 23);
    }

    public void addPlayerToCoolMap(UUID uuid, Integer seconds) {
        playerCoolDownMap.put(uuid, seconds);
    }

    public boolean isPlayerNotInCoolDownSpawn(UUID uuid) {
        return !playerCoolDownMap.containsKey(uuid);
    }

    public Integer getTime(UUID uuid) {
        if (isPlayerNotInCoolDownSpawn(uuid)) {
            return 0;
        } else {
            return playerCoolDownMap.get(uuid);
        }
    }
}
