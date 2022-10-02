package me.thegoldenmine.foodmaster.Items;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class TeamArmor {

    public static ItemStack setBlueHat() {
        ItemStack item = new ItemStack(Material.LEATHER_HELMET, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        LeatherArmorMeta armorMeta = (LeatherArmorMeta) meta;
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Helmet" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        armorMeta.setColor(Color.BLUE);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_FALL, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setBlueChestplate() {
        ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        LeatherArmorMeta armorMeta = (LeatherArmorMeta) meta;
        armorMeta.setColor(Color.BLUE);
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Chestplate" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_FALL, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setBlueLeggings() {
        ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        LeatherArmorMeta armorMeta = (LeatherArmorMeta) meta;
        armorMeta.setColor(Color.BLUE);
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Leggings" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_FALL, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setBlueBoots() {
        ItemStack item = new ItemStack(Material.LEATHER_BOOTS, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        LeatherArmorMeta armorMeta = (LeatherArmorMeta) meta;
        armorMeta.setColor(Color.BLUE);
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Boots" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_FALL, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    // Red
    public static ItemStack setRedHat() {
        ItemStack item = new ItemStack(Material.LEATHER_HELMET, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        LeatherArmorMeta armorMeta = (LeatherArmorMeta) meta;
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Helmet" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        armorMeta.setColor(Color.RED);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_FALL, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setRedChest() {
        ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        LeatherArmorMeta armorMeta = (LeatherArmorMeta) meta;
        armorMeta.setColor(Color.RED);
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Chestplate" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_FALL, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setRedLegs() {
        ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        LeatherArmorMeta armorMeta = (LeatherArmorMeta) meta;
        armorMeta.setColor(Color.RED);
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Leggings" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_FALL, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setRedBoots() {
        ItemStack item = new ItemStack(Material.LEATHER_BOOTS, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        LeatherArmorMeta armorMeta = (LeatherArmorMeta) meta;
        armorMeta.setColor(Color.RED);
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Boots" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_FALL, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    // Yellow
    public static ItemStack setYellowHat() {
        ItemStack item = new ItemStack(Material.LEATHER_HELMET, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        LeatherArmorMeta armorMeta = (LeatherArmorMeta) meta;
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Helmet" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        armorMeta.setColor(Color.YELLOW);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_FALL, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setYellowChest() {
        ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        LeatherArmorMeta armorMeta = (LeatherArmorMeta) meta;
        armorMeta.setColor(Color.YELLOW);
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Chestplate" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_FALL, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setYellowLegs() {
        ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        LeatherArmorMeta armorMeta = (LeatherArmorMeta) meta;
        armorMeta.setColor(Color.YELLOW);
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Leggings" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_FALL, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setYellowBoots() {
        ItemStack item = new ItemStack(Material.LEATHER_BOOTS, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        LeatherArmorMeta armorMeta = (LeatherArmorMeta) meta;
        armorMeta.setColor(Color.YELLOW);
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Boots" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_FALL, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    // Cyan
    public static ItemStack setCyanHat() {
        ItemStack item = new ItemStack(Material.LEATHER_HELMET, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        LeatherArmorMeta armorMeta = (LeatherArmorMeta) meta;
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Helmet" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        armorMeta.setColor(Color.AQUA);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_FALL, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setCyanChest() {
        ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        LeatherArmorMeta armorMeta = (LeatherArmorMeta) meta;
        armorMeta.setColor(Color.AQUA);
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Chestplate" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_FALL, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setCyanLegs() {
        ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        LeatherArmorMeta armorMeta = (LeatherArmorMeta) meta;
        armorMeta.setColor(Color.AQUA);
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Leggings" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_FALL, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setCyanBoots() {
        ItemStack item = new ItemStack(Material.LEATHER_BOOTS, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        LeatherArmorMeta armorMeta = (LeatherArmorMeta) meta;
        armorMeta.setColor(Color.AQUA);
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Boots" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_FALL, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    // Green
    public static ItemStack setGreenHat() {
        ItemStack item = new ItemStack(Material.LEATHER_HELMET, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        LeatherArmorMeta armorMeta = (LeatherArmorMeta) meta;
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Helmet" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        armorMeta.setColor(Color.GREEN);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_FALL, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setGreenChest() {
        ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        LeatherArmorMeta armorMeta = (LeatherArmorMeta) meta;
        armorMeta.setColor(Color.GREEN);
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Chestplate" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_FALL, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setGreenLegs() {
        ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        LeatherArmorMeta armorMeta = (LeatherArmorMeta) meta;
        armorMeta.setColor(Color.GREEN);
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Leggings" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_FALL, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setGreenBoots() {
        ItemStack item = new ItemStack(Material.LEATHER_BOOTS, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        LeatherArmorMeta armorMeta = (LeatherArmorMeta) meta;
        armorMeta.setColor(Color.GREEN);
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Boots" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_FALL, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, false);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }
}
