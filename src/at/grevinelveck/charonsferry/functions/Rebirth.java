package at.grevinelveck.charonsferry.functions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import at.grevinelveck.charonsferry.CharonsFerry;

public class Rebirth implements CommandExecutor {
	FileConfiguration config = CharonsFerry.plugin.getConfig();

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if ((label.equalsIgnoreCase("ReviveAll"))
				&& (sender.hasPermission("CharonsFerry.ReviveAll"))) {
			Bukkit.broadcastMessage(ChatColor.GOLD+"The gods are smiling upon you this day.  Everyone is granted new life.");
			Player[] players = Bukkit.getServer().getOnlinePlayers();
				for (int i = 0; i < players.length; i++) {
			    Player player=players[i];
				for (Player targets : Bukkit.getOnlinePlayers()) {
				targets.showPlayer(player);
				String name=player.getPlayer().getName();
				if ((config.getBoolean("player." + name + ".alive")) == false){
    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 0, 0), true);
    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 0, 0), true);
    player.setHealth(10);
    player.setFoodLevel(10);
				}
				}

			}
			for (String setAlive : config.getConfigurationSection("player").getKeys(false)) {
	            config.set("player."+setAlive + ".alive", true);
	            
	            CharonsFerry.plugin.saveConfig();

			}

		}

		return true;
	}
}
