package me.thegoldenmine.foodmaster.Listeners.AntiGlitchListeners;

import me.thegoldenmine.foodmaster.FoodMaster;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PlayerDeathListener implements Listener {
    private final FoodMaster plugin;

    public PlayerDeathListener(FoodMaster main) {
        plugin = main;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
       Player player = event.getEntity();
        if (plugin.isPlayerInWaitingLobby(player) || plugin.isPlayerInGame(player)) {
            event.setDroppedExp(0);
            event.getDrops().clear();
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        NamespacedKey name = new NamespacedKey(plugin, "boss");
        NamespacedKey namePlayer = new NamespacedKey(plugin, "player");
        PersistentDataContainer data = entity.getPersistentDataContainer();
        if (data.has(name, PersistentDataType.STRING) && data.has(namePlayer, PersistentDataType.STRING)) {
            event.getDrops().clear();
            event.setDroppedExp(0);
        }
    }
}
