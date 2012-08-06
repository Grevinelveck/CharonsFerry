package at.grevinelveck.herosdeath;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class HerosDeath extends JavaPlugin {
	Logger logger = this.getLogger();
	private Events Events;
	private DivineCommands DivineCommands;
	private FileConfiguration customConfig = null;
    private File walkingDead = null;

	@Override
	public void onDisable() {
		System.out.println("Im off ter buy more booze!");
	}

	@Override
	public void onEnable() {
		System.out.println("Lets get drinkin");
		Events = new Events();
		DivineCommands =new DivineCommands();
		getServer().getPluginManager().registerEvents(Events, this);
		getCommand("Revive").setExecutor(DivineCommands);
		getCommand("Ghost").setExecutor(DivineCommands);
		getCommand("Haunt").setExecutor(DivineCommands);
	}
	public void reloadCustomConfig() {
        if (walkingDead == null) {
        walkingDead = new File(getDataFolder(), "customConfig.yml");
        }
        customConfig = YamlConfiguration.loadConfiguration(walkingDead);
     
        // Look for defaults in the jar
        InputStream defConfigStream = this.getResource("customConfig.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            customConfig.setDefaults(defConfig);
        }
    }
	public FileConfiguration getCustomConfig() {
        if (customConfig == null) {
            this.reloadCustomConfig();
        }
        return customConfig;
    }
	public void saveCustomConfig() {
        if (customConfig == null || walkingDead == null) {
        return;
        }
        try {
            getCustomConfig().save(walkingDead);
        } catch (IOException ex) {
            this.getLogger().log(Level.SEVERE, "Could not save config to " + walkingDead, ex);
        }
    }

}
