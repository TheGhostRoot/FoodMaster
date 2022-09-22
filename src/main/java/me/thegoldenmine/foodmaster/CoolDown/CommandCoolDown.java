package me.thegoldenmine.foodmaster.CoolDown;

import me.thegoldenmine.foodmaster.FoodMaster;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class CommandCoolDown {
    public Map<UUID, Integer> playerCoolDownMap = new HashMap<>();

    public CommandCoolDown(FoodMaster plugin) {
        new BukkitRunnable() {
            @Override
            public void run() {
                List<UUID> delList = new ArrayList<>();
                for (UUID uuid : playerCoolDownMap.keySet()) {
                    if (playerCoolDownMap.get(uuid) == 1) {
                        delList.add(uuid);
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
        }.runTaskTimer(plugin, 0, 22);
    }

    public void addPlayerToCoolMap(Player player, Integer seconds) {
        playerCoolDownMap.put(player.getUniqueId(), seconds);
    }

    public boolean isPlayerInCoolDown(Player player) {
        return playerCoolDownMap.containsKey(player.getUniqueId());
    }

    public Integer getTime(Player player) {
        if (!isPlayerInCoolDown(player)) {
            return 0;
        } else {
            return playerCoolDownMap.get(player.getUniqueId());
        }
    }
}
