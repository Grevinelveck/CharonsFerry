package at.grevinelveck.charonsferry.functions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import at.grevinelveck.charonsferry.CharonsFerry;

public class Ressurection {
	FileConfiguration config = CharonsFerry.plugin.getConfig();

	public void life(String player, Player target) {
		target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 0,
				0), true);
		target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 0, 0),
				true);
		config.set("player." + player + ".alive", true);
		CharonsFerry.plugin.saveConfig();
		for (Player players : Bukkit.getOnlinePlayers()) {
			players.showPlayer(target);
		}
		target.sendMessage(ChatColor.GOLD + "You have been Revived");
		if (config.getString(("player." + player + ".block")).equalsIgnoreCase(
				"Lapis")) {
			target.setHealth(5);
			target.setFoodLevel(5);
			config.set("player." + player + ".block", "Null");
		}
		if (config.getString(("player." + player + ".block")).equalsIgnoreCase(
				"Iron")) {
			target.setHealth(10);
			target.setFoodLevel(10);
			config.set("player." + player + ".block", "Null");
		}
		if (config.getString(("player." + player + ".block")).equalsIgnoreCase(
				"Gold")) {
			target.setHealth(15);
			target.setFoodLevel(15);
			config.set("player." + player + ".block", "Null");
		}
		if (config.getString(("player." + player + ".block")).equalsIgnoreCase(
				"Diamond")) {
			target.setHealth(20);
			target.setFoodLevel(20);
			config.set("player." + player + ".block", "Null");
		}

	}
}
