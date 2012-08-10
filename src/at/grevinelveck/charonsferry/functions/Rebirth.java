package at.grevinelveck.charonsferry.functions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.configuration.file.FileConfiguration;

import at.grevinelveck.charonsferry.CharonsFerry;

public class Rebirth implements CommandExecutor {
	FileConfiguration config = CharonsFerry.plugin.getConfig();

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if ((label.equalsIgnoreCase("ReviveAll"))
				&& (sender.hasPermission("CharonsFerry.ReviveAll"))) {
			for (String setAlive : config.getConfigurationSection("player").getKeys(false)) {
	            config.set("player."+setAlive + ".alive", true);
	            CharonsFerry.plugin.saveConfig();

			}

		}
		Bukkit.broadcastMessage(ChatColor.GOLD+"The gods are smiling apon you this day.  Everyone is granted new life.");
		return true;
	}
}
