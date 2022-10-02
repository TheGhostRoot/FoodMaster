package me.thegoldenmine.foodmaster.Others;

import me.thegoldenmine.foodmaster.FoodMaster;
import me.thegoldenmine.foodmaster.Items.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class Game {
    private final FoodMaster plugin;

    public Game(FoodMaster main) {
        plugin = main;
    }

    public void setReadyStatus(Player player) {
        String ReadyName = Objects.requireNonNull(ItemManager.isReady.getItemMeta()).getDisplayName();
        String NotReadyName = Objects.requireNonNull(ItemManager.notReady.getItemMeta()).getDisplayName();
        if (plugin.ReadyPlayers.contains(player.getUniqueId())) {
            List<String> lore3 = new ArrayList<>();
            lore3.add(ChatColor.GRAY + "" + ChatColor.ITALIC + " Your ready status >> " + ChatColor.GREEN + "" + ChatColor.BOLD + "Ready");
            ItemMeta meta = getItemByName(player.getInventory(), NotReadyName).getItemMeta();
            ItemStack item = getItemByName(player.getInventory(), NotReadyName);
            assert meta != null;
            meta.setLore(lore3);
            assert item != null;
            item.setItemMeta(meta);
        } else {
            List<String> lore4 = new ArrayList<>();
            lore4.add(ChatColor.GRAY + "" + ChatColor.ITALIC + " Your ready status >> " + ChatColor.RED + "" + ChatColor.BOLD + "UNReady");
            ItemMeta meta = getItemByName(player.getInventory(), ReadyName).getItemMeta();
            ItemStack item = getItemByName(player.getInventory(), ReadyName);
            assert meta != null;
            meta.setLore(lore4);
            assert item != null;
            item.setItemMeta(meta);
        }
    }

    public ItemStack getItemByName(Inventory inventory, String name) {
        for (ItemStack item : inventory) {
            if (item != null && item.getItemMeta() != null && item.getItemMeta().getDisplayName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    public String getGameName(Player player) {
        UUID uuid = player.getUniqueId();
        String name = null;
        for (String GameName : plugin.playersInGame.keySet()) {
            if (GameName != null) {
                Set<UUID> players = plugin.playersInGame.get(GameName);
                if (players.contains(uuid)) {
                    name = GameName;
                }
            }
        }
        return name;
    }

    public boolean isPlayerInGame(Player player) {
        UUID uuid = player.getUniqueId();
        for (String GameName : plugin.playersInGame.keySet()) {
            if (GameName != null) {
                Set<UUID> players = plugin.playersInGame.get(GameName);
                if (plugin.playersInGame.get(GameName) != null) {
                    if (players.contains(uuid)) {
                        return true;
                    }
                } else {
                    Set<UUID> empty = new HashSet<>();
                    plugin.playersInGame.put(GameName, empty);
                }
            }
        }
        return false;
    }

    public synchronized void removePlayerFromGame(Player player) {
        String name = getGameName(player);
        if (name != null) {
            Set<UUID> players = plugin.playersInGame.get(name);
            if (players == null || players.isEmpty()) {
                plugin.playersInGame.remove(name);
            }
            if (players != null) {
                players.remove(player.getUniqueId());
                plugin.tpPlayersInGameNameLoc.remove(player.getUniqueId());
            }
        }
    }

    public Set<UUID> getAllPlayersInGame() {
        Set<UUID> all = new HashSet<>();
        for (String name : plugin.playersInGame.keySet()) {
            if (name != null && !plugin.playersInGame.get(name).isEmpty()) {
                all.addAll(new HashSet<>(plugin.playersInGame.get(name)));
            }
        }
        return all;
    }

    public void randomKitGiver(Player player) {
        ChatColor darkGray = ChatColor.DARK_GRAY;
        ChatColor strikethrough = ChatColor.STRIKETHROUGH;
        ChatColor gold = ChatColor.GOLD;
        ChatColor bold = ChatColor.BOLD;
        ChatColor green = ChatColor.GREEN;
        ChatColor italic = ChatColor.ITALIC;
        String s;
        if (plugin.mainConfig.getStrMain("name") != null) {
            s = " " + plugin.mainConfig.getStrMain("name") + " ";
        } else {
            s = " FoodMaster ";
        }
        String NORMAL = darkGray + "" + strikethrough + "-" + gold + "" + bold + s + darkGray + "" + strikethrough + "-" + green + "" + italic + " ";
        UUID uuid = player.getUniqueId();
        if (plugin.playersRandomKit.contains(uuid)) {
            if (plugin.playersChoiceFoodGame.contains(uuid)) {
                // Cookie
                plugin.playersInCookieKit.add(uuid);
                player.sendMessage(NORMAL + "" + gold + "" + italic + "Cookie" + green + "" + italic + " kit was chosen.");
            } else {
                int randomInt = new Random().nextInt(6);
                if (randomInt == 0) {
                    // Fish
                    plugin.playersInFishKit.add(uuid);
                    player.sendMessage(NORMAL + "" + gold + "" + italic + "Fish" + green + "" + italic + " kit was chosen.");
                } else if (randomInt == 1) {
                    // Bread
                    plugin.playersInBreadKit.add(uuid);
                    player.sendMessage(NORMAL + "" + gold + "" + italic + "Bread" + green + "" + italic + " kit was chosen.");
                } else if (randomInt == 2) {
                    // Potato
                    plugin.playersInPotatoKit.add(uuid);
                    player.sendMessage(NORMAL + "" + gold + "" + italic + "Potato" + green + "" + italic + " kit was chosen.");
                } else if (randomInt == 3) {
                    // Melon
                    plugin.playersInMelonKit.add(uuid);
                    player.sendMessage(NORMAL + "" + gold + "" + italic + "Melon" + green + "" + italic + " kit was chosen.");
                } else if (randomInt == 4) {
                    // Beef
                    plugin.playersInBeefKit.add(uuid);
                    player.sendMessage(NORMAL + "" + gold + "" + italic + "Beef" + green + "" + italic + " kit was chosen.");
                } else {
                    // Cookie
                    plugin.playersInCookieKit.add(uuid);
                    player.sendMessage(NORMAL + "" + gold + "" + italic + "Cookie" + green + "" + italic + " kit was chosen.");
                }
            }
            plugin.playersRandomKit.remove(uuid);
        }
    }
}
