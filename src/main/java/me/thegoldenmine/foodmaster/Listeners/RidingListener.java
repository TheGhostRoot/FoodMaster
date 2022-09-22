package me.thegoldenmine.foodmaster.Listeners;

import me.thegoldenmine.foodmaster.FoodMaster;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class RidingListener implements Listener {
    private final FoodMaster plugin;

    public RidingListener(FoodMaster main) {
        plugin = main;
    }

    @EventHandler
    public void onClick(PlayerInteractAtEntityEvent event) {
        Entity entity = event.getRightClicked();
        NamespacedKey name = new NamespacedKey(plugin, "boss");
        NamespacedKey namePlayer = new NamespacedKey(plugin, "player");
        PersistentDataContainer data = entity.getPersistentDataContainer();
        Player playerRider = event.getPlayer();
        if (entity instanceof LivingEntity) {
            if (entity.getPassengers().contains(playerRider)) {
                boolean mob = data.has(name, PersistentDataType.STRING) && data.has(namePlayer, PersistentDataType.STRING);
                if (mob || entity instanceof Player) {
                    entity.removePassenger(playerRider);
                }
            }
            if (!playerRider.getPassengers().isEmpty() && playerRider.getPassengers().contains(entity)) {
                playerRider.removePassenger(entity);
            }
            if (plugin.mainConfig.getBooleanPvE("ride_pve_bosses") && data.has(name, PersistentDataType.STRING) && data.has(namePlayer, PersistentDataType.STRING)) {
                entity.addPassenger(playerRider);
            }
            if (plugin.mainConfig.getBooleanWaitLobby("ride_players") && entity instanceof Player) {
                Player playerEntity = (Player) entity;
                if (plugin.isPlayerInWaitingLobby(playerEntity) && plugin.isPlayerInWaitingLobby(playerRider)) {
                    playerEntity.addPassenger(playerRider);
                }
            }
        }
    }
}
