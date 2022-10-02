package me.thegoldenmine.foodmaster.Listeners;

import me.thegoldenmine.foodmaster.FoodMaster;
import me.thegoldenmine.foodmaster.Items.ItemManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class OpenGUI implements Listener {
    private final FoodMaster plugin;

    public OpenGUI(FoodMaster plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onOpenInv(PlayerInteractEvent event) {
        Action action = event.getAction();
        Player player = event.getPlayer();
        if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
            if (player.getInventory().getItemInMainHand().equals(ItemManager.kitChooser)) {
                plugin.createGUI.waitGUIkitChoose(player);
            } else if (player.getInventory().getItemInMainHand().equals(ItemManager.teams)) {
                if (plugin.playersThatChoice2Teams.contains(player.getUniqueId())) {
                    boolean red = plugin.deathmatch.getGroupPlayersInRedTeam(player) != null && !plugin.deathmatch.getGroupPlayersInRedTeam(player).isEmpty();
                    boolean blue = plugin.deathmatch.getGroupPlayersInBlueTeam(player) != null && !plugin.deathmatch.getGroupPlayersInBlueTeam(player).isEmpty();
                    if (red && !blue) {
                        plugin.createGUI.createTeam2GUI(plugin.createGUI.guiTeam2.getItem(12), ItemManager.blue);
                    } else if (!red && blue) {
                        plugin.createGUI.createTeam2GUI(ItemManager.red, plugin.createGUI.guiTeam2.getItem(11));
                    } else if (!red) {
                        plugin.createGUI.createTeam2GUI(ItemManager.red, ItemManager.blue);
                    } else {
                        plugin.createGUI.createTeam2GUI(plugin.createGUI.guiTeam2.getItem(12), plugin.createGUI.guiTeam2.getItem(11));
                    }
                    plugin.createGUI.openTeamGUI(player, 2);
                } else if (plugin.playersThatChoice3Teams.contains(player.getUniqueId())) {
                    boolean red = plugin.deathmatch.getGroupPlayersInRedTeam(player) != null && !plugin.deathmatch.getGroupPlayersInRedTeam(player).isEmpty();
                    boolean blue = plugin.deathmatch.getGroupPlayersInBlueTeam(player) != null && !plugin.deathmatch.getGroupPlayersInBlueTeam(player).isEmpty();
                    boolean green = plugin.deathmatch.getGroupPlayersInGreenTeam(player) != null && !plugin.deathmatch.getGroupPlayersInGreenTeam(player).isEmpty();
                    if (red && blue && green) {
                        plugin.createGUI.createTeam3GUI(plugin.createGUI.guiTeam3.getItem(13), plugin.createGUI.guiTeam3.getItem(11), plugin.createGUI.guiTeam3.getItem(12));
                    } else if (!red && blue && green) {
                        plugin.createGUI.createTeam3GUI(ItemManager.red, plugin.createGUI.guiTeam3.getItem(11), plugin.createGUI.guiTeam3.getItem(12));
                    } else if (red && !blue && green) {
                        plugin.createGUI.createTeam3GUI(plugin.createGUI.guiTeam3.getItem(13), ItemManager.blue, plugin.createGUI.guiTeam3.getItem(12));
                    } else if (red && blue) {
                        plugin.createGUI.createTeam3GUI(plugin.createGUI.guiTeam3.getItem(13), plugin.createGUI.guiTeam3.getItem(11), ItemManager.green);
                    } else if (!red && !blue && green) {
                        plugin.createGUI.createTeam3GUI(ItemManager.red, ItemManager.blue, plugin.createGUI.guiTeam3.getItem(12));
                    } else if (!red && !blue) {
                        plugin.createGUI.createTeam3GUI(ItemManager.red, ItemManager.blue, ItemManager.green);
                    } else if (!red) {
                        plugin.createGUI.createTeam3GUI(ItemManager.red, plugin.createGUI.guiTeam3.getItem(11), ItemManager.green);
                    } else {
                        plugin.createGUI.createTeam3GUI(plugin.createGUI.guiTeam3.getItem(13), ItemManager.blue, ItemManager.green);
                    }
                    plugin.createGUI.openTeamGUI(player, 3);
                } else if (plugin.playersThatChoice4Teams.contains(player.getUniqueId())) {
                    boolean red = plugin.deathmatch.getGroupPlayersInRedTeam(player) != null && !plugin.deathmatch.getGroupPlayersInRedTeam(player).isEmpty();
                    boolean blue = plugin.deathmatch.getGroupPlayersInBlueTeam(player) != null && !plugin.deathmatch.getGroupPlayersInBlueTeam(player).isEmpty();
                    boolean green = plugin.deathmatch.getGroupPlayersInGreenTeam(player) != null && !plugin.deathmatch.getGroupPlayersInGreenTeam(player).isEmpty();
                    boolean cyan = plugin.deathmatch.getGroupPlayersInCyanTeam(player) != null && !plugin.deathmatch.getGroupPlayersInCyanTeam(player).isEmpty();
                    if (red && blue && green && cyan) {
                        plugin.createGUI.createTeam4GUI(plugin.createGUI.guiTeam4.getItem(14), plugin.createGUI.guiTeam4.getItem(11), plugin.createGUI.guiTeam4.getItem(13), plugin.createGUI.guiTeam4.getItem(12));
                    } else if (!red && !blue && !green && !cyan) {
                        plugin.createGUI.createTeam4GUI(ItemManager.red, ItemManager.blue, ItemManager.green, ItemManager.aqua);
                    } else if (!red && blue && green && cyan) {
                        plugin.createGUI.createTeam4GUI(ItemManager.red, plugin.createGUI.guiTeam4.getItem(11), plugin.createGUI.guiTeam4.getItem(13), plugin.createGUI.guiTeam4.getItem(12));
                    } else if (red && !blue && green && cyan) {
                        plugin.createGUI.createTeam4GUI(plugin.createGUI.guiTeam4.getItem(14), ItemManager.blue, plugin.createGUI.guiTeam4.getItem(13), plugin.createGUI.guiTeam4.getItem(12));
                    } else if (red && blue && !green && cyan) {
                        plugin.createGUI.createTeam4GUI(plugin.createGUI.guiTeam4.getItem(14), plugin.createGUI.guiTeam4.getItem(11), ItemManager.green, plugin.createGUI.guiTeam4.getItem(12));
                    } else if (red && blue && green) {
                        plugin.createGUI.createTeam4GUI(plugin.createGUI.guiTeam4.getItem(14), plugin.createGUI.guiTeam4.getItem(11), plugin.createGUI.guiTeam4.getItem(13), ItemManager.aqua);
                    } else if (!red && !blue && green && cyan) {
                        plugin.createGUI.createTeam4GUI(ItemManager.red, ItemManager.blue, plugin.createGUI.guiTeam4.getItem(13), plugin.createGUI.guiTeam4.getItem(12));
                    } else if (red && !blue && !green && cyan) {
                        plugin.createGUI.createTeam4GUI(plugin.createGUI.guiTeam4.getItem(14), ItemManager.blue, ItemManager.green, plugin.createGUI.guiTeam4.getItem(12));
                    } else if (red && blue) {
                        plugin.createGUI.createTeam4GUI(plugin.createGUI.guiTeam4.getItem(14), plugin.createGUI.guiTeam4.getItem(11), ItemManager.green, ItemManager.aqua);
                    } else if (!red && blue && !green && !cyan) {
                        plugin.createGUI.createTeam4GUI(ItemManager.red, plugin.createGUI.guiTeam4.getItem(11), ItemManager.green, ItemManager.aqua);
                    } else if (!red && blue && !green) {
                        plugin.createGUI.createTeam4GUI(ItemManager.red, plugin.createGUI.guiTeam4.getItem(11), ItemManager.green, plugin.createGUI.guiTeam4.getItem(12));
                    } else if (!red && blue) {
                        plugin.createGUI.createTeam4GUI(ItemManager.red, plugin.createGUI.guiTeam4.getItem(11), plugin.createGUI.guiTeam4.getItem(13), ItemManager.aqua);
                    } else if (!red && !green) {
                        plugin.createGUI.createTeam4GUI(ItemManager.red, ItemManager.blue, ItemManager.green, plugin.createGUI.guiTeam4.getItem(12));
                    } else if (red && green) {
                        plugin.createGUI.createTeam4GUI(plugin.createGUI.guiTeam4.getItem(14), ItemManager.blue, plugin.createGUI.guiTeam4.getItem(13), ItemManager.aqua);
                    } else if (red) {
                        plugin.createGUI.createTeam4GUI(plugin.createGUI.guiTeam4.getItem(14), ItemManager.blue, ItemManager.green, ItemManager.aqua);
                    } else {
                        plugin.createGUI.createTeam4GUI(ItemManager.red, ItemManager.blue, plugin.createGUI.guiTeam4.getItem(13), ItemManager.aqua);
                    }
                    plugin.createGUI.openTeamGUI(player, 4);
                } else if (plugin.playersThatChoice5Teams.contains(player.getUniqueId())) {
                    boolean red = plugin.deathmatch.getGroupPlayersInRedTeam(player) != null && !plugin.deathmatch.getGroupPlayersInRedTeam(player).isEmpty();
                    boolean blue = plugin.deathmatch.getGroupPlayersInBlueTeam(player) != null && !plugin.deathmatch.getGroupPlayersInBlueTeam(player).isEmpty();
                    boolean green = plugin.deathmatch.getGroupPlayersInGreenTeam(player) != null && !plugin.deathmatch.getGroupPlayersInGreenTeam(player).isEmpty();
                    boolean cyan = plugin.deathmatch.getGroupPlayersInCyanTeam(player) != null && !plugin.deathmatch.getGroupPlayersInCyanTeam(player).isEmpty();
                    boolean yellow = plugin.deathmatch.getGroupPlayersInYellowTeam(player) != null && !plugin.deathmatch.getGroupPlayersInYellowTeam(player).isEmpty();
                    if (red && blue && green && cyan && yellow) {
                        plugin.createGUI.createTeam5GUI(plugin.createGUI.guiTeam5.getItem(15), plugin.createGUI.guiTeam5.getItem(11), plugin.createGUI.guiTeam5.getItem(13), plugin.createGUI.guiTeam5.getItem(12), plugin.createGUI.guiTeam5.getItem(14));
                    } else if (!red && !blue && !green && !cyan && !yellow) {
                        plugin.createGUI.createTeam5GUI(ItemManager.red, ItemManager.blue, ItemManager.green, ItemManager.aqua, ItemManager.yellow);
                    } else if (!red && blue && green && cyan && yellow) {
                        plugin.createGUI.createTeam5GUI(ItemManager.red, plugin.createGUI.guiTeam5.getItem(11), plugin.createGUI.guiTeam5.getItem(13), plugin.createGUI.guiTeam5.getItem(12), plugin.createGUI.guiTeam5.getItem(14));
                    } else if (red && !blue && green && cyan && yellow) {
                        plugin.createGUI.createTeam5GUI(plugin.createGUI.guiTeam5.getItem(15), ItemManager.blue, plugin.createGUI.guiTeam5.getItem(13), plugin.createGUI.guiTeam5.getItem(12), plugin.createGUI.guiTeam5.getItem(14));
                    } else if (red && blue && !green && cyan && yellow) {
                        plugin.createGUI.createTeam5GUI(plugin.createGUI.guiTeam5.getItem(15), plugin.createGUI.guiTeam5.getItem(11), ItemManager.green, plugin.createGUI.guiTeam5.getItem(12), plugin.createGUI.guiTeam5.getItem(14));
                    } else if (red && blue && green && !cyan && yellow) {
                        plugin.createGUI.createTeam5GUI(plugin.createGUI.guiTeam5.getItem(15), plugin.createGUI.guiTeam5.getItem(11), plugin.createGUI.guiTeam5.getItem(13), ItemManager.aqua, plugin.createGUI.guiTeam5.getItem(14));
                    } else if (red && blue && green && cyan) {
                        plugin.createGUI.createTeam5GUI(plugin.createGUI.guiTeam5.getItem(15), plugin.createGUI.guiTeam5.getItem(11), plugin.createGUI.guiTeam5.getItem(13), plugin.createGUI.guiTeam5.getItem(12), ItemManager.yellow);
                    } else if (!red && !blue && green && cyan && yellow) {
                        plugin.createGUI.createTeam5GUI(ItemManager.red, ItemManager.blue, plugin.createGUI.guiTeam5.getItem(13), plugin.createGUI.guiTeam5.getItem(12), plugin.createGUI.guiTeam5.getItem(14));
                    } else if (!red && blue && !green && cyan && yellow) {
                        plugin.createGUI.createTeam5GUI(ItemManager.red, plugin.createGUI.guiTeam5.getItem(11), ItemManager.green, plugin.createGUI.guiTeam5.getItem(12), plugin.createGUI.guiTeam5.getItem(14));
                    } else if (!red && blue && green && !cyan && yellow) { // 10
                        plugin.createGUI.createTeam5GUI(ItemManager.red, plugin.createGUI.guiTeam5.getItem(11), plugin.createGUI.guiTeam5.getItem(13), ItemManager.aqua, plugin.createGUI.guiTeam5.getItem(14));
                    } else if (!red && blue && green && cyan) {
                        plugin.createGUI.createTeam5GUI(ItemManager.red, plugin.createGUI.guiTeam5.getItem(11), plugin.createGUI.guiTeam5.getItem(13), plugin.createGUI.guiTeam5.getItem(12), ItemManager.yellow);
                    } else if (!red && !blue && !green && cyan && yellow) {
                        plugin.createGUI.createTeam5GUI(ItemManager.red, ItemManager.blue, ItemManager.green, plugin.createGUI.guiTeam5.getItem(12), plugin.createGUI.guiTeam5.getItem(14));
                    } else if (!red && blue && !green && !cyan && yellow) {
                        plugin.createGUI.createTeam5GUI(ItemManager.red, plugin.createGUI.guiTeam5.getItem(11), ItemManager.green, ItemManager.aqua, plugin.createGUI.guiTeam5.getItem(14));
                    } else if (!red && blue && green) {
                        plugin.createGUI.createTeam5GUI(ItemManager.red, plugin.createGUI.guiTeam5.getItem(11), plugin.createGUI.guiTeam5.getItem(13), ItemManager.aqua, ItemManager.yellow);
                    } else if (!red && !blue && !green && !cyan) {
                        plugin.createGUI.createTeam5GUI(ItemManager.red, ItemManager.blue, ItemManager.green, ItemManager.aqua, plugin.createGUI.guiTeam5.getItem(14));
                    } else if (red && !blue && !green && cyan && yellow) {
                        plugin.createGUI.createTeam5GUI(plugin.createGUI.guiTeam5.getItem(15), ItemManager.blue, ItemManager.green, plugin.createGUI.guiTeam5.getItem(12), plugin.createGUI.guiTeam5.getItem(14));
                    } else if (red && !blue && green && !cyan && yellow) {
                        plugin.createGUI.createTeam5GUI(plugin.createGUI.guiTeam5.getItem(15), ItemManager.blue, plugin.createGUI.guiTeam5.getItem(13), ItemManager.aqua, plugin.createGUI.guiTeam5.getItem(14));
                    } else if (red && !blue && green && cyan) {
                        plugin.createGUI.createTeam5GUI(plugin.createGUI.guiTeam5.getItem(15), ItemManager.blue, plugin.createGUI.guiTeam5.getItem(13), plugin.createGUI.guiTeam5.getItem(12), ItemManager.yellow);
                    } else if (red && !blue && green) {
                        plugin.createGUI.createTeam5GUI(plugin.createGUI.guiTeam5.getItem(15), ItemManager.blue, plugin.createGUI.guiTeam5.getItem(13), ItemManager.aqua, ItemManager.yellow);
                    } else if (red && !blue && !cyan && yellow) { // 20
                        plugin.createGUI.createTeam5GUI(plugin.createGUI.guiTeam5.getItem(15), ItemManager.blue, ItemManager.green, ItemManager.aqua, plugin.createGUI.guiTeam5.getItem(14));
                    } else if (red && !blue && !cyan) {
                        plugin.createGUI.createTeam5GUI(plugin.createGUI.guiTeam5.getItem(15), ItemManager.blue, ItemManager.green, ItemManager.aqua, ItemManager.yellow);
                    } else if (red && !blue) {
                        plugin.createGUI.createTeam5GUI(plugin.createGUI.guiTeam5.getItem(15), ItemManager.blue, ItemManager.green, plugin.createGUI.guiTeam5.getItem(12), ItemManager.yellow);
                    } else if (!red && !blue && green && cyan) {
                        plugin.createGUI.createTeam5GUI(ItemManager.red, ItemManager.blue, plugin.createGUI.guiTeam5.getItem(13), plugin.createGUI.guiTeam5.getItem(12), ItemManager.yellow);
                    } else if (!red && !blue && green && !yellow) {
                        plugin.createGUI.createTeam5GUI(ItemManager.red, ItemManager.blue, plugin.createGUI.guiTeam5.getItem(13), ItemManager.aqua, ItemManager.yellow);
                    } else if (red && !green && cyan) {
                        plugin.createGUI.createTeam5GUI(plugin.createGUI.guiTeam5.getItem(15), plugin.createGUI.guiTeam5.getItem(11), ItemManager.green, plugin.createGUI.guiTeam5.getItem(12), ItemManager.yellow);
                    } else if (!red && blue && cyan) {
                        plugin.createGUI.createTeam5GUI(ItemManager.red, plugin.createGUI.guiTeam5.getItem(11), ItemManager.green, plugin.createGUI.guiTeam5.getItem(12), ItemManager.yellow);
                    } else if (!red && !blue && !green) {
                        plugin.createGUI.createTeam5GUI(ItemManager.red, ItemManager.blue, ItemManager.green, plugin.createGUI.guiTeam5.getItem(12), ItemManager.yellow);
                    } else if (!red && blue) {
                        plugin.createGUI.createTeam5GUI(ItemManager.red, plugin.createGUI.guiTeam5.getItem(11), ItemManager.green, ItemManager.aqua, ItemManager.yellow);
                    } else if (red && green) {
                        plugin.createGUI.createTeam5GUI(plugin.createGUI.guiTeam5.getItem(15), plugin.createGUI.guiTeam5.getItem(11), plugin.createGUI.guiTeam5.getItem(13), ItemManager.aqua, ItemManager.yellow);
                    } else if (red && !yellow) { // 30
                        plugin.createGUI.createTeam5GUI(plugin.createGUI.guiTeam5.getItem(15), plugin.createGUI.guiTeam5.getItem(11), ItemManager.green, ItemManager.aqua, ItemManager.yellow);
                    }
                    plugin.createGUI.openTeamGUI(player, 5);
                    plugin.createGUI.createTeam5GUI(plugin.createGUI.guiTeam5.getItem(15), plugin.createGUI.guiTeam5.getItem(11), plugin.createGUI.guiTeam5.getItem(13), plugin.createGUI.guiTeam5.getItem(12), plugin.createGUI.guiTeam5.getItem(14));
                }
            }
        }
    }
}
