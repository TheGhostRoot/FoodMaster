package me.thegoldenmine.foodmaster.Others;

import me.thegoldenmine.foodmaster.FoodMaster;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerPvE {
    private  final FoodMaster plugin;

    public PlayerPvE(FoodMaster main) {
        plugin = main;
    }

    public boolean isPlayerPlayingPvE(Player player) {
        UUID uuid = player.getUniqueId();
        return plugin.playersPlayingPvEZombie.contains(uuid) ||
                plugin.playersPlayingPvESkeleton.contains(uuid) ||
                plugin.playersPlayingPvESpider.contains(uuid) ||
                plugin.playersPlayingPvEEnderman.contains(uuid) ||
                plugin.playersPlayingPvESlime.contains(uuid);
    }

    public boolean isPlayerChooseToPlayPvE(Player player) {
        UUID uuid = player.getUniqueId();
        boolean zombie = !plugin.playersChoicePvEZombie.isEmpty() && plugin.playersChoicePvEZombie.contains(uuid);
        boolean skeleton = !plugin.playersChoicePvESkeleton.isEmpty() && plugin.playersChoicePvESkeleton.contains(uuid);
        boolean spider = !plugin.playersChoicePvESpider.isEmpty() && plugin.playersChoicePvESpider.contains(uuid);
        boolean enderman = !plugin.playersChoicePvEEnderman.isEmpty() && plugin.playersChoicePvEEnderman.contains(uuid);
        boolean slime = !plugin.playersChoicePvESlime.isEmpty() && plugin.playersChoicePvESlime.contains(uuid);
        return zombie || skeleton || spider || enderman || slime;
    }
}
