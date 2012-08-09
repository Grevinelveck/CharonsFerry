package at.grevinelveck.herosdeath;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class DivineCommands implements CommandExecutor {
	FileConfiguration config = HerosDeath.plugin.getConfig();

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		String player;
		Player target;
		if (label.equalsIgnoreCase("Revive")) {
			if (sender.hasPermission("HerosDeath.Revive")) {
				if (args.length == 0) {
					player = Bukkit.getPlayer(sender.getName()).getName();
					target = Bukkit.getServer().getPlayer(player);
					if (config.getBoolean("player." + player + ".alive") == true) {
						target.sendMessage(ChatColor.RED + "You are not dead");
						return false;
					}
						target.sendMessage(ChatColor.GOLD + "You were revived");
						config.set("player." + player + ".alive", true);
						config.set("player." + player + ".revive", false);
						HerosDeath.plugin.saveConfig();
					}
				} else {
					target = Bukkit.getPlayer(args[0]);
					player = target.getName();

				
				if (config.getBoolean("player." + player + ".alive") == true) {
					target.sendMessage(ChatColor.RED + "You are not dead");
					return false;
				}
					target.sendMessage(ChatColor.GOLD + "You were revived");
					config.set("player." + player + ".alive", true);
					config.set("player." + player + ".revive", false);
					HerosDeath.plugin.saveConfig();
				}
			}
		
		if (label.equalsIgnoreCase("Ghost")) {
			if (sender.hasPermission("HerosDeath.Ghost")) {
				if (args.length == 0) {
					player = Bukkit.getPlayer(sender.getName()).getName();
					target = Bukkit.getServer().getPlayer(player);
					target.sendMessage(ChatColor.RED + "You were Ghosted");
					config.set("player." + player + ".alive", false);
					config.set("player." + player + ".revive", false);
					HerosDeath.plugin.saveConfig();
				} else {
					target = Bukkit.getPlayer(args[0]);
					player = target.getName();

				}
				target.sendMessage(ChatColor.RED + "You were Ghosted");
				config.set("player." + player + ".alive", false);
				config.set("player." + player + ".revive", false);
				HerosDeath.plugin.saveConfig();
			}
			if (label.equalsIgnoreCase("Haunt")) {
				if (sender.hasPermission("HerosDeath.Haunt")) {
					player = Bukkit.getPlayer(sender.getName()).getName();
					target = Bukkit.getServer().getPlayer(player);
					if (args.length == 0) {
						sender.sendMessage(ChatColor.RED
								+ "You cannot haunt yourself");
						return false;

					}
					if (target == null) {
						sender.sendMessage(ChatColor.RED
								+ "Must have a name to revive a player");
					} else {
						if (config.getBoolean("player." + player + ".alive") == true) {
							target.sendMessage(ChatColor.RED
									+ "You are not dead");
							return false;
						} else {
							target.sendMessage(ChatColor.GOLD
									+ "You are now haunting" + (args[0]));

						}

					}
				}
			}
		}
		return false;
	}
}
