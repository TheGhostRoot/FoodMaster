package me.thegoldenmine.foodmaster.Others;

import me.thegoldenmine.foodmaster.FoodMaster;
import org.bukkit.entity.Player;

public class GiveOneLoseToPlayer {
    private final FoodMaster plugin;

    public GiveOneLoseToPlayer(FoodMaster main) {
        plugin = main;
    }

    public synchronized void givePlayerLose(Player player) {
        String uuidOfDeadString = String.valueOf(player.getUniqueId());
        if (uuidOfDeadString != null) {
            try {
                int killerCounter = plugin.mainConfig.getIntLose(uuidOfDeadString);
                plugin.mainConfig.setIntLose(uuidOfDeadString, killerCounter + 1);
                plugin.mainConfig.saveLose();
                plugin.mainConfig.reloadLose();
            } catch (Exception e) {
                plugin.mainConfig.setIntLose(uuidOfDeadString, 1);
            }
        }
    }
}
