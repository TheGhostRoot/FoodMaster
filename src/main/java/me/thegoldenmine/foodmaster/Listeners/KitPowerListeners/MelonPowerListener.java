package me.thegoldenmine.foodmaster.Listeners.KitPowerListeners;

import me.thegoldenmine.foodmaster.*;
import me.thegoldenmine.foodmaster.Items.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class MelonPowerListener implements Listener {
    private final FoodMaster plugin;

    public MelonPowerListener(FoodMaster main) {
        plugin = main;
    }

    @EventHandler
    public void cookiePower(PlayerInteractEvent event) {
        Action action = event.getAction();
        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand().equals(ItemManager.MelonKit)) {
            if (!plugin.game.isPlayerInGame(player)) {
                if (player.getInventory().contains(ItemManager.MelonKit)) {
                    player.getInventory().remove(ItemManager.MelonKit);
                }
                return;
            }
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
            if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
                if (plugin.kitPowerCoolDown.isPlayerInCoolDown(player)) {
                    player.sendMessage(WARN + "You have " + gold + "" + italic + "" + plugin.kitPowerCoolDown.getTime(player) + "" + yellow + "" + italic + " seconds left.");
                } else {
                    plugin.kitPowerCoolDown.addPlayerToCoolMap(player, 10);
                    new BukkitRunnable() {
                        int balls = 0;

                        @Override
                        public void run() {
                            if (balls <= 5) {
                                Location targetBlockLocation = player.getTargetBlock(null, 15).getLocation();
                                Snowball snowball = player.getWorld().spawn(player.getEyeLocation(), Snowball.class);
                                snowball.setGlowing(true);
                                snowball.setShooter(player);
                                // 0.040
                                // /*
                                Location snowBallLocation = snowball.getLocation();
                                Location targetLocation;
                                if (player.getLocation().getYaw() == 0) {
                                    // south
                                    targetLocation = targetBlockLocation.add(1, 2, 0);
                                } else if (player.getLocation().getYaw() == -180) {
                                    // north
                                    targetLocation = targetBlockLocation.add(0, 2, 1);
                                } else {
                                    targetLocation = targetBlockLocation.add(0, 2, 0);
                                }
                                Vector velocityVector = targetLocation.subtract(snowBallLocation).toVector();
                                velocityVector = velocityVector.normalize().multiply(0.8);
                                snowball.setVelocity(velocityVector);
                                // */
                                balls++;
                            } else {
                                this.cancel();
                            }
                        }
                    }.runTaskTimer(plugin, 0, 2);
                }
            }
            if (action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)) {
                if (plugin.kitsCoolDown.isPlayerInCoolDown(player)) {
                    player.sendMessage(WARN + "You have " + gold + "" + italic + "" + plugin.kitsCoolDown.getTime(player) + "" + yellow + "" + italic + " seconds left.");
                } else {
                    plugin.kitsCoolDown.addPlayerToCoolMap(player, 1);
                    new BukkitRunnable() {
                        int balls = 0;

                        @Override
                        public void run() {
                            if (balls <= 5) {
                                final Location targetBlockLocation = player.getTargetBlock(null, 15).getLocation();
                                Snowball snowball = player.getWorld().spawn(player.getEyeLocation(), Snowball.class);
                                snowball.setGlowing(false);
                                snowball.setShooter(player);
                                // 0.055
                                Location snowBallLocation = snowball.getLocation();
                                Location targetLocation;
                                if (player.getLocation().getYaw() == 0) {
                                    // south
                                    targetLocation = targetBlockLocation.add(1, 2, 0);
                                } else if (player.getLocation().getYaw() == -180) {
                                    // north
                                    targetLocation = targetBlockLocation.add(0, 2, 1);
                                } else {
                                    targetLocation = targetBlockLocation.add(0, 2, 0);
                                }
                                Vector velocityVector = targetLocation.subtract(snowBallLocation).toVector();
                                velocityVector = velocityVector.normalize().multiply(1.01);
                                snowball.setVelocity(velocityVector);
                                balls++;
                            } else {
                                this.cancel();
                            }
                        }
                    }.runTaskTimer(plugin, 0, 2);
                }
            }
        }
    }
}
