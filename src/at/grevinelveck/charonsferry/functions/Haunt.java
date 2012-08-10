package at.grevinelveck.charonsferry.functions;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import at.grevinelveck.charonsferry.CharonsFerry;

public class Haunt {
	FileConfiguration config = CharonsFerry.plugin.getConfig();
	public Haunt(String player2, Player target2) {

	}
	public void deadTP(String player2, String player, Player target2, Player target) {
		
		target.teleport(target2);
		target2.getWorld().createExplosion(target2.getLocation(), 0);
		if (!target.hasPermission("CharonsFerry.Poltergeist")){
		target2.sendMessage(ChatColor.RED+"You are being haunted by"+player);
		}
		
	}
}