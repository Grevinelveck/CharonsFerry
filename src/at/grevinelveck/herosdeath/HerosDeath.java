package at.grevinelveck.herosdeath;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class HerosDeath extends JavaPlugin {
	Logger logger = this.getLogger();

	@Override
	public void onDisable() {
		System.out.println("Im off ter buy more booze!");
	}

	@Override
	public void onEnable() {
		System.out.println("Lets get drinkin");

	}

}
