package me.thegoldenmine.foodmaster;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {
    public static ItemStack glassForGUI;
    public static ItemStack close;
    public static ItemStack kitChooser;
    public static ItemStack teams;
    public static ItemStack randomChoice;
    public static ItemStack groupLeave;
    public static ItemStack playAgain;

    // Main GUI
    public static ItemStack pveGUI;
    public static ItemStack pvpGUI;
    public static ItemStack back;
    public static ItemStack helpGUI;
    public static ItemStack rejoinGUI;

    //Ready system
    public static ItemStack isReady;
    public static ItemStack notReady;

    //team items for PlayerUseGUI
    public static ItemStack blue;
    public static ItemStack red;
    public static ItemStack green;
    public static ItemStack yellow;
    public static ItemStack aqua;

    // items for kits in PlayerUseGUI
    public static ItemStack fish;
    public static ItemStack cookie;
    public static ItemStack melon;
    public static ItemStack bread;
    public static ItemStack potato;
    public static ItemStack beef;

    // Items with power
    public static ItemStack FishKit;
    public static ItemStack CookieKit;
    public static ItemStack MelonKit;
    public static ItemStack BreadKit;
    public static ItemStack PotatoKit;
    public static ItemStack BeefKit;

    // Armor
    // Blue
    public static ItemStack BlueHat;
    public static ItemStack BlueChest;
    public static ItemStack BlueLeg;
    public static ItemStack BlueBoots;

    // Red
    public static ItemStack RedHat;
    public static ItemStack RedChest;
    public static ItemStack RedLeg;
    public static ItemStack RedBoots;

    // Yellow
    public static ItemStack YellowHat;
    public static ItemStack YellowChest;
    public static ItemStack YellowLeg;
    public static ItemStack YellowBoots;

    // Cyan
    public static ItemStack CyanHat;
    public static ItemStack CyanChest;
    public static ItemStack CyanLeg;
    public static ItemStack CyanBoots;

    // Green
    public static ItemStack GreenHat;
    public static ItemStack GreenChest;
    public static ItemStack GreenLeg;
    public static ItemStack GreenBoots;

    // pvp gui
    public static ItemStack freeforall;
    public static ItemStack teamDeathmatch;
    public static ItemStack foodGame;

    // teams
    public static ItemStack team2;
    public static ItemStack team3;
    public static ItemStack team4;
    public static ItemStack team5;

    // PvE
    public static ItemStack Helmet;
    public static ItemStack Chestplate;
    public static ItemStack Leggins;
    public static ItemStack Boots;

    public static ItemStack PvEZombie;
    public static ItemStack PvESkeleton;
    public static ItemStack PvESpider;
    public static ItemStack PvESlime;
    public static ItemStack PvEEnderman;

    public static void init() {
        setPlayAgain();
        glassGUI();
        kitsChoose();
        bannerClose();
        teamChoose();
        // Ready system
        setIsReady();
        setNotReady();
        // team items for PlayerUseGUI
        blueTeam();
        redTeam();
        aquaTeam();
        yellowTeam();
        greenTeam();
        // food items for kit PlayerUseGUI
        fishKit();
        cookieKit();
        melonKit();
        breadKit();
        potatoKit();
        // others
        groupLeaveItem();
        setRandomChoice();
        // kit Items
        setPotatoKit();
        setBreadKit();
        setFishKit();
        setCookieKit();
        setMelonKit();

        // some shield
        setSomeShieldHat();
        setSomeShieldChest();
        setSomeShieldLegs();
        setSomeShieldBoots();

        // red
        setRedBoots();
        setRedChest();
        setRedLegs();
        setRedHat();

        // yellow
        setYellowBoots();
        setYellowChest();
        setYellowLegs();
        setYellowHat();

        // cyan
        setCyanBoots();
        setCyanChest();
        setCyanLegs();
        setCyanHat();

        // green
        setGreenBoots();
        setGreenChest();
        setGreenLegs();
        setGreenHat();

        // main gui
        setRejoinGUI();
        setHelpGUI();
        setBack();
        setPvPGUI();
        setPvEGUI();

        // games
        setFreeForAll();
        setFoodGame();
        setTeamDeathmatch();

        // Teams
        setTeam2();
        setTeam3();
        setTeam4();
        setTeam5();

        // PvE
        setHelmet();
        setChestplate();
        setLeggins();
        setBoots();

        setPvEZombie();
        setPvESkeleton();
        setPvESpider();
        setPvEEnderman();
        setPvESlime();

        // new food
        setBeef();
        setBeefKit();
    }
    private static void setBeef() {
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
        beef = item;
    }

    private static void setBeefKit() {
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
        BeefKit = item;
    }

    // PvE
    private static void setHelmet() {
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
        Helmet = item;
    }
    private static void setChestplate() {
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
        Chestplate = item;
    }
    private static void setLeggins() {
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
        Leggins = item;
    }
    private static void setBoots() {
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
        Boots = item;
    }
    private static void setPvESlime() {
        ItemStack item = new ItemStack(Material.SLIME_BALL, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Slime");
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        PvESlime = item;
    }
    private static void setPvEEnderman() {
        ItemStack item = new ItemStack(Material.ENDER_PEARL, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Enderman");
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        PvEEnderman = item;
    }
    private static void setPvEZombie() {
        ItemStack item = new ItemStack(Material.ROTTEN_FLESH, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Zombie");
        meta.setUnbreakable(true);
        /*
        List<String> lore = new ArrayList<>();
        lore.add("§6§lSpawns minions and thunder.");
        meta.setLore(lore);

         */
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        PvEZombie = item;
    }
    private static void setPvESkeleton() {
        ItemStack item = new ItemStack(Material.BONE, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Skeleton");
        meta.setUnbreakable(true);
        /*
        List<String> lore = new ArrayList<>();
        lore.add("§6§lHe shoots fireballs and strikes everyone around him with thunder.");
        meta.setLore(lore);

         */
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        PvESkeleton = item;
    }
    private static void setPvESpider() {
        ItemStack item = new ItemStack(Material.SPIDER_EYE, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Spider");
        meta.setUnbreakable(true);
        /*
        List<String> lore = new ArrayList<>();
        lore.add("§6§lIt jumps at you and spawns minions.");
        meta.setLore(lore);

         */
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        PvESpider = item;
    }

    // Teams
    private static void setTeam5() {
        ItemStack item = new ItemStack(Material.DIAMOND_AXE, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GOLD+""+ChatColor.ITALIC+"5 Teams");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        lore.add("§6§lClick§7§o to play §6Team Deathmatch§7§o with 5 teams.");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        team5 = item;
    }
    private static void setTeam4() {
        ItemStack item = new ItemStack(Material.IRON_AXE, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GOLD+""+ChatColor.ITALIC+"4 Teams");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        lore.add("§6§lClick§7§o to play §6Team Deathmatch§7§o with 4 teams.");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        team4 = item;
    }
    private static void setTeam3() {
        ItemStack item = new ItemStack(Material.STONE_AXE, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GOLD+""+ChatColor.ITALIC+"3 Teams");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        lore.add("§6§lClick§7§o to play §6Team Deathmatch§7§o with 3 teams.");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        team3 = item;
    }
    private static void setTeam2() {
        ItemStack item = new ItemStack(Material.WOODEN_AXE, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GOLD+""+ChatColor.ITALIC+"2 Teams");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        lore.add("§6§lClick§7§o to play §6Team Deathmatch§7§o with 2 teams.");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        team2 = item;
    }

    // pvp GUI
    private static void setFoodGame() {
        ItemStack item = new ItemStack(Material.GOLDEN_APPLE, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Food Game" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        lore.add("§6§lClick§7§o to play §6Food Game");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        foodGame = item;
    }
    private static void setTeamDeathmatch() {
        ItemStack item = new ItemStack(Material.STONE_SWORD, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Team DeathMatch" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        lore.add("§6§lClick§7§o to play §6Team Deathmatch");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        teamDeathmatch = item;
    }
    private static void setFreeForAll() {
        ItemStack item = new ItemStack(Material.GOLDEN_SWORD, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Free For All" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        lore.add("§6§lClick§7§o to play §6Free For All");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        freeforall = item;
    }

    // main GUI
    private static void setRejoinGUI() {
        ItemStack item = new ItemStack(Material.SNOWBALL, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Rejoin" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        lore.add("§6§lClick§7§o to §6Rejoin");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        rejoinGUI = item;
    }
    private static void setHelpGUI() {
        ItemStack item = new ItemStack(Material.BOOK, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|" + ChatColor.GOLD + "" + ChatColor.BOLD + "Help Menu" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        lore.add("§6§lClick§7§o to open §6Help Menu");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        helpGUI = item;
    }
    private static void setBack() {
        ItemStack item = new ItemStack(Material.ARROW, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|" + ChatColor.GREEN + "" + ChatColor.BOLD + "Back" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        lore.add("§6§oClick this to go to the previous menu");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        back = item;
    }
    private static void setPvPGUI() {
        ItemStack item = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|" + ChatColor.RED + "" + ChatColor.BOLD + "PvP" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        lore.add("§6§lClick§7§o to play §cPvP");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        pvpGUI = item;
    }
    private static void setPvEGUI() {
        ItemStack item = new ItemStack(Material.WITHER_SKELETON_SKULL, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|" + ChatColor.RED + "" + ChatColor.BOLD + "PvE" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "|");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        lore.add("§6§lClick§7§o to play §cPvE");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        pveGUI = item;
    }

    // dwdw
    private static void setPlayAgain() {
        ItemStack item = new ItemStack(Material.CREEPER_HEAD, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        //  bold I - blue, magic Play Again - aqua, ital
        meta.setDisplayName(ChatColor.DARK_GREEN + "" + ChatColor.MAGIC + "I" + ChatColor.GREEN + "" + ChatColor.ITALIC + "Play Again" + ChatColor.DARK_GREEN + "" + ChatColor.MAGIC + "I");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        lore.add("§b§oAll group members must hold");
        lore.add("§b§othis item in order to start a game.");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        playAgain = item;
    }

    private static void glassGUI() {
        ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GRAY + "");
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        glassForGUI = item;
        //plugin.items.add(item);
    }

    // Set Shield
    // Blue
    private static void setSomeShieldHat() {
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
        BlueHat = item;
    }

    private static void setSomeShieldChest() {
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
        BlueChest = item;
    }

    private static void setSomeShieldLegs() {
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
        BlueLeg = item;
    }

    private static void setSomeShieldBoots() {
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
        BlueBoots = item;
    }

    // Red
    private static void setRedHat() {
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
        RedHat = item;
    }

    private static void setRedChest() {
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
        RedChest = item;
    }

    private static void setRedLegs() {
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
        RedLeg = item;
    }

    private static void setRedBoots() {
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
        RedBoots = item;
    }

    // Yellow
    private static void setYellowHat() {
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
        YellowHat = item;
    }

    private static void setYellowChest() {
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
        YellowChest = item;
    }

    private static void setYellowLegs() {
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
        YellowLeg = item;
    }

    private static void setYellowBoots() {
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
        YellowBoots = item;
    }

    // Cyan
    private static void setCyanHat() {
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
        CyanHat = item;
    }

    private static void setCyanChest() {
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
        CyanChest = item;
    }

    private static void setCyanLegs() {
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
        CyanLeg = item;
    }

    private static void setCyanBoots() {
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
        CyanBoots = item;
    }

    // Green
    private static void setGreenHat() {
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
        GreenHat = item;
    }

    private static void setGreenChest() {
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
        GreenChest = item;
    }

    private static void setGreenLegs() {
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
        GreenLeg = item;
    }

    private static void setGreenBoots() {
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
        GreenBoots = item;
    }

    // end shield

    private static void setPotatoKit() {
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
        PotatoKit = item;
        //plugin.items.add(item);
    }

    private static void setBreadKit() {
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
        BreadKit = item;
        //plugin.items.add(item);
    }

    private static void setFishKit() {
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
        FishKit = item;
        //plugin.items.add(item);
    }

    private static void setCookieKit() {
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
        CookieKit = item;
        //plugin.items.add(item);
    }

    private static void setMelonKit() {
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
        MelonKit = item;
        //plugin.items.add(item);
    }

    private static void setIsReady() {
        ItemStack item = new ItemStack(Material.LIME_DYE, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Ready");
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        isReady = item;
        //plugin.items.add(item);
    }

    private static void setNotReady() {
        ItemStack item = new ItemStack(Material.GRAY_DYE, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Unready");
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        notReady = item;
        //plugin.items.add(item);
    }

    private static void setRandomChoice() {
        ItemStack item = new ItemStack(Material.DIAMOND, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Random");
        List<String> lore = new ArrayList<>();
        // §
        lore.add("§6§lClick§7§o to pick random");
        meta.setLore(lore);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        randomChoice = item;
        //plugin.items.add(item);
    }

    private static void groupLeaveItem() {
        ItemStack item = new ItemStack(Material.BEETROOT_SOUP, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Group Leave");
        List<String> lore = new ArrayList<>();
        // §
        lore.add("§6§lRight Click§7§o to leave the group");
        meta.setLore(lore);
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        groupLeave = item;
        //plugin.items.add(item);
    }

    private static void bannerClose() {
        ItemStack item = new ItemStack(Material.BARRIER, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Close");
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        close = item;
        //plugin.items.add(item);
    }

    private static void kitsChoose() {
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Kits");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        // §
        lore.add("§6§lRight Click§7§o to open the kit GUI");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.KNOCKBACK, 1, false);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        kitChooser = item;
        //plugin.items.add(item);
    }

    private static void teamChoose() {
        ItemStack item = new ItemStack(Material.EMERALD, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Teams");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        // §
        lore.add("§6§lRight Click§7§o to open the teams GUI");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.KNOCKBACK, 1, false);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        teams = item;
        //plugin.items.add(item);
    }

    // Team items for PlayerUseGUI
    private static void blueTeam() {
        ItemStack item = new ItemStack(Material.BLUE_WOOL, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "Blue team");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        // §
        lore.add("§6§lJoin§7§o the §9§lBlue§7§o team");
        lore.add("");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        blue = item;
        //plugin.items.add(item);
    }

    private static void redTeam() {
        ItemStack item = new ItemStack(Material.RED_WOOL, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Red team");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        // §
        lore.add("§6§lJoin§7§o the §c§lRed§7§o team");
        lore.add("");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        red = item;
        //plugin.items.add(item);
    }

    private static void aquaTeam() {
        ItemStack item = new ItemStack(Material.CYAN_WOOL, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Cyan team");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        // §
        lore.add("§6§lJoin§7§o the §b§lCyan§7§o team");
        lore.add("");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        aqua = item;
        //plugin.items.add(item);
    }

    private static void greenTeam() {
        ItemStack item = new ItemStack(Material.GREEN_WOOL, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Green team");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        // §
        lore.add("§6§lJoin§7§o the §a§lGreen§7§o team");
        lore.add("");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        green = item;
        //plugin.items.add(item);
    }

    private static void yellowTeam() {
        ItemStack item = new ItemStack(Material.YELLOW_WOOL, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "Yellow team");
        meta.setUnbreakable(true);
        List<String> lore = new ArrayList<>();
        // §
        lore.add("§6§lJoin§7§o the §e§lYellow§7§o team");
        lore.add("");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        yellow = item;
        //plugin.items.add(item);
    }

    // food kits
    private static void fishKit() {
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
        fish = item;
        //plugin.items.add(item);
    }

    private static void cookieKit() {
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
        cookie = item;
        //plugin.items.add(item);
    }

    private static void melonKit() {
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
        melon = item;
        //plugin.items.add(item);
    }

    private static void breadKit() {
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
        bread = item;
        //plugin.items.add(item);
    }

    private static void potatoKit() {
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
        potato = item;
    }
}
