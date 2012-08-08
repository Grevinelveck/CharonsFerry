package at.grevinelveck.herosdeath;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class HerosDeath extends JavaPlugin {
	public final Logger logger = Logger.getLogger("Minecraft");
	private Events checkEvents;
	private DivineCommands dCommands;

public void loadConfiguration() {
    getConfig().options().copyDefaults(true);
    saveConfig();

}



	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " is offline");
	}

	@Override
	public void onEnable() {
		loadConfiguration();
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " is online");
		checkEvents = new Events();
		dCommands =new DivineCommands();
		getServer().getPluginManager().registerEvents(checkEvents, this);
		getCommand("Revive").setExecutor(dCommands);
		getCommand("Ghost").setExecutor(dCommands);
		getCommand("Haunt").setExecutor(dCommands);
		
        }
    }

