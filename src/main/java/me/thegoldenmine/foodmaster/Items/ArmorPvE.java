package me.thegoldenmine.foodmaster.Items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ArmorPvE {

    public static ItemStack setHelmet() {
        ItemStack item = new ItemStack(Material.IRON_HELMET, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        //meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Zombie's Helmet" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_FALL, 4, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setChestplate() {
        ItemStack item = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        //meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Zombie's Chestplate" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_FALL, 4, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setLeggins() {
        ItemStack item = new ItemStack(Material.IRON_LEGGINGS, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        //meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Zombie's Leggings" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_FALL, 4, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setBoots() {
        ItemStack item = new ItemStack(Material.IRON_BOOTS, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        //meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Zombie's Boots" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_FALL, 4, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }
}
