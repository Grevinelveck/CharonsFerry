package at.grevinelveck.herosdeath;

import java.awt.List;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class HerosDeath extends JavaPlugin {
	Logger logger = this.getLogger();
	private Events Events;
	private DivineCommands DivineCommands;
	List names = (List) this.getConfig().getStringList("names");

	@Override
	public void onDisable() {
		System.out.println("Disabling HerosDeath");
	}

	@Override
	public void onEnable() {
		System.out.println("Enabling HerosDeath");
		Events = new Events();
		DivineCommands =new DivineCommands();
		getServer().getPluginManager().registerEvents(Events, this);
		getCommand("Revive").setExecutor(DivineCommands);

        }
    }

