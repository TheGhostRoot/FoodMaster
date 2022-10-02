package me.thegoldenmine.foodmaster;

import me.thegoldenmine.foodmaster.Items.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SpawnBoss {
    private final FoodMaster plugin;

    public SpawnBoss(FoodMaster main) {
        plugin = main;
    }

    public void ZombieBoss(Location loc, String playerName) {
        if (loc.getWorld() != null) {
            Zombie zombieBoss = loc.getWorld().spawn(loc, Zombie.class);
            zombieBoss.getEquipment().setHelmet(ItemManager.Helmet);
            zombieBoss.getEquipment().setChestplate(ItemManager.Chestplate);
            zombieBoss.getEquipment().setLeggings(ItemManager.Leggins);
            zombieBoss.setCanPickupItems(false);
            zombieBoss.getEquipment().setBoots(ItemManager.Boots);
            zombieBoss.setCustomNameVisible(true);
            AttributeInstance attributeInstance = zombieBoss.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            AttributeInstance attributeDAMAGE = zombieBoss.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
            assert attributeInstance != null;
            assert attributeDAMAGE != null;
            attributeDAMAGE.setBaseValue(plugin.mainConfig.getIntPvE("Zombie_Damage"));
            attributeInstance.setBaseValue(plugin.mainConfig.getIntPvE("Zombie_Health"));
            zombieBoss.setHealth(plugin.mainConfig.getIntPvE("Zombie_Health"));
            zombieBoss.setMaximumNoDamageTicks(35);
            zombieBoss.setNoDamageTicks(35);
            zombieBoss.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999999, 2));
            zombieBoss.getWorld().strikeLightningEffect(zombieBoss.getLocation());
            NamespacedKey name = new NamespacedKey(plugin, "boss");
            NamespacedKey namePlayer = new NamespacedKey(plugin, "player");
            PersistentDataContainer data = zombieBoss.getPersistentDataContainer();
            data.set(name, PersistentDataType.STRING, "BOSS");
            data.set(namePlayer, PersistentDataType.STRING, playerName);
            zombieBoss.setCustomName(plugin.mainConfig.getStrPvE("Zombie_Name") + " " + ChatColor.GRAY + "<" + ChatColor.GOLD + "" + (int) zombieBoss.getHealth() + "" + ChatColor.GRAY + "/" + ChatColor.GREEN + "" + plugin.mainConfig.getIntPvE("Zombie_Health") + "" + ChatColor.GRAY + ">");
            if (plugin.mainConfig.getBooleanPvE("Zombie_Spawn_Minions")) {
                for (int i = 0; i < plugin.mainConfig.getIntPvE("Zombie_Minions"); i++) {
                    Zombie zombieMinion = zombieBoss.getWorld().spawn(zombieBoss.getLocation(), Zombie.class);
                    zombieMinion.getEquipment().setHelmet(ItemManager.Helmet);
                    zombieMinion.getEquipment().setChestplate(ItemManager.Chestplate);
                    zombieMinion.getEquipment().setLeggings(ItemManager.Leggins);
                    zombieMinion.getEquipment().setBoots(ItemManager.Boots);
                    zombieMinion.setCustomNameVisible(true);
                    AttributeInstance attributeInstanceM = zombieMinion.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                    AttributeInstance attributeDAMAGEm = zombieMinion.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
                    assert attributeInstanceM != null;
                    assert attributeDAMAGEm != null;
                    zombieMinion.setMaximumNoDamageTicks(35);
                    zombieMinion.setNoDamageTicks(35);
                    zombieMinion.setCanPickupItems(false);
                    attributeDAMAGEm.setBaseValue(plugin.mainConfig.getIntPvE("Zombie_Minion_Damage"));
                    attributeInstanceM.setBaseValue(plugin.mainConfig.getIntPvE("Zombie_Minion_Health"));
                    zombieMinion.setHealth(plugin.mainConfig.getIntPvE("Zombie_Minion_Health"));
                    zombieMinion.setCustomName(plugin.mainConfig.getStrPvE("Zombie_Name") + "" + ChatColor.BLUE + "" + ChatColor.ITALIC + "'s Minion " + ChatColor.GRAY + "<" + ChatColor.GOLD + "" + (int) zombieMinion.getHealth() + "" + ChatColor.GRAY + "/" + ChatColor.GREEN + "" + plugin.mainConfig.getIntPvE("Zombie_Minion_Health") + "" + ChatColor.GRAY + ">");
                    zombieMinion.setBaby(false);
                    PersistentDataContainer dataMini = zombieMinion.getPersistentDataContainer();
                    dataMini.set(name, PersistentDataType.STRING, "MINI");
                    dataMini.set(namePlayer, PersistentDataType.STRING, playerName);
                    zombieMinion.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999999, 2));
                }
            }
        }
    }

    public void SkeletonBoss(Location loc, String playerName) {
        if (loc.getWorld() != null) {
            Skeleton skeleton = loc.getWorld().spawn(loc, Skeleton.class);
            skeleton.getEquipment().setHelmet(ItemManager.Helmet);
            skeleton.getEquipment().setChestplate(ItemManager.Chestplate);
            skeleton.getEquipment().setLeggings(ItemManager.Leggins);
            skeleton.getEquipment().setBoots(ItemManager.Boots);
            AttributeInstance attributeInstance = skeleton.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            AttributeInstance attributeDAMAGE = skeleton.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
            assert attributeInstance != null;
            assert attributeDAMAGE != null;
            skeleton.setCanPickupItems(false);
            skeleton.setMaximumNoDamageTicks(35);
            skeleton.setNoDamageTicks(35);
            attributeDAMAGE.setBaseValue(plugin.mainConfig.getIntPvE("Skeleton_Damage"));
            attributeInstance.setBaseValue(plugin.mainConfig.getIntPvE("Skeleton_Health"));
            skeleton.setHealth(plugin.mainConfig.getIntPvE("Skeleton_Health"));
            skeleton.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999999, 2));
            skeleton.setCustomName(plugin.mainConfig.getStrPvE("Skeleton_Name") + " " + ChatColor.GRAY + "<" + ChatColor.GOLD + "" + (int) skeleton.getHealth() + "" + ChatColor.GRAY + "/" + ChatColor.GREEN + "" + plugin.mainConfig.getIntPvE("Skeleton_Health") + "" + ChatColor.GRAY + ">");
            skeleton.setCustomNameVisible(true);
            NamespacedKey name = new NamespacedKey(plugin, "boss");
            NamespacedKey namePlayer = new NamespacedKey(plugin, "player");
            PersistentDataContainer data = skeleton.getPersistentDataContainer();
            data.set(name, PersistentDataType.STRING, "BOSS");
            data.set(namePlayer, PersistentDataType.STRING, playerName);
            skeleton.getWorld().strikeLightningEffect(skeleton.getLocation());
        }
    }

    public void SpiderBoss(Location loc, String playerName) {
        if (loc.getWorld() != null) {
            Spider spider = loc.getWorld().spawn(loc, Spider.class);
            spider.setCustomNameVisible(true);
            AttributeInstance attributeInstance = spider.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            AttributeInstance attributeDAMAGE = spider.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
            assert attributeInstance != null;
            assert attributeDAMAGE != null;
            attributeDAMAGE.setBaseValue(plugin.mainConfig.getIntPvE("Spider_Damage"));
            attributeInstance.setBaseValue(plugin.mainConfig.getIntPvE("Spider_Health"));
            spider.setHealth(plugin.mainConfig.getIntPvE("Spider_Health"));
            spider.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999999, 2));
            spider.getWorld().strikeLightningEffect(spider.getLocation());
            spider.setCanPickupItems(false);
            spider.setMaximumNoDamageTicks(35);
            spider.setNoDamageTicks(35);
            NamespacedKey name = new NamespacedKey(plugin, "boss");
            NamespacedKey namePlayer = new NamespacedKey(plugin, "player");
            PersistentDataContainer data = spider.getPersistentDataContainer();
            data.set(name, PersistentDataType.STRING, "BOSS");
            data.set(namePlayer, PersistentDataType.STRING, playerName);
            spider.setCustomName(plugin.mainConfig.getStrPvE("Spider_Name") + " " + ChatColor.GRAY + "<" + ChatColor.GOLD + "" + (int) spider.getHealth() + "" + ChatColor.GRAY + "/" + ChatColor.GREEN + "" + plugin.mainConfig.getIntPvE("Spider_Health") + "" + ChatColor.GRAY + ">");
            if (plugin.mainConfig.getBooleanPvE("Spider_Spawn_Minions")) {
                for (int i = 0; i < plugin.mainConfig.getIntPvE("Spider_Minions"); i++) {
                    Spider spiderMinion = spider.getWorld().spawn(spider.getLocation(), Spider.class);
                    spiderMinion.setCustomNameVisible(true);
                    AttributeInstance attributeInstanceM = spiderMinion.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                    AttributeInstance attributeDAMAGEm = spiderMinion.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
                    assert attributeInstanceM != null;
                    assert attributeDAMAGEm != null;
                    spiderMinion.setCanPickupItems(false);
                    spiderMinion.setMaximumNoDamageTicks(35);
                    spiderMinion.setNoDamageTicks(35);
                    attributeDAMAGEm.setBaseValue(plugin.mainConfig.getIntPvE("Spider_Minion_Damage"));
                    attributeInstanceM.setBaseValue(plugin.mainConfig.getIntPvE("Spider_Minion_Health"));
                    spiderMinion.setHealth(plugin.mainConfig.getIntPvE("Spider_Minion_Health"));
                    spiderMinion.setCustomName(plugin.mainConfig.getStrPvE("Spider_Name") + "" + ChatColor.BLUE + "" + ChatColor.ITALIC + "'s Minion " + ChatColor.GRAY + "<" + ChatColor.GOLD + "" + (int) spiderMinion.getHealth() + "" + ChatColor.GRAY + "/" + ChatColor.GREEN + "" + plugin.mainConfig.getIntPvE("Spider_Minion_Health") + "" + ChatColor.GRAY + ">");
                    PersistentDataContainer dataMini = spiderMinion.getPersistentDataContainer();
                    dataMini.set(name, PersistentDataType.STRING, "MINI");
                    dataMini.set(namePlayer, PersistentDataType.STRING, playerName);
                    spiderMinion.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999999, 2));
                }
            }
        }
    }

    public void EndermanBoss(Location location, String playerName) {
        if (location.getWorld() != null) {
            Enderman enderman = location.getWorld().spawn(location, Enderman.class);
            enderman.setCustomNameVisible(true);
            AttributeInstance attributeInstance = enderman.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            AttributeInstance attributeInstance1 = enderman.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
            assert attributeInstance != null;
            assert attributeInstance1 != null;
            attributeInstance1.setBaseValue(plugin.mainConfig.getIntPvE("Enderman_Damage"));
            attributeInstance.setBaseValue(plugin.mainConfig.getIntPvE("Enderman_Health"));
            enderman.setHealth(plugin.mainConfig.getIntPvE("Enderman_Health"));
            enderman.getWorld().strikeLightningEffect(enderman.getLocation());
            NamespacedKey name = new NamespacedKey(plugin, "boss");
            NamespacedKey namePlayer = new NamespacedKey(plugin, "player");
            enderman.setCanPickupItems(false);
            PersistentDataContainer data = enderman.getPersistentDataContainer();
            data.set(name, PersistentDataType.STRING, "BOSS");
            data.set(namePlayer, PersistentDataType.STRING, playerName);
            enderman.setMaximumNoDamageTicks(35);
            enderman.setNoDamageTicks(35);
            enderman.setCustomName(plugin.mainConfig.getStrPvE("Enderman_Name") + " " + ChatColor.GRAY + "<" + ChatColor.GOLD + "" + (int) enderman.getHealth() + "" + ChatColor.GRAY + "/" + ChatColor.GREEN + "" + plugin.mainConfig.getIntPvE("Enderman_Health") + "" + ChatColor.GRAY + ">");
            if (plugin.mainConfig.getBooleanPvE("Enderman_Spawn_Minions")) {
                for (int i = 0; i < plugin.mainConfig.getIntPvE("Enderman_Minions"); i++) {
                    Enderman endermanMinion = enderman.getWorld().spawn(enderman.getLocation(), Enderman.class);
                    endermanMinion.setCustomNameVisible(true);
                    AttributeInstance attributeInstanceM = endermanMinion.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                    AttributeInstance attributeDAMAGEm = endermanMinion.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
                    assert attributeInstanceM != null;
                    assert attributeDAMAGEm != null;
                    endermanMinion.setCanPickupItems(false);
                    attributeDAMAGEm.setBaseValue(plugin.mainConfig.getIntPvE("Enderman_Minion_Damage"));
                    attributeInstanceM.setBaseValue(plugin.mainConfig.getIntPvE("Enderman_Minion_Health"));
                    endermanMinion.setHealth(plugin.mainConfig.getIntPvE("Enderman_Minion_Health"));
                    endermanMinion.setMaximumNoDamageTicks(35);
                    endermanMinion.setNoDamageTicks(35);
                    endermanMinion.setCustomName(plugin.mainConfig.getStrPvE("Enderman_Name") + "" + ChatColor.BLUE + "" + ChatColor.ITALIC + "'s Minion " + ChatColor.GRAY + "<" + ChatColor.GOLD + "" + (int) endermanMinion.getHealth() + "" + ChatColor.GRAY + "/" + ChatColor.GREEN + "" + plugin.mainConfig.getIntPvE("Enderman_Minion_Health") + "" + ChatColor.GRAY + ">");
                    PersistentDataContainer dataMini = endermanMinion.getPersistentDataContainer();
                    dataMini.set(name, PersistentDataType.STRING, "MINI");
                    dataMini.set(namePlayer, PersistentDataType.STRING, playerName);
                }
            }
        }
    }

    public void SlimeBoss(Location location, String playerName) {
        if (location.getWorld() != null) {
            Slime slime = location.getWorld().spawn(location, Slime.class);
            NamespacedKey name = new NamespacedKey(plugin, "boss");
            NamespacedKey namePlayer = new NamespacedKey(plugin, "player");
            PersistentDataContainer data = slime.getPersistentDataContainer();
            data.set(name, PersistentDataType.STRING, "BOSS");
            data.set(namePlayer, PersistentDataType.STRING, playerName);
            slime.setSize(3);
            AttributeInstance attributeInstance = slime.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            AttributeInstance attributeDAMAGE = slime.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
            assert attributeDAMAGE != null;
            assert attributeInstance != null;
            attributeDAMAGE.setBaseValue(plugin.mainConfig.getIntPvE("Slime_Damage"));
            attributeInstance.setBaseValue(plugin.mainConfig.getIntPvE("Slime_Health"));
            slime.setHealth(plugin.mainConfig.getIntPvE("Slime_Health"));
            slime.setCustomName(plugin.mainConfig.getStrPvE("Slime_Name") + " " + ChatColor.GRAY + "<" + ChatColor.GOLD + "" + (int) slime.getHealth() + "" + ChatColor.GRAY + "/" + ChatColor.GREEN + "" + plugin.mainConfig.getIntPvE("Slime_Health") + "" + ChatColor.GRAY + ">");
            slime.setCustomNameVisible(true);
            slime.setSilent(true);
            slime.setMaximumNoDamageTicks(35);
            slime.setNoDamageTicks(35);
            slime.setCanPickupItems(false);
            slime.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999999, 2));
            if (plugin.mainConfig.getBooleanPvE("Slime_Spawn_Minions")) {
                for (int i = 0; i < plugin.mainConfig.getIntPvE("Slime_Minions"); i++) {
                    Slime endermanMinion = slime.getWorld().spawn(slime.getLocation(), Slime.class);
                    endermanMinion.setCustomNameVisible(true);
                    endermanMinion.setSize(2);
                    AttributeInstance attributeInstanceM = endermanMinion.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                    AttributeInstance attributeDAMAGEm = endermanMinion.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
                    assert attributeInstanceM != null;
                    assert attributeDAMAGEm != null;
                    endermanMinion.setCanPickupItems(false);
                    attributeDAMAGEm.setBaseValue(plugin.mainConfig.getIntPvE("Slime_Minion_Damage"));
                    attributeInstanceM.setBaseValue(plugin.mainConfig.getIntPvE("Slime_Minion_Health"));
                    endermanMinion.setHealth(plugin.mainConfig.getIntPvE("Enderman_Minion_Health"));
                    endermanMinion.setMaximumNoDamageTicks(35);
                    endermanMinion.setNoDamageTicks(35);
                    endermanMinion.setCustomName(plugin.mainConfig.getStrPvE("Slime_Name") + "" + ChatColor.BLUE + "" + ChatColor.ITALIC + "'s Minion " + ChatColor.GRAY + "<" + ChatColor.GOLD + "" + (int) endermanMinion.getHealth() + "" + ChatColor.GRAY + "/" + ChatColor.GREEN + "" + plugin.mainConfig.getIntPvE("Slime_Minion_Health") + "" + ChatColor.GRAY + ">");
                    PersistentDataContainer dataMini = endermanMinion.getPersistentDataContainer();
                    dataMini.set(name, PersistentDataType.STRING, "MINI");
                    dataMini.set(namePlayer, PersistentDataType.STRING, playerName);
                }
            }
        }
    }
}
