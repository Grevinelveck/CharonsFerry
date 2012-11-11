package at.grevinelveck.charonsferry.functions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import at.grevinelveck.charonsferry.CharonsFerry;

public class ReviveAll implements CommandExecutor {
	FileConfiguration config = CharonsFerry.plugin.getConfig();

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if ((label.equalsIgnoreCase("ReviveAll"))
				&& (sender.hasPermission("CharonsFerry.ReviveAll"))) {
			Player[] players = Bukkit.getServer().getOnlinePlayers();
			for (int i = 0; i < players.length; i++) {
				Player player = players[i];

				if (player.hasPermission("CharonsFerry.Poltergeist")) {

				} else {
					String name = player.getPlayer().getName();
					if ((config.getBoolean("player." + name + ".alive")) == false) {
						for (Player targets : Bukkit.getOnlinePlayers()) {
							targets.showPlayer(player);
						}
						player.sendMessage(ChatColor.GOLD
								+ "The gods have smiled apon you.  You have a new lease on life.");
						player.addPotionEffect(new PotionEffect(
								PotionEffectType.BLINDNESS, 0, 0), true);
						player.addPotionEffect(new PotionEffect(
								PotionEffectType.SLOW, 0, 0), true);
						player.setHealth(20);
						player.setFoodLevel(20);
					}
				}

			}
			for (String setAlive : config.getConfigurationSection("player")
					.getKeys(false)) {
				config.set("player." + setAlive + ".alive", true);
				config.set("player." + setAlive + ".time", 0);
				CharonsFerry.plugin.saveConfig();

			}

		}

		return true;
	}
}
