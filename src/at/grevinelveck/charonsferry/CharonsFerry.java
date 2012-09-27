package at.grevinelveck.charonsferry;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.PersistenceException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.avaje.ebean.EbeanServer;

import at.grevinelveck.charonsferry.functions.PlayerDatabase;
import at.grevinelveck.charonsferry.functions.Rebirth;

public class CharonsFerry extends JavaPlugin {
	EbeanServer database;
	public final Logger logger = Logger.getLogger("Minecraft");
	private Events checkEvents;
	private DivineCommands dCommands;
	private Rebirth giasLife;
	public static CharonsFerry plugin;
	long repeat;
	long tempRepeat;

	public void loadConfiguration() {
		getConfig().options().copyDefaults(true);
		saveConfig();

	}

	private void setupDatabase() {
		try {
			database.find(PlayerDatabase.class).findRowCount();
		} catch (PersistenceException ex) {
			// ex.printStackTrace();
			System.out
					.println("Installing database for "
							+ getDescription().getName()
							+ " due to first time use.");
			installDDL();
		}
	}
		
		@Override
		public List<Class<?>> getDatabaseClasses() {
			List<Class<?>> list = new ArrayList<Class<?>>();
			list.add(PlayerDatabase.class);
			return list;
		}
	
	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " is offline");
	}

	@Override
	public void onEnable() {
		database=getDatabase();
		plugin = this;
		loadConfiguration();
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " is online");
		checkEvents = new Events();
		dCommands = new DivineCommands();
		giasLife = new Rebirth();
		getServer().getPluginManager().registerEvents(checkEvents, this);
		getCommand("CharonsFerry").setExecutor(dCommands);
		getCommand("CF").setExecutor(dCommands);
		getCommand("ReviveAll").setExecutor(giasLife);
		if (!getConfig().contains("Minutes")) {
			getConfig().addDefault("Minutes", 3);
		}
		tempRepeat = plugin.getConfig().getInt("Minutes");
		repeat = tempRepeat * 1200;
//replace with per person autorevive
		getServer().getScheduler().scheduleAsyncRepeatingTask(plugin,
				new Runnable() {
					public void run() {//getting fancy with those classes serious
						Bukkit.getServer().dispatchCommand(
								Bukkit.getConsoleSender(), "reviveall");
					}
				}, 60L, repeat);

	}
}
