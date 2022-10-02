package me.thegoldenmine.foodmaster.CoolDown;

import me.thegoldenmine.foodmaster.FoodMaster;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class BossPower {
    private final FoodMaster plugin;

    public BossPower(FoodMaster main) {
        plugin = main;
        // jumps
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!Bukkit.getOnlinePlayers().isEmpty()) {
                    for (World world : Bukkit.getWorlds()) {
                        if (!world.getLivingEntities().isEmpty()) {
                            for (LivingEntity living : world.getLivingEntities()) {
                                NamespacedKey name = new NamespacedKey(plugin, "boss");
                                NamespacedKey namePlayer = new NamespacedKey(plugin, "player");
                                PersistentDataContainer data = living.getPersistentDataContainer();
                                if (living.getCustomName() != null && data.has(name, PersistentDataType.STRING) && data.has(namePlayer, PersistentDataType.STRING)) {
                                    if (living.getCustomName().contains(plugin.mainConfig.getStrPvE("Spider_Name")) && living instanceof Spider && plugin.mainConfig.getBooleanPvE("Spider_Jump")) {
                                        Spider spider = (Spider) living;
                                        if (spider.getTarget() != null) {
                                            spider.setVelocity(spider.getTarget().getLocation().add(0, 1, 0).subtract(spider.getLocation()).toVector().normalize().multiply(1.5));
                                        }
                                    }
                                    if (living.getCustomName().contains(plugin.mainConfig.getStrPvE("Zombie_Name")) && living instanceof Zombie && plugin.mainConfig.getBooleanPvE("Zombie_Jump")) {
                                        Zombie zombieBoss = (Zombie) living;
                                        if (zombieBoss.getTarget() != null) {
                                            zombieBoss.setVelocity(zombieBoss.getTarget().getLocation().add(0, 1, 0).subtract(zombieBoss.getLocation()).toVector().normalize().multiply(1.2));
                                        }
                                    }
                                    if (living.getCustomName().contains(plugin.mainConfig.getStrPvE("Slime_Name")) && living instanceof Slime && plugin.mainConfig.getBooleanPvE("Slime_Jump")) {
                                        Slime slime = (Slime) living;
                                        if (slime.getTarget() != null) {
                                            slime.setVelocity(slime.getTarget().getLocation().subtract(slime.getLocation()).toVector().normalize().multiply(1.2));
                                        }
                                    }
                                    if (living.getCustomName().contains(plugin.mainConfig.getStrPvE("Slime_Name")) && living instanceof Slime && plugin.mainConfig.getBooleanPvE("Slime_Spawn_Explosion") && data.get(name, PersistentDataType.STRING).equals("BOSS")) {
                                        Slime slime = (Slime) living;
                                        if (!slime.getNearbyEntities(10, 10, 10).isEmpty()) {
                                            for (Entity entity : slime.getNearbyEntities(10, 10, 10)) {
                                                if (entity instanceof Player) {
                                                    Player playerHit = (Player) entity;
                                                    slime.getWorld().spawnParticle(Particle.FLAME, playerHit.getLocation(), 30);
                                                    playerHit.damage(plugin.mainConfig.getIntPvE("Slime_Explosion_Damage"));
                                                }
                                            }
                                        }
                                    }
                                    if (living.getCustomName().contains(plugin.mainConfig.getStrPvE("Enderman_Name")) && living instanceof Enderman && plugin.mainConfig.getBooleanPvE("Enderman_Jump")) {
                                        Enderman enderman = (Enderman) living;
                                        if (enderman.getTarget() != null) {
                                            enderman.setVelocity(enderman.getTarget().getLocation().subtract(enderman.getLocation()).toVector().normalize().multiply(1.2));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 200);

        // thunder and fireball
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!Bukkit.getOnlinePlayers().isEmpty()) {
                    for (World world : Bukkit.getWorlds()) {
                        if (!world.getLivingEntities().isEmpty()) {
                            for (LivingEntity living : world.getLivingEntities()) {
                                NamespacedKey name = new NamespacedKey(plugin, "boss");
                                NamespacedKey namePlayer = new NamespacedKey(plugin, "player");
                                PersistentDataContainer data = living.getPersistentDataContainer();
                                if (living.getCustomName() != null && data.has(name, PersistentDataType.STRING) && data.has(namePlayer, PersistentDataType.STRING)) {
                                    if (living instanceof Zombie) {
                                        Zombie zombie = (Zombie) living;
                                        if (zombie.getTarget() == null && !zombie.getNearbyEntities(10, 10, 10).isEmpty()) {
                                            for (Entity entity : zombie.getNearbyEntities(10, 10, 10)) {
                                                if (entity instanceof Player) {
                                                    Player players = (Player) entity;
                                                    if (players.getGameMode().equals(GameMode.SURVIVAL)) {
                                                        zombie.setTarget(players);
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (living instanceof Slime) {
                                        Slime slime = (Slime) living;
                                        if (slime.getTarget() == null && !slime.getNearbyEntities(10, 10, 10).isEmpty()) {
                                            for (Entity entity : slime.getNearbyEntities(10, 10, 10)) {
                                                if (entity instanceof Player) {
                                                    Player players = (Player) entity;
                                                    if (players.getGameMode().equals(GameMode.SURVIVAL)) {
                                                        slime.setTarget(players);
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (living instanceof Enderman) {
                                        Enderman enderman = (Enderman) living;
                                        if (enderman.getTarget() == null && !enderman.getNearbyEntities(10, 10, 10).isEmpty()) {
                                            for (Entity entity : enderman.getNearbyEntities(10, 10, 10)) {
                                                if (entity instanceof Player) {
                                                    Player players = (Player) entity;
                                                    if (players.getGameMode().equals(GameMode.SURVIVAL)) {
                                                        enderman.setTarget(players);
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (living instanceof Spider) {
                                        Spider spider = (Spider) living;
                                        if (spider.getTarget() == null && !spider.getNearbyEntities(10, 10, 10).isEmpty()) {
                                            for (Entity entity : spider.getNearbyEntities(10, 10, 10)) {
                                                if (entity instanceof Player) {
                                                    Player players = (Player) entity;
                                                    if (players.getGameMode().equals(GameMode.SURVIVAL)) {
                                                        spider.setTarget(players);
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (living instanceof Skeleton && data.get(name, PersistentDataType.STRING).equals("BOSS")) {
                                        Skeleton skeleton = (Skeleton) living;
                                        if (skeleton.getTarget() == null && !skeleton.getNearbyEntities(10, 10, 10).isEmpty()) {
                                            for (Entity entity : skeleton.getNearbyEntities(10, 10, 10)) {
                                                if (entity instanceof Player) {
                                                    Player players = (Player) entity;
                                                    if (players.getGameMode().equals(GameMode.SURVIVAL)) {
                                                        skeleton.setTarget(players);
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                        if (plugin.mainConfig.getBooleanPvE("Skeleton_Shoot_Fireballs") && skeleton.getTarget() != null) {
                                            WitherSkull fireball = skeleton.launchProjectile(WitherSkull.class);
                                            fireball.setShooter(skeleton);
                                            fireball.setYield(0);
                                        }
                                        if (plugin.mainConfig.getBooleanPvE("Skeleton_Spawn_Thunder") && !skeleton.getNearbyEntities(7, 7, 7).isEmpty()) {
                                            for (Entity entity : skeleton.getNearbyEntities(7, 7, 7)) {
                                                if (entity instanceof Player) {
                                                    Player players = (Player) entity;
                                                    if (players.getGameMode().equals(GameMode.SURVIVAL)) {
                                                        skeleton.getWorld().strikeLightningEffect(players.getLocation());
                                                        players.damage(plugin.mainConfig.getIntPvE("Skeleton_Thunder_Damage"));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 120);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!Bukkit.getWorlds().isEmpty()) {
                    for (World world : Bukkit.getWorlds()) {
                        if (!world.getLivingEntities().isEmpty()) {
                            for (LivingEntity entity : world.getLivingEntities()) {
                                NamespacedKey name = new NamespacedKey(plugin, "boss");
                                NamespacedKey namePlayer = new NamespacedKey(plugin, "player");
                                PersistentDataContainer data = entity.getPersistentDataContainer();
                                if (data.has(name, PersistentDataType.STRING) && data.has(namePlayer, PersistentDataType.STRING) && entity instanceof Enderman) {
                                    String playerName = data.get(namePlayer, PersistentDataType.STRING);
                                    if (playerName != null) {
                                        Player player = Bukkit.getPlayer(playerName);
                                        if (player != null && player.getLocation().add(0, 1, 0).getBlock().getType().equals(Material.AIR)) {
                                            if (plugin.playerGroup.isPlayerInGroup(player)) {
                                                List<UUID> group = new ArrayList<>(plugin.playerGroup.getPlayersInGroupOfPlayer(player));
                                                int rand = new Random().nextInt(group.size() - 1);
                                                UUID uuid = group.get(rand);
                                                Player randPlayer = Bukkit.getPlayer(uuid);
                                                if (randPlayer != null) {
                                                    if (data.get(name, PersistentDataType.STRING).equals("BOSS") && plugin.mainConfig.getBooleanPvE("Enderman_Teleport_To_Player")) {
                                                        entity.teleport(randPlayer.getLocation());
                                                    }
                                                    if (data.get(name, PersistentDataType.STRING).equals("MINI") && plugin.mainConfig.getBooleanPvE("Enderman_Minion_Teleport_To_Player")) {
                                                        entity.teleport(randPlayer.getLocation());
                                                    }
                                                }
                                                return;
                                            }
                                            if (data.get(name, PersistentDataType.STRING).equals("BOSS") && plugin.mainConfig.getBooleanPvE("Enderman_Teleport_To_Player")) {
                                                entity.teleport(player.getLocation());
                                            }
                                            if (data.get(name, PersistentDataType.STRING).equals("MINI") && plugin.mainConfig.getBooleanPvE("Enderman_Minion_Teleport_To_Player")) {
                                                entity.teleport(player.getLocation());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 100);
    }
}
