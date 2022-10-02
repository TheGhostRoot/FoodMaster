package me.thegoldenmine.foodmaster.Listeners.AntiGlitchListeners;

import me.thegoldenmine.foodmaster.FoodMaster;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class ItemPickupAndDropListener implements Listener {
    private final FoodMaster plugin;

    public ItemPickupAndDropListener(FoodMaster main) {
        plugin = main;
    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent event) {
        ItemStack item = event.getItem().getItemStack();
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity instanceof Player) {
            Player player = (Player) livingEntity;
            if (plugin.playerGroup.isPlayerInGroup(player) && !plugin.waitingLobby.isPlayerInWaitingLobby(player) && !plugin.game.isPlayerInGame(player) &&  !plugin.mainConfig.getBooleanMain("group_player_pickup")) {
                event.setCancelled(true);
            }
            if (plugin.game.isPlayerInGame(player) || plugin.waitingLobby.isPlayerInWaitingLobby(player)) {
                event.setCancelled(true);
            }
        }
        if (plugin.items.contains(item)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Item item = event.getItemDrop();
        if (plugin.items.contains(item.getItemStack())) {
            event.setCancelled(true);
        }
    }
}
