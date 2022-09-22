package me.thegoldenmine.foodmaster.Listeners.KitPowerListeners;

import me.thegoldenmine.foodmaster.FoodMaster;
import me.thegoldenmine.foodmaster.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class PotatoPowerListener implements Listener {
    private final FoodMaster plugin;

    public PotatoPowerListener(FoodMaster main) {
        plugin = main;
    }

    @EventHandler
    public void cookiePower(PlayerInteractEvent event) {
        Action action = event.getAction();
        Player player = event.getPlayer();

        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor yellow = ChatColor.YELLOW;
        ChatColor italic = ChatColor.ITALIC;
        String s;
        if (plugin.mainConfig.getStrMain("name") != null) {
            s = " "+plugin.mainConfig.getStrMain("name")+" ";
        } else {
            s = " FoodMaster ";
        }
        String WARN = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + yellow + "" + bold + "WARN " + darkGray + "" + strikethrough + "-" + yellow + "" + italic + " ";
        if (player.getInventory().getItemInMainHand().equals(ItemManager.PotatoKit)) {
            if (!plugin.isPlayerInGame(player)) {
                if (player.getInventory().contains(ItemManager.PotatoKit)) {
                    player.getInventory().remove(ItemManager.PotatoKit);
                }
                return;
            }
            if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
                if (plugin.kitPowerCoolDown.isPlayerInCoolDown(player)) {
                    player.sendMessage(WARN + "You have " + gold + "" + italic + "" + plugin.kitPowerCoolDown.getTime(player) + "" + yellow + "" + italic + " seconds left.");
                } else {
                    plugin.kitPowerCoolDown.addPlayerToCoolMap(player, 10);
                  //  /*
                    new BukkitRunnable() {
                        int balls = 0;

                        @Override
                        public void run() {
                            if (balls <= 5) {
                                if (!plugin.playersInPotatoKit.contains(player.getUniqueId())) {
                                    this.cancel();
                                    return;
                                }
                                WitherSkull fireball = player.getWorld().spawn(player.getEyeLocation(), WitherSkull.class);
                                fireball.setYield(0);
                                fireball.setDirection(player.getTargetBlock(null, 15).getLocation().subtract(fireball.getLocation()).toVector().multiply(0.075));
                                fireball.setGlowing(true);
                                fireball.setCharged(true);
                                fireball.setShooter(player);
                                balls++;
                            } else {
                                this.cancel();
                            }
                        }
                    }.runTaskTimer(plugin, 0, 5);

                    // */
                }
            }
            if (action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)) {
                if (plugin.kitsCoolDown.isPlayerInCoolDown(player)) {
                    player.sendMessage(WARN + "You have " + gold + "" + italic + "" + plugin.kitsCoolDown.getTime(player) + "" + yellow + "" + italic + " seconds left.");
                } else {
                    plugin.kitsCoolDown.addPlayerToCoolMap(player, 1);
                   // /*
                    new BukkitRunnable() {
                        int balls = 0;

                        @Override
                        public void run() {
                            if (balls <= 5) {
                                if (!plugin.playersInPotatoKit.contains(player.getUniqueId())) {
                                    this.cancel();
                                    return;
                                }
                                WitherSkull fireball = player.getWorld().spawn(player.getEyeLocation(), WitherSkull.class);
                                fireball.setYield(0);
                                fireball.setDirection(player.getTargetBlock(null, 15).getLocation().subtract(fireball.getLocation()).toVector().multiply(0.075));
                                fireball.setShooter(player);
                                balls++;
                            } else {
                                this.cancel();
                            }
                        }
                    }.runTaskTimer(plugin, 0, 5);

                  //   */
                }
            }
        }
    }
}
