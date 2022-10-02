package me.thegoldenmine.foodmaster.Items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Foods {

    public static ItemStack setPotatoKit() {
        ItemStack item = new ItemStack(Material.POTATO, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Potato" + ChatColor.GREEN + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        // §
        lore.add("§6§lLeft Click§7§o - Burst Fire");
        lore.add("§7§o Wither Skulls");
        lore.add("");
        lore.add("§6§lRight Click§7§o - Burst Fire");
        lore.add("§7§o Wither Skulls which deal more damage");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setBreadKit() {
        ItemStack item = new ItemStack(Material.BREAD, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Bread" + ChatColor.GREEN + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        // §
        lore.add("§6§lLeft Click§7§o - creates a fire");
        lore.add("§7§o explosion");
        lore.add("");
        lore.add("§6§lRight Click§7§o - heals everyone");
        lore.add("§7§o from your team near you");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setFishKit() {
        ItemStack item = new ItemStack(Material.TROPICAL_FISH, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Fish" + ChatColor.GREEN + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        // §
        lore.add("§6§lLeft Click§7§o - slap the");
        lore.add("§7§o enemy with fish");
        lore.add("");
        lore.add("§6§lRight Click§7§o - splash all");
        lore.add("§7§o enemies around you");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addEnchant(Enchantment.KNOCKBACK, 2, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setCookieKit() {
        ItemStack item = new ItemStack(Material.COOKIE, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Cookie" + ChatColor.GREEN + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        // §
        lore.add("§6§lLeft Click§7§o - hits lightning");
        lore.add("§7§o where you look within 15 blocks radius");
        lore.add("");
        lore.add("§6§lRight Click§7§o - all enemies around you");
        lore.add("§7§o will be hit by lightning");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setMelonKit() {
        ItemStack item = new ItemStack(Material.MELON_SLICE, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Melon" + ChatColor.GREEN + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        // §
        lore.add("§6§lLeft Click§7§o - Burst Fire");
        lore.add("");
        lore.add("§6§lRight Click§7§o - the bullets get");
        lore.add("§7§o stronger");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setBeefKit() {
        ItemStack item = new ItemStack(Material.COOKED_BEEF, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Beef" + ChatColor.GREEN + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        // §
        lore.add("§6§lLeft Click§7§o - launches cow");
        lore.add("§7§o that explode when hit the ground");
        lore.add("");
        lore.add("§6§lRight Click§7§o - rapid fires cows");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addEnchant(Enchantment.KNOCKBACK, 2, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack fishGUI() {
        ItemStack item = new ItemStack(Material.TROPICAL_FISH, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Fish kit");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        // §
        lore.add("§6§oKit Class > §7§lMelee");
        lore.add("§6§oPower > §7§lSplash");
        lore.add("§9§oHealth §a§l+");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack cookieGUI() {
        ItemStack item = new ItemStack(Material.COOKIE, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Cookie kit");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        // §
        lore.add("§6§oKit Class > §7§lUltimate");
        lore.add("§6§oPower > §7§lLightning");
        lore.add("§9§oNo Bonus Health");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack melonGUI() {
        ItemStack item = new ItemStack(Material.MELON_SLICE, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Melon kit");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        // §
        lore.add("§6§oKit Class > §7§lGun");
        lore.add("§6§oPower > §7§lBurst");
        lore.add("§9§oHealth §a§l+");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack breadGUI() {
        ItemStack item = new ItemStack(Material.BREAD, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Bread kit");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        // §
        lore.add("§6§oKit Class > §7§lTeam Support");
        lore.add("§6§oPower > §7§lHealer");
        lore.add("§9§oNo Bonus Health");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack potatoGUI() {
        ItemStack item = new ItemStack(Material.POTATO, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Potato kit");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        // §
        lore.add("§6§oKit Class > §7§lUndead");
        lore.add("§6§oPower > §7§lWither Skulls");
        lore.add("§9§oNo Bonus Health");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack beefGUI() {
        ItemStack item = new ItemStack(Material.COOKED_BEEF, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Beef kit");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        // §
        lore.add("§6§oKit Class > §7§lLauncher");
        lore.add("§6§oPower > §7§lCow");
        lore.add("§9§oNo Bonus Health");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }
}
