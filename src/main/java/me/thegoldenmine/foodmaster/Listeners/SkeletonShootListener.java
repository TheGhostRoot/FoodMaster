package me.thegoldenmine.foodmaster.Listeners;

import me.thegoldenmine.foodmaster.FoodMaster;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class SkeletonShootListener implements Listener {
    private final FoodMaster plugin;

    public SkeletonShootListener(FoodMaster main) {
        plugin = main;
    }


    @EventHandler
    public void onShoot(EntityShootBowEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof Skeleton) {
            Skeleton skeleton = (Skeleton) entity;
            NamespacedKey name = new NamespacedKey(plugin, "boss");
            NamespacedKey namePlayer = new NamespacedKey(plugin, "player");
            PersistentDataContainer data = skeleton.getPersistentDataContainer();
            if (data.has(name, PersistentDataType.STRING) && data.has(namePlayer, PersistentDataType.STRING) && plugin.mainConfig.getBooleanPvE("Skeleton_Arrow_Rapid_Fire")) {
                new BukkitRunnable() {
                    int arrows = 0;
                    @Override
                    public void run() {
                        if (arrows <= 5) {
                            skeleton.launchProjectile(Arrow.class);
                            arrows++;
                        } else {
                            this.cancel();
                        }
                    }
                }.runTaskTimer(plugin, 0, 7);
            }
        }
    }
}
