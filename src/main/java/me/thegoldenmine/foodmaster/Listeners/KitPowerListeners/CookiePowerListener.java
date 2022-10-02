package me.thegoldenmine.foodmaster.Listeners.KitPowerListeners;

import me.thegoldenmine.foodmaster.FoodMaster;
import me.thegoldenmine.foodmaster.Items.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CookiePowerListener implements Listener {
    private final FoodMaster plugin;

    public CookiePowerListener(FoodMaster main) {
        plugin = main;
    }

    @EventHandler
    public void cookiePower(PlayerInteractEvent event) {
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
        /*
        Left Click - strikes lightning with in 50 blocks radius at the block you are looking at and gives harmful effects to the enemy

        Right Click - gives helpful effects to everyone in your team
        * */
        Action action = event.getAction();
        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand().equals(ItemManager.CookieKit)) {
            if (!plugin.game.isPlayerInGame(player)) {
                if (player.getInventory().contains(ItemManager.CookieKit)) {
                    player.getInventory().remove(ItemManager.CookieKit);
                }
                return;
            }
            UUID uuid = player.getUniqueId();
            if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
                int hitPlayers = 0;
                if (plugin.kitPowerCoolDown.isPlayerInCoolDown(player)) {
                    player.sendMessage(WARN + "You have " + gold + "" + italic + "" + plugin.kitPowerCoolDown.getTime(player) + "" + yellow + "" + italic + " seconds left.");
                } else {
                    plugin.kitPowerCoolDown.addPlayerToCoolMap(player, 10);
                    for (Entity entity : player.getNearbyEntities(15, 15, 15)) {
                        if (hitPlayers <= 5) {
                            if (entity instanceof Enderman) {
                                Enderman enderman = (Enderman) entity;
                                enderman.getWorld().strikeLightningEffect(enderman.getLocation());
                                enderman.damage(damageGame);
                                hitPlayers = hitPlayers + 1;
                            }
                            if (entity instanceof Slime) {
                                Slime slime = (Slime) entity;
                                slime.getWorld().strikeLightningEffect(slime.getLocation());
                                slime.damage(damageGame);
                                hitPlayers = hitPlayers + 1;
                            }
                            if (entity instanceof Zombie) {
                                Zombie zombie = (Zombie) entity;
                                zombie.getWorld().strikeLightningEffect(zombie.getLocation());
                                zombie.damage(damageGame);
                                hitPlayers = hitPlayers + 1;
                            }
                            if (entity instanceof Skeleton) {
                                Skeleton skeleton = (Skeleton) entity;
                                skeleton.getWorld().strikeLightningEffect(skeleton.getLocation());
                                skeleton.damage(damageGame);
                                hitPlayers = hitPlayers + 1;
                            }
                            if (entity instanceof Spider) {
                                Spider spider = (Spider) entity;
                                spider.getWorld().strikeLightningEffect(spider.getLocation());
                                spider.damage(damageGame);
                                hitPlayers = hitPlayers + 1;
                            }
                            if (entity instanceof Player) {
                                Player playerNear = (Player) entity;
                                // playerNear = enemy
                                UUID uniqueId = playerNear.getUniqueId();
                                boolean checkPlayerHealth = playerNear.getHealth() - damageGame < 1;
                                boolean checkLives = plugin.mainConfig.getBooleanGame("enable_lives_free-for-all") && plugin.lives.containsKey(uniqueId) && plugin.lives.get(uniqueId) == 0;
                                boolean respawnOrLives = checkLives || !plugin.mainConfig.getBooleanGame("respawn_free-for-all");
                                if (plugin.isPlayerPlayingFoodGame(player) && plugin.isPlayerPlayingFoodGame(playerNear)) {
                                    player.getWorld().strikeLightningEffect(playerNear.getLocation());
                                    //damage
                                    if (!plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uniqueId)) {
                                        playerNear.sendMessage(INFO + "You have spawn protection for " + gold + "" + italic + "" + plugin.gameSpawnCoolDown.getTime(uniqueId) + "" + aqua + "" + italic + " seconds. You won't die.");
                                    } else {
                                        if (checkPlayerHealth) {
                                            plugin.playersInCookieKit.remove(uuid);
                                            plugin.playersInBreadKit.add(uuid);
                                            plugin.kitsCoolDown.playerCoolDownMap.remove(uuid);
                                            plugin.kitPowerCoolDown.playerCoolDownMap.remove(uuid);
                                            plugin.respawnPlayer.respawnThePlayer(playerNear);
                                            // player - killer
                                            // playerNear - dead
                                            plugin.giveOneKillAndDeathToPlayer.givePlayerKD(playerNear, player);
                                        } else {
                                            playerNear.damage(damageGame);
                                        }
                                    }
                                    hitPlayers = hitPlayers + 1;
                                } else if (plugin.isPlayerPlayingFreeForAll(player) && plugin.isPlayerPlayingFreeForAll(playerNear)) {
                                    player.getWorld().strikeLightningEffect(playerNear.getLocation());
                                    //damage
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
                                    hitPlayers = hitPlayers + 1;
                                } else if (plugin.deathmatch.isPlayerPlayingFoodWars(player) && plugin.deathmatch.isPlayerPlayingFoodWars(playerNear)) {
                                    if (plugin.deathmatch.getGroupPlayersInRedTeam(player) != null && !plugin.deathmatch.getGroupPlayersInRedTeam(player).isEmpty() && !plugin.deathmatch.getGroupPlayersInRedTeam(player).contains(playerNear.getUniqueId())) {
                                        player.getWorld().strikeLightningEffect(playerNear.getLocation());
                                        //damage
                                        if (!plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uniqueId)) {
                                            playerNear.sendMessage(INFO + "You have spawn protection for " + gold + "" + italic + "" + plugin.gameSpawnCoolDown.getTime(uniqueId) + "" + aqua + "" + italic + " seconds. You won't die.");
                                        } else {
                                            if (checkPlayerHealth && respawnOrLives) {
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
                                        hitPlayers = hitPlayers + 1;
                                    }
                                    if (plugin.deathmatch.getGroupPlayersInBlueTeam(player) != null && !plugin.deathmatch.getGroupPlayersInBlueTeam(player).isEmpty() && !plugin.deathmatch.getGroupPlayersInBlueTeam(player).contains(playerNear.getUniqueId())) {
                                        player.getWorld().strikeLightningEffect(playerNear.getLocation());
                                        //damage
                                        boolean checkLive1 = plugin.mainConfig.getBooleanGame("enable_lives_team_deathmatch") && plugin.lives.containsKey(uniqueId) && plugin.lives.get(uniqueId) == 0;
                                        boolean respawnOrLives1 = checkLive1 || !plugin.mainConfig.getBooleanGame("respawn_team_deathmatch");
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
                                        hitPlayers = hitPlayers + 1;
                                    }
                                    if (plugin.deathmatch.getGroupPlayersInCyanTeam(player) != null && !plugin.deathmatch.getGroupPlayersInCyanTeam(player).isEmpty() && !plugin.deathmatch.getGroupPlayersInCyanTeam(player).contains(playerNear.getUniqueId())) {
                                        player.getWorld().strikeLightningEffect(playerNear.getLocation());
                                        //damage
                                        boolean checkLive1 = plugin.mainConfig.getBooleanGame("enable_lives_team_deathmatch") && plugin.lives.containsKey(uniqueId) && plugin.lives.get(uniqueId) == 0;
                                        boolean respawnOrLives1 = checkLive1 || !plugin.mainConfig.getBooleanGame("respawn_team_deathmatch");
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
                                        hitPlayers = hitPlayers + 1;
                                    }
                                    if (plugin.deathmatch.getGroupPlayersInGreenTeam(player) != null && !plugin.deathmatch.getGroupPlayersInGreenTeam(player).isEmpty() && !plugin.deathmatch.getGroupPlayersInGreenTeam(player).contains(playerNear.getUniqueId())) {
                                        player.getWorld().strikeLightningEffect(playerNear.getLocation());
                                        //damage
                                        boolean checkLive1 = plugin.mainConfig.getBooleanGame("enable_lives_team_deathmatch") && plugin.lives.containsKey(uniqueId) && plugin.lives.get(uniqueId) == 0;
                                        boolean respawnOrLives1 = checkLive1 || !plugin.mainConfig.getBooleanGame("respawn_team_deathmatch");
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
                                        hitPlayers = hitPlayers + 1;
                                    }
                                    if (plugin.deathmatch.getGroupPlayersInYellowTeam(player) != null && !plugin.deathmatch.getGroupPlayersInYellowTeam(player).isEmpty() && !plugin.deathmatch.getGroupPlayersInYellowTeam(player).contains(playerNear.getUniqueId())) {
                                        player.getWorld().strikeLightningEffect(playerNear.getLocation());
                                        //damage
                                        boolean checkLive1 = plugin.mainConfig.getBooleanGame("enable_lives_team_deathmatch") && plugin.lives.containsKey(uniqueId) && plugin.lives.get(uniqueId) == 0;
                                        boolean respawnOrLives1 = checkLive1 || !plugin.mainConfig.getBooleanGame("respawn_team_deathmatch");
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
                                        hitPlayers = hitPlayers + 1;
                                    }
                                }
                            }
                        }
                    }
                    hitPlayers = 0;
                }
            }
            if (action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)) {
                Block theBlockThatThePlayerInLookingAt = player.getTargetBlock(null, 15);
                Location blockLocation = theBlockThatThePlayerInLookingAt.getLocation();
                World playerInLookingAtWorld = theBlockThatThePlayerInLookingAt.getWorld();
                // deals the damage
                if (plugin.kitsCoolDown.isPlayerInCoolDown(player)) {
                    player.sendMessage(WARN + "You have " + gold + "" + italic + "" + plugin.kitsCoolDown.getTime(player) + "" + yellow + "" + italic + " seconds left.");
                } else {
                    plugin.kitsCoolDown.addPlayerToCoolMap(player, 1);
                    List<Location> locationsNearTheLightning = new ArrayList<>();
                    // Adds damage location
                    for (int x = blockLocation.getBlockX() - 3; x < 3 + blockLocation.getBlockX(); x++) {
                        for (int y = blockLocation.getBlockY() - 3; y < 3 + blockLocation.getBlockY(); y++) {
                            for (int z = blockLocation.getBlockZ() - 3; z < 3 + blockLocation.getBlockZ(); z++) {
                                Location loc = new Location(playerInLookingAtWorld, x, y, z);
                                locationsNearTheLightning.add(loc);
                            }
                        }
                    }
                    playerInLookingAtWorld.strikeLightningEffect(blockLocation);
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
                                            plugin.playersInCookieKit.remove(uuid);
                                            plugin.playersInBreadKit.add(uuid);
                                            player.getInventory().removeItem(ItemManager.CookieKit);
                                            player.getInventory().addItem(ItemManager.BreadKit);
                                            plugin.kitsCoolDown.playerCoolDownMap.remove(uuid);
                                            plugin.kitPowerCoolDown.playerCoolDownMap.remove(uuid);
                                            plugin.respawnPlayer.respawnThePlayer(playerNear);
                                            // player - killer
                                            // playerNear - dead
                                            plugin.giveOneKillAndDeathToPlayer.givePlayerKD(playerNear, player);
                                            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, 2));
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
        }
    }
}

