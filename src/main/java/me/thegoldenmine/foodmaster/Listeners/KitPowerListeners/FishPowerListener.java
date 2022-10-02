package me.thegoldenmine.foodmaster.Listeners.KitPowerListeners;

import me.thegoldenmine.foodmaster.*;
import me.thegoldenmine.foodmaster.Items.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class FishPowerListener implements Listener {
    private final FoodMaster plugin;

    public FishPowerListener(FoodMaster main) {
        plugin = main;
    }

    @EventHandler
    public void cookiePower(PlayerInteractEvent event) {
        /*
        Left Click - dealing some damage with knockback

        Right Click§7§o - splash
        * */
        Action action = event.getAction();
        Player player = event.getPlayer();
        boolean b = action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK);
        if (player.getInventory().getItemInMainHand().equals(ItemManager.FishKit) && b) {
            // 10 block
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
            UUID uuid = player.getUniqueId();
            String WARN = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + yellow + "" + bold + "WARN " + darkGray + "" + strikethrough + "-" + yellow + "" + italic + " ";
            String INFO = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + aqua + "" + bold + "INFO " + darkGray + "" + strikethrough + "-" + aqua + "" + italic + " ";
            int hitPlayers = 0;
            final int damage = plugin.mainConfig.getIntGame("damage");
            if (!plugin.game.isPlayerInGame(player)) {
                if (player.getInventory().contains(ItemManager.FishKit)) {
                    player.getInventory().remove(ItemManager.FishKit);
                }
                return;
            }
            if (plugin.kitPowerCoolDown.isPlayerInCoolDown(player)) {
                player.sendMessage(WARN + "You have " + gold + "" + italic + "" + plugin.kitPowerCoolDown.getTime(player) + "" + yellow + "" + italic + " seconds left.");
            } else {
                plugin.kitPowerCoolDown.addPlayerToCoolMap(player, 10);
                for (Entity entity : player.getNearbyEntities(15, 15, 15)) {
                    if (entity instanceof Slime) {
                        new BukkitRunnable() {
                            int spawned = 0;

                            @Override
                            public void run() {
                                if (spawned <= 10) {
                                    spawnPart(player, entity);
                                    spawned++;
                                } else {
                                    this.cancel();
                                }
                            }
                        }.runTaskTimer(plugin, 0, 1);
                        ((Slime) entity).damage(damage);
                    } else if (entity instanceof Enderman) {
                        new BukkitRunnable() {
                            int spawned = 0;

                            @Override
                            public void run() {
                                if (spawned <= 10) {
                                    spawnPart(player, entity);
                                    spawned++;
                                } else {
                                    this.cancel();
                                }
                            }
                        }.runTaskTimer(plugin, 0, 1);
                        ((Enderman) entity).damage(damage);
                    } else if (entity instanceof Zombie) {
                        new BukkitRunnable() {
                            int spawned = 0;

                            @Override
                            public void run() {
                                if (spawned <= 10) {
                                    spawnPart(player, entity);
                                    spawned++;
                                } else {
                                    this.cancel();
                                }
                            }
                        }.runTaskTimer(plugin, 0, 1);
                        ((Zombie) entity).damage(damage);
                    } else if (entity instanceof Spider) {
                        new BukkitRunnable() {
                            int spawned = 0;

                            @Override
                            public void run() {
                                if (spawned <= 10) {
                                    spawnPart(player, entity);
                                    spawned++;
                                } else {
                                    this.cancel();
                                }
                            }
                        }.runTaskTimer(plugin, 0, 1);
                        ((Spider) entity).damage(damage);
                    } else if (entity instanceof Skeleton) {
                        new BukkitRunnable() {
                            int spawned = 0;

                            @Override
                            public void run() {
                                if (spawned <= 10) {
                                    spawnPart(player, entity);
                                    spawned++;
                                } else {
                                    this.cancel();
                                }
                            }
                        }.runTaskTimer(plugin, 0, 1);
                        ((Skeleton) entity).damage(damage);
                    } else if (entity instanceof Player) {
                        Player playerNear = (Player) entity;
                        UUID uniqueId = playerNear.getUniqueId();
                        boolean checkPlayerHealth = playerNear.getHealth() - damage < 2;
                        boolean checkLives = plugin.mainConfig.getBooleanGame("enable_lives_free-for-all") && plugin.lives.containsKey(uniqueId) && plugin.lives.get(uniqueId) == 0;
                        boolean respawnOrLives = checkLives || !plugin.mainConfig.getBooleanGame("respawn_free-for-all");
                        if (plugin.isPlayerPlayingFoodGame(player) && plugin.isPlayerPlayingFoodGame(playerNear)) {
                            //damage
                            if (!plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uniqueId)) {
                                playerNear.sendMessage(INFO + "You have spawn protection for " + gold + "" + italic + "" + plugin.gameSpawnCoolDown.getTime(uniqueId) + "" + aqua + "" + italic + " seconds. You won't die.");
                            } else {
                                if (checkPlayerHealth) {
                                    plugin.playersInFishKit.remove(uuid);
                                    plugin.playersInBeefKit.add(uuid);
                                    plugin.kitsCoolDown.playerCoolDownMap.remove(uuid);
                                    plugin.kitPowerCoolDown.playerCoolDownMap.remove(uuid);
                                    plugin.respawnPlayer.respawnThePlayer(playerNear);
                                    player.getInventory().remove(ItemManager.FishKit);
                                    // player - killer
                                    // playerNear - dead
                                    plugin.giveOneKillAndDeathToPlayer.givePlayerKD(playerNear, player);
                                } else {
                                    new BukkitRunnable() {
                                        int spawned = 0;

                                        @Override
                                        public void run() {
                                            if (spawned <= 10) {
                                                spawnWaterParticles(playerNear);
                                                spawned++;
                                            } else {
                                                this.cancel();
                                            }
                                        }
                                    }.runTaskTimer(plugin, 0, 1);
                                    playerNear.damage(damage);
                                }
                            }
                        } else if (plugin.isPlayerPlayingFreeForAll(player) && plugin.isPlayerPlayingFreeForAll(playerNear)) {
                            //damage
                            if (!plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uniqueId)) {
                                playerNear.sendMessage(INFO + "You have spawn protection for " + gold + "" + italic + "" + plugin.gameSpawnCoolDown.getTime(uniqueId) + "" + aqua + "" + italic + " seconds. You won't die.");
                            } else {
                                new BukkitRunnable() {
                                    int spawned = 0;

                                    @Override
                                    public void run() {
                                        if (spawned <= 10) {
                                            spawnWaterParticles(playerNear);
                                            spawned++;
                                        } else {
                                            this.cancel();
                                        }
                                    }
                                }.runTaskTimer(plugin, 0, 1);
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
                                    playerNear.damage(damage);
                                }
                            }
                            hitPlayers = hitPlayers + 1;
                        } else if (plugin.deathmatch.isPlayerPlayingFoodWars(player) && plugin.deathmatch.isPlayerPlayingFoodWars(playerNear)) {
                            if (plugin.deathmatch.getGroupPlayersInRedTeam(player) != null && !plugin.deathmatch.getGroupPlayersInRedTeam(player).isEmpty() && !plugin.deathmatch.getGroupPlayersInRedTeam(player).contains(playerNear.getUniqueId())) {
                                //damage
                                if (!plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uniqueId)) {
                                    playerNear.sendMessage(INFO + "You have spawn protection for " + gold + "" + italic + "" + plugin.gameSpawnCoolDown.getTime(uniqueId) + "" + aqua + "" + italic + " seconds. You won't die.");
                                } else {
                                    new BukkitRunnable() {
                                        int spawned = 0;

                                        @Override
                                        public void run() {
                                            if (spawned <= 10) {
                                                spawnWaterParticles(playerNear);
                                                spawned++;
                                            } else {
                                                this.cancel();
                                            }
                                        }
                                    }.runTaskTimer(plugin, 0, 1);
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
                                        playerNear.damage(damage);
                                    }
                                }
                                hitPlayers = hitPlayers + 1;
                            }
                            boolean enableLivesTeamDeathmatch = plugin.mainConfig.getBooleanGame("enable_lives_team_deathmatch");
                            if (plugin.deathmatch.getGroupPlayersInBlueTeam(player) != null && !plugin.deathmatch.getGroupPlayersInBlueTeam(player).isEmpty() && !plugin.deathmatch.getGroupPlayersInBlueTeam(player).contains(playerNear.getUniqueId())) {
                                //damage
                                boolean checkLive1 = enableLivesTeamDeathmatch && plugin.lives.containsKey(uniqueId) && plugin.lives.get(uniqueId) == 0;
                                boolean respawnOrLives1 = checkLive1 || !plugin.mainConfig.getBooleanGame("respawn_team_deathmatch");
                                if (!plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uniqueId)) {
                                    playerNear.sendMessage(INFO + "You have spawn protection for " + gold + "" + italic + "" + plugin.gameSpawnCoolDown.getTime(uniqueId) + "" + aqua + "" + italic + " seconds. You won't die.");
                                } else {
                                    new BukkitRunnable() {
                                        int spawned = 0;

                                        @Override
                                        public void run() {
                                            if (spawned <= 10) {
                                                spawnWaterParticles(playerNear);
                                                spawned++;
                                            } else {
                                                this.cancel();
                                            }
                                        }
                                    }.runTaskTimer(plugin, 0, 1);
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
                                        playerNear.damage(damage);
                                    }
                                }
                                hitPlayers = hitPlayers + 1;
                            }
                            boolean respawnTeamDeathmatch = plugin.mainConfig.getBooleanGame("respawn_team_deathmatch");
                            if (plugin.deathmatch.getGroupPlayersInCyanTeam(player) != null && !plugin.deathmatch.getGroupPlayersInCyanTeam(player).isEmpty() && !plugin.deathmatch.getGroupPlayersInCyanTeam(player).contains(playerNear.getUniqueId())) {
                                //damage
                                boolean checkLive1 = enableLivesTeamDeathmatch && plugin.lives.containsKey(uniqueId) && plugin.lives.get(uniqueId) == 0;
                                boolean respawnOrLives1 = checkLive1 || !plugin.mainConfig.getBooleanGame("respawn_team_deathmatch");
                                if (!plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uniqueId)) {
                                    playerNear.sendMessage(INFO + "You have spawn protection for " + gold + "" + italic + "" + plugin.gameSpawnCoolDown.getTime(uniqueId) + "" + aqua + "" + italic + " seconds. You won't die.");
                                } else {
                                    new BukkitRunnable() {
                                        int spawned = 0;

                                        @Override
                                        public void run() {
                                            if (spawned <= 10) {
                                                spawnWaterParticles(playerNear);
                                                spawned++;
                                            } else {
                                                this.cancel();
                                            }
                                        }
                                    }.runTaskTimer(plugin, 0, 1);
                                    if (checkPlayerHealth && respawnOrLives1) {
                                        // player - killer
                                        // playerNear - dead
                                        plugin.giveOneKillAndDeathToPlayer.givePlayerKD(playerNear, player);
                                        plugin.playerDead.thePlayerIsDead(playerNear);
                                    } else if (checkPlayerHealth && respawnTeamDeathmatch) {
                                        plugin.respawnPlayer.respawnThePlayer(playerNear);
                                        // player - killer
                                        // playerNear - dead
                                        plugin.giveOneKillAndDeathToPlayer.givePlayerKD(playerNear, player);
                                    } else {
                                        playerNear.damage(damage);
                                    }
                                }
                                hitPlayers = hitPlayers + 1;
                            }
                            if (plugin.deathmatch.getGroupPlayersInGreenTeam(player) != null && !plugin.deathmatch.getGroupPlayersInGreenTeam(player).isEmpty() && !plugin.deathmatch.getGroupPlayersInGreenTeam(player).contains(playerNear.getUniqueId())) {
                                //damage
                                boolean checkLive1 = enableLivesTeamDeathmatch && plugin.lives.containsKey(uniqueId) && plugin.lives.get(uniqueId) == 0;
                                boolean respawnOrLives1 = checkLive1 || !respawnTeamDeathmatch;
                                if (!plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uniqueId)) {
                                    playerNear.sendMessage(INFO + "You have spawn protection for " + gold + "" + italic + "" + plugin.gameSpawnCoolDown.getTime(uniqueId) + "" + aqua + "" + italic + " seconds. You won't die.");
                                } else {
                                    new BukkitRunnable() {
                                        int spawned = 0;

                                        @Override
                                        public void run() {
                                            if (spawned <= 10) {
                                                spawnWaterParticles(playerNear);
                                                spawned++;
                                            } else {
                                                this.cancel();
                                            }
                                        }
                                    }.runTaskTimer(plugin, 0, 1);
                                    if (checkPlayerHealth && respawnOrLives1) {
                                        // player - killer
                                        // playerNear - dead
                                        plugin.giveOneKillAndDeathToPlayer.givePlayerKD(playerNear, player);
                                        plugin.playerDead.thePlayerIsDead(playerNear);
                                    } else if (checkPlayerHealth && respawnTeamDeathmatch) {
                                        plugin.respawnPlayer.respawnThePlayer(playerNear);
                                        // player - killer
                                        // playerNear - dead
                                        plugin.giveOneKillAndDeathToPlayer.givePlayerKD(playerNear, player);
                                    } else {
                                        playerNear.damage(damage);
                                    }
                                }
                                hitPlayers = hitPlayers + 1;
                            }
                            if (plugin.deathmatch.getGroupPlayersInYellowTeam(player) != null && !plugin.deathmatch.getGroupPlayersInYellowTeam(player).isEmpty() && !plugin.deathmatch.getGroupPlayersInYellowTeam(player).contains(playerNear.getUniqueId())) {
                                //damage
                                boolean checkLive1 = enableLivesTeamDeathmatch && plugin.lives.containsKey(uniqueId) && plugin.lives.get(uniqueId) == 0;
                                boolean respawnOrLives1 = checkLive1 || !respawnTeamDeathmatch;
                                if (!plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uniqueId)) {
                                    playerNear.sendMessage(INFO + "You have spawn protection for " + gold + "" + italic + "" + plugin.gameSpawnCoolDown.getTime(uniqueId) + "" + aqua + "" + italic + " seconds. You won't die.");
                                } else {
                                    new BukkitRunnable() {
                                        int spawned = 0;

                                        @Override
                                        public void run() {
                                            if (spawned <= 10) {
                                                spawnWaterParticles(playerNear);
                                                spawned++;
                                            } else {
                                                this.cancel();
                                            }
                                        }
                                    }.runTaskTimer(plugin, 0, 1);
                                    if (checkPlayerHealth && respawnOrLives1) {
                                        // player - killer
                                        // playerNear - dead
                                        plugin.giveOneKillAndDeathToPlayer.givePlayerKD(playerNear, player);
                                        plugin.playerDead.thePlayerIsDead(playerNear);
                                    } else if (checkPlayerHealth && respawnTeamDeathmatch) {
                                        plugin.respawnPlayer.respawnThePlayer(playerNear);
                                        // player - killer
                                        // playerNear - dead
                                        plugin.giveOneKillAndDeathToPlayer.givePlayerKD(playerNear, player);
                                    } else {
                                        playerNear.damage(damage);
                                    }
                                }
                                hitPlayers = hitPlayers + 1;
                            }
                        }
                    }
                }
            }
        }
    }

    private void parti(Player players, Location loc) {
        for (double x = loc.getX() - 1.5; x < 1.5 + loc.getX(); x++) {
            for (int y = loc.getBlockY() - 3; y < 3+ loc.getBlockY(); y++) {
                for (double z = loc.getZ() - 1.5; z < 1.5 + loc.getZ(); z++) {
                    Location newLoc = new Location(loc.getWorld(), x+0.5, y, z+0.5);
                    players.spawnParticle(Particle.WATER_BUBBLE, newLoc, 3);
                }
            }
        }
    }

    private void spawnPart(Player player, Entity entity) {
        Location loc = entity.getLocation();
        if (plugin.playerGroup.isPlayerInGroup(player)) {
            for (UUID uuids : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
                if (uuids != null) {
                    Player players = Bukkit.getPlayer(uuids);
                    if (players != null) {
                        parti(players, loc);
                    }
                }
            }
        } else {
            for (Player players : Bukkit.getOnlinePlayers()) {
                if (players != null) {
                    parti(players, loc);
                }
            }
        }
    }

    private void spawnWaterParticles(Player playerNear) {
        Location loc = playerNear.getLocation();
        if (plugin.playerGroup.isPlayerInGroup(playerNear)) {
            for (UUID uuids : plugin.playerGroup.getPlayersInGroupOfPlayer(playerNear)) {
                if (uuids != null) {
                    Player players = Bukkit.getPlayer(uuids);
                    if (players != null) {
                        parti(players, loc);
                    }
                }
            }
        } else {
            for (Player players : Bukkit.getOnlinePlayers()) {
                if (players != null) {
                    parti(players, loc);
                }
            }
        }
    }
}
