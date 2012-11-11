package at.grevinelveck.charonsferry;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import at.grevinelveck.charonsferry.functions.AutoRevive;
import at.grevinelveck.charonsferry.functions.ReviveAll;

public class CharonsFerry extends JavaPlugin {
	public final Logger logger = Logger.getLogger("Minecraft");
	private Events checkEvents;
	private DivineCommands dCommands;
	private ReviveAll giasLife;
	private AutoRevive autoLife;
	public static CharonsFerry plugin;
	long repeat;
	long tempRepeat;

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
		plugin = this;
		loadConfiguration();
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " is online");
		checkEvents = new Events();
		dCommands = new DivineCommands();
		giasLife = new ReviveAll();
		autoLife=new AutoRevive();
		new AutoRevive();

		getServer().getPluginManager().registerEvents(checkEvents, this);
		getCommand("CharonsFerry").setExecutor(dCommands);
		getCommand("CF").setExecutor(dCommands);
		getCommand("ReviveAll").setExecutor(giasLife);
		if (!getConfig().contains("Minutes")) {
			getConfig().addDefault("Minutes", 3);
		}
		// replace with per person autorevive
		getServer().getScheduler().scheduleAsyncRepeatingTask(plugin,
				new Runnable() {
					public void run() {
						autoLife.autoRevive();
					}
				}, 60L, 1200L);

	}

}
