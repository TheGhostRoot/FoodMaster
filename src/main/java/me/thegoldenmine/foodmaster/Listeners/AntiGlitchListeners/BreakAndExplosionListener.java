package me.thegoldenmine.foodmaster.Listeners.AntiGlitchListeners;

import me.thegoldenmine.foodmaster.FoodMaster;
import me.thegoldenmine.foodmaster.Items.ItemManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.projectiles.ProjectileSource;

public class BreakAndExplosionListener implements Listener {
    private final FoodMaster plugin;

    public BreakAndExplosionListener(FoodMaster plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        //BlockBreakEvent
        Player player = event.getPlayer();
        if (plugin.playerGroup.isPlayerInGroup(player) && !plugin.waitingLobby.isPlayerInWaitingLobby(player) && !plugin.game.isPlayerInGame(player) && !plugin.mainConfig.getBooleanMain("group_player_break_blocks")) {
            event.setCancelled(true);
        }
        if (plugin.waitingLobby.isPlayerInWaitingLobby(player)) {
            event.setCancelled(true);
        }
        if (plugin.game.isPlayerInGame(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (plugin.playerGroup.isPlayerInGroup(player) && !plugin.game.isPlayerInGame(player) && !plugin.waitingLobby.isPlayerInWaitingLobby(player) && !plugin.mainConfig.getBooleanMain("group_player_place_blocks")) {
            event.setCancelled(true);
        }
        if (player.getInventory().getItemInMainHand().equals(ItemManager.playAgain)) {
            event.setCancelled(true);
        }
        if (plugin.waitingLobby.isPlayerInWaitingLobby(player)) {
            event.setCancelled(true);
        }
        if (plugin.game.isPlayerInGame(player)) {
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
                if (plugin.game.isPlayerInGame(player) && plugin.playersInPotatoKit.contains(player.getUniqueId())) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
