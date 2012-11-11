package at.grevinelveck.charonsferry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import at.grevinelveck.charonsferry.functions.*;

public class DivineCommands implements CommandExecutor {
	FileConfiguration config = CharonsFerry.plugin.getConfig();
	Revive grantLife = new Revive();
	Ghost takeLife = new Ghost();
	Haunt stalkLife = new Haunt();

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		String player = sender.getName();
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
				if (args[0].equalsIgnoreCase("Revive")) {
					if (!sender.hasPermission("CharonsFerry.Revive")) {
						sender.sendMessage(ChatColor.RED
								+ "You do not have permission to use this command");
						return false;
					}
					if (!config.getBoolean("player." + player + ".alive") == false) {
						sender.sendMessage(ChatColor.RED + "You are not dead");
						return false;

					}

					grantLife.life(player, target);
					return true;
				}
				if (args[0].equalsIgnoreCase("Ghost")) {
					if (!sender.hasPermission("CharonsFerry.Ghost")) {
						sender.sendMessage(ChatColor.RED
								+ "You do not have permission to use this command");
						return false;
					}
					if (!config.getBoolean("player." + player + ".alive") == true) {
						sender.sendMessage(ChatColor.RED + "You are not alive");
						return false;

					}
					takeLife.death(player, target);
					return true;
				}
				if ((args[0].equalsIgnoreCase("Haunt"))
						&& (sender.hasPermission("CharonsFerry.Haunt"))) {
					sender.sendMessage(ChatColor.RED
							+ "You cannot haunt yourself.");
					return false;
				}
				if (args[0].equalsIgnoreCase("Boon")) {
					if (!sender.hasPermission("CharonsFerry.Boon")) {
						sender.sendMessage(ChatColor.RED
								+ "You do not have permission to use this command");
						return false;
					}
					if (config.getBoolean("player." + player + ".revive") == true) {
						sender.sendMessage(ChatColor.BLUE
								+ "You have a boon of the gods and will be ressurected apon death");
						return true;
					}
					if ((config.getBoolean("player." + player + ".revive") == false)
							&& (config
									.getBoolean("player." + player + ".alive") == false)) {
						sender.sendMessage(ChatColor.BLUE + "You are dead");
						return true;
					}
					if (config.getBoolean("player." + player + ".revive") == false) {
						sender.sendMessage(ChatColor.BLUE
								+ "You do not have a boon from the gods.");
						return true;
					}
				}
				break;
			}
			case 2: {
				if (!config.contains("player." + args[1])) {
					sender.sendMessage(ChatColor.RED
							+ "That player does not exist.");
					return false;
				}
				if (Bukkit.getPlayerExact(args[1]) == null) {
					sender.sendMessage(ChatColor.RED
							+ "That player is not online.");
					return false;
				}
				Player target2 = Bukkit.getPlayer(args[1]);
				String player2 = target2.getName();

				if (args[0].equalsIgnoreCase("Revive")) {
					if (!sender.hasPermission("CharonsFerry.Revive")) {
						sender.sendMessage(ChatColor.RED
								+ "You do not have permission to use this command");
						return false;

					}
					if (!config.getBoolean("player." + args[1] + ".alive") == false) {
						sender.sendMessage(ChatColor.RED
								+ "That player is not dead");
						return false;
					}
					grantLife.life(player2, target2);
					sender.sendMessage(ChatColor.BLUE + "You have ressurected "
							+ player2);
					return true;
				}
				if (args[0].equalsIgnoreCase("Ghost")) {
					if (!sender.hasPermission("CharonsFerry.Ghost")) {
						sender.sendMessage(ChatColor.RED
								+ "You do not have permission to use this command");
						return false;
					}
					if (!config.getBoolean("player." + args[1] + ".alive") == true) {
						sender.sendMessage(ChatColor.RED
								+ "That player is not alive");
						return false;
					}
					takeLife.death(player2, target2);
					sender.sendMessage(ChatColor.BLUE + "You have ghosted "
							+ player2);
					return true;
				}
				if (args[0].equalsIgnoreCase("Boon")) {
					if (!sender.hasPermission("CharonsFerry.Boon.Other")) {
						sender.sendMessage(ChatColor.RED
								+ "You do not have permission to use this command");
						return false;
					}
					if (config.getBoolean("player." + player2 + ".revive") == true) {
						sender.sendMessage(ChatColor.BLUE
								+ "They have a boon of the gods and will be ressurected apon death");
						return true;
					}
					if ((config.getBoolean("player." + player2 + ".revive") == false)
							&& (config
									.getBoolean("player." + player + ".alive") == false)) {
						sender.sendMessage(ChatColor.BLUE
								+ "That player is dead");
						return true;
					}
					if (config.getBoolean("player." + player2 + ".revive") == false) {
						sender.sendMessage(ChatColor.BLUE
								+ "They do not have a boon from the gods.");
						return true;
					}
				}
				if (args[0].equalsIgnoreCase("Haunt")) {
					if (!sender.hasPermission("CharonsFerry.Haunt.Killer")) {
						sender.sendMessage(ChatColor.RED
								+ "You do not have permission to use this command");
						return false;
					}
					if (config.getBoolean("player." + player + ".alive") == true) {
						sender.sendMessage(ChatColor.RED + "You are not dead");
						return false;
					}
					if ((!sender.hasPermission("CharonsFerry.Haunt"))
							&& (target2 != target.getKiller())) {
						sender.sendMessage(ChatColor.RED
								+ "That is not your killer");
						return false;

					}
					stalkLife.deadTP(player2, player, target2, target);
					sender.sendMessage(ChatColor.BLUE + "You are haunting "
							+ player2);
					return true;
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
