package me.thegoldenmine.foodmaster.Listeners.AntiGlitchListeners;

import me.thegoldenmine.foodmaster.FoodMaster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class FallDamage implements Listener {
    private final FoodMaster plugin;

    public FallDamage(FoodMaster main) {
        plugin = main;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player && event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            Player player = (Player) event.getEntity();
            if (!plugin.mainConfig.getBooleanGame("enable_fall_damage")) {
                if (plugin.waitingLobby.isPlayerInWaitingLobby(player)) {
                    event.setCancelled(true);
                }
                if (plugin.game.isPlayerInGame(player)) {
                    event.setCancelled(true);
                }
                if (plugin.playerGroup.isPlayerInGroup(player)) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
