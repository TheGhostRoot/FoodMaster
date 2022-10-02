package me.thegoldenmine.foodmaster.Listeners.AntiGlitchListeners;

import me.thegoldenmine.foodmaster.FoodMaster;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class EndermanTpListener implements Listener {
    private final FoodMaster plugin;
    
    public EndermanTpListener(FoodMaster main) {
        plugin = main;
    }
    
    @EventHandler
    public void EndermanTel(EntityTeleportEvent event){
        Entity entity = event.getEntity();
        if (entity instanceof Enderman) {
            Enderman enderman = (Enderman) entity;
            NamespacedKey name = new NamespacedKey(plugin, "boss");
            PersistentDataContainer data = enderman.getPersistentDataContainer();
            if (data.has(name, PersistentDataType.STRING)) {
                if (data.get(name, PersistentDataType.STRING).equals("BOSS") || data.get(name, PersistentDataType.STRING).equals("MINI")) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
