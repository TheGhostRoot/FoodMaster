package me.thegoldenmine.foodmaster.Listeners.AntiGlitchListeners;

import me.thegoldenmine.foodmaster.FoodMaster;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.projectiles.ProjectileSource;

public class BreakListener implements Listener {
    private final FoodMaster plugin;

    public BreakListener(FoodMaster plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        //BlockBreakEvent
        Player player = event.getPlayer();
        if (plugin.isPlayerInGroup(player) && !plugin.isPlayerInWaitingLobby(player) && !plugin.isPlayerInGame(player) && !plugin.mainConfig.getBooleanMain("group_player_break_blocks")) {
            event.setCancelled(true);
        }
        if (plugin.isPlayerInWaitingLobby(player)) {
            event.setCancelled(true);
        }
        if (plugin.isPlayerInGame(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onExplosion(ExplosionPrimeEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof WitherSkull) {
            WitherSkull skull = (WitherSkull) entity;
            ProjectileSource shooter = skull.getShooter();
            if (shooter instanceof Player) {
                Player player = (Player) shooter;
                if (plugin.isPlayerInGame(player) && plugin.playersInPotatoKit.contains(player.getUniqueId())) {
                    // event.setRadius(0);
                    // event.setFire(false);
                    event.setCancelled(true);
                }
            }
        }
    }
}
