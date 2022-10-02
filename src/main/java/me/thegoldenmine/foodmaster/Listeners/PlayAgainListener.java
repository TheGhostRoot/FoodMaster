package me.thegoldenmine.foodmaster.Listeners;

import me.thegoldenmine.foodmaster.*;
import me.thegoldenmine.foodmaster.Items.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class PlayAgainListener implements Listener {
    private final FoodMaster plugin;

    public PlayAgainListener(FoodMaster main) {
        plugin = main;
    }

    @EventHandler
    public synchronized void onClick(PlayerInteractEvent event) {
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
        Action action = event.getAction();
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        boolean b = action.equals(Action.RIGHT_CLICK_BLOCK) || action.equals(Action.RIGHT_CLICK_AIR);
        if (b && player.getInventory().getItemInMainHand().equals(ItemManager.playAgain) && plugin.playAgain.containsKey(uuid) && plugin.playerGroup.isPlayerInGroup(player)) {
            String name = plugin.playAgain.get(player.getUniqueId());
            switch (name) {
                case "pve-zombie": {
                    String[] args = {"start", "pve", "Zombie"};
                    if (checkIfHolding(player)) {
                        removeThePlayerFromPlayAgain(player);
                        plugin.startCommand.mainStart(player, args);
                        player.spawnParticle(Particle.EXPLOSION_NORMAL, player.getLocation(), 1);
                    } else {
                        for (UUID uuids : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
                            if (uuids != null) {
                                Player playersInG = Bukkit.getPlayer(uuids);
                                if (playersInG != null) {
                                    playersInG.sendMessage(INFO + "All the group members must hold a play again item.");
                                }
                            }
                        }
                    }
                    break;
                }
                case "pve-skeleton": {
                    String[] args = {"start", "pve", "Skeleton"};
                    if (checkIfHolding(player)) {
                        removeThePlayerFromPlayAgain(player);
                        plugin.startCommand.mainStart(player, args);
                        player.spawnParticle(Particle.EXPLOSION_NORMAL, player.getLocation(), 1);
                    } else {
                        for (UUID uuids : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
                            if (uuids != null) {
                                Player playersInG = Bukkit.getPlayer(uuids);
                                if (playersInG != null) {
                                    playersInG.sendMessage(INFO + "All the group members must hold a play again item.");
                                }
                            }
                        }
                    }
                    break;
                }
                case "pve-slime": {
                    String[] args = {"start", "pve", "Slime"};
                    if (checkIfHolding(player)) {
                        removeThePlayerFromPlayAgain(player);
                        plugin.startCommand.mainStart(player, args);
                        player.spawnParticle(Particle.EXPLOSION_NORMAL, player.getLocation(), 1);
                    } else {
                        for (UUID uuids : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
                            if (uuids != null) {
                                Player playersInG = Bukkit.getPlayer(uuids);
                                if (playersInG != null) {
                                    playersInG.sendMessage(INFO + "All the group members must hold a play again item.");
                                }
                            }
                        }
                    }
                    break;
                }
                case "pve-spider": {
                    String[] args = {"start", "pve", "Spider"};
                    if (checkIfHolding(player)) {
                        removeThePlayerFromPlayAgain(player);
                        plugin.startCommand.mainStart(player, args);
                        player.spawnParticle(Particle.EXPLOSION_NORMAL, player.getLocation(), 1);
                    } else {
                        for (UUID uuids : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
                            if (uuids != null) {
                                Player playersInG = Bukkit.getPlayer(uuids);
                                if (playersInG != null) {
                                    playersInG.sendMessage(INFO + "All the group members must hold a play again item.");
                                }
                            }
                        }
                    }
                    break;
                }
                case "pve-enderman": {
                    String[] args = {"start", "pve", "Enderman"};
                    if (checkIfHolding(player)) {
                        removeThePlayerFromPlayAgain(player);
                        plugin.startCommand.mainStart(player, args);
                        player.spawnParticle(Particle.EXPLOSION_NORMAL, player.getLocation(), 1);
                    } else {
                        for (UUID uuids : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
                            if (uuids != null) {
                                Player playersInG = Bukkit.getPlayer(uuids);
                                if (playersInG != null) {
                                    playersInG.sendMessage(INFO + "All the group members must hold a play again item.");
                                }
                            }
                        }
                    }
                    break;
                }
                case "free-for-all": {
                    String[] args = {"start", "free-for-all"};
                    if (checkIfHolding(player)) {
                        removeThePlayerFromPlayAgain(player);
                        plugin.startCommand.mainStart(player, args);
                        player.spawnParticle(Particle.EXPLOSION_NORMAL, player.getLocation(), 1);
                    } else {
                        for (UUID uuids : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
                            if (uuids != null) {
                                Player playersInG = Bukkit.getPlayer(uuids);
                                if (playersInG != null) {
                                    playersInG.sendMessage(INFO + "All the group members must hold a play again item.");
                                }
                            }
                        }
                    }
                    break;
                }
                case "food-game": {
                    String[] args = {"start", "food-game"};
                    if (checkIfHolding(player)) {
                        removeThePlayerFromPlayAgain(player);
                        plugin.startCommand.mainStart(player, args);
                        player.spawnParticle(Particle.EXPLOSION_NORMAL, player.getLocation(), 1);
                    } else {
                        for (UUID uuids : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
                            if (uuids != null) {
                                Player playersInG = Bukkit.getPlayer(uuids);
                                if (playersInG != null) {
                                    playersInG.sendMessage(INFO + "All the group members must hold a play again item.");
                                }
                            }
                        }
                    }
                    break;
                }
                case "team-deathmatch2": {
                    String[] args = {"start", "team-deathmatch", "2"};
                    if (checkIfHolding(player)) {
                        removeThePlayerFromPlayAgain(player);
                        plugin.startCommand.mainStart(player, args);
                        player.spawnParticle(Particle.EXPLOSION_NORMAL, player.getLocation(), 1);
                    } else {
                        for (UUID uuids : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
                            if (uuids != null) {
                                Player playersInG = Bukkit.getPlayer(uuids);
                                if (playersInG != null) {
                                    playersInG.sendMessage(INFO + "All the group members must hold a play again item.");
                                }
                            }
                        }
                    }
                    break;
                }
                case "team-deathmatch3": {
                    String[] args = {"start", "team-deathmatch", "3"};
                    if (checkIfHolding(player)) {
                        removeThePlayerFromPlayAgain(player);
                        plugin.startCommand.mainStart(player, args);
                        player.spawnParticle(Particle.EXPLOSION_NORMAL, player.getLocation(), 1);
                    } else {
                        for (UUID uuids : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
                            if (uuids != null) {
                                Player playersInG = Bukkit.getPlayer(uuids);
                                if (playersInG != null) {
                                    playersInG.sendMessage(INFO + "All the group members must hold a play again item.");
                                }
                            }
                        }
                    }
                    break;
                }
                case "team-deathmatch4": {
                    String[] args = {"start", "team-deathmatch", "4"};
                    if (checkIfHolding(player)) {
                        removeThePlayerFromPlayAgain(player);
                        plugin.startCommand.mainStart(player, args);
                        player.spawnParticle(Particle.EXPLOSION_NORMAL, player.getLocation(), 1);
                    } else {
                        for (UUID uuids : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
                            if (uuids != null) {
                                Player playersInG = Bukkit.getPlayer(uuids);
                                if (playersInG != null) {
                                    playersInG.sendMessage(INFO + "All the group members must hold a play again item.");
                                }
                            }
                        }
                    }
                    break;
                }
                case "team-deathmatch5": {
                    String[] args = {"start", "team-deathmatch", "5"};
                    if (checkIfHolding(player)) {
                        removeThePlayerFromPlayAgain(player);
                        plugin.startCommand.mainStart(player, args);
                        player.spawnParticle(Particle.EXPLOSION_NORMAL, player.getLocation(), 1);
                    } else {
                        for (UUID uuids : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
                            if (uuids != null) {
                                Player playersInG = Bukkit.getPlayer(uuids);
                                if (playersInG != null) {
                                    playersInG.sendMessage(INFO + "All the group members must hold a play again item.");
                                }
                            }
                        }
                    }
                    break;
                }
            }
        }
    }

    public void removeThePlayerFromPlayAgain(Player player) {
        for (UUID uuid : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
            if (uuid != null) {
                Player playerInGroup = Bukkit.getPlayer(uuid);
                if (playerInGroup != null) {
                    UUID uuids = playerInGroup.getUniqueId();
                    plugin.playAgain.remove(uuids);
                }
            }
        }
    }

    public boolean checkIfHolding(Player player) {
        List<UUID> holding = new ArrayList<>();
        for (UUID uuid : plugin.playerGroup.getPlayersInGroupOfPlayer(player)) {
            if (uuid != null) {
                Player playerInGroup = Bukkit.getPlayer(uuid);
                if (playerInGroup != null && playerInGroup.getInventory().getItemInMainHand().equals(ItemManager.playAgain)) {
                    holding.add(playerInGroup.getUniqueId());
                }
            }
        }
        return holding.containsAll(new HashSet<>(plugin.playerGroup.getPlayersInGroupOfPlayer(player)));
    }
}
