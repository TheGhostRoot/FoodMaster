package me.thegoldenmine.foodmaster.Listeners.AntiGlitchListeners;

import me.thegoldenmine.foodmaster.*;
import me.thegoldenmine.foodmaster.Items.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;

import java.util.UUID;

public class DamageListener implements Listener {
    private final FoodMaster plugin;

    public DamageListener(FoodMaster plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSlime(SlimeSplitEvent event) {
        Slime slime = event.getEntity();
        NamespacedKey name = new NamespacedKey(plugin, "boss");
        NamespacedKey namePlayer = new NamespacedKey(plugin, "player");
        PersistentDataContainer data = slime.getPersistentDataContainer();
        if (event.isCancelled()) {
            return;
        }
        if (data.has(name, PersistentDataType.STRING) && data.has(namePlayer, PersistentDataType.STRING)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
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
        Entity defender = event.getEntity();
        if (defender instanceof Player) {
            Player player = (Player) defender;
            // respawm_player
            if (!plugin.playerPvE.isPlayerPlayingPvE(player) && plugin.waitingLobby.isPlayerInWaitingLobby(player)) {
                event.setCancelled(true);
            }
            if (plugin.playerPvE.isPlayerPlayingPvE(player)) {
                double healthNewDo = player.getHealth() - event.getDamage();
                int healthNew = (int) healthNewDo;
                if (healthNew <= 0) {
                    event.setCancelled(true);
                    if (plugin.mainConfig.getBooleanPvE("respawm_player")) {
                        plugin.respawnPlayer.respawnThePlayer(player);
                    } else {
                        String name = plugin.game.getGameName(player);
                        boolean groupDeath = plugin.stillAlive.get(name).size() == 1 && plugin.playerGroup.isPlayerInGroup(player);
                        boolean soloDeath = plugin.stillAlive.get(name).size() == 1 && !plugin.playerGroup.isPlayerInGroup(player);
                        if (soloDeath || groupDeath) {
                            if (groupDeath) {
                                plugin.stillAlive.get(name).remove(player.getUniqueId());
                                for (UUID uuid : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
                                    if (uuid != null) {
                                        Player player1 = Bukkit.getPlayer(uuid);
                                        if (player1 != null && !player1.equals(player)) {
                                            player1.sendMessage(INFO + "" + gold + "" + italic + "" + player.getName() + "" + aqua + "" + italic + " just die and the game ends.");
                                        }
                                    }
                                }
                                player.sendMessage(INFO + "You just die and the game ends.");
                                plugin.endTheGame.endThePvE(player);
                                return;
                            }
                            plugin.stillAlive.get(name).remove(player.getUniqueId());
                            player.sendMessage(INFO + "You just die and the game ends.");
                            plugin.endTheGame.endThePvE(player);
                            return;
                        } else {
                            UUID uuid = player.getUniqueId();
                            plugin.stillAlive.get(name).remove(player.getUniqueId());
                            if (plugin.playerGroup.isPlayerInGroup(player)) {
                                // kits
                                plugin.playersInPotatoKit.remove(uuid);
                                plugin.playersInBreadKit.remove(uuid);
                                plugin.playersInMelonKit.remove(uuid);
                                plugin.playersInCookieKit.remove(uuid);
                                plugin.playersInFishKit.remove(uuid);
                                plugin.playersRandomKit.remove(uuid);
                                // teams
                                plugin.playersInBlueTeams.remove(uuid);
                                plugin.playersInCyanTeams.remove(uuid);
                                plugin.playersInRedTeams.remove(uuid);
                                plugin.playersInGreenTeams.remove(uuid);
                                plugin.playersInYellowTeams.remove(uuid);
                                plugin.playersRandomTeam.remove(uuid);
                                plugin.playersInFoodGame.remove(uuid);
                                // others
                                plugin.kickedPlayers.remove(uuid);
                                plugin.ReadySystem.remove(uuid);
                                plugin.ReadyPlayers.remove(uuid);
                                AttributeInstance attributeInstance = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                                assert attributeInstance != null;
                                attributeInstance.setBaseValue(20);
                                player.setHealth(20);
                                player.removePotionEffect(PotionEffectType.SPEED);
                                player.getInventory().clear();
                                player.setGameMode(GameMode.SPECTATOR);
                                for (UUID uuid1 : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
                                    if (uuid1 != null) {
                                        Player player1 = Bukkit.getPlayer(uuid1);
                                        if (player1 != null) {
                                            if (player1.getUniqueId() != player.getUniqueId()) {
                                                player1.sendMessage(INFO + "" + gold + "" + italic + "" + player.getName() + "" + aqua + "" + italic + " just die.");
                                            } else {
                                                player1.sendMessage(INFO + "You just die.");
                                            }
                                        }
                                    }
                                }
                                return;
                            }
                            player.sendMessage(INFO + "You just die.");
                            plugin.endTheGame.endThePvE(player);
                        }
                    }
                }
            }
            double healthNewDo = player.getHealth() - event.getDamage();
            int healthNew = (int) healthNewDo;
            if (plugin.waitingLobby.isPlayerInWaitingLobby(player)) {
                event.setCancelled(true);
            }
            if (healthNew <= 0 && plugin.playerGroup.isPlayerInGroup(player)) {
                event.setCancelled(true);
            }
        }
        if (defender instanceof LivingEntity) {
            LivingEntity living = (LivingEntity) defender;
            if (!living.isDead()) {
                double healthNewDo = living.getHealth() - event.getDamage();
                int healthNew = (int) healthNewDo;
                NamespacedKey name = new NamespacedKey(plugin, "boss");
                NamespacedKey namePlayer = new NamespacedKey(plugin, "player");
                PersistentDataContainer data = living.getPersistentDataContainer();
                if (data.has(name, PersistentDataType.STRING) && data.has(namePlayer, PersistentDataType.STRING)) {
                    if (event.isCancelled()) {
                        return;
                    }
                    String playerName = data.get(namePlayer, PersistentDataType.STRING);
                    String bossType = data.get(name, PersistentDataType.STRING);
                    if (playerName != null && bossType != null) {
                        Player player = Bukkit.getPlayer(playerName);
                        if (healthNew <= 0 && player != null) {
                            if (bossType.equals("BOSS")) {
                                if (plugin.playerGroup.isPlayerInGroup(player) && plugin.game.isPlayerInGame(player) && !plugin.waitingLobby.isPlayerInWaitingLobby(player)) {
                                    for (UUID uuid : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
                                        if (uuid != null) {
                                            Player player1 = Bukkit.getPlayer(uuid);
                                            if (player1 != null) {
                                                if (!player1.equals(player)) {
                                                    player1.sendMessage(INFO + "" + gold + "" + italic + "" + player.getName() + "" + aqua + "" + italic + " just killed the boss.");
                                                } else {
                                                    player1.sendMessage(INFO + "You just killed the boss.");
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    player.sendMessage(INFO + "You just killed the boss.");
                                }
                                plugin.endTheGame.endThePvE(player);
                                return;
                            } else if (bossType.equals("MINI")) {
                                return;
                            }
                        }
                    }
                    if (living instanceof Zombie) {
                        if (data.get(name, PersistentDataType.STRING).equals("BOSS")) {
                            living.setCustomName(plugin.mainConfig.getStrPvE("Zombie_Name") + " " + ChatColor.GRAY + "<" + ChatColor.GOLD + "" + String.valueOf(healthNew) + "" + ChatColor.GRAY + "/" + ChatColor.GREEN + "" + String.valueOf(plugin.mainConfig.getIntPvE("Zombie_Health")) + "" + ChatColor.GRAY + ">");
                        }
                        if (data.get(name, PersistentDataType.STRING).equals("MINI")) {
                            living.setCustomName(plugin.mainConfig.getStrPvE("Zombie_Name") + "" + ChatColor.BLUE + "" + ChatColor.ITALIC + "'s Minion " + ChatColor.GRAY + "<" + ChatColor.GOLD + "" + String.valueOf(healthNew) + "" + ChatColor.GRAY + "/" + ChatColor.GREEN + "" + String.valueOf(plugin.mainConfig.getIntPvE("Zombie_Minion_Health")) + "" + ChatColor.GRAY + ">");
                        }
                    } else if (living instanceof Spider) {
                        if (data.get(name, PersistentDataType.STRING).equals("BOSS")) {
                            living.setCustomName(plugin.mainConfig.getStrPvE("Spider_Name") + " " + ChatColor.GRAY + "<" + ChatColor.GOLD + "" + String.valueOf(healthNew) + "" + ChatColor.GRAY + "/" + ChatColor.GREEN + "" + String.valueOf(plugin.mainConfig.getIntPvE("Spider_Health")) + "" + ChatColor.GRAY + ">");
                        }
                        if (data.get(name, PersistentDataType.STRING).equals("MINI")) {
                            living.setCustomName(plugin.mainConfig.getStrPvE("Spider_Name") + "" + ChatColor.BLUE + "" + ChatColor.ITALIC + "'s Minion " + ChatColor.GRAY + "<" + ChatColor.GOLD + "" + String.valueOf(healthNew) + "" + ChatColor.GRAY + "/" + ChatColor.GREEN + "" + String.valueOf(plugin.mainConfig.getIntPvE("Spider_Minion_Health")) + "" + ChatColor.GRAY + ">");
                        }
                    } else if (living instanceof Skeleton && data.get(name, PersistentDataType.STRING).equals("BOSS")) {
                        living.setCustomName(plugin.mainConfig.getStrPvE("Skeleton_Name") + " " + ChatColor.GRAY + "<" + ChatColor.GOLD + "" + String.valueOf(healthNew) + "" + ChatColor.GRAY + "/" + ChatColor.GREEN + "" + String.valueOf(plugin.mainConfig.getIntPvE("Skeleton_Health")) + "" + ChatColor.GRAY + ">");
                    } else if (living instanceof Slime) {
                        if (data.get(name, PersistentDataType.STRING).equals("BOSS")) {
                            living.setCustomName(plugin.mainConfig.getStrPvE("Slime_Name") + " " + ChatColor.GRAY + "<" + ChatColor.GOLD + "" + String.valueOf(healthNew) + "" + ChatColor.GRAY + "/" + ChatColor.GREEN + "" + String.valueOf(plugin.mainConfig.getIntPvE("Slime_Health")) + "" + ChatColor.GRAY + ">");
                        }
                        if (data.get(name, PersistentDataType.STRING).equals("MINI")) {
                            living.setCustomName(plugin.mainConfig.getStrPvE("Slime_Name") + "" + ChatColor.BLUE + "" + ChatColor.ITALIC + "'s Minion " + ChatColor.GRAY + "<" + ChatColor.GOLD + "" + String.valueOf(healthNew) + "" + ChatColor.GRAY + "/" + ChatColor.GREEN + "" + String.valueOf(plugin.mainConfig.getIntPvE("Slime_Minion_Health")) + "" + ChatColor.GRAY + ">");
                        }
                    } else if (living instanceof Enderman) {
                        if (data.get(name, PersistentDataType.STRING).equals("BOSS")) {
                            living.setCustomName(plugin.mainConfig.getStrPvE("Enderman_Name") + " " + ChatColor.GRAY + "<" + ChatColor.GOLD + "" + String.valueOf(healthNew) + "" + ChatColor.GRAY + "/" + ChatColor.GREEN + "" + String.valueOf(plugin.mainConfig.getIntPvE("Enderman_Health")) + "" + ChatColor.GRAY + ">");
                        }
                        if (data.get(name, PersistentDataType.STRING).equals("MINI")) {
                            living.setCustomName(plugin.mainConfig.getStrPvE("Enderman_Name") + "" + ChatColor.BLUE + "" + ChatColor.ITALIC + "'s Minion " + ChatColor.GRAY + "<" + ChatColor.GOLD + "" + String.valueOf(healthNew) + "" + ChatColor.GRAY + "/" + ChatColor.GREEN + "" + String.valueOf(plugin.mainConfig.getIntPvE("Enderman_Minion_Health")) + "" + ChatColor.GRAY + ">");
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
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
        Entity attacker = event.getDamager();
        Entity defender = event.getEntity();
        final int damage = plugin.mainConfig.getIntGame("damage");
        if (attacker instanceof Player && defender instanceof Player) {
            Player playerAtt = (Player) attacker;
            UUID uuid = playerAtt.getUniqueId();
            Player playerDef = (Player) defender;
            if (!plugin.playerGroup.isPlayerInGroup(playerAtt) && !plugin.playerGroup.isPlayerInGroup(playerDef) && !plugin.mainConfig.getBooleanGame("enable_pvp_outside_the_plugin")) {
                event.setCancelled(true);
            }
            if (!plugin.waitingLobby.isPlayerInWaitingLobby(playerAtt) && !plugin.game.isPlayerInGame(playerAtt) && plugin.playerGroup.isPlayerInGroup(playerAtt) && !plugin.mainConfig.getBooleanMain("group_player_hit_teammate") && !plugin.waitingLobby.isPlayerInWaitingLobby(playerDef) && !plugin.game.isPlayerInGame(playerDef) && plugin.playerGroup.isPlayerInGroup(playerDef) && !plugin.mainConfig.getBooleanMain("group_player_hit_teammate")) {
                event.setCancelled(true);
            }
            if (plugin.waitingLobby.isPlayerInWaitingLobby(playerAtt)) {
                event.setCancelled(true);
            }
            if (plugin.playerPvE.isPlayerPlayingPvE(playerAtt) && plugin.playerPvE.isPlayerPlayingPvE(playerDef)) {
                event.setCancelled(true);
            }
            if (plugin.game.isPlayerInGame(playerAtt) && !playerAtt.getInventory().getItemInMainHand().equals(ItemManager.FishKit) && !plugin.playersInFishKit.contains(playerAtt.getUniqueId())) {
                event.setCancelled(true);
            }
            if (plugin.game.isPlayerInGame(playerDef) && playerAtt.getInventory().getItemInMainHand().equals(ItemManager.FishKit) && plugin.playersInFishKit.contains(playerAtt.getUniqueId())) {
                final int friendlyDamage = plugin.mainConfig.getIntGame("friendly_damage");
                UUID uniqueId = playerDef.getUniqueId();
                boolean checkLive1 = plugin.mainConfig.getBooleanGame("enable_lives_team_deathmatch") && plugin.lives.containsKey(uniqueId) && plugin.lives.get(uniqueId) == 0;
                boolean respawnOrLives1 = checkLive1 || !plugin.mainConfig.getBooleanGame("respawn_team_deathmatch");
                boolean checkPlayerHealth = playerDef.getHealth() - damage < 2;
                boolean checkLives = plugin.mainConfig.getBooleanGame("enable_lives_free-for-all") && plugin.lives.containsKey(uniqueId) && plugin.lives.get(uniqueId) == 0;
                boolean respawnOrLives = checkLives || !plugin.mainConfig.getBooleanGame("respawn_free-for-all");
                if (plugin.isPlayerPlayingFoodGame(playerAtt) && plugin.isPlayerPlayingFoodGame(playerDef)) {
                    //damage
                    if (!plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uniqueId)) {
                        playerDef.sendMessage(INFO + "You have spawn protection for " + gold + "" + italic + "" + plugin.gameSpawnCoolDown.getTime(uniqueId) + "" + aqua + "" + italic + " seconds. You won't die.");
                        event.setCancelled(true);
                    } else {
                        if (checkPlayerHealth) {
                            // player is the winner
                            event.setCancelled(true);
                            plugin.playersInFishKit.remove(uuid);
                            plugin.playersInBeefKit.add(uuid);
                            plugin.kitsCoolDown.playerCoolDownMap.remove(uuid);
                            plugin.kitPowerCoolDown.playerCoolDownMap.remove(uuid);
                            plugin.respawnPlayer.respawnThePlayer(playerDef);
                            playerAtt.getInventory().remove(ItemManager.FishKit);
                            // player - killer
                            // playerNear - dead
                            plugin.giveOneKillAndDeathToPlayer.givePlayerKD(playerDef, playerAtt);
                            return;
                        }
                        playerDef.damage(damage);
                        try {
                            playerAtt.getVelocity().normalize().multiply(-0.25).checkFinite();
                            playerAtt.setVelocity(playerAtt.getVelocity().normalize().multiply(-0.25));
                        } catch (IllegalArgumentException e) {
                        }
                    }
                } else if (plugin.isPlayerPlayingFreeForAll(playerAtt) && plugin.isPlayerPlayingFreeForAll(playerDef)) {
                    if (plugin.kitsCoolDown.isPlayerInCoolDown(playerAtt)) {
                        playerAtt.sendMessage(WARN + "You have " + gold + "" + italic + "" + plugin.kitsCoolDown.getTime(playerAtt) + "" + yellow + "" + italic + " seconds left.");
                        event.setCancelled(true);
                    } else {
                        plugin.kitsCoolDown.addPlayerToCoolMap(playerAtt, 1);
                        if (!plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uniqueId)) {
                            playerDef.sendMessage(INFO + "You have spawn protection for " + gold + "" + italic + "" + plugin.gameSpawnCoolDown.getTime(uniqueId) + "" + aqua + "" + italic + " seconds. You won't die.");
                            event.setCancelled(true);
                        } else {
                            if (checkPlayerHealth && respawnOrLives) {
                                event.setCancelled(true);
                                // player - killer
                                // playerNear - dead
                                plugin.giveOneKillAndDeathToPlayer.givePlayerKD(playerDef, playerAtt);
                                plugin.playerDead.thePlayerIsDead(playerDef);
                                try {
                                    playerAtt.getVelocity().normalize().multiply(-0.25).checkFinite();
                                    playerAtt.setVelocity(playerAtt.getVelocity().normalize().multiply(-0.25));
                                } catch (IllegalArgumentException e) {
                                }
                            } else if (checkPlayerHealth && plugin.mainConfig.getBooleanGame("respawn_free-for-all")) {
                                event.setCancelled(true);
                                plugin.respawnPlayer.respawnThePlayer(playerDef);
                                // player - killer
                                // playerNear - dead
                                plugin.giveOneKillAndDeathToPlayer.givePlayerKD(playerDef, playerAtt);
                                try {
                                    playerAtt.getVelocity().normalize().multiply(-0.25).checkFinite();
                                    playerAtt.setVelocity(playerAtt.getVelocity().normalize().multiply(-0.25));
                                } catch (IllegalArgumentException e) {
                                }
                            } else {
                                playerDef.damage(damage);
                                try {
                                    playerAtt.getVelocity().normalize().multiply(-0.25).checkFinite();
                                    playerAtt.setVelocity(playerAtt.getVelocity().normalize().multiply(-0.25));
                                } catch (IllegalArgumentException e) {
                                }
                            }
                        }
                    }
                } else if (plugin.deathmatch.isPlayerPlayingFoodWars(playerAtt) && plugin.deathmatch.isPlayerPlayingFoodWars(playerDef)) {
                    if (plugin.kitsCoolDown.isPlayerInCoolDown(playerAtt)) {
                        playerAtt.sendMessage(WARN + "You have " + gold + "" + italic + "" + plugin.kitsCoolDown.getTime(playerAtt) + "" + yellow + "" + italic + " seconds left.");
                        event.setCancelled(true);
                    } else {
                        plugin.kitsCoolDown.addPlayerToCoolMap(playerAtt, 1);
                        if (plugin.mainConfig.getBooleanGame("friendly-fire") && plugin.deathmatch.getPlayerTeam(playerAtt).equals(plugin.deathmatch.getPlayerTeam(playerDef))) {
                            if (!plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uniqueId)) {
                                playerDef.sendMessage(INFO + "You have spawn protection for " + gold + "" + italic + "" + plugin.gameSpawnCoolDown.getTime(uniqueId) + "" + aqua + "" + italic + " seconds. You won't die.");
                                event.setCancelled(true);
                            } else {
                                if (playerDef.getHealth() - friendlyDamage < 2 && respawnOrLives1) {
                                    event.setCancelled(true);
                                    // player - killer
                                    // playerNear - dead
                                    plugin.giveOneKillAndDeathToPlayer.givePlayerKD(playerDef, playerAtt);
                                    plugin.playerDead.thePlayerIsDead(playerDef);
                                    try {
                                        playerAtt.getVelocity().normalize().multiply(-0.25).checkFinite();
                                        playerAtt.setVelocity(playerAtt.getVelocity().normalize().multiply(-0.25));
                                    } catch (IllegalArgumentException e) {
                                    }
                                } else if (playerDef.getHealth() - friendlyDamage < 2 && plugin.mainConfig.getBooleanGame("respawn_team_deathmatch")) {
                                    event.setCancelled(true);
                                    plugin.respawnPlayer.respawnThePlayer(playerDef);
                                    // player - killer
                                    // playerNear - dead
                                    plugin.giveOneKillAndDeathToPlayer.givePlayerKD(playerDef, playerAtt);
                                    try {
                                        playerAtt.getVelocity().normalize().multiply(-0.25).checkFinite();
                                        playerAtt.setVelocity(playerAtt.getVelocity().normalize().multiply(-0.25));
                                    } catch (IllegalArgumentException e) {
                                    }
                                } else {
                                    playerDef.damage(friendlyDamage);
                                    try {
                                        playerAtt.getVelocity().normalize().multiply(-0.25).checkFinite();
                                        playerAtt.setVelocity(playerAtt.getVelocity().normalize().multiply(-0.25));
                                    } catch (IllegalArgumentException e) {
                                    }
                                }
                            }
                        } else if (!plugin.deathmatch.getPlayerTeam(playerAtt).equals(plugin.deathmatch.getPlayerTeam(playerDef))) {
                            if (!plugin.gameSpawnCoolDown.isPlayerNotInCoolDownSpawn(uniqueId)) {
                                playerDef.sendMessage(INFO + "You have spawn protection for " + gold + "" + italic + "" + plugin.gameSpawnCoolDown.getTime(uniqueId) + "" + aqua + "" + italic + " seconds. You won't die.");
                                event.setCancelled(true);
                            } else {
                                if (checkPlayerHealth && respawnOrLives1) {
                                    event.setCancelled(true);
                                    // player - killer
                                    // playerNear - dead
                                    plugin.giveOneKillAndDeathToPlayer.givePlayerKD(playerDef, playerAtt);
                                    plugin.playerDead.thePlayerIsDead(playerDef);
                                    try {
                                        playerAtt.getVelocity().normalize().multiply(-0.25).checkFinite();
                                        playerAtt.setVelocity(playerAtt.getVelocity().normalize().multiply(-0.25));
                                    } catch (IllegalArgumentException e) {
                                    }
                                } else if (checkPlayerHealth && plugin.mainConfig.getBooleanGame("respawn_team_deathmatch")) {
                                    event.setCancelled(true);
                                    plugin.respawnPlayer.respawnThePlayer(playerDef);
                                    // player - killer
                                    // playerNear - dead
                                    plugin.giveOneKillAndDeathToPlayer.givePlayerKD(playerDef, playerAtt);
                                    playerAtt.setVelocity(playerAtt.getVelocity().normalize().multiply(-0.25));
                                } else {
                                    playerDef.damage(damage);
                                    try {
                                        playerAtt.getVelocity().normalize().multiply(-0.25).checkFinite();
                                        playerAtt.setVelocity(playerAtt.getVelocity().normalize().multiply(-0.25));
                                    } catch (IllegalArgumentException e) {
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (attacker instanceof WitherSkull && defender instanceof Player) {
            WitherSkull witherSkull = (WitherSkull) attacker;
            Player playerDef = (Player) defender;
            ProjectileSource shooter = witherSkull.getShooter();
            if (shooter instanceof Player) {
                Player playerAtt = (Player) shooter;
                if (plugin.playerPvE.isPlayerPlayingPvE(playerAtt) && plugin.playerPvE.isPlayerPlayingPvE(playerDef)) {
                    event.setCancelled(true);
                }
                if (plugin.deathmatch.isPlayerPlayingFoodWars(playerAtt) && plugin.deathmatch.isPlayerPlayingFoodWars(playerDef) && plugin.deathmatch.getPlayerTeam(playerAtt).equals(plugin.deathmatch.getPlayerTeam(playerDef)) && !plugin.mainConfig.getBooleanGame("friendly-fire")) {
                    event.setCancelled(true);
                }
            }
        }
        if (attacker instanceof Player && defender instanceof LivingEntity) {
            Player player = (Player) attacker;
            LivingEntity living = (LivingEntity) defender;
            NamespacedKey name = new NamespacedKey(plugin, "boss");
            PersistentDataContainer data = living.getPersistentDataContainer();
            if (plugin.game.isPlayerInGame(player)) {
                UUID uuid = player.getUniqueId();
                if (player.getInventory().getItemInMainHand().equals(ItemManager.FishKit) && plugin.playersInFishKit.contains(uuid) && data.has(name, PersistentDataType.STRING) && living.getCustomName() != null) {
                    boolean BossOrMini = data.get(name, PersistentDataType.STRING).equals("BOSS") || data.get(name, PersistentDataType.STRING).equals("MINI");
                    if (living instanceof Enderman && living.getCustomName().contains(plugin.mainConfig.getStrPvE("Enderman_Name")) && BossOrMini) {
                        living.damage(damage);
                        try {
                            player.getVelocity().normalize().multiply(-0.25).checkFinite();
                            player.setVelocity(player.getVelocity().normalize().multiply(-0.25));
                        } catch (IllegalArgumentException e) {
                        }
                    }
                    if (living instanceof Slime && living.getCustomName().contains(plugin.mainConfig.getStrPvE("Slime_Name")) && data.get(name, PersistentDataType.STRING).equals("BOSS")) {
                        living.damage(damage);
                        try {
                            player.getVelocity().normalize().multiply(-0.25).checkFinite();
                            player.setVelocity(player.getVelocity().normalize().multiply(-0.25));
                        } catch (IllegalArgumentException e) {
                        }
                    }
                    if (living instanceof Zombie && living.getCustomName().contains(plugin.mainConfig.getStrPvE("Zombie_Name")) && BossOrMini) {
                        living.damage(damage);
                        try {
                            player.getVelocity().normalize().multiply(-0.25).checkFinite();
                            player.setVelocity(player.getVelocity().normalize().multiply(-0.25));
                        } catch (IllegalArgumentException e) {
                        }
                    }
                    if (living instanceof Skeleton && living.getCustomName().contains(plugin.mainConfig.getStrPvE("Skeleton_Name")) && data.get(name, PersistentDataType.STRING).equals("BOSS")) {
                        living.damage(damage);
                        try {
                            player.getVelocity().normalize().multiply(-0.25).checkFinite();
                            player.setVelocity(player.getVelocity().normalize().multiply(-0.25));
                        } catch (IllegalArgumentException e) {
                        }
                    }
                    if (living instanceof Spider && living.getCustomName().contains(plugin.mainConfig.getStrPvE("Spider_Name")) && BossOrMini) {
                        living.damage(damage);
                        try {
                            player.getVelocity().normalize().multiply(-0.25).checkFinite();
                            player.setVelocity(player.getVelocity().normalize().multiply(-0.25));
                        } catch (IllegalArgumentException e) {
                        }
                    }
                }
            }
        }
        if (attacker instanceof Player) {
            Player playerAtt = (Player) attacker;
            if (!plugin.waitingLobby.isPlayerInWaitingLobby(playerAtt) && !plugin.game.isPlayerInGame(playerAtt) && plugin.playerGroup.isPlayerInGroup(playerAtt) && plugin.mainConfig.getBooleanMain("group_player_hit_mobs")) {
                event.setCancelled(true);
            }
            if (plugin.waitingLobby.isPlayerInWaitingLobby(playerAtt)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (plugin.waitingLobby.isPlayerInWaitingLobby(player) || plugin.game.isPlayerInGame(player)) {
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
