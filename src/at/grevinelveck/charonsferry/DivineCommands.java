package at.grevinelveck.charonsferry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import at.grevinelveck.charonsferry.functions.*;

public class DivineCommands implements CommandExecutor {
	FileConfiguration config = CharonsFerry.plugin.getConfig();
	Ressurection grantLife = new Ressurection(null, null);
	Ghost takeLife = new Ghost(null, null);
	Haunt stalkLife = new Haunt(null, null);


	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		String player = Bukkit.getPlayer(sender.getName()).getName();
		Player target = Bukkit.getServer().getPlayer(player);
		if (label.equalsIgnoreCase("CharonsFerry")
				|| label.equalsIgnoreCase("CF")) {
			switch (args.length) {
			case 0:
				sender.sendMessage(ChatColor.RED
						+ "You must use a command.  valid commands are");
				sender.sendMessage(ChatColor.RED + "Revive,Haunt,Ghost,Boon");
				break;
			case 1: {
				if ((args[0].equalsIgnoreCase("Revive"))
						&& (sender.hasPermission("CharonsFerry.Revive"))) {
					grantLife.life(player, target);
					return true;
				}
				if ((args[0].equalsIgnoreCase("Ghost"))
						&& (sender.hasPermission("CharonsFerry.Ghost"))) {
					takeLife.death(player, target);
					return true;
				}
				if ((args[0].equalsIgnoreCase("Haunt"))
						&& (sender.hasPermission("CharonsFerry.Haunt"))) {
					sender.sendMessage(ChatColor.RED
							+ "You cannot haunt yourself.");
					return true;
				}
				if ((args[0].equalsIgnoreCase("Boon"))
						&& (sender.hasPermission("CharonsFerry.Boon"))) {
					if (config.getBoolean("player." + player + ".revive") == true) {
						sender.sendMessage(ChatColor.BLUE
								+ "You have a boon of the gods and will be ressurected apon death");
						return true;
					}
					if (config.getBoolean("player." + player + ".revive") == false) {
						sender.sendMessage(ChatColor.BLUE
								+ "You do not have a boon from the gods");
						return true;
					}
				} else {
					sender.sendMessage(ChatColor.RED
							+ "Invalid command or you lack approriate permission: availiable types:");
					sender.sendMessage(ChatColor.RED
							+ "Revive,Haunt,Ghost,Boon");
				}
				break;
			}
			case 2: {

				Player target2 = Bukkit.getPlayer(args[1]);
				String player2 = target2.getName();
				if ((args[0].equalsIgnoreCase("Revive"))
						&& (sender instanceof ConsoleCommandSender)
						&& (config.getBoolean("player." + args[1] + ".alive") == false)) {
					grantLife.life(player2, target2);
					return true;
				}
				if ((args[0].equalsIgnoreCase("Revive"))
						&& (sender.hasPermission("CharonsFerry.Revive"))
						&& (config.getBoolean("player." + args[1] + ".alive") == false)) {
					grantLife.life(player2, target2);
					sender.sendMessage(ChatColor.BLUE + "You have ressurected "
							+ player2);
					return true;
				}
				if ((args[0].equalsIgnoreCase("Ghost"))
						&& (sender.hasPermission("CharonsFerry.Ghost"))
						&& (config.getBoolean("player." + args[1] + ".alive") == true)) {
					takeLife.death(player2, target2);
					sender.sendMessage(ChatColor.BLUE + "You have ghosted "
							+ player2);
					return true;
				}
				if ((args[0].equalsIgnoreCase("Haunt"))
						&& (sender.hasPermission("CharonsFerry.Haunt"))
						&& (config.getBoolean("player." + args[1] + ".alive") == false)) {
					stalkLife.deadTP(player2, player, target2, target);
					sender.sendMessage(ChatColor.BLUE + "You are haunting "
							+ player2);
					return true;
				} else {
					sender.sendMessage(ChatColor.RED
							+ "Invalid command, target, or you lack approriate permission: availiable types:");
					sender.sendMessage(ChatColor.RED + "Revive,Haunt,Ghost");
				}
				break;
			}
			default:
				sender.sendMessage(ChatColor.RED + "Too many arguments!");
			}
		}
		return false;
	}
}