package me.thegoldenmine.foodmaster.Listeners.KitPowerListeners;

import me.thegoldenmine.foodmaster.FoodMaster;
import me.thegoldenmine.foodmaster.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BreadPowerListener implements Listener {
    private final FoodMaster plugin;

    public BreadPowerListener(FoodMaster main) {
        plugin = main;
    }

    private void giveHeath(Player player) {
        UUID uuid = player.getUniqueId();
        double newHeathForPlayer = player.getHealth() + 10;
        if (plugin.playersInFishKit.contains(uuid) || plugin.playersInMelonKit.contains(uuid)) {
            if (newHeathForPlayer >= 35) {
                player.setHealth(35);
            } else {
                player.setHealth(newHeathForPlayer);
            }
        } else {
            if (newHeathForPlayer >= 20) {
                player.setHealth(20);
            } else {
                player.setHealth(newHeathForPlayer);
            }
        }
        if (plugin.isPlayerInGroup(player)) {
            for (UUID uuids : plugin.getPlayersInGroupOfPlayer(player)) {
                if (uuids != null) {
                    Player playerInGroup = Bukkit.getPlayer(uuids);
                    if (playerInGroup != null) {
                        playerInGroup.spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation().add(0, 1, 0), 20, 0.0000001, 0.0000001, 0.0000001, 0.2);
                    }
                }
            }
        } else {
            for (Player playerInGroup : Bukkit.getOnlinePlayers()) {
                if (playerInGroup != null) {
                    playerInGroup.spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation().add(0, 1, 0), 20, 0.0000001, 0.0000001, 0.0000001, 0.2);
                }
            }
        }
    }

    @EventHandler
    public void cookiePower(PlayerInteractEvent event) {
        /*
        Left Click - shoot poison

        Right Click - heals everyone in your team
        **/
        Action action = event.getAction();
        Player player = event.getPlayer();

        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor yellow = ChatColor.YELLOW;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor aqua = ChatColor.AQUA;
        String s;
        if (plugin.mainConfig.getStrMain("name") != null) {
            s = " "+plugin.mainConfig.getStrMain("name")+" ";
        } else {
            s = " FoodMaster ";
        }
        String WARN = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + yellow + "" + bold + "WARN " + darkGray + "" + strikethrough + "-" + yellow + "" + italic + " ";
        String INFO = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + aqua + "" + bold + "INFO " + darkGray + "" + strikethrough + "-" + aqua + "" + italic + " ";
        final int friendlyDamage = plugin.mainConfig.getIntGame("friendly_damage");
        final int damage = plugin.mainConfig.getIntGame("damage");
        if (player.getInventory().getItemInMainHand().equals(ItemManager.BreadKit)) {
            if (!plugin.isPlayerInGame(player)) {
                if (player.getInventory().contains(ItemManager.BreadKit)) {
                    player.getInventory().remove(ItemManager.BreadKit);
                }
                return;
            }
            if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
                if (plugin.kitPowerCoolDown.isPlayerInCoolDown(player)) {
                    player.sendMessage(WARN + "You have " + gold + "" + italic + "" + plugin.kitPowerCoolDown.getTime(player) + "" + yellow + "" + italic + " seconds left.");
                } else {
                    plugin.kitPowerCoolDown.addPlayerToCoolMap(player, 10);
                    // here
                    if (plugin.isPlayerPlayingFreeForAll(player) || plugin.playersInFoodGame.contains(player.getUniqueId())) {
                        UUID uuid = player.getUniqueId();
                        boolean doesPlayerHaveExtraHealth = plugin.playersInFishKit.contains(uuid) || plugin.playersInMelonKit.contains(uuid);
                        double newHeathForPlayer = player.getHealth() + 10;
                        if (doesPlayerHaveExtraHealth) {
                            if (newHeathForPlayer >= 35) {
                                player.setHealth(35);
                            } else {
                                player.setHealth(newHeathForPlayer);
                            }
                        } else {
                            if (newHeathForPlayer >= 20) {
                                player.setHealth(20);
                            } else {
                                player.setHealth(newHeathForPlayer);
                            }
                        }
                        for (UUID uuids : plugin.getPlayersInGroupOfPlayer(player)) {
                            if (uuids != null) {
                                Player playerInGroup = Bukkit.getPlayer(uuids);
                                if (playerInGroup != null) {
                                    playerInGroup.spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation().add(0, 1, 0), 20, 0.625, 0.625, 0.625, 0.4);
                                }
                            }
                        }
                        player.sendMessage(INFO+"You healed yourself");
                    } else if (plugin.isPlayerPlayingPvE(player)) {
                        if (plugin.isPlayerInGroup(player)) {
                            for (Entity entity : player.getNearbyEntities(15, 15, 15)) {
                                if (entity instanceof Player) {
                                    Player playerNear = (Player) entity;
                                    giveHeath(playerNear);
                                    for (UUID uuids : plugin.getPlayersInGroupOfPlayer(player)) {
                                        if (uuids != null) {
                                            Player playerInGroup = Bukkit.getPlayer(uuids);
                                            if (playerInGroup != null) {
                                                playerInGroup.spawnParticle(Particle.VILLAGER_HAPPY, playerNear.getLocation().add(0, 1, 0), 20, 0.625, 0.625, 0.625, 0.4);
                                            }
                                        }
                                    }
                                    playerNear.sendMessage(INFO+""+gold+""+player.getName()+""+aqua+""+italic+" healed you.");
                                    player.sendMessage(INFO+""+gold+""+playerNear.getName()+""+aqua+""+italic+" is healed "+ChatColor.RED+""+String.valueOf(((int)playerNear.getHealth()) * 2) +" hearts");
                                }
                            }
                        }
                        UUID uuid = player.getUniqueId();
                        double newHeathForPlayer = player.getHealth() + 10;
                        if (plugin.playersInFishKit.contains(uuid) || plugin.playersInMelonKit.contains(uuid)) {
                            if (newHeathForPlayer >= 35) {
                                player.setHealth(35);
                            } else {
                                player.setHealth(newHeathForPlayer);
                            }
                        } else {
                            if (newHeathForPlayer >= 20) {
                                player.setHealth(20);
                            } else {
                                player.setHealth(newHeathForPlayer);
                            }
                        }
                        if (plugin.isPlayerInGroup(player)) {
                            for (UUID uuids : plugin.getPlayersInGroupOfPlayer(player)) {
                                if (uuids != null) {
                                    Player playerInGroup = Bukkit.getPlayer(uuids);
                                    if (playerInGroup != null) {
                                        playerInGroup.spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation().add(0, 1, 0), 20, 0.625, 0.625, 0.625, 0.4);
                                    }
                                }
                            }
                        } else {
                            for (Player playerInGroup : Bukkit.getOnlinePlayers()) {
                                if (playerInGroup != null) {
                                    playerInGroup.spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation().add(0, 1, 0), 20, 0.625, 0.625, 0.625, 0.4);
                                }
                            }
                        }
                        player.sendMessage(INFO+"You healed yourself");
                    } else if (plugin.isPlayerPlayingFoodWars(player)) {
                        for (Entity entity : player.getNearbyEntities(15, 15, 15)) {
                            if (entity instanceof Player) {
                                Player playerNear = (Player) entity;
                                if (plugin.getPlayerTeam(player).equals(plugin.getPlayerTeam(playerNear))) {
                                    giveHeath(playerNear);
                                    for (UUID uuids : plugin.getPlayersInGroupOfPlayer(player)) {
                                        if (uuids != null) {
                                            Player playerInGroup = Bukkit.getPlayer(uuids);
                                            if (playerInGroup != null) {
                                                playerInGroup.spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation().add(0, 1, 0), 20, 0.625, 0.625, 0.625, 0.4);
                                            }
                                        }
                                    }
                                    player.sendMessage(INFO+""+gold+""+playerNear.getName()+""+aqua+""+italic+" is healed "+ChatColor.RED+""+String.valueOf(((int)playerNear.getHealth()) * 2) +" hearts");
                                }
                            }
                        }
                        UUID uuid = player.getUniqueId();
                        double newHeathForPlayer = player.getHealth() + 10;
                        if (plugin.playersInFishKit.contains(uuid) || plugin.playersInMelonKit.contains(uuid)) {
                            if (newHeathForPlayer >= 35) {
                                player.setHealth(35);
                            } else {
                                player.setHealth(newHeathForPlayer);
                            }
                        } else {
                            if (newHeathForPlayer >= 20) {
                                player.setHealth(20);
                            } else {
                                player.setHealth(newHeathForPlayer);
                            }
                        }
                        for (UUID uuids : plugin.getPlayersInGroupOfPlayer(player)) {
                            if (uuids != null) {
                                Player playerInGroup = Bukkit.getPlayer(uuids);
                                if (playerInGroup != null) {
                                    playerInGroup.spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation().add(0, 1, 0), 20, 0.625, 0.625, 0.625, 0.4);
                                }
                            }
                        }
                        player.sendMessage(INFO+"You healed yourself");
                    }
                }
            } else if (action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)) {
                if (plugin.kitsCoolDown.isPlayerInCoolDown(player)) {
                    player.sendMessage(WARN + "You have " + gold + "" + italic + "" + plugin.kitsCoolDown.getTime(player) + "" + yellow + "" + italic + " seconds left.");
                } else {
                    plugin.kitsCoolDown.addPlayerToCoolMap(player, 1);
                    // here
                    // flame
                    Location blockLoc = player.getTargetBlock(null, 15).getLocation();
                    List<Location> locationsNearTheExplotion = new ArrayList<>();
                    // Adds damage location
                    for (int x = blockLoc.getBlockX() - 3; x < 3 + blockLoc.getBlockX(); x++) {
                        for (int y = blockLoc.getBlockY() - 3; y < 3 + blockLoc.getBlockY(); y++) {
                            for (int z = blockLoc.getBlockZ() - 3; z < 3 + blockLoc.getBlockZ(); z++) {
                                Location loc1 = new Location(blockLoc.getWorld(), x, y, z);
                                locationsNearTheExplotion.add(loc1);
                            }
                        }
                    }
                    if (plugin.isPlayerInGroup(player)) {
                        for (UUID uuid : plugin.getPlayersInGroupOfPlayer(player)) {
                            if (uuid != null) {
                                Player players = Bukkit.getPlayer(uuid);
                                if (players != null) {
                                    players.spawnParticle(Particle.FLAME, blockLoc, 50, 0.4, 0.4, 0.4, 0.4);
                                }
                            }
                        }
                    } else {
                        for (Player players : Bukkit.getOnlinePlayers()) {
                            if (players != null) {
                                players.spawnParticle(Particle.FLAME, blockLoc, 50, 0.4, 0.4, 0.4, 0.4);
                            }
                        }
                    }
                    for (Entity entity : player.getNearbyEntities(15, 15, 15)) {
                        if (entity instanceof Slime) {
                            Slime slime = (Slime) entity;
                            Location loc = slime.getLocation();
                            int locX = loc.getBlockX();
                            int locY = loc.getBlockY();
                            int locZ = loc.getBlockZ();
                            Location playerNearLocation = new Location(player.getWorld(), locX, locY, locZ);
                            if (locationsNearTheExplotion.contains(playerNearLocation)) {
                                slime.damage(damage);
                            }
                        } else if (entity instanceof Enderman) {
                            Enderman enderman = (Enderman) entity;
                            Location loc = enderman.getLocation();
                            int locX = loc.getBlockX();
                            int locY = loc.getBlockY();
                            int locZ = loc.getBlockZ();
                            Location playerNearLocation = new Location(player.getWorld(), locX, locY, locZ);
                            if (locationsNearTheExplotion.contains(playerNearLocation)) {
                                enderman.damage(damage);
                            }
                        } else if (entity instanceof Skeleton) {
                            Skeleton skeleton = (Skeleton) entity;
                            Location loc = skeleton.getLocation();
                            int locX = loc.getBlockX();
                            int locY = loc.getBlockY();
                            int locZ = loc.getBlockZ();
                            Location playerNearLocation = new Location(player.getWorld(), locX, locY, locZ);
                            if (locationsNearTheExplotion.contains(playerNearLocation)) {
                                skeleton.damage(damage);
                            }
                        } else if (entity instanceof Zombie) {
                            Zombie zombie = (Zombie) entity;
                            Location loc = zombie.getLocation();
                            int locX = loc.getBlockX();
                            int locY = loc.getBlockY();
                            int locZ = loc.getBlockZ();
                            Location playerNearLocation = new Location(player.getWorld(), locX, locY, locZ);
                            if (locationsNearTheExplotion.contains(playerNearLocation)) {
                                zombie.damage(damage);
                            }
                        } else if (entity instanceof Spider) {
                            Spider spider = (Spider) entity;
                            Location loc = spider.getLocation();
                            int locX = loc.getBlockX();
                            int locY = loc.getBlockY();
                            int locZ = loc.getBlockZ();
                            Location playerNearLocation = new Location(player.getWorld(), locX, locY, locZ);
                            if (locationsNearTheExplotion.contains(playerNearLocation)) {
                                spider.damage(damage);
                            }
                        } else if (entity instanceof Player) {
                            Player playerNear = (Player) entity;
                            Location loc = playerNear.getLocation();
                            int locX = loc.getBlockX();
                            int locY = loc.getBlockY();
                            int locZ = loc.getBlockZ();
                            Location playerNearLocation = new Location(player.getWorld(), locX, locY, locZ);
                            if (locationsNearTheExplotion.contains(playerNearLocation)) {
                                UUID uniqueId = playerNear.getUniqueId();
                                boolean checkPlayerHealth = playerNear.getHealth() - damage < 1;
                                boolean checkLives = plugin.mainConfig.getBooleanGame("enable_lives_free-for-all") && plugin.lives.containsKey(uniqueId) && plugin.lives.get(uniqueId) == 0;
                                boolean respawnOrLives = checkLives || !plugin.mainConfig.getBooleanGame("respawn_free-for-all");
                                if (plugin.isPlayerPlayingFoodGame(player) && plugin.isPlayerPlayingFoodGame(playerNear)) {
                                    //damage
                                    if (!plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uniqueId)) {
                                        playerNear.sendMessage(INFO + "You have spawn protection for " + gold + "" + italic + "" + plugin.gameSpawnCoolDown.getTime(uniqueId) + "" + aqua + "" + italic + " seconds. You won't die.");
                                    } else {
                                        if (checkPlayerHealth) {
                                            UUID uuid = player.getUniqueId();
                                            plugin.playersInBreadKit.remove(uuid);
                                            plugin.playersInMelonKit.add(uuid);
                                            player.getInventory().removeItem(ItemManager.BreadKit);
                                            player.getInventory().addItem(ItemManager.MelonKit);
                                            plugin.kitsCoolDown.playerCoolDownMap.remove(uuid);
                                            plugin.kitPowerCoolDown.playerCoolDownMap.remove(uuid);
                                            plugin.respawnThePlayer(playerNear);
                                            // player - killer
                                            // playerNear - dead
                                            plugin.givePlayerKD(playerNear, player);
                                        } else {
                                            playerNear.damage(damage);
                                        }
                                    }
                                } else if (plugin.isPlayerPlayingFreeForAll(player) && plugin.isPlayerPlayingFreeForAll(playerNear)) {
                                    // Spawn protection
                                    if (!plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uniqueId)) {
                                        playerNear.sendMessage(INFO + "You have spawn protection for " + gold + "" + italic + "" + plugin.gameSpawnCoolDown.getTime(uniqueId) + "" + aqua + "" + italic + " seconds. You won't die.");
                                    } else {
                                        if (checkPlayerHealth && respawnOrLives) {
                                            // player - killer
                                            // playerNear - dead
                                            plugin.givePlayerKD(playerNear, player);
                                            plugin.thePlayerIsDead(playerNear);
                                        } else if (checkPlayerHealth && plugin.mainConfig.getBooleanGame("respawn_free-for-all")) {
                                            plugin.respawnThePlayer(playerNear);
                                            // player - killer
                                            // playerNear - dead
                                            plugin.givePlayerKD(playerNear, player);
                                        } else {
                                            playerNear.damage(damage);
                                        }
                                    }
                                    // plugin.isPlayerPlayingFoodWars(player) - false
                                } else if (plugin.isPlayerPlayingFoodWars(player) && plugin.isPlayerPlayingFoodWars(playerNear)) {
                                    // friendly-fire
                                    boolean checkLive1 = plugin.mainConfig.getBooleanGame("enable_lives_team_deathmatch") && plugin.lives.containsKey(uniqueId) && plugin.lives.get(uniqueId) == 0;
                                    boolean respawnOrLives1 = checkLive1 || !plugin.mainConfig.getBooleanGame("respawn_team_deathmatch");
                                    if (plugin.mainConfig.getBooleanGame("friendly-fire") && plugin.getPlayerTeam(player).equals(plugin.getPlayerTeam(playerNear))) {
                                        if (!plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uniqueId)) {
                                            playerNear.sendMessage(INFO + "You have spawn protection for " + gold + "" + italic + "" + plugin.gameSpawnCoolDown.getTime(uniqueId) + "" + aqua + "" + italic + " seconds. You won't die.");
                                        } else {
                                            if (playerNear.getHealth() - friendlyDamage < 1 && respawnOrLives1) {
                                                // player - killer
                                                // playerNear - dead
                                                plugin.thePlayerIsDead(playerNear);
                                                plugin.givePlayerKD(playerNear, player);
                                            } else if (playerNear.getHealth() - friendlyDamage < 1 && plugin.mainConfig.getBooleanGame("respawn_team_deathmatch")) {
                                                plugin.respawnThePlayer(playerNear);
                                                // player - killer
                                                // playerNear - dead
                                                plugin.givePlayerKD(playerNear, player);
                                            } else {
                                                playerNear.damage(friendlyDamage);
                                            }
                                        }
                                    } else if (!plugin.getPlayerTeam(player).equals(plugin.getPlayerTeam(playerNear))) {
                                        if (!plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uniqueId)) {
                                            playerNear.sendMessage(INFO + "You have spawn protection for " + gold + "" + italic + "" + plugin.gameSpawnCoolDown.getTime(uniqueId) + "" + aqua + "" + italic + " seconds. You won't die.");
                                        } else {
                                            if (checkPlayerHealth && respawnOrLives1) {
                                                // player - killer
                                                // playerNear - dead
                                                plugin.givePlayerKD(playerNear, player);
                                                plugin.thePlayerIsDead(playerNear);
                                            } else if (checkPlayerHealth && plugin.mainConfig.getBooleanGame("respawn_team_deathmatch")) {
                                                plugin.respawnThePlayer(playerNear);
                                                // player - killer
                                                // playerNear - dead
                                                plugin.givePlayerKD(playerNear, player);
                                            } else {
                                                playerNear.damage(damage);
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
}
