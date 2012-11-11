package at.grevinelveck.charonsferry.functions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import at.grevinelveck.charonsferry.CharonsFerry;

public class AutoRevive {
	FileConfiguration config = CharonsFerry.plugin.getConfig();

	public void autoRevive() {
		Player[] players = Bukkit.getServer().getOnlinePlayers();
		for (int i = 0; i < players.length; i++) {
			Player player = players[i];
			String target = players[i].getName();
			if (!player.hasPermission("CharonsFerry.Poltergeist")) {
			int temp = config.getInt("player." + target + ".time");
			if (temp > 0) {
			int newTime = temp - 1;
			config.set("player." + target + ".time", newTime);
			CharonsFerry.plugin.saveConfig();
			player.sendMessage(ChatColor.BLUE + "You have " + newTime
					+ " minutes left untill ressurection");
			if ((config.getInt("player." + target + ".time")) == 0) {
				for (Player targets : Bukkit.getOnlinePlayers()) {
					targets.showPlayer(player);
				}
				config.set("player." + target + ".alive", true);
				config.set("player." + target + ".time", 0);

				CharonsFerry.plugin.saveConfig();
				player.sendMessage(ChatColor.GOLD
						+ "The gods have smiled apon you.  You have a new lease on life.");
				player.addPotionEffect(new PotionEffect(
						PotionEffectType.BLINDNESS, 0, 0), true);
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,
						0, 0), true);
				player.setHealth(10);
				player.setFoodLevel(10);
			}
		}
	}
}
}
}