package at.grevinelveck.charonsferry.functions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import at.grevinelveck.charonsferry.CharonsFerry;

public class Ressurection {
	FileConfiguration config = CharonsFerry.plugin.getConfig();

	public Ressurection(String player, Player target) {
	}

	public void life(String player, Player target) {
		config.set("player." + player + ".alive", true);
		config.set("player." + player + ".revive", false);
		CharonsFerry.plugin.saveConfig();
		target.sendMessage(ChatColor.GOLD + "You have been revived");
		for (Player players : Bukkit.getOnlinePlayers()) {
			players.showPlayer(target);
		}
		target.sendMessage(ChatColor.GOLD + "You have been Revived");
	}
}