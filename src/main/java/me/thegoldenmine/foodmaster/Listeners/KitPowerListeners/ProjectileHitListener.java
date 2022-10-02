package me.thegoldenmine.foodmaster.Listeners.KitPowerListeners;

import me.thegoldenmine.foodmaster.*;
import me.thegoldenmine.foodmaster.Items.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.projectiles.ProjectileSource;

import java.util.UUID;

public class ProjectileHitListener implements Listener {
    private final FoodMaster plugin;

    public ProjectileHitListener(FoodMaster main) {
        plugin = main;
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        Entity attackedEntity = event.getHitEntity();
        Projectile projectile = event.getEntity();
        ProjectileSource shooter = projectile.getShooter();
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor italic = ChatColor.ITALIC;
        ChatColor aqua = ChatColor.AQUA;
        String s;
        if (plugin.mainConfig.getStrMain("name") != null) {
            s = " "+plugin.mainConfig.getStrMain("name")+" ";
        } else {
            s = " FoodMaster ";
        }
        String INFO = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + aqua + "" + bold + "INFO " + darkGray + "" + strikethrough + "-" + aqua + "" + italic + " ";
        String ERROR = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + ChatColor.RED + "" + bold + "ERROR " + darkGray + "" + strikethrough + "-" + ChatColor.RED + "" + italic + " ";
        final int friendlyDamage = plugin.mainConfig.getIntGame("friendly_damage");
        final int MegaFriendlyDamage = plugin.mainConfig.getIntGame("friendly_damage") + 2;
        final int damage = plugin.mainConfig.getIntGame("damage");
        final int MegaDamage = plugin.mainConfig.getIntGame("damage") + 2;
        if (shooter instanceof Player) {
            if (attackedEntity instanceof Enderman) {
                Enderman enderman = (Enderman) attackedEntity;
                if (projectile instanceof Snowball) {
                    Snowball snowball = (Snowball) projectile;
                    if (snowball.isGlowing()) {
                        enderman.damage(damage);
                    } else {
                        enderman.damage(MegaDamage);
                    }
                } else if (projectile instanceof Fireball) {
                    Fireball fireball = (Fireball) projectile;
                    if (fireball.isGlowing()) {
                        enderman.damage(damage);
                    } else {
                        enderman.damage(MegaDamage);
                    }
                }
                return;
            }
            if (attackedEntity instanceof Slime) {
                Slime slime = (Slime) attackedEntity;
                if (projectile instanceof Snowball) {
                    Snowball snowball = (Snowball) projectile;
                    if (snowball.isGlowing()) {
                        slime.damage(damage);
                    } else {
                        slime.damage(MegaDamage);
                    }
                } else if (projectile instanceof Fireball) {
                    Fireball fireball = (Fireball) projectile;
                    if (fireball.isGlowing()) {
                        slime.damage(damage);
                    } else {
                        slime.damage(MegaDamage);
                    }
                }
                return;
            }
            if (attackedEntity instanceof Zombie) {
                Zombie zombie = (Zombie) attackedEntity;
                if (projectile instanceof Snowball) {
                    Snowball snowball = (Snowball) projectile;
                    if (snowball.isGlowing()) {
                        zombie.damage(damage);
                    } else {
                        zombie.damage(MegaDamage);
                    }
                } else if (projectile instanceof Fireball) {
                    Fireball fireball = (Fireball) projectile;
                    if (fireball.isGlowing()) {
                        zombie.damage(damage);
                    } else {
                        zombie.damage(MegaDamage);
                    }
                }
                return;
            }
            if (attackedEntity instanceof Skeleton) {
                Skeleton skeleton = (Skeleton) attackedEntity;
                if (projectile instanceof Snowball) {
                    Snowball snowball = (Snowball) projectile;
                    if (snowball.isGlowing()) {
                        skeleton.damage(damage);
                    } else {
                        skeleton.damage(MegaDamage);
                    }
                } else if (projectile instanceof Fireball) {
                    Fireball fireball = (Fireball) projectile;
                    if (fireball.isGlowing()) {
                        skeleton.damage(damage);
                    } else {
                        skeleton.damage(MegaDamage);
                    }
                }
                return;
            }
            if (attackedEntity instanceof Spider) {
                Spider spider = (Spider) attackedEntity;
                if (projectile instanceof Snowball) {
                    Snowball snowball = (Snowball) projectile;
                    if (snowball.isGlowing()) {
                        spider.damage(damage);
                    } else {
                        spider.damage(MegaDamage);
                    }
                } else if (projectile instanceof Fireball) {
                    Fireball fireball = (Fireball) projectile;
                    if (fireball.isGlowing()) {
                        spider.damage(damage);
                    } else {
                        spider.damage(MegaDamage);
                    }
                }
                return;
            }
            if (attackedEntity instanceof Player) {
                Player AttackedPlayer = (Player) attackedEntity;
                Player playerShooter = (Player) shooter;
                boolean enableLivesTeamDeathmatch = plugin.mainConfig.getBooleanGame("enable_lives_team_deathmatch");
                boolean respawnTeamDeathmatch = plugin.mainConfig.getBooleanGame("respawn_team_deathmatch");
                if (plugin.game.isPlayerInGame(AttackedPlayer) && plugin.game.isPlayerInGame(playerShooter) && projectile instanceof Snowball) {
                    Snowball snowball = (Snowball) projectile;
                    String s1 = "You have spawn protection for ";
                    String path = "respawn_free-for-all";
                    String s2 = " seconds. You won't die.";
                    if (AttackedPlayer.getName().equals(playerShooter.getName())) {
                        playerShooter.sendMessage(ERROR + "Nope.");
                        return;
                    }
                    if (!snowball.isGlowing()) {
                        UUID uniqueId = AttackedPlayer.getUniqueId();
                        boolean checkPlayerHealth = AttackedPlayer.getHealth() - damage < 1;
                        boolean checkLives = plugin.mainConfig.getBooleanGame("enable_lives_free-for-all") && plugin.lives.containsKey(uniqueId) && plugin.lives.get(uniqueId) == 0;
                        boolean respawnOrLives = checkLives || !plugin.mainConfig.getBooleanGame(path);
                        if (plugin.isPlayerPlayingFoodGame(playerShooter) && plugin.isPlayerPlayingFoodGame(AttackedPlayer)) {
                            //damage
                            if (!plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uniqueId)) {
                                AttackedPlayer.sendMessage(INFO + "You have spawn protection for " + gold + "" + italic + "" + plugin.gameSpawnCoolDown.getTime(uniqueId) + "" + aqua + "" + italic + " seconds. You won't die.");
                            } else {
                                if (checkPlayerHealth) {
                                    UUID uuid = playerShooter.getUniqueId();
                                    plugin.playersInMelonKit.remove(uuid);
                                    plugin.playersInPotatoKit.add(uuid);
                                    plugin.kitsCoolDown.playerCoolDownMap.remove(uuid);
                                    plugin.kitPowerCoolDown.playerCoolDownMap.remove(uuid);
                                    playerShooter.getInventory().remove(ItemManager.MelonKit);
                                    plugin.respawnPlayer.respawnThePlayer(AttackedPlayer);
                                    // player - killer
                                    // playerNear - dead
                                    plugin.giveOneKillAndDeathToPlayer.givePlayerKD(AttackedPlayer, playerShooter);
                                } else {
                                    AttackedPlayer.damage(damage);
                                }
                            }
                        } else if (plugin.isPlayerPlayingFreeForAll(playerShooter) && plugin.isPlayerPlayingFreeForAll(AttackedPlayer)) {
                            if (!plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uniqueId)) {
                                AttackedPlayer.sendMessage(INFO + s1 + gold + "" + italic + "" + plugin.gameSpawnCoolDown.getTime(uniqueId) + "" + aqua + "" + italic + s2);
                            } else {
                                if (checkPlayerHealth && respawnOrLives) {
                                    // player - killer
                                    // AttackedPlayer - dead
                                    plugin.giveOneKillAndDeathToPlayer.givePlayerKD(AttackedPlayer, playerShooter);
                                    plugin.playerDead.thePlayerIsDead(AttackedPlayer);
                                } else if (checkPlayerHealth && plugin.mainConfig.getBooleanGame(path)) {
                                    plugin.respawnPlayer.respawnThePlayer(AttackedPlayer);
                                    // player - killer
                                    // AttackedPlayer - dead
                                    plugin.giveOneKillAndDeathToPlayer.givePlayerKD(AttackedPlayer, playerShooter);
                                } else {
                                    AttackedPlayer.damage(damage);
                                }
                            }
                        } else if (plugin.deathmatch.isPlayerPlayingFoodWars(playerShooter) && plugin.deathmatch.isPlayerPlayingFoodWars(AttackedPlayer)) {
                            // friendly-fire
                            boolean checkLive1 = enableLivesTeamDeathmatch && plugin.lives.containsKey(uniqueId) && plugin.lives.get(uniqueId) == 0;
                            boolean respawnOrLives1 = checkLive1 || !respawnTeamDeathmatch;
                            if (plugin.mainConfig.getBooleanGame("friendly-fire") && plugin.deathmatch.getPlayerTeam(AttackedPlayer).equals(plugin.deathmatch.getPlayerTeam(playerShooter))) {
                                if (!plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uniqueId)) {
                                    AttackedPlayer.sendMessage(INFO + s1 + gold + "" + italic + "" + plugin.gameSpawnCoolDown.getTime(uniqueId) + "" + aqua + "" + italic + s2);
                                } else {
                                    if (AttackedPlayer.getHealth() - friendlyDamage < 1 && respawnOrLives1) {
                                        // player - killer
                                        // AttackedPlayer - dead
                                        plugin.giveOneKillAndDeathToPlayer.givePlayerKD(AttackedPlayer, playerShooter);
                                        plugin.playerDead.thePlayerIsDead(AttackedPlayer);
                                    } else if (AttackedPlayer.getHealth() - friendlyDamage < 1 && respawnTeamDeathmatch) {
                                        plugin.respawnPlayer.respawnThePlayer(AttackedPlayer);
                                        // player - killer
                                        // AttackedPlayer - dead
                                        plugin.giveOneKillAndDeathToPlayer.givePlayerKD(AttackedPlayer, playerShooter);
                                    } else {
                                        AttackedPlayer.damage(friendlyDamage);
                                    }
                                }
                            } else if (!plugin.deathmatch.getPlayerTeam(AttackedPlayer).equals(plugin.deathmatch.getPlayerTeam(playerShooter))) {
                                if (!plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uniqueId)) {
                                    AttackedPlayer.sendMessage(INFO + s1 + gold + "" + italic + "" + plugin.gameSpawnCoolDown.getTime(uniqueId) + "" + aqua + "" + italic + s2);
                                } else {
                                    if (checkPlayerHealth && respawnOrLives1) {
                                        // player - killer
                                        // AttackedPlayer - dead
                                        plugin.giveOneKillAndDeathToPlayer.givePlayerKD(AttackedPlayer, playerShooter);
                                        plugin.playerDead.thePlayerIsDead(AttackedPlayer);
                                    } else if (checkPlayerHealth) {
                                        plugin.respawnPlayer.respawnThePlayer(AttackedPlayer);
                                        // player - killer
                                        // AttackedPlayer - dead
                                        plugin.giveOneKillAndDeathToPlayer.givePlayerKD(AttackedPlayer, playerShooter);
                                    } else {
                                        AttackedPlayer.damage(damage);
                                    }
                                }
                            }
                        }
                    } else if (snowball.isGlowing()) {
                        UUID uniqueId = AttackedPlayer.getUniqueId();
                        boolean checkPlayerHealth = AttackedPlayer.getHealth() - MegaDamage < 1;
                        boolean checkLives = plugin.mainConfig.getBooleanGame("enable_lives_free-for-all") && plugin.lives.containsKey(uniqueId) && plugin.lives.get(uniqueId) == 0;
                        boolean respawnOrLives = checkLives || !plugin.mainConfig.getBooleanGame(path);
                        if (plugin.isPlayerPlayingFoodGame(playerShooter) && plugin.isPlayerPlayingFoodGame(AttackedPlayer)) {
                            //damage
                            if (!plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uniqueId)) {
                                AttackedPlayer.sendMessage(INFO + "You have spawn protection for " + gold + "" + italic + "" + plugin.gameSpawnCoolDown.getTime(uniqueId) + "" + aqua + "" + italic + " seconds. You won't die.");
                            } else {
                                if (checkPlayerHealth) {
                                    UUID uuid = playerShooter.getUniqueId();
                                    plugin.playersInMelonKit.remove(uuid);
                                    plugin.playersInPotatoKit.add(uuid);
                                    plugin.kitsCoolDown.playerCoolDownMap.remove(uuid);
                                    plugin.kitPowerCoolDown.playerCoolDownMap.remove(uuid);
                                    playerShooter.getInventory().remove(ItemManager.MelonKit);
                                    plugin.respawnPlayer.respawnThePlayer(AttackedPlayer);
                                    // player - killer
                                    // playerNear - dead
                                    plugin.giveOneKillAndDeathToPlayer.givePlayerKD(AttackedPlayer, playerShooter);
                                } else {
                                    AttackedPlayer.damage(damage);
                                }
                            }
                        } else if (plugin.isPlayerPlayingFreeForAll(playerShooter) && plugin.isPlayerPlayingFreeForAll(AttackedPlayer)) {
                            if (!plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uniqueId)) {
                                AttackedPlayer.sendMessage(INFO + s1 + gold + "" + italic + "" + plugin.gameSpawnCoolDown.getTime(uniqueId) + "" + aqua + "" + italic + s2);
                            } else {
                                if (checkPlayerHealth && respawnOrLives) {
                                    // player - killer
                                    // AttackedPlayer - dead
                                    plugin.giveOneKillAndDeathToPlayer.givePlayerKD(AttackedPlayer, playerShooter);
                                    plugin.playerDead.thePlayerIsDead(AttackedPlayer);
                                } else if (checkPlayerHealth && plugin.mainConfig.getBooleanGame(path)) {
                                    plugin.respawnPlayer.respawnThePlayer(AttackedPlayer);
                                    // player - killer
                                    // AttackedPlayer - dead
                                    plugin.giveOneKillAndDeathToPlayer.givePlayerKD(AttackedPlayer, playerShooter);
                                } else {
                                    AttackedPlayer.damage(MegaDamage);
                                }
                            }
                        } else if (plugin.deathmatch.isPlayerPlayingFoodWars(playerShooter) && plugin.deathmatch.isPlayerPlayingFoodWars(AttackedPlayer)) {
                            // friendly-fire
                            boolean checkLive1 = enableLivesTeamDeathmatch && plugin.lives.containsKey(uniqueId) && plugin.lives.get(uniqueId) == 0;
                            boolean respawnOrLives1 = checkLive1 || !respawnTeamDeathmatch;
                            if (plugin.mainConfig.getBooleanGame("friendly-fire") && plugin.deathmatch.getPlayerTeam(AttackedPlayer).equals(plugin.deathmatch.getPlayerTeam(playerShooter))) {
                                if (!plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uniqueId)) {
                                    AttackedPlayer.sendMessage(INFO + s1 + gold + "" + italic + "" + plugin.gameSpawnCoolDown.getTime(uniqueId) + "" + aqua + "" + italic + s2);
                                } else {
                                    if (AttackedPlayer.getHealth() - MegaFriendlyDamage < 1 && respawnOrLives1) {
                                        // player - killer
                                        // AttackedPlayer - dead
                                        plugin.giveOneKillAndDeathToPlayer.givePlayerKD(AttackedPlayer, playerShooter);
                                        plugin.playerDead.thePlayerIsDead(AttackedPlayer);
                                    } else if (AttackedPlayer.getHealth() - MegaFriendlyDamage < 1 && respawnTeamDeathmatch) {
                                        plugin.respawnPlayer.respawnThePlayer(AttackedPlayer);
                                        // player - killer
                                        // AttackedPlayer - dead
                                        plugin.giveOneKillAndDeathToPlayer.givePlayerKD(AttackedPlayer, playerShooter);
                                    } else {
                                        AttackedPlayer.damage(MegaFriendlyDamage);
                                    }
                                }
                            } else if (!plugin.deathmatch.getPlayerTeam(AttackedPlayer).equals(plugin.deathmatch.getPlayerTeam(playerShooter))) {
                                if (!plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uniqueId)) {
                                    AttackedPlayer.sendMessage(INFO + s1 + gold + "" + italic + "" + plugin.gameSpawnCoolDown.getTime(uniqueId) + "" + aqua + "" + italic + s2);
                                } else {
                                    if (checkPlayerHealth && respawnOrLives1) {
                                        // player - killer
                                        // AttackedPlayer - dead
                                        plugin.giveOneKillAndDeathToPlayer.givePlayerKD(AttackedPlayer, playerShooter);
                                        plugin.playerDead.thePlayerIsDead(AttackedPlayer);
                                    } else if (checkPlayerHealth && respawnTeamDeathmatch) {
                                        plugin.respawnPlayer.respawnThePlayer(AttackedPlayer);
                                        // player - killer
                                        // AttackedPlayer - dead
                                        plugin.giveOneKillAndDeathToPlayer.givePlayerKD(AttackedPlayer, playerShooter);
                                    } else {
                                        AttackedPlayer.damage(MegaDamage);
                                    }
                                }
                            }
                        }
                    }
                } else if (plugin.game.isPlayerInGame(AttackedPlayer) && plugin.game.isPlayerInGame(playerShooter) && projectile instanceof WitherSkull) {
                    WitherSkull snowball = (WitherSkull) projectile;
                    String s1 = "You have spawn protection for ";
                    String path = "respawn_free-for-all";
                    String s2 = " seconds. You won't die.";
                    if (!snowball.isGlowing()) {
                        UUID uniqueId = AttackedPlayer.getUniqueId();
                        boolean checkPlayerHealth = AttackedPlayer.getHealth() - damage < 1;
                        boolean checkLives = plugin.mainConfig.getBooleanGame("enable_lives_free-for-all") && plugin.lives.containsKey(uniqueId) && plugin.lives.get(uniqueId) == 0;
                        boolean respawnOrLives = checkLives || !plugin.mainConfig.getBooleanGame(path);
                        if (plugin.isPlayerPlayingFoodGame(playerShooter) && plugin.isPlayerPlayingFoodGame(AttackedPlayer)) {
                            //damage
                            if (!plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uniqueId)) {
                                AttackedPlayer.sendMessage(INFO + "You have spawn protection for " + gold + "" + italic + "" + plugin.gameSpawnCoolDown.getTime(uniqueId) + "" + aqua + "" + italic + " seconds. You won't die.");
                            } else {
                                if (checkPlayerHealth) {
                                    plugin.playersInPotatoKit.remove(playerShooter.getUniqueId());
                                    plugin.playersInFishKit.add(playerShooter.getUniqueId());
                                    plugin.respawnPlayer.respawnThePlayer(AttackedPlayer);
                                    // player - killer
                                    // playerNear - dead
                                    plugin.giveOneKillAndDeathToPlayer.givePlayerKD(AttackedPlayer, playerShooter);
                                } else {
                                    AttackedPlayer.damage(damage);
                                }
                            }
                        } else if (plugin.isPlayerPlayingFreeForAll(playerShooter) && plugin.isPlayerPlayingFreeForAll(AttackedPlayer)) {
                            if (!plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uniqueId)) {
                                AttackedPlayer.sendMessage(INFO + s1 + gold + "" + italic + "" + plugin.gameSpawnCoolDown.getTime(uniqueId) + "" + aqua + "" + italic + s2);
                            } else {
                                if (checkPlayerHealth && respawnOrLives) {
                                    // player - killer
                                    // AttackedPlayer - dead
                                    plugin.giveOneKillAndDeathToPlayer.givePlayerKD(AttackedPlayer, playerShooter);
                                    plugin.playerDead.thePlayerIsDead(AttackedPlayer);
                                } else if (checkPlayerHealth && plugin.mainConfig.getBooleanGame(path)) {
                                    plugin.respawnPlayer.respawnThePlayer(AttackedPlayer);
                                    // player - killer
                                    // AttackedPlayer - dead
                                    plugin.giveOneKillAndDeathToPlayer.givePlayerKD(AttackedPlayer, playerShooter);
                                } else {
                                    AttackedPlayer.damage(damage);
                                }
                            }
                        } else if (plugin.deathmatch.isPlayerPlayingFoodWars(playerShooter) && plugin.deathmatch.isPlayerPlayingFoodWars(AttackedPlayer)) {
                            // friendly-fire
                            boolean checkLive1 = enableLivesTeamDeathmatch && plugin.lives.containsKey(uniqueId) && plugin.lives.get(uniqueId) == 0;
                            boolean respawnOrLives1 = checkLive1 || !respawnTeamDeathmatch;
                            if (plugin.mainConfig.getBooleanGame("friendly-fire") && plugin.deathmatch.getPlayerTeam(AttackedPlayer).equals(plugin.deathmatch.getPlayerTeam(playerShooter))) {
                                if (!plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uniqueId)) {
                                    AttackedPlayer.sendMessage(INFO + s1 + gold + "" + italic + "" + plugin.gameSpawnCoolDown.getTime(uniqueId) + "" + aqua + "" + italic + s2);
                                } else {
                                    if (AttackedPlayer.getHealth() - friendlyDamage < 1 && respawnOrLives1) {
                                        // player - killer
                                        // AttackedPlayer - dead
                                        plugin.giveOneKillAndDeathToPlayer.givePlayerKD(AttackedPlayer, playerShooter);
                                        plugin.playerDead.thePlayerIsDead(AttackedPlayer);
                                    } else if (AttackedPlayer.getHealth() - friendlyDamage < 1 && respawnTeamDeathmatch) {
                                        plugin.respawnPlayer.respawnThePlayer(AttackedPlayer);
                                        // player - killer
                                        // AttackedPlayer - dead
                                        plugin.giveOneKillAndDeathToPlayer.givePlayerKD(AttackedPlayer, playerShooter);
                                    } else {
                                        AttackedPlayer.damage(friendlyDamage);
                                    }
                                }
                            } else if (!plugin.deathmatch.getPlayerTeam(AttackedPlayer).equals(plugin.deathmatch.getPlayerTeam(playerShooter))) {
                                if (!plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uniqueId)) {
                                    AttackedPlayer.sendMessage(INFO + s1 + gold + "" + italic + "" + plugin.gameSpawnCoolDown.getTime(uniqueId) + "" + aqua + "" + italic + s2);
                                } else {
                                    if (checkPlayerHealth && respawnOrLives1) {
                                        // player - killer
                                        // AttackedPlayer - dead
                                        plugin.giveOneKillAndDeathToPlayer.givePlayerKD(AttackedPlayer, playerShooter);
                                        plugin.playerDead.thePlayerIsDead(AttackedPlayer);
                                    } else if (checkPlayerHealth && respawnTeamDeathmatch) {
                                        plugin.respawnPlayer.respawnThePlayer(AttackedPlayer);
                                        // player - killer
                                        // AttackedPlayer - dead
                                        plugin.giveOneKillAndDeathToPlayer.givePlayerKD(AttackedPlayer, playerShooter);
                                    } else {
                                        AttackedPlayer.damage(damage);
                                    }
                                }
                            }
                        }
                    } else if (snowball.isGlowing()) {
                        UUID uniqueId = AttackedPlayer.getUniqueId();
                        boolean checkPlayerHealth = AttackedPlayer.getHealth() - MegaDamage < 1;
                        boolean checkLives = plugin.mainConfig.getBooleanGame("enable_lives_free-for-all") && plugin.lives.containsKey(uniqueId) && plugin.lives.get(uniqueId) == 0;
                        boolean respawnOrLives = checkLives || !plugin.mainConfig.getBooleanGame(path);
                        if (plugin.isPlayerPlayingFoodGame(playerShooter) && plugin.isPlayerPlayingFoodGame(AttackedPlayer)) {
                            //damage
                            if (!plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uniqueId)) {
                                AttackedPlayer.sendMessage(INFO + "You have spawn protection for " + gold + "" + italic + "" + plugin.gameSpawnCoolDown.getTime(uniqueId) + "" + aqua + "" + italic + " seconds. You won't die.");
                            } else {
                                if (checkPlayerHealth) {
                                    UUID uuid = playerShooter.getUniqueId();
                                    plugin.playersInPotatoKit.remove(uuid);
                                    plugin.playersInFishKit.add(uuid);
                                    playerShooter.getInventory().remove(ItemManager.PotatoKit);
                                    plugin.kitsCoolDown.playerCoolDownMap.remove(uuid);
                                    plugin.kitPowerCoolDown.playerCoolDownMap.remove(uuid);
                                    plugin.respawnPlayer.respawnThePlayer(AttackedPlayer);
                                    // player - killer
                                    // playerNear - dead
                                    plugin.giveOneKillAndDeathToPlayer.givePlayerKD(AttackedPlayer, playerShooter);
                                } else {
                                    AttackedPlayer.damage(damage);
                                }
                            }
                        } else if (plugin.isPlayerPlayingFreeForAll(playerShooter) && plugin.isPlayerPlayingFreeForAll(AttackedPlayer)) {
                            if (!plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uniqueId)) {
                                AttackedPlayer.sendMessage(INFO + s1 + gold + "" + italic + "" + plugin.gameSpawnCoolDown.getTime(uniqueId) + "" + aqua + "" + italic + s2);
                            } else {
                                if (checkPlayerHealth && respawnOrLives) {
                                    // player - killer
                                    // AttackedPlayer - dead
                                    plugin.giveOneKillAndDeathToPlayer.givePlayerKD(AttackedPlayer, playerShooter);
                                    plugin.playerDead.thePlayerIsDead(AttackedPlayer);
                                } else if (checkPlayerHealth && plugin.mainConfig.getBooleanGame(path)) {
                                    plugin.respawnPlayer.respawnThePlayer(AttackedPlayer);
                                    // player - killer
                                    // AttackedPlayer - dead
                                    plugin.giveOneKillAndDeathToPlayer.givePlayerKD(AttackedPlayer, playerShooter);
                                } else {
                                    AttackedPlayer.damage(MegaDamage);
                                }
                            }
                        } else if (plugin.deathmatch.isPlayerPlayingFoodWars(playerShooter) && plugin.deathmatch.isPlayerPlayingFoodWars(AttackedPlayer)) {
                            // friendly-fire
                            boolean checkLive1 = enableLivesTeamDeathmatch && plugin.lives.containsKey(uniqueId) && plugin.lives.get(uniqueId) == 0;
                            boolean respawnOrLives1 = checkLive1 || !respawnTeamDeathmatch;
                            if (plugin.mainConfig.getBooleanGame("friendly-fire") && plugin.deathmatch.getPlayerTeam(AttackedPlayer).equals(plugin.deathmatch.getPlayerTeam(playerShooter))) {
                                if (!plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uniqueId)) {
                                    AttackedPlayer.sendMessage(INFO + s1 + gold + "" + italic + "" + plugin.gameSpawnCoolDown.getTime(uniqueId) + "" + aqua + "" + italic + s2);
                                } else {
                                    if (AttackedPlayer.getHealth() - MegaFriendlyDamage < 1 && respawnOrLives1) {
                                        // player - killer
                                        // AttackedPlayer - dead
                                        plugin.giveOneKillAndDeathToPlayer.givePlayerKD(AttackedPlayer, playerShooter);
                                        plugin.playerDead.thePlayerIsDead(AttackedPlayer);
                                    } else if (AttackedPlayer.getHealth() - MegaFriendlyDamage < 1 && respawnTeamDeathmatch) {
                                        plugin.respawnPlayer.respawnThePlayer(AttackedPlayer);
                                        // player - killer
                                        // AttackedPlayer - dead
                                        plugin.giveOneKillAndDeathToPlayer.givePlayerKD(AttackedPlayer, playerShooter);
                                    } else {
                                        AttackedPlayer.damage(MegaFriendlyDamage);
                                    }
                                }
                            } else if (!plugin.deathmatch.getPlayerTeam(AttackedPlayer).equals(plugin.deathmatch.getPlayerTeam(playerShooter))) {
                                if (!plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uniqueId)) {
                                    AttackedPlayer.sendMessage(INFO + s1 + gold + "" + italic + "" + plugin.gameSpawnCoolDown.getTime(uniqueId) + "" + aqua + "" + italic + s2);
                                } else {
                                    if (checkPlayerHealth && respawnOrLives1) {
                                        // player - killer
                                        // AttackedPlayer - dead
                                        plugin.giveOneKillAndDeathToPlayer.givePlayerKD(AttackedPlayer, playerShooter);
                                        plugin.playerDead.thePlayerIsDead(AttackedPlayer);
                                    } else if (checkPlayerHealth) {
                                        plugin.respawnPlayer.respawnThePlayer(AttackedPlayer);
                                        // player - killer
                                        // AttackedPlayer - dead
                                        plugin.giveOneKillAndDeathToPlayer.givePlayerKD(AttackedPlayer, playerShooter);
                                    } else {
                                        AttackedPlayer.damage(MegaDamage);
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
