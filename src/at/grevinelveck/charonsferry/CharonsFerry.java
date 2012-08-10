package at.grevinelveck.charonsferry;

import java.util.logging.Logger;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import at.grevinelveck.charonsferry.functions.Rebirth;


public class CharonsFerry extends JavaPlugin {
	public final Logger logger = Logger.getLogger("Minecraft");
	private Events checkEvents;
	private DivineCommands dCommands;
	private Rebirth giasLife;
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
		giasLife =new Rebirth();
		getServer().getPluginManager().registerEvents(checkEvents, this);
		getCommand("CharonsFerry").setExecutor(dCommands);
		getCommand("CF").setExecutor(dCommands);
		getCommand("ReviveAll").setExecutor(giasLife);
		
        }
    }

