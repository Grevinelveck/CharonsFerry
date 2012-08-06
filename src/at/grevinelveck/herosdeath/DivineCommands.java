package at.grevinelveck.herosdeath;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DivineCommands implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {
		Player player;
		if (label.equalsIgnoreCase("Revive")) {
			if (sender.hasPermission("HerosDeath.Revive")) {
				if (args.length == 0) {
					player = Bukkit.getPlayer(sender.getName());
				} else {
					player = Bukkit.getPlayer(args[0]);
				}
				if (player == null) {
					sender.sendMessage("Must have a name to revive a player");
				}
			}
		}
		if (label.equalsIgnoreCase("Ghost")) {
			if (sender.hasPermission("HerosDeath.Ghost")) {
				if (args.length == 0) {
					player = Bukkit.getPlayer(sender.getName());
				} else {
					player = Bukkit.getPlayer(args[0]);
					;
				}
			}
		}
		if (label.equalsIgnoreCase("Haunt")) {
			if (sender.hasPermission("HerosDeath.Haunt")) {
				player = Bukkit.getPlayer(args[0]);
			} else {
				sender.sendMessage("Player not found");

			}
		}
		return false;
	}
}
