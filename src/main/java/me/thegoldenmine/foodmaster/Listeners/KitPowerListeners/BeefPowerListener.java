package me.thegoldenmine.foodmaster.Listeners.KitPowerListeners;

import me.thegoldenmine.foodmaster.FoodMaster;
import me.thegoldenmine.foodmaster.Items.ItemManager;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BeefPowerListener implements Listener {
    private final FoodMaster plugin;

    public BeefPowerListener(FoodMaster main) {
        plugin = main;
    }

    public void DealDamage(Cow cow, Player player) {
        UUID uuid = player.getUniqueId();
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor yellow = ChatColor.YELLOW;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor aqua = ChatColor.AQUA;
        String s;
        if (plugin.mainConfig.getStrMain("name") != null) {
            s = " " + plugin.mainConfig.getStrMain("name") + " ";
        } else {
            s = " FoodMaster ";
        }
        String WARN = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + yellow + "" + bold + "WARN " + darkGray + "" + strikethrough + "-" + yellow + "" + italic + " ";
        String INFO = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + aqua + "" + bold + "INFO " + darkGray + "" + strikethrough + "-" + aqua + "" + italic + " ";
        final int friendlyDamage = plugin.mainConfig.getIntGame("friendly_damage");
        final int damageGame = plugin.mainConfig.getIntGame("damage");
        List<Location> locationsNearTheLightning = new ArrayList<>();
        // Adds damage location
        Location blockLocation = cow.getLocation();
        World playerInLookingAtWorld = cow.getWorld();
        for (int x = blockLocation.getBlockX() - 3; x < 3 + blockLocation.getBlockX(); x++) {
            for (int y = blockLocation.getBlockY() - 3; y < 3 + blockLocation.getBlockY(); y++) {
                for (int z = blockLocation.getBlockZ() - 3; z < 3 + blockLocation.getBlockZ(); z++) {
                    Location loc = new Location(playerInLookingAtWorld, x, y, z);
                    locationsNearTheLightning.add(loc);
                }
            }
        }
        if (!player.getNearbyEntities(15, 15, 15).isEmpty()) {
            for (Entity entity : player.getNearbyEntities(15, 15, 15)) {
                if (entity instanceof Enderman) {
                    Enderman enderman = (Enderman) entity;
                    Location loc = enderman.getLocation();
                    int locX = loc.getBlockX();
                    int locY = loc.getBlockY();
                    int locZ = loc.getBlockZ();
                    Location playerNearLocation = new Location(playerInLookingAtWorld, locX, locY, locZ);
                    if (locationsNearTheLightning.contains(playerNearLocation)) {
                        enderman.damage(damageGame);
                    }
                }
                if (entity instanceof Slime) {
                    Slime slime = (Slime) entity;
                    Location loc = slime.getLocation();
                    int locX = loc.getBlockX();
                    int locY = loc.getBlockY();
                    int locZ = loc.getBlockZ();
                    Location playerNearLocation = new Location(playerInLookingAtWorld, locX, locY, locZ);
                    if (locationsNearTheLightning.contains(playerNearLocation)) {
                        slime.damage(damageGame);
                    }
                }
                if (entity instanceof Zombie) {
                    Zombie zombie = (Zombie) entity;
                    Location loc = zombie.getLocation();
                    int locX = loc.getBlockX();
                    int locY = loc.getBlockY();
                    int locZ = loc.getBlockZ();
                    Location playerNearLocation = new Location(playerInLookingAtWorld, locX, locY, locZ);
                    if (locationsNearTheLightning.contains(playerNearLocation)) {
                        zombie.damage(damageGame);
                    }
                }
                if (entity instanceof Skeleton) {
                    Skeleton skeleton = (Skeleton) entity;
                    Location loc = skeleton.getLocation();
                    int locX = loc.getBlockX();
                    int locY = loc.getBlockY();
                    int locZ = loc.getBlockZ();
                    Location playerNearLocation = new Location(playerInLookingAtWorld, locX, locY, locZ);
                    if (locationsNearTheLightning.contains(playerNearLocation)) {
                        skeleton.damage(damageGame);
                    }
                }
                if (entity instanceof Spider) {
                    Spider spider = (Spider) entity;
                    Location loc = spider.getLocation();
                    int locX = loc.getBlockX();
                    int locY = loc.getBlockY();
                    int locZ = loc.getBlockZ();
                    Location playerNearLocation = new Location(playerInLookingAtWorld, locX, locY, locZ);
                    if (locationsNearTheLightning.contains(playerNearLocation)) {
                        spider.damage(damageGame);
                    }
                }
                if (entity instanceof Player) {
                    Player playerNear = (Player) entity;
                    Location loc = playerNear.getLocation();
                    int locX = loc.getBlockX();
                    int locY = loc.getBlockY();
                    int locZ = loc.getBlockZ();
                    Location playerNearLocation = new Location(playerInLookingAtWorld, locX, locY, locZ);
                    if (locationsNearTheLightning.contains(playerNearLocation)) {
                        UUID uniqueId = playerNear.getUniqueId();
                        boolean checkPlayerHealth = playerNear.getHealth() - damageGame < 1;
                        boolean checkLives = plugin.mainConfig.getBooleanGame("enable_lives_free-for-all") && plugin.lives.containsKey(uniqueId) && plugin.lives.get(uniqueId) == 0;
                        boolean respawnOrLives = checkLives || !plugin.mainConfig.getBooleanGame("respawn_free-for-all");
                        if (plugin.isPlayerPlayingFoodGame(player) && plugin.isPlayerPlayingFoodGame(playerNear)) {
                            //damage
                            if (!plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uniqueId)) {
                                playerNear.sendMessage(INFO + "You have spawn protection for " + gold + "" + italic + "" + plugin.gameSpawnCoolDown.getTime(uniqueId) + "" + aqua + "" + italic + " seconds. You won't die.");
                            } else {
                                if (checkPlayerHealth) {
                                    plugin.FoodGameWinner.add(uuid);
                                    Set<UUID> groupClone = plugin.playerGroup.getPlayersInGroupOfPlayer(player);
                                    for (UUID uuid8 : groupClone) {
                                        Player playerInGroup = Bukkit.getPlayer(uuid8);
                                        if (playerInGroup != null) {
                                            plugin.endTheGame.endTheGameWithStatus(playerInGroup);
                                        }
                                    }
                                    for (UUID uuid7 : groupClone) {
                                        if (uuid7 != null) {
                                            plugin.inGameKills.remove(uuid7);
                                            plugin.inGameDeaths.remove(uuid7);
                                            plugin.playersInBlueTeams.remove(uuid7);
                                            plugin.playersInCyanTeams.remove(uuid7);
                                            plugin.playersInRedTeams.remove(uuid7);
                                            plugin.playersInGreenTeams.remove(uuid7);
                                            plugin.playersInYellowTeams.remove(uuid7);
                                            plugin.playersRandomTeam.remove(uuid7);
                                            plugin.playersChoiceFoodGame.remove(uuid7);
                                            plugin.playersInFoodGame.remove(uuid7);
                                            plugin.FoodGameWinner.remove(uuid7);
                                        }
                                    }
                                    for (UUID uuid6 : plugin.winners) {
                                        if (uuid6 != null) {
                                            Player player11 = Bukkit.getPlayer(uuid6);
                                            if (player11 != null) {
                                                plugin.giveOneWinToPlayer.givePlayerWin(player11);
                                            }
                                        }
                                    }
                                    for (UUID uuid5 : plugin.losses) {
                                        if (uuid5 != null) {
                                            Player player11 = Bukkit.getPlayer(uuid5);
                                            if (player11 != null) {
                                                plugin.giveOneLoseToPlayer.givePlayerLose(player11);
                                            }
                                        }
                                    }
                                    plugin.winners.clear();
                                    plugin.losses.clear();
                                } else {
                                    playerNear.damage(damageGame);
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
                                    plugin.giveOneKillAndDeathToPlayer.givePlayerKD(playerNear, player);
                                    plugin.playerDead.thePlayerIsDead(playerNear);
                                } else if (checkPlayerHealth && plugin.mainConfig.getBooleanGame("respawn_free-for-all")) {
                                    plugin.respawnPlayer.respawnThePlayer(playerNear);
                                    // player - killer
                                    // playerNear - dead
                                    plugin.giveOneKillAndDeathToPlayer.givePlayerKD(playerNear, player);
                                } else {
                                    playerNear.damage(damageGame);
                                }
                            }
                        } else if (plugin.deathmatch.isPlayerPlayingFoodWars(player) && plugin.deathmatch.isPlayerPlayingFoodWars(playerNear)) {
                            // friendly-fire
                            boolean checkLive1 = plugin.mainConfig.getBooleanGame("enable_lives_team_deathmatch") && plugin.lives.containsKey(uniqueId) && plugin.lives.get(uniqueId) == 0;
                            boolean respawnOrLives1 = checkLive1 || !plugin.mainConfig.getBooleanGame("respawn_team_deathmatch");
                            if (plugin.mainConfig.getBooleanGame("friendly-fire") && plugin.deathmatch.getPlayerTeam(player).equals(plugin.deathmatch.getPlayerTeam(playerNear))) {
                                if (!plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uniqueId)) {
                                    playerNear.sendMessage(INFO + "You have spawn protection for " + gold + "" + italic + "" + plugin.gameSpawnCoolDown.getTime(uniqueId) + "" + aqua + "" + italic + " seconds. You won't die.");
                                } else {
                                    if (playerNear.getHealth() - friendlyDamage < 1 && respawnOrLives1) {
                                        // player - killer
                                        // playerNear - dead
                                        plugin.giveOneKillAndDeathToPlayer.givePlayerKD(playerNear, player);
                                        plugin.playerDead.thePlayerIsDead(playerNear);
                                    } else if (playerNear.getHealth() - friendlyDamage < 1 && plugin.mainConfig.getBooleanGame("respawn_team_deathmatch")) {
                                        plugin.respawnPlayer.respawnThePlayer(playerNear);
                                        // player - killer
                                        // playerNear - dead
                                        plugin.giveOneKillAndDeathToPlayer.givePlayerKD(playerNear, player);
                                    } else {
                                        playerNear.damage(friendlyDamage);
                                    }
                                }
                            } else if (!plugin.deathmatch.getPlayerTeam(player).equals(plugin.deathmatch.getPlayerTeam(playerNear))) {
                                if (!plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uniqueId)) {
                                    playerNear.sendMessage(INFO + "You have spawn protection for " + gold + "" + italic + "" + plugin.gameSpawnCoolDown.getTime(uniqueId) + "" + aqua + "" + italic + " seconds. You won't die.");
                                } else {
                                    if (checkPlayerHealth && respawnOrLives1) {
                                        // player - killer
                                        // playerNear - dead
                                        plugin.giveOneKillAndDeathToPlayer.givePlayerKD(playerNear, player);
                                        plugin.playerDead.thePlayerIsDead(playerNear);
                                    } else if (checkPlayerHealth && plugin.mainConfig.getBooleanGame("respawn_team_deathmatch")) {
                                        plugin.respawnPlayer.respawnThePlayer(playerNear);
                                        // player - killer
                                        // playerNear - dead
                                        plugin.giveOneKillAndDeathToPlayer.givePlayerKD(playerNear, player);
                                    } else {
                                        playerNear.damage(damageGame);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            locationsNearTheLightning.clear();
        }
    }

    @EventHandler
    public void onBeef(PlayerInteractEvent event) {
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor yellow = ChatColor.YELLOW;
        ChatColor italic = ChatColor.ITALIC;
        String s;
        if (plugin.mainConfig.getStrMain("name") != null) {
            s = " " + plugin.mainConfig.getStrMain("name") + " ";
        } else {
            s = " FoodMaster ";
        }
        String WARN = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + yellow + "" + bold + "WARN " + darkGray + "" + strikethrough + "-" + yellow + "" + italic + " ";
        /*
        Left Click - launches cow that explode when hit the ground

        Right Click - rapid fires cows
        * */
        Action action = event.getAction();
        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand().equals(ItemManager.BeefKit)) {
            if (!plugin.game.isPlayerInGame(player)) {
                if (player.getInventory().contains(ItemManager.BeefKit)) {
                    player.getInventory().remove(ItemManager.BeefKit);
                }
                return;
            }
            if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
                if (plugin.kitPowerCoolDown.isPlayerInCoolDown(player)) {
                    player.sendMessage(WARN + "You have " + gold + "" + italic + "" + plugin.kitPowerCoolDown.getTime(player) + "" + yellow + "" + italic + " seconds left.");
                } else {
                    plugin.kitPowerCoolDown.addPlayerToCoolMap(player, 10);
                    new BukkitRunnable() {
                        int cows = 0;

                        @Override
                        public void run() {
                            if (cows <= 5) {
                                NamespacedKey namePlayer = new NamespacedKey(plugin, "p");
                                NamespacedKey time = new NamespacedKey(plugin, "time");
                                Cow cow = player.getWorld().spawn(player.getEyeLocation(), Cow.class);
                                cow.setInvulnerable(true);
                                cow.setCanPickupItems(false);
                                cow.setAdult();
                                cow.setSilent(true);
                                PersistentDataContainer data = cow.getPersistentDataContainer();
                                data.set(namePlayer, PersistentDataType.STRING, player.getName());
                                data.set(time, PersistentDataType.INTEGER, 0);
                                final Location targetBlockLocation = player.getTargetBlock(null, 15).getLocation();
                                // 0.055
                                Location snowBallLocation = cow.getLocation();
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
                                velocityVector = velocityVector.normalize().multiply(2.31);
                                cow.setVelocity(velocityVector);
                                plugin.allCows.add(cow);
                                cows++;
                            } else {
                                this.cancel();
                            }
                        }
                    }.runTaskTimer(plugin, 0, 5);
                }
            }
            if (action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)) {
                if (plugin.kitsCoolDown.isPlayerInCoolDown(player)) {
                    player.sendMessage(WARN + "You have " + gold + "" + italic + "" + plugin.kitsCoolDown.getTime(player) + "" + yellow + "" + italic + " seconds left.");
                } else {
                    plugin.kitsCoolDown.addPlayerToCoolMap(player, 1);
                    Cow cow = player.getWorld().spawn(player.getEyeLocation(), Cow.class);
                    cow.setInvulnerable(true);
                    cow.setCanPickupItems(false);
                    cow.setAdult();
                    cow.setSilent(true);
                    final Location targetBlockLocation = player.getTargetBlock(null, 15).getLocation();
                    // 0.055
                    Location snowBallLocation = cow.getLocation();
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
                    velocityVector = velocityVector.normalize().multiply(2.31);
                    cow.setVelocity(velocityVector);
                    new BukkitRunnable() {
                        int timer = 0;

                        @Override
                        public void run() {
                            if (cow.isOnGround() || timer == 60 || cow.isSwimming()) {
                                // explosion and kill the cow and end the runnable
                                DealDamage(cow, player);
                                Location loc = cow.getLocation();
                                loc.setY(-2000);
                                loc.setX(0);
                                loc.setZ(0);
                                cow.setInvulnerable(false);
                                cow.teleport(loc);
                                this.cancel();
                            } else {
                                timer += 1;
                            }
                        }
                    }.runTaskTimer(plugin, 0, 10);
                }
            }
        }
    }
}
