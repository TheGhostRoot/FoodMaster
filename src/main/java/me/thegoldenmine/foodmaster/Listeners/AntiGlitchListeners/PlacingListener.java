package me.thegoldenmine.foodmaster.Listeners.AntiGlitchListeners;

import me.thegoldenmine.foodmaster.FoodMaster;
import me.thegoldenmine.foodmaster.ItemManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlacingListener implements Listener {
    private final FoodMaster plugin;

    public PlacingListener(FoodMaster plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (plugin.isPlayerInGroup(player) && !plugin.isPlayerInGame(player) && !plugin.isPlayerInWaitingLobby(player) && !plugin.mainConfig.getBooleanMain("group_player_place_blocks")) {
            event.setCancelled(true);
        }
        if (player.getInventory().getItemInMainHand().equals(ItemManager.playAgain)) {
            event.setCancelled(true);
        }
        if (plugin.isPlayerInWaitingLobby(player)) {
            event.setCancelled(true);
        }
        if (plugin.isPlayerInGame(player)) {
            event.setCancelled(true);
        }
    }
}
