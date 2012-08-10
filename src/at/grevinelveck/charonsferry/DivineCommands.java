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
	private Ressurection grantLife;
	private Ghost takeLife;
	private Haunt stalkLife;

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		String player = Bukkit.getPlayer(sender.getName()).getName();
		Player target = Bukkit.getServer().getPlayer(player);
		Player target2 = Bukkit.getPlayer(args[1]);
		String player2 = target.getName();
		if ((label.equalsIgnoreCase("CharonsFrerry"))||(label.equalsIgnoreCase("CF"))) {
			switch (args.length) {
			case 0:
				sender.sendMessage(ChatColor.RED+"You must use a command");
				sender.sendMessage(ChatColor.RED+"Revive,Haunt,Ghost,Boon");
				break;
			case 1:
			{
				if ((args[0].equalsIgnoreCase("Revive"))&&(sender.hasPermission("CharonsFerry.Revive"))) {
					grantLife=new Ressurection(player, target);
					return true;
				}
				if ((args[0].equalsIgnoreCase("Ghost"))&&(sender.hasPermission("CharonsFerry.Ghost"))) {
					takeLife=new Ghost(player, target);
					return true;
				}
				if ((args[0].equalsIgnoreCase("Haunt"))&&(sender.hasPermission("CharonsFerry.Haunt"))) {
					sender.sendMessage(ChatColor.RED+"You cannot haunt yourself.");
					return true;
				}
					if ((args[0].equalsIgnoreCase("Boon"))&&(sender.hasPermission("CharonsFerry.Boon"))) {

						return true;
				}else{
					sender.sendMessage(ChatColor.RED+"Invalid command or you lack approriate permission: availiable types:");
					sender.sendMessage(ChatColor.RED+"Revive,Haunt,Ghost,Boon");
				}
				break;
			}
			case 2: {
				// assign args[1] as player
				if ((args[0].equalsIgnoreCase("Revive"))&&(sender.hasPermission("CharonsFerry.Revive"))&&((config.getBoolean("player." + player2 + ".alive") == false))) {
					grantLife=new Ressurection(player2, target2);
					return true;
				}
				if ((args[0].equalsIgnoreCase("Ghost"))&&(sender.hasPermission("CharonsFerry.Ghost"))&&((config.getBoolean("player." + player2 + ".alive") == false))) {
					takeLife=new Ghost(player2, target2);
					return true;
				}
				if ((args[0].equalsIgnoreCase("Haunt"))&&(sender.hasPermission("CharonsFerry.haunt"))&&((config.getBoolean("player." + player2 + ".alive") == false))) {
					stalkLife=new Haunt(player2, target2);
					return true;
					}else {
					sender.sendMessage(ChatColor.RED+"Invalid command, target, or you lack approriate permission: availiable types:");
					sender.sendMessage(ChatColor.RED+"Revive,Haunt,Ghost");
				}
				break;
			}
			default:
				sender.sendMessage(ChatColor.RED+"Too many arguments!");
			}
		}
		return false;
	}
}