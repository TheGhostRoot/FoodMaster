package me.thegoldenmine.foodmaster.Listeners.AntiGlitchListeners;

import me.thegoldenmine.foodmaster.FoodMaster;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class PickupListener implements Listener {
    private final FoodMaster plugin;

    public PickupListener(FoodMaster main) {
        plugin = main;
    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent event) {
        ItemStack item = event.getItem().getItemStack();
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity instanceof Player) {
            Player player = (Player) livingEntity;
            if (plugin.isPlayerInGroup(player) && !plugin.isPlayerInWaitingLobby(player) && !plugin.isPlayerInGame(player) &&  !plugin.mainConfig.getBooleanMain("group_player_pickup")) {
                event.setCancelled(true);
            }
            if (plugin.isPlayerInGame(player) || plugin.isPlayerInWaitingLobby(player)) {
                event.setCancelled(true);
            }
        }
        if (plugin.items.contains(item)) {
            event.setCancelled(true);
        }
    }
}
