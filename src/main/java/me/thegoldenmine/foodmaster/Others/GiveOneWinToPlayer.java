package me.thegoldenmine.foodmaster.Others;

import me.thegoldenmine.foodmaster.FoodMaster;
import org.bukkit.entity.Player;

public class GiveOneWinToPlayer {
    private final FoodMaster plugin;

    public GiveOneWinToPlayer(FoodMaster main) {
        plugin = main;
    }

    public synchronized void givePlayerWin(Player player) {
        String uuidOfDeadString = String.valueOf(player.getUniqueId());
        if (uuidOfDeadString != null) {
            String uuidOfDeadString1 = String.valueOf(player.getUniqueId());
            if (uuidOfDeadString1 != null) {
                try {
                    int killerCounter = plugin.mainConfig.getIntWin(uuidOfDeadString);
                    plugin.mainConfig.setIntWin(uuidOfDeadString1, killerCounter + 1);
                    plugin.mainConfig.saveWin();
                    plugin.mainConfig.reloadWin();
                } catch (Exception e) {
                    plugin.mainConfig.setIntWin(uuidOfDeadString1, 1);
                }
            }
        }
    }
}
