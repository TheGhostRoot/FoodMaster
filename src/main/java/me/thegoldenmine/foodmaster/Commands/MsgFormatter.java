package me.thegoldenmine.foodmaster.Commands;

import static org.bukkit.ChatColor.*;

import me.thegoldenmine.foodmaster.FoodMaster;

public class MsgFormatter {
	private final String prefixInfo;
	private final String prefixWarn;
	private final String prefixNormal;
	private final String prefixError;

	public MsgFormatter(FoodMaster plugin) {
		String pluginName;
		if (plugin.mainConfig.getStrMain("name") != null) {
			pluginName = " " + plugin.mainConfig.getStrMain("name");
		} else {
			pluginName = " FoodMaster";
		}

		String dash = "" + DARK_GRAY + STRIKETHROUGH + "-";
		String pName = " " + GOLD + BOLD + pluginName;

		String info = " " + AQUA + BOLD + "INFO ";
		String infoStyle = " " + AQUA + ITALIC;

		String normal = " ";
		String normalStyle = " " + GREEN + ITALIC;

		String warn = " " + YELLOW + BOLD + "WARN ";
		String warnStyle = " " + YELLOW + ITALIC;

		String error = " " + RED + BOLD + "ERROR ";
		String errorStyle = " " + RED + ITALIC;

		prefixInfo = dash + pName + info + dash + infoStyle;
		prefixNormal = dash + pName + normal + dash + normalStyle;
		prefixWarn = dash + pName + warn + dash + warnStyle;
		prefixError = dash + pName + error + dash + errorStyle;
	}

	public String getPrefixInfo() {
		return prefixInfo;
	}

	public String getPrefixWarn() {
		return prefixWarn;
	}

	public String getPrefixNormal() {
		return prefixNormal;
	}

	public String getPrefixError() {
		return prefixError;
	}
}
