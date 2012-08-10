package at.grevinelveck.charonsferry.functions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import at.grevinelveck.charonsferry.CharonsFerry;

public class Ghost{
	FileConfiguration config = CharonsFerry.plugin.getConfig();
	public Ghost(String player2, Player target2) {

	}
	public void death(String player,Player target){
	config.set("player." + player + ".alive", false);
	config.set("player." + player + ".revive", false);
	CharonsFerry.plugin.saveConfig();
	for (Player players : Bukkit.getOnlinePlayers())
    {
    players.hidePlayer(target);
    }
	target.sendMessage(ChatColor.RED+"You have been Ghosted");
	}
}