package me.thegoldenmine.foodmaster.Others;

import me.thegoldenmine.foodmaster.FoodMaster;
import org.bukkit.entity.Player;

import java.util.UUID;

public class GiveOneKillAndDeathToPlayer {
    private final FoodMaster plugin;

    public GiveOneKillAndDeathToPlayer(FoodMaster main) {
        plugin = main;
    }

    public synchronized void givePlayerKD(Player dead, Player killer) {
        String uuidOfDeadString = String.valueOf(dead.getUniqueId());
        String uuidOfKillerString = String.valueOf(killer.getUniqueId());
        if (uuidOfDeadString != null && uuidOfKillerString != null) {
            try {
                int deadCounter = plugin.mainConfig.getIntDeath(uuidOfDeadString);
                plugin.mainConfig.setIntDeath(uuidOfDeadString, deadCounter + 1);
                plugin.mainConfig.saveDeath();
                UUID uuidD = dead.getUniqueId();
                if (plugin.inGameDeaths.containsKey(uuidD)) {
                    int deaths = plugin.inGameDeaths.get(uuidD);
                    plugin.inGameDeaths.put(uuidD, deaths + 1);
                } else {
                    plugin.inGameDeaths.put(uuidD, 1);
                }
            } catch (Exception e) {
                plugin.mainConfig.setIntDeath(uuidOfDeadString, 1);
                UUID uuidD = dead.getUniqueId();
                if (plugin.inGameDeaths.containsKey(uuidD)) {
                    int deaths = plugin.inGameDeaths.get(uuidD);
                    plugin.inGameDeaths.put(uuidD, deaths + 1);
                } else {
                    plugin.inGameDeaths.put(uuidD, 1);
                }
            }
            try {
                int killerCounter = plugin.mainConfig.getIntKill(uuidOfKillerString);
                plugin.mainConfig.setIntKill(uuidOfKillerString, killerCounter + 1);
                plugin.mainConfig.saveKill();
                plugin.mainConfig.reloadKill();
                UUID uuid = killer.getUniqueId();
                if (plugin.inGameKills.containsKey(uuid)) {
                    int kills = plugin.inGameKills.get(uuid);
                    plugin.inGameKills.put(uuid, kills + 1);
                } else {
                    plugin.inGameKills.put(uuid, 1);
                }
            } catch (Exception e) {
                plugin.mainConfig.setIntKill(uuidOfKillerString, 1);
                UUID uuid = killer.getUniqueId();
                if (plugin.inGameKills.containsKey(uuid)) {
                    int kills = plugin.inGameKills.get(uuid);
                    plugin.inGameKills.put(uuid, kills + 1);
                } else {
                    plugin.inGameKills.put(uuid, 1);
                }
            }
        }
    }
}
