package me.thegoldenmine.foodmaster.Listeners;

import me.thegoldenmine.foodmaster.FoodMaster;
import me.thegoldenmine.foodmaster.Items.ItemManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class ReadyListener implements Listener {
    private final FoodMaster plugin;

    public ReadyListener(FoodMaster plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onReady(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        if (action.equals(Action.RIGHT_CLICK_BLOCK) || action.equals(Action.RIGHT_CLICK_AIR)) {
            String ReadyName = Objects.requireNonNull(ItemManager.isReady.getItemMeta()).getDisplayName();
            String NotReadyName = Objects.requireNonNull(ItemManager.notReady.getItemMeta()).getDisplayName();
            boolean b = player.getInventory().getItemInMainHand().equals(plugin.game.getItemByName(player.getInventory(), ReadyName)) || player.getInventory().getItemInMainHand().equals(plugin.game.getItemByName(player.getInventory(), NotReadyName));
            if (b && !plugin.playerGroup.isPlayerInGroup(player) && plugin.playerPvE.isPlayerChooseToPlayPvE(player)) {
                plugin.ReadySystem.add(player.getUniqueId());
            }
            if (b && plugin.playerGroup.isPlayerInGroup(player)) {
                plugin.ReadySystem.add(player.getUniqueId());
            }
        }
    }
}
