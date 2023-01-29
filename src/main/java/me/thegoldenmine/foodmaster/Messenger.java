package me.thegoldenmine.foodmaster;

import static org.bukkit.ChatColor.*;

import org.bukkit.entity.Player;

public class Messenger {
	public static final String MAIN_STYLE = "" + GOLD + BOLD;
	public static final String MAIN_GENERAL = "" + GOLD + ITALIC;

	public static final String COMMAND_GENERAL = "" + DARK_PURPLE + ITALIC;
	public static final String COMMAND_DIS = "" + DARK_GRAY + ITALIC;

	public static final String NORMAL_STYLE = "" + GREEN + BOLD;
	public static final String NORMAL_GENERAL = "" + GREEN + ITALIC;

	public static final String NORMAL_STYLE_2 = "" + LIGHT_PURPLE + BOLD;
	public static final String NORMAL_GENERAL_2 = "" + LIGHT_PURPLE + ITALIC;

	public static final String INFO_STYLE = "" + AQUA + BOLD;
	public static final String INFO_GENERAL = "" + AQUA + ITALIC;

	public static final String WARN_STYLE = "" + YELLOW + BOLD;
	public static final String WARN_GENERAL = "" + YELLOW + ITALIC;

	public static final String ERROR_STYLE = "" + RED + BOLD;
	public static final String ERROR_GENERAL = "" + RED + ITALIC;

	public static final String DASH = "" + DARK_GRAY + STRIKETHROUGH + "-" + RESET;

	private final String prefixInfo;
	private final String prefixWarn;
	private final String prefixNormal;
	private final String prefixError;

	public Messenger(FoodMaster plugin) {
		String pluginName = plugin.mainConfig.getPluginName();
		String prefix = DASH + " " + MAIN_STYLE + pluginName + " ";

		prefixNormal = prefix + DASH + NORMAL_GENERAL + " ";
		prefixInfo = prefix + INFO_STYLE + "INFO " + DASH + INFO_GENERAL + " ";
		prefixWarn = prefix + WARN_STYLE + "WARN " + DASH + WARN_GENERAL + " ";
		prefixError = prefix + ERROR_STYLE + "ERROR " + DASH + ERROR_GENERAL + " ";
	}

	public void info(Player player, String msg) {
		player.sendMessage(prefixInfo + msg);
	}

	public void normal(Player player, String msg) {
		player.sendMessage(prefixNormal + msg);
	}

	public void warn(Player player, String msg) {
		player.sendMessage(prefixWarn + msg);
	}

	public void error(Player sendMessageToPlayer, String msg) {
		sendMessageToPlayer.sendMessage(prefixError + msg);
	}

	public String getYouDontHavePermissionMessage(String permission) {
		String format = "You don't have " + ERROR_GENERAL + "\"%s\"" + WARN_GENERAL + " permission";
		return String.format(format, permission);
	}

	public void warnPermission(Player player, String permission) {
		warn(player, getYouDontHavePermissionMessage(permission));
	}
}
