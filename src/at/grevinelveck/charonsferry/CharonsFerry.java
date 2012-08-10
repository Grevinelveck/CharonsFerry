package at.grevinelveck.charonsferry;

import java.util.logging.Logger;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;


public class CharonsFerry extends JavaPlugin {
	public final Logger logger = Logger.getLogger("Minecraft");
	private Events checkEvents;
	private DivineCommands dCommands;
	public static CharonsFerry plugin;

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
		plugin=this;
		loadConfiguration();
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " is online");
		checkEvents = new Events();
		dCommands =new DivineCommands();
		getServer().getPluginManager().registerEvents(checkEvents, this);
		getCommand("Ferry").setExecutor(dCommands);
		getCommand("CF").setExecutor(dCommands);
		
        }
    }

