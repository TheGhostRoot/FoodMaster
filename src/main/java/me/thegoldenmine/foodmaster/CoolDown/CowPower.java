package me.thegoldenmine.foodmaster.CoolDown;

import me.thegoldenmine.foodmaster.FoodMaster;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class CowPower {
    private final FoodMaster plugin;

    public CowPower(FoodMaster main) {
        plugin = main;

        new BukkitRunnable() {

            @Override
            public void run() {
                if (!plugin.allCows.isEmpty()) {
                    List<Cow> removeCows = new ArrayList<>();
                    for (Cow cow : plugin.allCows) {
                        NamespacedKey name = new NamespacedKey(plugin, "p");
                        NamespacedKey time = new NamespacedKey(plugin, "time");
                        PersistentDataContainer data = cow.getPersistentDataContainer();
                        if (data.has(name, PersistentDataType.STRING) && data.has(time, PersistentDataType.INTEGER)) {
                            int timer = data.get(time, PersistentDataType.INTEGER);
                            // 1200
                            if (cow.isOnGround() || timer == 60 || cow.isSwimming()) {
                                // explosion and kill the cow and end the runnable
                                String playerName = data.get(name, PersistentDataType.STRING);
                                if (playerName != null) {
                                    Player player = Bukkit.getPlayer(playerName);
                                    if (player != null) {
                                        plugin.beefPowerListener.DealDamage(cow, player);
                                        Location loc = cow.getLocation();
                                        loc.setY(-2000);
                                        loc.setX(0);
                                        loc.setZ(0);
                                        cow.setInvulnerable(false);
                                        cow.teleport(loc);
                                        removeCows.add(cow);
                                    }
                                }
                            } else {
                                data.set(time, PersistentDataType.INTEGER, ++timer);
                            }
                        }
                    }
                    if (!removeCows.isEmpty()) {
                        for (Cow cow : removeCows) {
                            plugin.allCows.remove(cow);
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 10);
    }
}
