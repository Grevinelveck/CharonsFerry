package at.grevinelveck.herosdeath;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class HerosDeath extends JavaPlugin {
	public List<String> names;
	public List<String> life;
	public final Logger logger = Logger.getLogger("Minecraft");
	private Events Events;
	private DivineCommands DivineCommands;

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
		Events = new Events();
		DivineCommands =new DivineCommands();
		getServer().getPluginManager().registerEvents(Events, this);
		getCommand("Revive").setExecutor(DivineCommands);
		getCommand("Ghost").setExecutor(DivineCommands);
		getCommand("Haunt").setExecutor(DivineCommands);
		
        }
    }

